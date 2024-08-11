package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long id;
    private String name;
    private Integer supply_price;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.supply_price = product.getSupply_price();
    }

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .supply_price(product.getSupply_price())
                .build();
    }
}
