package com.musinsa.cody.service;

import com.musinsa.cody.dto.BrandMinPriceResponse;
import com.musinsa.cody.dto.CategoryMinPriceResponse;
import com.musinsa.cody.dto.PriceRangeResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.entity.Product;
import com.musinsa.cody.repository.BrandRepository;
import com.musinsa.cody.repository.ProductRepository;
import com.musinsa.cody.util.FormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        Optional<Brand> minPriceBrand = brandRepository.findMinTotalPriceBrand();
        Long brandId = minPriceBrand.get().getId();

        List<Product> allMinProductByBrandAndGroupByCategory = productRepository.findAllMinProductByBrandAndGroupByCategory(brandId);

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
        Optional<Product> byMinPriceProductByCategory = productRepository.findByMinPriceProductByCategory(categoryId);
        Optional<Product> byMaxPriceProductByCategory = productRepository.findByMaxPriceProductByCategory(categoryId);

        Optional<PriceRangeResponse.ProductDto> minProduct = byMinPriceProductByCategory.map(PriceRangeResponse.ProductDto::fromEntity);
        Optional<PriceRangeResponse.ProductDto> maxProduct = byMaxPriceProductByCategory.map(PriceRangeResponse.ProductDto::fromEntity);

        String categoryName = byMinPriceProductByCategory.get().getCategory().getName();

        return PriceRangeResponse.builder()
                .categoryName(categoryName)
                .minProduct(minProduct.stream().toList())
                .maxProduct(maxProduct.stream().toList())
                .build();
    }

}
