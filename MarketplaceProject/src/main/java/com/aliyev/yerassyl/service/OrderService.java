package com.aliyev.yerassyl.service;

import com.aliyev.yerassyl.model.dto.OrderDTO;
import com.aliyev.yerassyl.model.entity.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(OrderDTO dto);
    Optional<Order> getOrderById(Long id);
    List<Order> getOrdersByUserId(Long userId);
    Order updateOrder(Long id, OrderDTO dto);
    void deleteOrder(Long id);
    List<Order> getAllOrders();
    void deleteAllOrders();

}