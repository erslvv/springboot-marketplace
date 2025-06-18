package com.aliyev.yerassyl.controllers;

import com.aliyev.yerassyl.facade.OrderFacade;
import com.aliyev.yerassyl.model.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "Ручки для работы с заказами")
public class OrderController {

    OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @Operation(summary = "Создать ордер", description = "Создает ордер")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or #dto.userId == authentication.principal.id")
    public ResponseEntity<OrderDTO> createOrder( @P("dto")@RequestBody OrderDTO dto) {
        OrderDTO result = orderFacade.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Получить ордер по ID", description = "Возвращает ордер по ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        return orderFacade.getOrder(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Получить ордер юзера по ID", description = "Возвращает ордер юзера по ID")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId) {
        log.debug("Request to get user orders from Controller: {}", userId);
        return ResponseEntity.ok(orderFacade.getUserOrders(userId));
    }

    @Operation(summary = "Обновить ордер по ID", description = "Обновляет ордер по ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #dto.userId == authentication.principal.id")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id,
                                                @P("dto") @RequestBody OrderDTO dto) {
        OrderDTO result = orderFacade.updateOrder(id, dto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Удалить ордер по ID", description = "Удаляет ордер по ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.debug("Request to delete order from Controller with Order ID: {}", id);
        orderFacade.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить все ордеры", description = "Возвращает все ордеры")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAll() {
        List<OrderDTO> orders = orderFacade.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Удалить все ордеры", description = "Удаляет все ордеры")
    @DeleteMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAll() {
        orderFacade.deleteAllOrders();
        return ResponseEntity.noContent().build();
    }
}