package com.example.gestion_reservations_hotels.controller;

import com.example.gestion_reservations_hotels.dto.ReservationInput;
import com.example.gestion_reservations_hotels.entity.Client;
import com.example.gestion_reservations_hotels.entity.Chambre;
import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.repository.ClientRepository;
import com.example.gestion_reservations_hotels.repository.ChambreRepository;
import com.example.gestion_reservations_hotels.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ReservationResolver {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @QueryMapping
    public Reservation getReservation(@Argument Long id) {
        return reservationService.getReservation(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @QueryMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @QueryMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @QueryMapping
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    @MutationMapping
    public Reservation createReservation(@Argument ReservationInput input) {
        Client client = clientRepository.findById(input.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        
        Chambre chambre = chambreRepository.findById(input.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre not found"));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(input.getDateDebut()));
        reservation.setDateFin(LocalDate.parse(input.getDateFin()));
        reservation.setPreferences(input.getPreferences());

        return reservationService.createReservation(reservation);
    }

    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument ReservationInput input) {
        Client client = clientRepository.findById(input.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        
        Chambre chambre = chambreRepository.findById(input.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre not found"));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(input.getDateDebut()));
        reservation.setDateFin(LocalDate.parse(input.getDateFin()));
        reservation.setPreferences(input.getPreferences());

        return reservationService.updateReservation(id, reservation);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        try {
            reservationService.deleteReservation(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}