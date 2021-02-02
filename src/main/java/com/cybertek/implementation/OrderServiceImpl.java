package com.cybertek.implementation;

import com.cybertek.enums.OrderStatus;
import com.cybertek.model.Order;
import com.cybertek.model.User;
import com.cybertek.repository.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order order){

        //TODO How can I check order is Exist or Not

        return null;
    }

    public void update(Order order){

        //TODO How can I check order is Exist or Not

    }
    public Order readById(Order order){

        return orderRepository.findById(order.getId()).orElse(null);

    }
    public List<Order> readAll(){

        return orderRepository.findAll(Sort.by("orderDate")); // maybe user_id but not necessary I think

    }

    public List<Order> readByUserAndStatus(User user, OrderStatus status){
        return orderRepository.findByUserAndStatus(user,status);

    }


}
