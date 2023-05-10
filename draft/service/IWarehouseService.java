package com.sapo.edu.demo.service;

import com.sapo.edu.demo.entity.Warehouse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IWarehouseService {

    public List<Warehouse> findAll(Pageable pageable);

    public Optional<Warehouse> findById(Integer id);

    public void add(Warehouse warehouse) throws Exception;

    public void update(Warehouse warehouse) throws Exception;
    public void deleteById(Integer id);
}
