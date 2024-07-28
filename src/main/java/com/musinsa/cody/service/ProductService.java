package com.musinsa.cody.service;

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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductRequest request) {

        Optional<Brand> brand = brandRepository.findById(request.getBrandId());
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());

        Product product = Product.builder()
                .price(request.getPrice())
                .brand(brand.get())
                .category(category.get())
                .isDeleted(false)
                .build();

        Product saveProduct = productRepository.save(product);
        updateBrandInfo(brand.get().getId());
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

    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        int result = productRepository.updateById(productId, request);
        Optional<Product> product = productRepository.findById(productId);
        updateBrandInfo(product.get().getBrand().getId());
        return ProductResponse.fromEntity(product.get());
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        Long brandId = product.get().getBrand().getId();
        productRepository.deleteById(productId);
        updateBrandInfo(brandId);
    }

    private void updateBrandInfo(Long brandId) {
        List<Long> productCategory= productRepository.findProductCategoryByBrandId(brandId);
        List<Category> categories = categoryRepository.findAll();
        boolean isActive = (productCategory.size() == categories.size());
        brandRepository.updateIsActiveById(brandId, isActive);

        if (isActive) {
            List<Product> minProductByBrandId = productRepository.findAllMinProductByBrandAndGroupByCategory(brandId);
            long minPrice = minProductByBrandId.stream()
                    .mapToLong(Product::getPrice)
                    .sum();
            brandRepository.updateMinPriceById(brandId, minPrice);
        }
    }
}
