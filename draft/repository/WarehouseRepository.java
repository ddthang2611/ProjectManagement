package com.sapo.edu.demo.repository;

import com.sapo.edu.demo.entity.Product;
import com.sapo.edu.demo.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {
    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END " +
            "FROM Warehouse w WHERE w.id = :warehouseId")
    boolean existsByWarehouseId(@Param("warehouseId") Integer warehouseId);
    @Modifying
    @Query("DELETE FROM Warehouse w WHERE w.warehouse_id = :warehouseId")
    void deleteWarehouseByWarehouseId(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT p FROM Product p ORDER BY p.quantity_sold DESC")
    List<Product> findTopSoldProducts(@Param("limit") int limit);


}
