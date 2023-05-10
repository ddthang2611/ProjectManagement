package com.sapo.edu.demo.repository;

import com.sapo.edu.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Category c WHERE c.id = :categoryId")
    boolean existsByCategoryId(@Param("categoryId") Integer categoryId);

    @Modifying
    @Query("DELETE FROM Category c WHERE c.category_id = :categoryId")
    void deleteProductsByWarehouseId(@Param("categoryId") Integer categoryId);

    @Query("SELECT c.id, c.category_id, c.category_name, SUM(p.quantity_of_product) AS quantity_sold " +
            "FROM Product p JOIN Category c ON p.category_id = c.id " +
            "GROUP BY c.category_name " +
            "ORDER BY quantity_sold DESC")
    List<Object[]> findCategoryByProductQuantity();
}
