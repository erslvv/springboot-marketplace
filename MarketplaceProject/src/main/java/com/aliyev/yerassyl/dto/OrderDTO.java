package com.aliyev.yerassyl.dto;

import java.util.List;

public class OrderDTO {
    private Long userId;
    private List<ItemDTO> items;

    public static class ItemDTO {
        public Long productId;
        public int quantity;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<ItemDTO> getItems() {
        return items;
    }
    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
