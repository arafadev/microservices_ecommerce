package com.ecommerce.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    // Temporary fixed price. Replace with the real price from product-service.
    private static final BigDecimal TEMP_UNIT_PRICE = BigDecimal.valueOf(1000);

    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        // Stock is owned by product-service, so it is not checked here yet.
        if (request.getProductId() == null
                || request.getQuantity() == null || request.getQuantity() < 1) {
            return false;
        }

        String productId = String.valueOf(request.getProductId());
        CartItem cartItem = cartItemRepository
                .findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (cartItem != null) {
            // Update the quantity
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            // Create new cart item
            cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(request.getQuantity());
        }

        cartItem.setUnitPrice(TEMP_UNIT_PRICE);
        cartItemRepository.save(cartItem);
        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        return cartItemRepository.deleteByUserIdAndProductId(userId, productId) > 0;
    }

    public List<CartItemResponse> getCart(String userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(CartService::mapToCartItemResponse)
                .toList();
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    private static CartItemResponse mapToCartItemResponse(CartItem item) {
        CartItemResponse response = new CartItemResponse();
        response.setId(item.getId());
        response.setProductId(item.getProductId());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getUnitPrice());
        response.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        response.setCreatedAt(item.getCreatedAt());
        response.setUpdatedAt(item.getUpdatedAt());
        return response;
    }
}
