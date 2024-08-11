package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, HttpServletResponse response) {
        response.addHeader("Server-Port", serverPort);
        return productService.createProduct(requestDto);

    }

    @GetMapping("")
    public List<ProductResponseDto> getProducts(HttpServletResponse response) {
        response.addHeader("Server-Port", serverPort);
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Void> checkProductExists(@PathVariable Long productId) {
        boolean exists = productService.productExists(productId);

        if (exists) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
