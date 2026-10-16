package com.ecommerce.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> fetchAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        mapToProductEntity(product, request);
        return mapToProductResponse(productRepository.save(product));
    }

    public ProductResponse fetchProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponse)
                .orElse(null);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        return productRepository.findById(id)
                .map(product -> {
                    mapToProductEntity(product, request);
                    return mapToProductResponse(productRepository.save(product));
                })
                .orElse(null);
    }

    // حذف فعلي من الداتابيز
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    // Request DTO -> Entity
    private void mapToProductEntity(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
    }

    // Entity -> Response DTO
    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.getActive());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}