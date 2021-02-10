package com.cybertek.implementation;

import com.cybertek.dto.OrderDTO;
import com.cybertek.dto.OrderItemDTO;
import com.cybertek.entity.Order;
import com.cybertek.entity.OrderItem;
import com.cybertek.enums.OrderStatus;
import com.cybertek.entity.User;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.OrderRepository;
import com.cybertek.service.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final MapperUtil mapperUtil;

    public OrderServiceImpl(OrderRepository orderRepository, MapperUtil mapperUtil) {
        this.orderRepository = orderRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public Order create(OrderDTO orderDTO) {

        // TODO need to understand logic

        Order convertedOrder = mapperUtil.convert(orderDTO, new Order());

        return null;
    }

    @Override
    public void update(Order order) {

        // TODO need to understand logic
    }

    @Override
    public OrderDTO readById(Long id) throws EcommerceException {

        Order foundedOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EcommerceException("This order does not exist"));

        return mapperUtil.convert(foundedOrder,new OrderDTO());    }


    @Override
    public List<OrderDTO> readAll() {
        List<Order> list = orderRepository.findAll(Sort.by("orderStatus"));
        return list.stream().map(obj -> mapperUtil.convert(obj, new OrderDTO())).collect(Collectors.toList());    }

    @Override
    public List<Order> readByUserAndStatus(User user, OrderStatus status) {

        return orderRepository.findByUserAndStatus(user, status);

    }
}


