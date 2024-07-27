package com.musinsa.cody.dto;

import com.musinsa.cody.entity.Brand;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponse {

    public BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;

    public static BrandResponse fromEntity(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getName());
    }
}
