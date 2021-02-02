package com.cybertek.service;

import com.cybertek.dto.OrderItemDTO;
import com.cybertek.entity.OrderItem;

import com.cybertek.dto.CustomOrderItemDTO;
import com.cybertek.exception.EcommerceException;

import java.util.List;

public interface OrderItemService {


    public OrderItem create(OrderItemDTO orderItemDTO);
    public List<OrderItemDTO> buildOrderItems(CustomOrderItemDTO orderItemsDTO) ;
    public OrderItemDTO readById(Long id) throws EcommerceException;
    public List<OrderItemDTO> readAll();


}
