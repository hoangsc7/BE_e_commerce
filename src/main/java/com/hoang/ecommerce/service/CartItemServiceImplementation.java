package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.CartItemException;
import com.hoang.ecommerce.exception.UserException;
import com.hoang.ecommerce.model.Cart;
import com.hoang.ecommerce.model.CartItem;
import com.hoang.ecommerce.model.Product;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.repository.CartItemRepository;
import com.hoang.ecommerce.repository.CartRepository;
import com.hoang.ecommerce.request.UpdateCartItemRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService{
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    public CartItemServiceImplementation(CartItemRepository cartItemRepository,UserService userService,CartRepository cartRepository){
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        CartItem createCartItem = cartItemRepository.save(cartItem);
        return createCartItem;
    }



    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        System.out.println("--------------old quantity:"+item.getQuantity());
        User user = userService.findUserById(item.getUserId());
        System.out.println("--------------new quantity:"+cartItem.getQuantity());

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(cartItem.getQuantity() * item.getProduct().getDiscountedPrice());
        }
        System.out.println("--------------new quantity:"+item.getQuantity());
        return cartItemRepository.save(item);
    }



    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart,product,size,userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());
        User reqUser = userService.findUserById(userId);

        if (user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }else {
            throw new UserException("you can't remove another users item ");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id:"+cartItemId);
    }
}
