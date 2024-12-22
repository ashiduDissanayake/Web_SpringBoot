package com.scope.server.service;


import com.scope.server.exception.ProductNotFoundException;
import com.scope.server.model.Product;
import com.scope.server.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


    public Product getProductById(int id) {
        return productRepo.findById(id).orElse(null);
    }
}
