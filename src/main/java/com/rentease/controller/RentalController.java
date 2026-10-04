package com.rentease.controller;

import com.rentease.model.Rental;
import com.rentease.model.Product;
import com.rentease.service.RentalService;
import com.rentease.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalController {

    private final RentalService rentalService;
    private final ProductService productService;

    public RentalController(
            RentalService rentalService,
            ProductService productService) {

        this.rentalService = rentalService;
        this.productService = productService;
    }


    // ============================
    // CREATE RENTAL
    // ============================

    @PostMapping
    public ResponseEntity<?> createRental(
            @RequestBody Rental rental) {

        try {

            Rental savedRental =
                    rentalService.createRental(rental);

            return ResponseEntity.ok(savedRental);

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body("Rental failed: " + e.getMessage());
        }
    }


    // ============================
    // GET ALL RENTALS
    // ============================

    @GetMapping
    public ResponseEntity<?> getAllRentals() {

        List<Rental> rentals =
                rentalService.getAllRentals();

        return ResponseEntity.ok(rentals);
    }


    // ============================
    // GET USER RENTALS
    // ============================

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRentalsByUser(
            @PathVariable Long userId) {

        List<Rental> rentals =
                rentalService.getRentalsByUser(userId);

        return ResponseEntity.ok(rentals);
    }


    // ============================
    // GET RENTAL BY ID
    // ============================

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(
            @PathVariable Long id) {

        Optional<Rental> rental =
                rentalService.getRentalById(id);

        if (rental.isPresent()) {

            return ResponseEntity.ok(
                    rental.get()
            );

        }

        return ResponseEntity.notFound().build();
    }


    // ============================
    // GET PRODUCT OF RENTAL
    // ============================

    @GetMapping("/{id}/product")
    public ResponseEntity<?> getRentalProduct(
            @PathVariable Long id) {

        Optional<Rental> rental =
                rentalService.getRentalById(id);

        if (rental.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();

        }


        Long productId =
                rental.get().getProductId();


        Optional<Product> product =
                productService.getProductById(productId);


        if (product.isPresent()) {

            return ResponseEntity.ok(
                    product.get()
            );

        }


        return ResponseEntity
                .notFound()
                .build();
    }


    // ============================
    // CANCEL RENTAL
    // ============================

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelRental(
            @PathVariable Long id) {

        Optional<Rental> rental =
                rentalService.cancelRental(id);

        if (rental.isPresent()) {

            return ResponseEntity.ok(
                    rental.get()
            );

        }

        return ResponseEntity
                .notFound()
                .build();
    }


    // ============================
    // DELETE RENTAL
    // ============================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRental(
            @PathVariable Long id) {

        Optional<Rental> rental =
                rentalService.getRentalById(id);

        if (rental.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();

        }


        rentalService.deleteRental(id);


        return ResponseEntity.ok(
                "Rental deleted successfully"
        );
    }

}