package com.paul.ecommerce.service;

import com.paul.ecommerce.model.product.ProductDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    private final JdbcTemplate jdbcTemplate;

    public ProductService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProduct(ProductDTO request) throws Exception{
        String sql = "INSERT INTO products(`name`, `description`, `price`, `image`, `stock`, `category_id`)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getImage(),
                request.getStock(),
                request.getCategory_id()
        );
    }
}
