package com.cybertek.dto;

import com.cybertek.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemDTO  {

    private Long id ;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus orderStatus;
    private ProductDTO product;
    private OrderDTO order;
    private String productHistory;



}
