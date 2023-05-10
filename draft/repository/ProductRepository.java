package com.sapo.edu.demo.repository;

import com.sapo.edu.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.product_name = :name")
    List<Product> findByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.product_id = :productId")
    boolean existsByProductId(@Param("productId") String productId);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.warehouse_id = :warehouseId")
    void deleteProductsByWarehouseId(@Param("warehouseId") Integer warehouseId);
    @Modifying
    @Query("DELETE FROM Product p WHERE p.category_id = :categoryId")
    void deleteProductsByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p ORDER BY p.quantity_sold DESC")
    List<Product> findTopSoldProducts(Pageable pageable);

    Page<Product> findAll(Pageable pageable);

}
