package com.sparta.msa_exam.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private String name;
    private List<Long> productIds;

    @Getter
    @Setter
    public static class AddProductRequest{
        private Long productId;
    }
}
