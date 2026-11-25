package com.ecommerce.product.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates dynamic config refresh. @RefreshScope makes Spring rebuild this
 * bean when a refresh event arrives, so {@code message} picks up the new value
 * from the config server without restarting the service.
 */
@RestController
@RequestMapping("/api/products/config")
@RefreshScope
public class ConfigDemoController {

    @Value("${app.message:no message configured}")
    private String message;

    @GetMapping("/message")
    public String message() {
        return message;
    }
}
