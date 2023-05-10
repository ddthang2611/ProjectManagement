package com.sapo.edu.demo.service;

import com.sapo.edu.demo.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public List<Product> findAll(Pageable pageable);
    public Optional<Product> findById(Integer id) ;
    public void add(Product product) throws Exception;
    public void update(Product product) throws Exception;
    public void deleteById(Integer id);
    public List<Product> findByName(String name);
    public List<Product> sortByProductQuantity() throws Exception;
    public List<Product> getTopSoldProducts(Pageable pageable) throws Exception;
}
