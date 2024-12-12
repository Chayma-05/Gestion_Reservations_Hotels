package com.example.gestion_reservations_hotels.controller;

import com.example.gestion_reservations_hotels.entity.Client;
import com.example.gestion_reservations_hotels.entity.Chambre;
import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.service.ReservationService;
import com.example.gestion_reservations_hotels.repository.ClientRepository;
import com.example.gestion_reservations_hotels.repository.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return reservationService.getReservation(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.updateReservation(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/chambres")
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }
}

