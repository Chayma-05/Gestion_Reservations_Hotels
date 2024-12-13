package com.example.gestion_reservations_hotels.controller;

import com.example.gestion_reservations_hotels.entity.*;
import com.example.gestion_reservations_hotels.service.ReservationService;
import com.example.gestion_reservations_hotels.repository.ClientRepository;
import com.example.gestion_reservations_hotels.repository.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public Reservation createReservation(@Argument Map<String, Object> reservationInput) {
        Reservation reservation = new Reservation();
        
        Client client;
        if (reservationInput.containsKey("clientId")) {
            client = clientRepository.findById(Long.parseLong(reservationInput.get("clientId").toString()))
                    .orElseThrow(() -> new RuntimeException("Client not found"));
        } else {
            @SuppressWarnings("unchecked")
            Map<String, Object> clientData = (Map<String, Object>) reservationInput.get("client");
            client = new Client();
            client.setNom(clientData.get("nom").toString());
            client.setPrenom(clientData.get("prenom").toString());
            client.setEmail(clientData.get("email").toString());
            client.setTelephone(clientData.get("telephone").toString());
            client = clientRepository.save(client);
        }
        
        Chambre chambre;
        if (reservationInput.containsKey("chambreId")) {
            chambre = chambreRepository.findById(Long.parseLong(reservationInput.get("chambreId").toString()))
                    .orElseThrow(() -> new RuntimeException("Chambre not found"));
        } else {
            @SuppressWarnings("unchecked")
            Map<String, Object> chambreData = (Map<String, Object>) reservationInput.get("chambre");
            chambre = new Chambre();
            chambre.setType(TypeChambre.valueOf(chambreData.get("typeChambre").toString()));
            chambre.setPrix(Double.parseDouble(chambreData.get("prix").toString()));
            chambre.setDisponible((Boolean) chambreData.get("disponible"));
            chambre = chambreRepository.save(chambre);
        }
        
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(reservationInput.get("dateDebut").toString()));
        reservation.setDateFin(LocalDate.parse(reservationInput.get("dateFin").toString()));
        
        if (reservationInput.containsKey("preferences")) {
            reservation.setPreferences(reservationInput.get("preferences").toString());
        }

        return reservationService.createReservation(reservation);
    }

    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument Map<String, Object> reservationInput) {
        Reservation reservation = new Reservation();
        
        Client client = clientRepository.findById(Long.parseLong(reservationInput.get("clientId").toString()))
                .orElseThrow(() -> new RuntimeException("Client not found"));
        
        Chambre chambre = chambreRepository.findById(Long.parseLong(reservationInput.get("chambreId").toString()))
                .orElseThrow(() -> new RuntimeException("Chambre not found"));
        
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(reservationInput.get("dateDebut").toString()));
        reservation.setDateFin(LocalDate.parse(reservationInput.get("dateFin").toString()));
        
        if (reservationInput.containsKey("preferences")) {
            reservation.setPreferences(reservationInput.get("preferences").toString());
        }

        return reservationService.updateReservation(id, reservation);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        reservationService.deleteReservation(id);
        return true;
    }
}