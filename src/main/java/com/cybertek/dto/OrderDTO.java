package com.cybertek.dto;

import com.cybertek.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private Long id ;
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private UserDTO user;
    private ShipBillDTO shipping;
    private ShipBillDTO billing;
    private BigDecimal totalPrice;
}
