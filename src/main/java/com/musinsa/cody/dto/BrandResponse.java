package com.musinsa.cody.dto;

import com.musinsa.cody.entity.Brand;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponse {

    public BrandResponse(Long id, String name, Long totalPrice, boolean isActive, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.totalPrice = totalPrice;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    private Long id;
    private String name;
    private Long totalPrice;
    private boolean isActive;
    private boolean isDeleted;

    public static BrandResponse fromEntity(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getTotalPrice(),
                brand.isActive(),
                brand.isDeleted()
        );
    }
}
