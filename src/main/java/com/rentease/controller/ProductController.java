package com.rentease.controller;

import com.rentease.model.Product;
import com.rentease.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // ============================
    // GET ALL PRODUCTS
    // ============================

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }


    // ============================
    // GET PRODUCT BY ID
    // ============================

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @PathVariable Long id) {

        return productService
                .getProductById(id)
                .map(product ->
                        ResponseEntity.ok(product)
                )
                .orElse(
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }


    // ============================
    // ADD PRODUCT
    // ============================

    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestBody Product product) {

        return ResponseEntity.ok(
                productService.addProduct(product)
        );
    }


    // ============================
    // UPDATE PRODUCT
    // ============================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        try {

            Product updatedProduct =
                    productService.updateProduct(
                            id,
                            product
                    );

            return ResponseEntity.ok(
                    updatedProduct
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Unable to update product: "
                            + e.getMessage()
                    );
        }
    }


    // ============================
    // DELETE PRODUCT
    // ============================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id) {

        try {

            productService.deleteProduct(id);

            return ResponseEntity.ok(
                    "Product deleted successfully"
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Unable to delete product: "
                            + e.getMessage()
                    );
        }
    }

}