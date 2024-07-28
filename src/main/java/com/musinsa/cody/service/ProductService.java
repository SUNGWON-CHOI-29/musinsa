package com.musinsa.cody.service;

import com.musinsa.cody.common.constant.CodyErrorResult;
import com.musinsa.cody.common.exception.CodyException;
import com.musinsa.cody.dto.ProductListResponse;
import com.musinsa.cody.dto.ProductRequest;
import com.musinsa.cody.dto.ProductResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.entity.Category;
import com.musinsa.cody.entity.Product;
import com.musinsa.cody.repository.BrandRepository;
import com.musinsa.cody.repository.CategoryRepository;
import com.musinsa.cody.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new CodyException(CodyErrorResult.BRAND_NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CodyException(CodyErrorResult.CATEGORY_NOT_FOUND));

        Product product = Product.builder()
                .price(request.getPrice())
                .brand(brand)
                .category(category)
                .isDeleted(false)
                .build();

        Product saveProduct = productRepository.save(product);
        updateBrandInfoWithLock(brand.getId());
        return ProductResponse.fromEntity(saveProduct);
    }

    public ProductListResponse getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductListResponse.ProductDto> productDtoList = products.stream()
                .map(ProductListResponse.ProductDto::fromEntity)
                .toList();
        return ProductListResponse.builder()
                .productDtoList(productDtoList)
                .build();
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findByIdWithLock(productId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.PRODUCT_NOT_FOUND));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new CodyException(CodyErrorResult.BRAND_NOT_FOUND));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CodyException(CodyErrorResult.CATEGORY_NOT_FOUND));

        product.changeInfo(brand, category, request.getPrice());
        updateBrandInfoWithLock(brand.getId());
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findByIdWithLock(productId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.PRODUCT_NOT_FOUND));

        product.changeIsDeleted(true);
        updateBrandInfoWithLock(product.getBrand().getId());
    }

    private void updateBrandInfoWithLock(Long brandId) {
        Brand brand = brandRepository.findByIdWithLock(brandId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.BRAND_NOT_FOUND));

        List<Long> productCategory = productRepository.findProductCategoryByBrandId(brand.getId());
        List<Category> categories = categoryRepository.findAll();
        boolean isActive = (productCategory.size() == categories.size());
        brand.changeIsActive(isActive);

        if (isActive) {
            List<Product> minProductByBrandId = productRepository.findAllMinProductByBrandAndGroupByCategory(brand.getId());
            long minPrice = minProductByBrandId.stream()
                    .mapToLong(Product::getPrice)
                    .sum();
            brand.changeTotalPirce(minPrice);
        }
    }
}
