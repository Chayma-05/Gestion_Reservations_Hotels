package com.example.gestion_reservations_hotels.service;

import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository repository;

    public Reservation createReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    public Optional<Reservation> getReservation(Long id) {
        return repository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    public Reservation updateReservation(Long id, Reservation newReservation) {
        return repository.findById(id).map(reservation -> {
            reservation.setClient(newReservation.getClient());
            reservation.setChambre(newReservation.getChambre());
            reservation.setDateDebut(newReservation.getDateDebut());
            reservation.setDateFin(newReservation.getDateFin());
            return repository.save(reservation);
        }).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public void deleteReservation(Long id) {
        repository.deleteById(id);
    }
}
