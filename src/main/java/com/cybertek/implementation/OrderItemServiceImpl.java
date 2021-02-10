package com.cybertek.implementation;

import com.cybertek.dto.CurrencyDTO;
import com.cybertek.dto.CustomOrderItemDTO;
import com.cybertek.dto.OrderDTO;
import com.cybertek.dto.OrderItemDTO;
import com.cybertek.entity.Currency;
import com.cybertek.entity.Order;
import com.cybertek.entity.OrderItem;
import com.cybertek.entity.User;
import com.cybertek.enums.OrderStatus;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;

import com.cybertek.repository.OrderItemRepository;
import com.cybertek.service.OrderItemService;
import com.cybertek.service.OrderService;
import com.cybertek.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final UserService userService;
    private final MapperUtil mapperUtil;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderService orderService, UserService userService, MapperUtil mapperUtil) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public OrderItem create(OrderItemDTO orderItemDTO) {

        // TODO get the current user from securityContextholder
        // instead of "orderItem.getOrder().getUser()" add current user

        OrderItem convertedOrderItem = mapperUtil.convert(orderItemDTO, new OrderItem());
        List<Order> orders = orderService.readByUserAndStatus(convertedOrderItem.getOrder().getUser(), OrderStatus.IN_PROGRESS);

        if (orders.size() > 0) {
            Order currentOrder = orders.get(0);
            convertedOrderItem.setOrder(currentOrder); // cascade will know that
        }

        //TODO get the current user froum securityContextholder
        // instedad of "orderItem.getOrder().getUser()" add current user
        Optional<OrderItem> foundedItem = orderItemRepository.findAllByProductIdAndOrderUserIdAndOrderStatus(convertedOrderItem.getProduct().getId(),
                convertedOrderItem.getOrder().getUser().getId(),
                OrderStatus.IN_PROGRESS);

        if (foundedItem.isPresent()) {

            foundedItem.get().setPrice(convertedOrderItem.getPrice());
            foundedItem.get().setQuantity(convertedOrderItem.getQuantity());
            return orderItemRepository.save(foundedItem.get());
        }
        return orderItemRepository.save(convertedOrderItem);
    }

    @Override
    public List<OrderItemDTO> buildOrderItems(CustomOrderItemDTO orderItemsDTO) throws EcommerceException {

        User currentUser = userService.readByEmail("admin@admin.com");
        List<Order> orders = orderService.readByUserAndStatus(currentUser, OrderStatus.IN_PROGRESS);

        Order currentOrder = orders.get(0);
        currentOrder.setBilling(orderItemsDTO.getBilling());
        currentOrder.setShipping(orderItemsDTO.getShipping());
        currentOrder.setOrderStatus(OrderStatus.APPROVED);

        List<OrderItem> orderItems =
                orderItemsDTO.getOrderItem().stream().peek(orderItem -> {

                    currentOrder.setTotalPrice(currentOrder.getTotalPrice().add(orderItem.getPrice()));
                    orderItem.setOrderStatus(OrderStatus.APPROVED);
                    orderItem.setOrder(currentOrder);

                }).collect(Collectors.toList());

        return orderItems.stream().map(obj -> mapperUtil.convert(obj, new OrderItemDTO())).collect(Collectors.toList());
    }


    @Override
    public OrderItemDTO readById(Long id) throws EcommerceException {
        OrderItem foundedOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EcommerceException("This orderItem does not exist"));
        return mapperUtil.convert(foundedOrderItem,new OrderItemDTO());    }

    @Override
    public List<OrderItemDTO> readAll() {
        List<OrderItem> list = orderItemRepository.findAll(Sort.by("orderStatus"));
        return list.stream().map(obj -> mapperUtil.convert(obj, new OrderItemDTO())).collect(Collectors.toList());
    }
}


