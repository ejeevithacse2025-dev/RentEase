package com.rentease.repository;

import com.rentease.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserId(Long userId);

    List<Rental> findByProductId(Long productId);
}