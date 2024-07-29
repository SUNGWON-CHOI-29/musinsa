package com.musinsa.cody.service;

import com.musinsa.cody.common.constant.CodyErrorResult;
import com.musinsa.cody.common.exception.CodyException;
import com.musinsa.cody.common.util.FormatUtil;
import com.musinsa.cody.dto.BrandMinPriceResponse;
import com.musinsa.cody.dto.CategoryMinPriceResponse;
import com.musinsa.cody.dto.PriceRangeResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.entity.Product;
import com.musinsa.cody.repository.BrandRepository;
import com.musinsa.cody.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodyService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public CategoryMinPriceResponse getCategoryMinPriceProducts() {
        List<Product> allMinProductGroupByCategory = productRepository.findAllMinProductGroupByCategory();

        long total = allMinProductGroupByCategory.stream()
                .mapToLong(Product::getPrice)
                .sum();

        List<CategoryMinPriceResponse.ProductDto> productDtoList = allMinProductGroupByCategory.stream()
                .map(CategoryMinPriceResponse.ProductDto::fromEntity)
                .toList();

        return CategoryMinPriceResponse.builder()
                .productList(productDtoList)
                .totalPrice(FormatUtil.convertPrice(total))
                .build();
    }

    public BrandMinPriceResponse getBrandMinPriceProducts() {

        Brand minPriceBrand = brandRepository.findMinTotalPriceBrand()
                .orElseThrow(() -> new CodyException(CodyErrorResult.MIN_PRICE_BRAND_NOT_FOUND));

        List<Product> allMinProductByBrandAndGroupByCategory = productRepository
                .findAllMinProductByBrandAndGroupByCategory(minPriceBrand.getId());

        long total = allMinProductByBrandAndGroupByCategory.stream()
                .mapToLong(Product::getPrice)
                .sum();

        String brandName = allMinProductByBrandAndGroupByCategory.stream()
                .findAny()
                .get()
                .getBrand()
                .getName();

        List<BrandMinPriceResponse.ProductDto> categoryDtoList = allMinProductByBrandAndGroupByCategory.stream()
                .map(BrandMinPriceResponse.ProductDto::fromEntity)
                .toList();

        BrandMinPriceResponse.BrandMinPrice brandMinPrice = BrandMinPriceResponse.BrandMinPrice.builder()
                .brandName(brandName)
                .categories(categoryDtoList)
                .total(FormatUtil.convertPrice(total))
                .build();

        return BrandMinPriceResponse.builder()
                .brandMinPrice(brandMinPrice)
                .build();
    }

    public PriceRangeResponse getCategoryPriceRangeProducts(Long categoryId) {
        Product byMinPriceProductByCategory = productRepository.findByMinPriceProductByCategory(categoryId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.MIN_PRICE_PRODUCT_NOT_FOUND));

        Product byMaxPriceProductByCategory = productRepository.findByMaxPriceProductByCategory(categoryId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.MAX_PRICE_PRODUCT_NOT_FOUND));

        PriceRangeResponse.ProductDto minProduct = PriceRangeResponse.ProductDto.fromEntity(byMinPriceProductByCategory);
        PriceRangeResponse.ProductDto maxProduct = PriceRangeResponse.ProductDto.fromEntity(byMaxPriceProductByCategory);

        String categoryName = byMinPriceProductByCategory.getCategory().getName();

        return PriceRangeResponse.builder()
                .categoryName(categoryName)
                .minProduct(Arrays.asList(minProduct))
                .maxProduct(Arrays.asList(maxProduct))
                .build();
    }
}
