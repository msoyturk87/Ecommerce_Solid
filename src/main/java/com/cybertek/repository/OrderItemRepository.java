package com.cybertek.repository;

import com.cybertek.enums.OrderStatus;
import com.cybertek.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    Optional<OrderItem> findByOrderId(Long Id);
    Optional<OrderItem> findAllByProductIdAndOrderUserIdAndOrderStatus(Long productId, Long userId, OrderStatus orderStatus);

}
