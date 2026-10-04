package com.rentease.service;

import com.rentease.model.Product;
import com.rentease.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    // Get all products
    public List<Product> getAllProducts() {

        return productRepository.findAll();

    }


    // Get product by ID
    public Optional<Product> getProductById(Long id) {

        return productRepository.findById(id);

    }


    // Add a new product
    public Product addProduct(Product product) {

        return productRepository.save(product);

    }


    // Update an existing product
    public Product updateProduct(
            Long id,
            Product product) {

        Optional<Product> existingProduct =
                productRepository.findById(id);


        if (existingProduct.isEmpty()) {

            throw new RuntimeException(
                    "Product not found with ID: " + id
            );

        }


        Product updatedProduct =
                existingProduct.get();


        updatedProduct.setName(
                product.getName()
        );

        updatedProduct.setCategory(
                product.getCategory()
        );

        updatedProduct.setPricePerDay(
                product.getPricePerDay()
        );

        updatedProduct.setAvailable(
                product.isAvailable()
        );


        return productRepository.save(
                updatedProduct
        );

    }


    // Delete a product
    public void deleteProduct(Long id) {

        productRepository.deleteById(id);

    }

}
