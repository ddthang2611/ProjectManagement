package com.sapo.edu.demo.service.impl;

import com.sapo.edu.demo.entity.Product;
import com.sapo.edu.demo.repository.CategoryRepository;
import com.sapo.edu.demo.repository.ProductRepository;
import com.sapo.edu.demo.repository.WarehouseRepository;
import com.sapo.edu.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAll(Pageable pageable){
        return productRepository.findAll(pageable).getContent();
    }
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> sortByProductQuantity() throws Exception {
        List<Product> products = null;
//                try {
//                    products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "quantity_of_product"));
//                }catch (Exception e){
//                    throw new Exception(e.getMessage(),null);
//                }
         return products;
    }

    public void add(Product product) throws Exception {
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        product.setCreated_date(sqlDate);
        if(productRepository.existsByProductId(product.getProduct_id())){
            throw new ConstraintViolationException("This Product ID has already used",null);
        }
        if(!warehouseRepository.existsByWarehouseId(product.getWarehouse_id())){
            throw new Exception("Warehouse ID is not exist",null);
        }
        if(!categoryRepository.existsByCategoryId(product.getCategory_id())){
            throw new Exception("Category ID is not exist",null);
        }
        try {
            productRepository.save(product);
        }catch (Exception e){
            throw new Exception(e.getMessage());

        }
    }
    public void update(Product product) throws Exception {
        product.setCreated_date(productRepository.findById(product.getId()).get().getCreated_date());
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        product.setModified_date(sqlDate);
        if(!productRepository.existsByProductId(product.getProduct_id())){
            throw new Exception("Cannot found Product ID",null);
        }
        if(!warehouseRepository.existsByWarehouseId(product.getWarehouse_id())){
            throw new Exception("Warehouse ID is not exist",null);
        }
        if(!categoryRepository.existsByCategoryId(product.getCategory_id())){
            throw new Exception("Category ID is not exist",null);
        }
        try {
            productRepository.save(product);
        }catch (Exception e){
            throw new Exception(e.getMessage());

        }
    }
    public void deleteById(Integer id){
        productRepository.deleteById(id);
    }
    public List<Product> getTopSoldProducts(Pageable pageable) throws Exception{
    return productRepository.findTopSoldProducts(pageable);
    }
}
