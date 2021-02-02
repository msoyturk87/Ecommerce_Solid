package com.cybertek.dto;

import com.cybertek.entity.OrderItem;
import com.cybertek.entity.ShipBill;
import lombok.Data;

import java.util.List;

@Data
public class CustomOrderItemDTO {

    private List<OrderItem> orderItem;
    private ShipBill shipping;
    private ShipBill billing;

}