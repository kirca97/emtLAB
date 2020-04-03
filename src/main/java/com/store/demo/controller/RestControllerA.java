package com.store.demo.controller;

import com.store.demo.domain.Product;
import com.store.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class  RestControllerA {
    private final ProductService productService;

    public RestControllerA(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/rest/products")
    public List<Product> getProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("/rest/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return this.productService.getProductById(id);
    }

    @GetMapping("/rest/product/category/{categoryId}")
    public List<Product> getProductByCategoryId(@PathVariable Long categoryId) {
        return this.productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/rest/product/category/{categoryId}/manufacturer/{manufacturerId}")
    public List<Product> getProductByCategoryId(@PathVariable Long categoryId,
                                                @PathVariable Long manufacturerId) {
        return this.productService.getProductsByCategoryIdAndManufacturerId(categoryId, manufacturerId);
    }

    @GetMapping("/rest/product/category/{categoryId}/price")
    public Map<String, Double> getProductsPriceByCategoryId(@PathVariable Long categoryId) {
        Double productsPrice = this.productService.getProductsPriceByCategoryId(categoryId);
        Map<String, Double> response = new HashMap<>();
        response.put("price", productsPrice);

        return response;
    }
}
