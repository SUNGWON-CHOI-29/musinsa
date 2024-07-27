package com.musinsa.cody.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Brand extends Common{

    @Builder
    public Brand(String name, Long totalPrice, boolean isActive, boolean isDeleted) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean isDeleted;
}
