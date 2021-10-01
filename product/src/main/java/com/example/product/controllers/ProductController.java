package com.example.product.controllers;


import com.example.product.domain.Product;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;


   // @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/products")
    public List<Product> list() {
        return  productRepository.findAll();
    }

    @GetMapping("/product/{id}")
    public Optional<Product> get(@PathVariable Long id) {
        Optional<Product> productInstance = productRepository.findById(id);
        if (!productInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Specified product doesn't exist");
        return productInstance;

    }

}
