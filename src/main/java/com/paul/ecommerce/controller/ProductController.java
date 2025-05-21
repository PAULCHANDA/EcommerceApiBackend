package com.paul.ecommerce.controller;

import com.paul.ecommerce.model.product.ProductDTO;
import com.paul.ecommerce.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "createProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO request) {
        try {
            productService.createProduct(request);
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
