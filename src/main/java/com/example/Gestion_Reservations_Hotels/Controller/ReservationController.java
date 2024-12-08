package com.example.Gestion_Reservations_Hotels.Controller;

import com.example.Gestion_Reservations_Hotels.Entity.Reservation;
import com.example.Gestion_Reservations_Hotels.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService service;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return service.createReservation(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return service.getReservation(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return service.updateReservation(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
    }
}

