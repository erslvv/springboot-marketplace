package com.aliyev.yerassyl.mapper;

import com.aliyev.yerassyl.model.dto.OrderDTO;
import com.aliyev.yerassyl.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toEntity(OrderDTO dto, User user, List<Product> products) {
        Order order = new Order();
        order.setUser(user);

        List<OrderItem> items = dto.getItems().stream()
                .map(i -> {
                    Product product = products.stream()
                            .filter(p -> Objects.equals(p.getId(), i.getProductId()))
                            .findFirst()
                            .orElseThrow();

                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setQuantity(i.getQuantity());
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(items);
        return order;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setItems(order.getItems().stream()
                .map(item -> {
                    OrderDTO.ItemDTO i = new OrderDTO.ItemDTO();
                    i.setProductId(item.getProduct().getId());
                    i.setQuantity(item.getQuantity());
                    return i;
                })
                .collect(Collectors.toList()));
        return dto;
    }
}
