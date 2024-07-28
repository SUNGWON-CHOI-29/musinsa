package com.musinsa.cody.dto;

import com.musinsa.cody.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductListResponse {

    private List<ProductDto> productDtoList;

    @Getter
    public static class ProductDto {

        public ProductDto(Long id, String categoryName, String brandName, Long price, boolean isDeleted) {
            this.id = id;
            this.categoryName = categoryName;
            this.brandName = brandName;
            this.price = price;
            this.isDeleted = isDeleted;
        }

        private Long id;
        private String categoryName;
        private String brandName;
        private Long price;
        private boolean isDeleted;

        public static ProductDto fromEntity(Product product) {
            return new ProductDto(
                    product.getId(),
                    product.getCategory().getName(),
                    product.getBrand().getName(),
                    product.getPrice(),
                    product.isDeleted()
            );
        }
    }

}
