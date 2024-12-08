package com.example.Gestion_Reservations_Hotels.Service;

import com.example.Gestion_Reservations_Hotels.Entity.Reservation;
import com.example.Gestion_Reservations_Hotels.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Reservation updateReservation(Long id, Reservation newReservation) {
        return repository.findById(id).map(reservation -> {
            reservation.setClientName(newReservation.getClientName());
            reservation.setRoomType(newReservation.getRoomType());
            reservation.setStartDate(newReservation.getStartDate());
            reservation.setEndDate(newReservation.getEndDate());
            return repository.save(reservation);
        }).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public void deleteReservation(Long id) {
        repository.deleteById(id);
    }
}
