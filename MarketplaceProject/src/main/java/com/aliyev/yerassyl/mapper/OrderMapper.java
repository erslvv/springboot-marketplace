package com.aliyev.yerassyl.mapper;

import com.aliyev.yerassyl.dto.OrderDTO;
import com.aliyev.yerassyl.model.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}