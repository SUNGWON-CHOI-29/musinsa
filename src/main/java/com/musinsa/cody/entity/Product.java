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
public class Product extends Common {

    @Builder
    public Product(Brand brand, Category category, Long price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.isDeleted = false;
    }

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean isDeleted;

    public void changeInfo(Brand newBrand, Category newCategory, Long newPrice) {
        this.brand = newBrand;
        this.category = newCategory;
        this.price = newPrice;
    }

    public void changeIsDeleted(boolean newIsDeleted) {
        this.isDeleted = newIsDeleted;
    }
}
