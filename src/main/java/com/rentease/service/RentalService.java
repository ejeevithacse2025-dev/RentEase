package com.rentease.service;

import com.rentease.model.Rental;
import com.rentease.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental createRental(Rental rental) {

        if (rental.getStatus() == null ||
                rental.getStatus().isEmpty()) {

            rental.setStatus("ACTIVE");
        }

        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<Rental> getRentalsByUser(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    /*
     * Cancel a rental
     */
    public Optional<Rental> cancelRental(Long id) {

        Optional<Rental> rental =
                rentalRepository.findById(id);

        if (rental.isPresent()) {

            Rental existingRental =
                    rental.get();

            existingRental.setStatus("CANCELLED");

            return Optional.of(
                    rentalRepository.save(existingRental)
            );
        }

        return Optional.empty();
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}