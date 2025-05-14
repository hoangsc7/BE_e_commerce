package com.hoang.ecommerce.controller;

import com.hoang.ecommerce.exception.CartItemException;
import com.hoang.ecommerce.exception.UserException;
import com.hoang.ecommerce.model.CartItem;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.request.UpdateCartItemRequest;
import com.hoang.ecommerce.service.CartItemService;
import com.hoang.ecommerce.service.CartService;
import com.hoang.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_item")
@Tag(name = "Cart Item Manage")
public class CartItemController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

//    @PutMapping("/{cartItemId}")
//    @Operation()

    @PutMapping("/{cartItemId}")
    @Operation(description = "Update cart item quantity")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestBody CartItem cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization")String jwt) throws UserException, CartItemException {
            System.out.println("------------------------request"+cartItem);
            User user = userService.findUserProfileById(jwt);


            CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
            return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);

    }

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove cart_item from cart")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt )
            throws UserException, CartItemException {

        User user = userService.findUserProfileById(jwt);
        Long userId = user.getId();

        cartItemService.removeCartItem(userId, cartItemId);

        ApiResponse res = new ApiResponse();
        res.setDescription("Deleted");
        return new ResponseEntity<>(res,HttpStatus.OK);
    }


}
