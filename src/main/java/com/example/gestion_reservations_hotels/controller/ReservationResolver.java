package com.example.gestion_reservations_hotels.controller;

import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReservationResolver {

    @Autowired
    private ReservationService reservationService;

    @QueryMapping
    public Reservation getReservation(@Argument Long id) {
        return reservationService.getReservation(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }
    @QueryMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    @MutationMapping
    public Reservation createReservation(@Argument Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument Reservation reservation) {
        return reservationService.updateReservation(id, reservation);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        reservationService.deleteReservation(id);
        return true;
    }
}