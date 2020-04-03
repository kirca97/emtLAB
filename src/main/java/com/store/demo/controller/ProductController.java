package com.store.demo.controller;

import com.store.demo.domain.*;
import com.store.demo.repository.CategoryRepository;
import com.store.demo.repository.ManufacturerRepository;
import com.store.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;
    private CategoryRepository categoryRepository;
    private ManufacturerRepository manufacturerRepository;

    @Value("${stripe.pk}")
    private String stripePublicKey;

    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository,
                             ManufacturerRepository manufacturerRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping(value = "/products")
    public ModelAndView products() {
        ModelAndView modelAndView = new ModelAndView("products");

        List<Product> allProducts = this.productService.getAllProducts();
        modelAndView.addObject("products", allProducts);
        return modelAndView;
    }

    @GetMapping(value = "/products/{id}")
    public ModelAndView product(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("product");
        Product product = this.productService.getProductById(id);
        modelAndView.addObject("product", product);
        modelAndView.addObject("amount", (int) (product.getPrice() * 100));
        modelAndView.addObject("currency", ChargeRequest.Currency.EUR.name());
        modelAndView.addObject("stripePublicKey", stripePublicKey);

        return modelAndView;
    }

    @GetMapping("/products/edit-product/{id}")
    public ModelAndView getEditProductPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("edit-product");
        List<Category> categories = this.categoryRepository.findAll();
        List<Manufacturer> manufacturers = this.manufacturerRepository.findAll();

        Product productToEdit = this.productService.getProductById(id);
        AddProduct product = new AddProduct();
        product.setId(productToEdit.getId());
        product.setPrice(productToEdit.getPrice());
        product.setName(productToEdit.getName());
        product.setLink(productToEdit.getLink());
        product.setDescription(productToEdit.getDescription());
        product.setCategoryId(productToEdit.getCategory().getId());
        product.setManufacturerId(productToEdit.getManufacturer().getId());

        modelAndView.addObject("product", product);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("manufacturers", manufacturers);

        return modelAndView;
    }

    @PostMapping("/products/edit")
    public ModelAndView editProduct(@ModelAttribute AddProduct product) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");
        Product productById = this.productService.getProductById(product.getId());
        productById.setName(product.getName());
        productById.setLink(product.getLink());
        productById.setPrice(product.getPrice());
        productById.setDescription(product.getDescription());
        for (Category category : this.categoryRepository.findAll()) {
            if (category.getId().equals(product.getCategoryId())) {
                productById.setCategory(category);
                break;
            }
        }
        for (Manufacturer manufacturer : this.manufacturerRepository.findAll()) {
            if (manufacturer.getId().equals(product.getManufacturerId())) {
                productById.setManufacturer(manufacturer);
                break;
            }
        }

        this.productService.editProduct(productById);

        return modelAndView;
    }

    @GetMapping(value = "/products/add")
    public ModelAndView getAddProductPage() {
        ModelAndView modelAndView = new ModelAndView("add-product");
        List<Category> categories = this.categoryRepository.findAll();
        List<Manufacturer> manufacturers = this.manufacturerRepository.findAll();
        AddProduct product = new AddProduct();

        modelAndView.addObject("product", product);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("manufacturers", manufacturers);
        return modelAndView;
    }

    @PostMapping(value = "/products")
    public ModelAndView createProduct(@ModelAttribute AddProduct product) {
        System.out.println("product = " + product);
        Product newProduct = new Product();
        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setDescription(product.getDescription());
        newProduct.setLink(product.getLink());
        List<Category> categories = this.categoryRepository.findAll();
        for (Category category : categories) {
            if (category.getId().equals(product.getCategoryId())) {
                newProduct.setCategory(category);
                break;
            }
        }
        for (Manufacturer manufacturer : this.manufacturerRepository.findAll()) {
            if (manufacturer.getId().equals(product.getManufacturerId())) {
                newProduct.setManufacturer(manufacturer);
                break;
            }
        }


        this.productService.addProduct(newProduct);


        return new ModelAndView("redirect:/products");
    }

    @GetMapping(value = "/products/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");

        this.productService.deleteProduct(id);

        return modelAndView;

    }
}
