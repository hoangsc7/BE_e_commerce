package com.hoang.ecommerce.repository;

import com.hoang.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("SELECT p from Product p" + " WHERE (p.category.name = :category OR :category='')" +
            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice))"+
            "AND (:minDiscount IS NULL OR p.discountedPersent >= :minDiscount)"+
            "ORDER BY"+
            " CASE WHEN :sort ='price_low' THEN p.discountedPrice END ASC,"+
            " CASE WHEN :sort ='price_hight' THEN p.discountedPrice END DESC")
//            " CASE WHEN :sort = 'price_low' THEN p.discountedPrice " +
//            "      WHEN :sort = 'price_high' THEN -p.discountedPrice " + // giả lập DESC bằng số âm
//            " ELSE p.id END") // fallback nếu không khớp
//    @Query("SELECT p FROM Product p WHERE (p.category.name = :category OR :category = '') " +
//            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
//            "AND (:minDiscount IS NULL OR p.discountedPercent >= :minDiscount) " +
//            "ORDER BY CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
//            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
    public List<Product> filterProducts(
            @Param("category") String category,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minDiscount") Integer minDiscount,
            @Param("sort") String sort
    );
}
