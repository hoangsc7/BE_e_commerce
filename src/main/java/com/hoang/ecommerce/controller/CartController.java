package com.hoang.ecommerce.controller;

import com.hoang.ecommerce.exception.ProductException;
import com.hoang.ecommerce.exception.UserException;
import com.hoang.ecommerce.model.Cart;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.request.AddItemRequest;
import com.hoang.ecommerce.service.CartService;
import com.hoang.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Manager",description = "find user cart, add item to cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws UserException {

        User user = userService.findUserProfileById(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart,HttpStatus.OK);

    }
    @PostMapping("/add")
    public ResponseEntity<String> addCartItem(@RequestBody AddItemRequest req, @RequestHeader("Authorization")String jwt) throws UserException, ProductException {

        User user = userService.findUserProfileById(jwt);

        cartService.addCartItem(user.getId(), req);

        String res = "Add to cart success";

        return new ResponseEntity<>(res,HttpStatus.OK);

    }
}
