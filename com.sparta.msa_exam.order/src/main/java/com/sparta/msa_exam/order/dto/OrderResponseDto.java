package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderMapping;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class OrderResponseDto implements Serializable {
    private Long id;
    private String name;
    private List<OrderMapping> product_ids;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.name = order.getName();
        this.product_ids = order.getProduct_ids();
    }

    @Getter
    @Setter
    @Data
    @AllArgsConstructor
    public static class GetOneOrderResponse implements Serializable {
        private Long orderId;
        private List<Long> productIds;
    }
}
