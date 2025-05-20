package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.OrderException;
import com.hoang.ecommerce.model.*;
import com.hoang.ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {
    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;
    private CartRepository cartRepository;
    private CartService cartService;
    private ProductService productService;


    public OrderServiceImplementation(OrderRepository orderRepository,OrderItemRepository orderItemRepository,
                                      OrderItemService orderItemService,AddressRepository addressRepository,
                                      UserRepository userRepository,CartRepository cartRepository, CartService cartService,
                                      ProductService productService){
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;

    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        // shippingAddress.setUser(user);
        // Address address = addressRepository.save(shippingAddress);
        // user.getAddress().add(address);
        // userRepository.save(user);
        System.out.println("----------------------------Shipping address: " + shippingAddress);
        if (shippingAddress.getId() == null) {
            shippingAddress.setUser(user);
            Address address = addressRepository.save(shippingAddress);
            user.getAddress().add(address);
            userRepository.save(user);
            shippingAddress = address; 
        } else {
            shippingAddress = addressRepository.findById(shippingAddress.getId()).orElseThrow(() -> new RuntimeException("Address not found"));
            System.out.println("+++++++++Shipping address found: " + shippingAddress);
        }

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item: cart.getCartItem()){
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem createOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createOrderItem);
        }

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(shippingAddress);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreateAt(LocalDateTime.now());
        int discountedPrice = (int) (cart.getTotalPrice() - cart.getDiscount());
        createdOrder.setTotalDiscountedPrice(discountedPrice);

        Order saveOrder = orderRepository.save(createdOrder);


        for (OrderItem item:orderItems){
            item.setOrder(saveOrder);
            orderItemRepository.save(item);
        }


        return saveOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);

        if (opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("order not exit with id"+ orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        List<Order> orders = orderRepository.getUsersOrder(userId);
        return orders;
    }

    @Override
    public Order placeOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }
}
