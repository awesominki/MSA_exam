package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderMapping;
import com.sparta.msa_exam.order.repository.OrderMappingRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMappingRepository orderMappingRepository;

    @Transactional
    public Order addProductToOrder(Long orderId, Long productId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        try {
            productClient.checkProductExists(productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId);
        }

        OrderMapping orderMapping = OrderMapping.builder()
                .order(order)
                .product_id(productId)
                .build();

        order.addProductMapping(orderMapping);

        orderMappingRepository.save(orderMapping);

        return order;
    }

    @Transactional
    public OrderResponseDto createOrder(String name, List<Long> productIds) {
        Order order = Order.builder()
                .name(name)
                .build();
        for (Long productId : productIds) {
            OrderMapping orderMapping = OrderMapping.builder()
                    .product_id(productId)
                    .build();
            order.addProductMapping(orderMapping);
        }

        Order order_save = orderRepository.save(order);

        return new OrderResponseDto(order_save);
    }

    @Cacheable(value = "orders", key = "#orderId")
    @Transactional(readOnly = true)
    public OrderResponseDto.GetOneOrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        List<Long> productIds = order.getProduct_ids().stream()
                .map(orderMapping -> orderMapping.getProduct_id())
                .collect(Collectors.toList());

        return new OrderResponseDto.GetOneOrderResponse(order.getId(), productIds);
    }
}

