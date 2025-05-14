package com.hoang.ecommerce.service;

import com.hoang.ecommerce.model.OrderItem;
import com.hoang.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImplementation  implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

//    public OrderItemServiceImplementation(OrderItemRepository orderItemRepository){
//        this.orderItemRepository = orderItemRepository;
//    }
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);
    }
}
