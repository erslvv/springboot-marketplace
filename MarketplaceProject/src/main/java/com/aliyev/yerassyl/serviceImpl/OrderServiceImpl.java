package com.aliyev.yerassyl.serviceImpl;

import com.aliyev.yerassyl.dto.OrderDTO;
import com.aliyev.yerassyl.model.*;
import com.aliyev.yerassyl.repository.*;
import com.aliyev.yerassyl.service.OrderService;
import com.aliyev.yerassyl.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderMapper orderMapper;

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
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
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

        // Проверяем наличие продуктов
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

        // Обновляем связь пользователя
        existingOrder.setUser(user);

        // Очищаем старые элементы (orphanRemoval удалит их из БД)
        existingOrder.getItems().clear();

        // Добавляем новые элементы
        for (OrderDTO.ItemDTO itemDTO : dto.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(existingOrder);

            Product product = products.stream()
                    .filter(p -> Objects.equals(p.getId(), itemDTO.productId))
                    .findFirst()
                    .orElseThrow();
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity);

            OrderItemKey key = new OrderItemKey();
            key.setOrderId(existingOrder.getId());
            key.setProductId(product.getId());
            item.setId(key);

            existingOrder.getItems().add(item);
        }

        return orderRepository.save(existingOrder);
    }









    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}