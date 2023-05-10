package com.sapo.edu.demo.service;


import com.sapo.edu.demo.entity.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    List<Category> findAll(Pageable pageable);

    public Optional<Category> findById(int id);

    void save(Category category) throws Exception;

    void update(Category category) throws Exception;

    void deleteById(int id);
    public List<Object[]> findCategoryByProductQuantity();

}
