package com.ecommerce.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request) {
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("productId and a quantity of at least 1 are required");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart(
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @RequestHeader("X-User-ID") String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
