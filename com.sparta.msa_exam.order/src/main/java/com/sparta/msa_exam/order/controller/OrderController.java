package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto, HttpServletResponse response) {
        response.addHeader("Server-Port", serverPort);
        return orderService.createOrder(orderRequestDto.getName(), orderRequestDto.getProductIds());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> addProductToOrder(
            @PathVariable Long orderId,
            @RequestBody OrderRequestDto.AddProductRequest request,
            HttpServletResponse response) {
        response.addHeader("Server-Port", serverPort);
        Order updatedOrder = orderService.addProductToOrder(orderId, request.getProductId());
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto.GetOneOrderResponse> getOrder(@PathVariable Long orderId, HttpServletResponse response) {
        response.addHeader("Server-Port", serverPort);
        OrderResponseDto.GetOneOrderResponse orderResponseDto = orderService.getOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }
}
