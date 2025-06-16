package com.aliyev.yerassyl.service.impl;

import com.aliyev.yerassyl.mapper.OrderMapper;
import com.aliyev.yerassyl.model.dto.OrderDTO;
import com.aliyev.yerassyl.model.entity.*;
import com.aliyev.yerassyl.repository.OrderRepository;
import com.aliyev.yerassyl.repository.ProductRepository;
import com.aliyev.yerassyl.repository.UserRepository;
import com.aliyev.yerassyl.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;
    final UserRepository userRepository;
    final ProductRepository productRepository;
    final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public Order createOrder(OrderDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id `" + dto.getUserId() + "`"
                ));

        List<Long> wanted = dto.getItems().stream()
                .map(i -> i.productId)
                .toList();
        List<Product> products = productRepository.findAllById(wanted);
        if (products.size() != wanted.size()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "One or more products not found: " + wanted
            );
        }

        Order order = orderMapper.toEntity(dto, user, products);
        Order savedOrder = orderRepository.save(order);
        log.debug("Create order by Id {}", savedOrder.getId());
        return savedOrder;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        Optional<Order> getOrder =  orderRepository.findById(id);
        log.debug("Get order by Id {}", id);
        return getOrder;
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> getOrders = orderRepository.findByUserId(userId);
        log.debug("Get all orders");
        return getOrders;
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO dto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id `" + id + "`"
                ));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id `" + dto.getUserId() + "`"
                ));

        List<Long> wanted = dto.getItems().stream()
                .map(OrderDTO.ItemDTO::getProductId)
                .toList();
        List<Product> products = productRepository.findAllById(wanted);

        if (products.size() != wanted.size()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "One or more products not found: " + wanted
            );
        }

        existingOrder.setUser(user);
        existingOrder.getItems().clear();

        for (OrderDTO.ItemDTO itemDTO : dto.getItems()) {
            Product product = products.stream()
                    .filter(p -> Objects.equals(p.getId(), itemDTO.getProductId()))
                    .findFirst()
                    .orElseThrow();

            OrderItem item = new OrderItem();
            item.setOrder(existingOrder);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());

            existingOrder.getItems().add(item);
        }

        Order savedOrder = orderRepository.save(existingOrder);
        log.debug("Order updated by ID: {}", savedOrder.getId());
        return savedOrder;
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }



    @Transactional
    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
        log.debug("Order is deleted by Id {}", id);

    }
}