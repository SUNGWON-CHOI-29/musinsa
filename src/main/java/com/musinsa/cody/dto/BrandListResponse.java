package com.musinsa.cody.dto;

import com.musinsa.cody.entity.Brand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BrandListResponse {

    private List<BrandDto> brandDtoList;

    @Getter
    public static class BrandDto {

        public BrandDto(Long id, String name, Long totalPrice, boolean isActive, boolean isDeleted) {
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

        public static BrandDto fromEntity(Brand brand) {
            return new BrandDto(
                    brand.getId(),
                    brand.getName(),
                    brand.getTotalPrice(),
                    brand.isActive(),
                    brand.isDeleted()
            );
        }
    }
}
