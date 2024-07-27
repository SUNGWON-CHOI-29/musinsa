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

        public BrandDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        private Long id;
        private String name;

        public static BrandDto fromEntity(Brand brand) {
            return new BrandDto(brand.getId(), brand.getName());
        }
    }
}
