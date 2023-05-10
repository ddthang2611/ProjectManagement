package com.sapo.edu.demo.service.impl;

import com.sapo.edu.demo.entity.Category;
import com.sapo.edu.demo.repository.CategoryRepository;
import com.sapo.edu.demo.repository.ProductRepository;
import com.sapo.edu.demo.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public void save(Category category) throws Exception {
        LocalDate currentDate = LocalDate.now();
        Date sqlDate = Date.valueOf(currentDate);
        category.setCreated_date(sqlDate);

        if (categoryRepository.existsByCategoryId(category.getId())) {
            throw new ConstraintViolationException("This Category ID has already used", null);
        }

        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void update(Category category) throws Exception {
        category.setCreated_date(categoryRepository.findById(category.getId()).get().getCreated_date());
        LocalDate currentDate = LocalDate.now();
        Date sqlDate = Date.valueOf(currentDate);
        category.setModified_date(sqlDate);

        if (!categoryRepository.existsByCategoryId(category.getId())) {
            throw new Exception("Cannot find Category ID", null);
        }

        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public void deleteById(int id) {
        productRepository.deleteProductsByCategoryId(id);
        categoryRepository.deleteById(id);
    }
    public List<Object[]> findCategoryByProductQuantity(){
        return categoryRepository.findCategoryByProductQuantity();
    }
}
