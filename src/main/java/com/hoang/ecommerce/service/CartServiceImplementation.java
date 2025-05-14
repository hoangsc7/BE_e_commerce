package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.ProductException;
import com.hoang.ecommerce.model.Cart;
import com.hoang.ecommerce.model.CartItem;
import com.hoang.ecommerce.model.Product;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.repository.CartRepository;
import com.hoang.ecommerce.request.AddItemRequest;
import org.springframework.stereotype.Service;

import java.awt.image.ComponentSampleModel;

@Service
public class CartServiceImplementation implements CartService {
    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;



    public CartServiceImplementation(CartRepository cartRepository,CartItemService cartItemService,ProductService productService){
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }


    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart,product,req.getSize(),userId);

        if (isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price = req.getQuantity()*product.getDiscountedPrice();

            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItem().add(createCartItem);
        }
        return "item add to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;
        for (CartItem cartItem :cart.getCartItem()){
            totalPrice= totalPrice + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem((totalItem));
        cart.setDiscount(totalPrice - totalDiscountedPrice);
        return cartRepository.save(cart);
    }
}
