package com.store.demo.service;

import com.store.demo.domain.Product;
import com.store.demo.domain.Transaction;
import com.store.demo.repository.ProductRepository;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> byId = this.productRepository.findById(id);
        return byId.orElse(null);

    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return this.productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByCategoryIdAndManufacturerId(Long categoryId, Long manufacturerId) {
        return this.productRepository.findByCategoryIdAndManufacturerId(categoryId, manufacturerId);
    }

    public Double getProductsPriceByCategoryId(Long categoryId) {
        List<Product> byCategoryId = this.productRepository.findByCategoryId(categoryId);
        double sum = 0;
        for (Product product : byCategoryId) {
            sum += product.getPrice();
        }

        return sum;
    }

    public void addProduct(Product product) {
        this.productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    public void editProduct(Product product) {
        this.productRepository.save(product);
    }
}
