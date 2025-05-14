package com.hoang.ecommerce.controller;

import com.hoang.ecommerce.exception.OrderException;
import com.hoang.ecommerce.exception.UserException;
import com.hoang.ecommerce.model.Address;
import com.hoang.ecommerce.model.Order;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.service.OrderService;
import com.hoang.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,@RequestHeader("Authorization")String jwt) throws UserException {
        User user = userService.findUserProfileById(jwt);
        Order order = orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileById(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id")Long orderId,@RequestHeader("Authorization")String jwt) throws UserException, OrderException {
        User user = userService.findUserProfileById(jwt);
        Order order  = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}
