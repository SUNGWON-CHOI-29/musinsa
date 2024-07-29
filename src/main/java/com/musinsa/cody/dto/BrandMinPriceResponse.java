package com.musinsa.cody.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.cody.common.util.FormatUtil;
import com.musinsa.cody.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BrandMinPriceResponse {

    @JsonProperty("최저가")
    private BrandMinPrice brandMinPrice;

    @Builder
    @Getter
    public static class BrandMinPrice {
        @JsonProperty("브랜드")
        private String brandName;
        @JsonProperty("카테고리")
        private List<ProductDto> categories;
        @JsonProperty("총액")
        private String total;
    }

    @Getter
    public static class ProductDto {

        public ProductDto(String categoryName, String price) {
            this.categoryName = categoryName;
            this.price = price;
        }

        @JsonProperty("카테고리")
        private String categoryName;
        @JsonProperty("가격")
        private String price;

        public static ProductDto fromEntity(Product product) {
            return new ProductDto(
                    product.getCategory().getName(),
                    FormatUtil.convertPrice(product.getPrice())
            );
        }
    }
}

