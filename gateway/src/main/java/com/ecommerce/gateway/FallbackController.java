package com.ecommerce.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	// Target of forward:/fallback/products when the ecomBreaker circuit is open
	@RequestMapping("/fallback/products")
	public ResponseEntity<String> productsFallback() {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Product service is unavailable right now, please try again later");
	}
}
