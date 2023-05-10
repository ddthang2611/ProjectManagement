package com.sapo.edu.demo.service.impl;

import com.sapo.edu.demo.entity.Warehouse;
import com.sapo.edu.demo.repository.ProductRepository;
import com.sapo.edu.demo.repository.WarehouseRepository;
import com.sapo.edu.demo.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements IWarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Warehouse> findAll(Pageable pageable){
        return warehouseRepository.findAll(pageable).getContent();
    }

    public Optional<Warehouse> findById(Integer id) {
        return warehouseRepository.findById(id);
    }



    public void add(Warehouse warehouse) throws Exception {
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        warehouse.setCreated_date(sqlDate);

        if (warehouseRepository.existsByWarehouseId(warehouse.getId())) {
            throw new ConstraintViolationException("This Warehouse ID has already used", null);
        }

        try {
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void update(Warehouse warehouse) throws Exception {
        warehouse.setCreated_date(warehouseRepository.findById(warehouse.getId()).orElseThrow().getCreated_date());
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        warehouse.setModified_date(sqlDate);

        if (!warehouseRepository.existsByWarehouseId(warehouse.getId())) {
            throw new Exception("Cannot find Warehouse ID", null);
        }

        try {
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public void deleteById(Integer id){
        productRepository.deleteProductsByWarehouseId(id);
        warehouseRepository.deleteById(id);
    }

}

