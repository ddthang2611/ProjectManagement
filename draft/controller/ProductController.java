package com.sapo.edu.demo.controller;

import com.sapo.edu.demo.entity.Product;
import com.sapo.edu.demo.repository.ProductRepository;
import com.sapo.edu.demo.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;


@RestController
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            productService.add(product);
            return ResponseEntity.ok().build();
        }  catch (Exception e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.ok().build();
        }  catch (Exception e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable String id) throws Exception {
        try {
            product.setId(Integer.parseInt(id));
            productService.update(product);
            return ResponseEntity.ok().build();
        }  catch (Exception e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable(value = "id") int productId) {

        Optional<Product> product = productService.findById(productId);

        if(!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size,
    @RequestParam(name = "sort", required = false) String sort) throws Exception {
        List<Product> productList = null;
        Pageable pageable = PageRequest.of(page, size);
        if(name != null) {
           productList = productService.findByName(name);
        }else{
            productList = productService.findAll(pageable);
        }
//        if (sort.equals("quantity,desc")) {
//            productList = productService.sortByProductQuantity();
//        }
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/top-sold")
    public ResponseEntity<?> getTopSoldProducts(@RequestParam(name = "limit", defaultValue = "10") int limit) throws Exception {
        List<Product> productList = null;
        Pageable pageable = PageRequest.of(0, limit);
        try {
                productList = productService.getTopSoldProducts(pageable);
        }catch(Exception e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(productList);
    }
}
