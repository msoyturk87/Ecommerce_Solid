package com.cybertek.service;

import com.cybertek.dto.OrderDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Order;
import com.cybertek.entity.User;
import com.cybertek.enums.OrderStatus;

import com.cybertek.repository.OrderRepository;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface OrderService {


     Order create(OrderDTO orderDTO);
     void update(Order order);
     Order readById(OrderDTO orderDTO);
     List<OrderDTO> readAll();
     List<Order> readByUserAndStatus(User user, OrderStatus status);


}
