package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.CartItemException;
import com.hoang.ecommerce.exception.UserException;
import com.hoang.ecommerce.model.Cart;
import com.hoang.ecommerce.model.CartItem;
import com.hoang.ecommerce.model.Product;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.request.UpdateCartItemRequest;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
