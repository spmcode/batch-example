package com.sid.batch.controller;

import com.sid.batch.model.Product;
import com.sid.batch.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;
    @GetMapping
    public List<Product> getAll(){
        return  productRepo.findAll();
    }
}
