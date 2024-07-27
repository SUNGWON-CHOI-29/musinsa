package com.musinsa.cody.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends Common{

    @Builder
    public Product(Brand brand, Category category, Long price, boolean isDeleted) {
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.isDeleted = isDeleted;
    }

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean isDeleted;
}
