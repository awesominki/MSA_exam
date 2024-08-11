package com.sparta.msa_exam.product.entity;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer supply_price;

    public Product(ProductRequestDto productRequestDto) {
        this.name = productRequestDto.getName();
        this.supply_price = productRequestDto.getSupply_price();
    }
}
