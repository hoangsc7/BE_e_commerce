package com.hoang.ecommerce.controller;

import com.hoang.ecommerce.exception.OrderException;
import com.hoang.ecommerce.model.Order;
import com.hoang.ecommerce.service.OrderService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>>getAllOrderHandler(){
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
    }
    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> ConfirmOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)
            throws OrderException {
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> ShippedOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)
            throws OrderException {
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> DeliverOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)
            throws OrderException {
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> CancelOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)
            throws OrderException {
        Order order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @DeleteMapping ("/{orderId}/delete")
    public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws OrderException {
        orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse();
        res.setDescription("Deleted");
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
