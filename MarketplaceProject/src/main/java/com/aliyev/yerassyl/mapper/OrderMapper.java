package com.aliyev.yerassyl.mapper;

import com.aliyev.yerassyl.dto.OrderDTO;
import com.aliyev.yerassyl.model.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Order toEntity(OrderDTO dto, User user, List<Product> products) {
        Order order = new Order();
        order.setUser(user);

        List<OrderItem> items = new ArrayList<>();
        for (OrderDTO.ItemDTO itemDTO : dto.getItems()) {
            Product product = products.stream()
                    .filter(p -> Objects.equals(p.getId(), itemDTO.productId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDTO.productId));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity);

            OrderItemKey key = new OrderItemKey();
            key.setOrderId(null);
            key.setProductId(product.getId());

            item.setId(key);
            items.add(item);
        }



        order.setItems(items);
        return order;
    }
    public List<OrderItem> toOrderItems(List<OrderDTO.ItemDTO> itemDTOs, Order order, List<Product> products) {
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        return itemDTOs.stream().map(dto -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(productMap.get(dto.productId));
            item.setQuantity(dto.quantity);

            OrderItemKey key = new OrderItemKey();
            key.setOrderId(order.getId());
            key.setProductId(dto.productId);
            item.setId(key);

            return item;
        }).collect(Collectors.toCollection(ArrayList::new)); // ✅ возвращает изменяемый список
    }


}