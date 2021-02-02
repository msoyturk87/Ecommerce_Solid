package com.cybertek.entity;

import com.cybertek.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
// no need @Where because no delete option
public class Order extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus=OrderStatus.IN_PROGRESS; // default value of orderStatus

    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private ShipBill shipping;

    @ManyToOne
    @JoinColumn(name = "billing_id")
    private ShipBill billing;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
}
