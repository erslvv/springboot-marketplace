package com.aliyev.yerassyl.facade;

import com.aliyev.yerassyl.model.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderFacade {
    OrderDTO createOrder(OrderDTO dto);

    Optional<OrderDTO> getOrder(Long id);

    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO updateOrder(Long id, OrderDTO dto);

    void deleteOrder(Long id);

    List<OrderDTO> getAllOrders();

    void deleteAllOrders();

}