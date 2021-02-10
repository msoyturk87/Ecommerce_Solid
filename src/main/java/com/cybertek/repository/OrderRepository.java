package com.cybertek.repository;

import com.cybertek.dto.OrderDTO;
import com.cybertek.enums.OrderStatus;
import com.cybertek.entity.Order;
import com.cybertek.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserAndStatus(User user, OrderStatus status);
    Optional<OrderDTO> readById(Long id);
}
