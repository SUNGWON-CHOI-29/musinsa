package com.musinsa.cody.repository;

import com.musinsa.cody.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            JOIN FETCH p.category c
            JOIN FETCH p.brand b
            WHERE p.id IN (
               SELECT p2.id
               FROM Product p2
               WHERE p2.id = (
                   SELECT p3.id
                   FROM Product p3
                   WHERE    p3.category.id      = p2.category.id
                   AND      p3.isDeleted        = false
                   AND      p3.brand.isDeleted  = false
                   AND      p3.brand.isActive   = true
                   ORDER BY p3.price ASC, p3.id DESC
                   LIMIT 1
               )
            )
            ORDER BY p.category.id ASC
            """)
    List<Product> findAllMinProductGroupByCategory();

    @Query("""
            SELECT p
            FROM Product p
            JOIN FETCH p.category c
            JOIN FETCH p.brand b
            WHERE p.id IN (
               SELECT p2.id
               FROM Product p2
               WHERE p2.id = (
                   SELECT p3.id
                   FROM Product p3
                   WHERE    p3.category.id  = p2.category.id
                   AND      p3.isDeleted    = false
                   AND      p3.brand.id     = :brandId
                   ORDER BY p3.price ASC, p3.id DESC
                   LIMIT 1
               )
            )
            ORDER BY p.category.id ASC
            """)
    List<Product> findAllMinProductByBrandAndGroupByCategory(@Param("brandId") Long brandId);

    @Query("""
            SELECT p
            FROM Product p
            JOIN FETCH p.category c
            JOIN FETCH p.brand b
            WHERE p.id = (
               SELECT p2.id
               FROM Product p2
               WHERE    p2.category.id      = :categoryId
               AND      p2.isDeleted        = false
               AND      p2.brand.isDeleted  = false
               AND      p2.brand.isActive   = true
               ORDER BY p2.price DESC, p2.id DESC
               LIMIT 1
            )
            """)
    Optional<Product> findByMaxPriceProductByCategory(@Param("categoryId") Long categoryId);

    @Query("""
            SELECT p
            FROM Product p
            JOIN FETCH p.category c
            JOIN FETCH p.brand b
            WHERE p.id = (
               SELECT p2.id
               FROM Product p2
               WHERE p2.category.id         = :categoryId
               AND      p2.isDeleted        = false
               AND      p2.brand.isDeleted  = false
               AND      p2.brand.isActive   = true
               ORDER BY p2.price ASC, p2.id DESC
               LIMIT 1
            )
            """)
    Optional<Product> findByMinPriceProductByCategory(@Param("categoryId") Long categoryId);

    @Query("""
            SELECT count(p)
            FROM Product p
            WHERE p.brand.id = :brandId
            AND p.isDeleted  = false
            GROUP BY p.category.id
            """)
    List<Long> findProductCategoryByBrandId(@Param("brandId") Long brandId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithLock(@Param("id") Long id);
}
