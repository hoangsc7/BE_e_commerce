package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.OrderException;
import com.hoang.ecommerce.model.Address;
import com.hoang.ecommerce.model.Order;
import com.hoang.ecommerce.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> userOrderHistory(Long userId);
    public Order placeOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order canceledOrder(Long orderId) throws OrderException;
    public List<Order>getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;



}
