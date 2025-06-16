package com.aliyev.yerassyl.facade.impl;

import com.aliyev.yerassyl.facade.OrderFacade;
import com.aliyev.yerassyl.mapper.OrderMapper;
import com.aliyev.yerassyl.model.dto.OrderDTO;
import com.aliyev.yerassyl.model.entity.Order;
import com.aliyev.yerassyl.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class OrderFacadeImpl implements OrderFacade {
    final OrderService orderService;
    final OrderMapper orderMapper;

    public OrderFacadeImpl(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO dto) {
        Order saved = orderService.createOrder(dto);
        log.debug("Create order on Facade layer, id={}", saved.getId());
        return orderMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public Optional<OrderDTO> getOrder(Long id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        Optional<OrderDTO> dtoOptional = orderOptional.map(orderMapper::toDTO);

        log.debug("Get order on Facade layer with ID: {}", id);

        return dtoOptional;
    }

    @Override
    @Transactional
    public List<OrderDTO> getUserOrders(Long userId) {
        return orderService.getOrdersByUserId(userId).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO dto) {
        Order updated = orderService.updateOrder(id, dto);
        log.debug("Update order on Facade layer, id={}", updated.getId());
        return orderMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        orderService.deleteOrder(id);
        log.debug("Deleted order on Facade layer with ID: {}", id);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllOrders() {
        orderService.deleteAllOrders();
    }
}
