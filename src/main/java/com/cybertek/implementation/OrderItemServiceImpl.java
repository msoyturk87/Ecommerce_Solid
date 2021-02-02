package com.cybertek.implementation;

import com.cybertek.enums.OrderStatus;
import com.cybertek.model.Order;
import com.cybertek.model.OrderItem;
import com.cybertek.model.User;
import com.cybertek.model.dto.CustomOrderItemDTO;
import com.cybertek.repository.OrderItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl {

    private final OrderItemRepository orderItemRepository;
    private final OrderServiceImpl orderService;
    private final UserServiceImpl userService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderServiceImpl orderService,
                                UserServiceImpl userService) {

        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.userService = userService;
    }


    public OrderItem create(OrderItem orderItem) throws Exception {

        //TODO get the current user froum securityContextholder
        // instedad of "orderItem.getOrder().getUser()" add current user
        List<Order> orders = orderService.readByUserAndStatus(orderItem.getOrder().getUser(), OrderStatus.IN_PROGRESS);

        if(orders.size()>0){
            Order currentOrder=orders.get(0);
            orderItem.setOrder(currentOrder); // cascade will know that
        }

        //TODO get the current user froum securityContextholder
        // instedad of "orderItem.getOrder().getUser()" add current user
        Optional<OrderItem> foundedItem=orderItemRepository.findAllByProductIdAndOrderUserIdAndOrderStatus(orderItem.getProduct().getId(),
                orderItem.getOrder().getUser().getId(),
                OrderStatus.IN_PROGRESS);

        if(foundedItem.isPresent()){

            foundedItem.get().setPrice(orderItem.getPrice());
            foundedItem.get().setQuantity(orderItem.getQuantity());
            return orderItemRepository.save(foundedItem.get());
        }



        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> buildOrderItems(CustomOrderItemDTO orderItemsDTO) throws Exception {

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

        return orderItemRepository.saveAll(orderItems);
    }

    public OrderItem readById(Long id) throws Exception {

        return orderItemRepository.findById(id).orElseThrow(()->new Exception("No orderItem "));
    }

    public List<OrderItem> readAll(){

        return orderItemRepository.findAll(Sort.by("orderStatus"));
    }






}
