package com.example.gestion_reservations_hotels.ws;

import com.example.gestion_reservations_hotels.entity.Client;
import com.example.gestion_reservations_hotels.entity.Chambre;
import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.repository.ReservationRepository;
import com.example.gestion_reservations_hotels.repository.ClientRepository;
import com.example.gestion_reservations_hotels.repository.ChambreRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebService
@Component
public class ReservationSoapService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @WebMethod
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @WebMethod
    public Reservation getReservationById(@WebParam(name = "id") Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @WebMethod
    public Reservation createReservation(
            @WebParam(name = "clientId") Long clientId,
            @WebParam(name = "roomNumber") int roomNumber,
            @WebParam(name = "chambreId") Long chambreId,
            @WebParam(name = "dateDebut") String dateDebut,
            @WebParam(name = "dateFin") String dateFin) {

        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        Chambre chambre = chambreRepository.findById(chambreId).orElseThrow(() -> new RuntimeException("Chambre not found"));
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRoomNumber(roomNumber);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(dateDebut));
        reservation.setDateFin(LocalDate.parse(dateFin));
        return reservationRepository.save(reservation);
    }

    @WebMethod
    public Reservation updateReservation(
            @WebParam(name = "id") Long id,
            @WebParam(name = "clientId") Long clientId,
            @WebParam(name = "roomNumber") int roomNumber,
            @WebParam(name = "chambreId") Long chambreId,
            @WebParam(name = "dateDebut") String dateDebut,
            @WebParam(name = "dateFin") String dateFin) {

        Optional<Reservation> existingReservation = reservationRepository.findById(id);
        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
            Chambre chambre = chambreRepository.findById(chambreId).orElseThrow(() -> new RuntimeException("Chambre not found"));
            reservation.setClient(client);
            reservation.setRoomNumber(roomNumber);
            reservation.setChambre(chambre);
            reservation.setDateDebut(LocalDate.parse(dateDebut));
            reservation.setDateFin(LocalDate.parse(dateFin));
            return reservationRepository.save(reservation);
        }
        return null;
    }

    @WebMethod
    public boolean deleteReservation(@WebParam(name = "id") Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @WebMethod
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @WebMethod
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }
}
