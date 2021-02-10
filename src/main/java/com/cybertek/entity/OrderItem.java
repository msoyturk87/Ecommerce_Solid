package com.cybertek.entity;

import com.cybertek.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_items")
@Where(clause = "is_deleted=false")
public class OrderItem extends BaseEntity<Long> {

    private Integer quantity;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productHistory;



}
