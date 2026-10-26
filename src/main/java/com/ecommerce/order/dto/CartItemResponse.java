package com.ecommerce.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long id;
    private String productId;
    private String productName;
    private String imageUrl;
    private Integer quantity;
    private BigDecimal price;       
    private BigDecimal totalPrice;  

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}