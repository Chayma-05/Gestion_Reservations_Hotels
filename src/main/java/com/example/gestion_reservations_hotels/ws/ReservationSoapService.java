package com.example.gestion_reservations_hotels.ws;

import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.repository.ReservationRepository;
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
            @WebParam(name = "clientName") String clientName,
            @WebParam(name = "roomNumber") int roomNumber,
            @WebParam(name = "startDate") String startDate,
            @WebParam(name = "endDate") String endDate) {

        Reservation reservation = new Reservation();
        reservation.setClientName(clientName);
        reservation.setRoomNumber(roomNumber);
        reservation.setStartDate(LocalDate.parse(startDate));
        reservation.setEndDate(LocalDate.parse(endDate));
        return reservationRepository.save(reservation);
    }

    @WebMethod
    public Reservation updateReservation(
            @WebParam(name = "id") Long id,
            @WebParam(name = "clientName") String clientName,
            @WebParam(name = "roomNumber") int roomNumber,
            @WebParam(name = "startDate") String startDate,
            @WebParam(name = "endDate") String endDate) {

        Optional<Reservation> existingReservation = reservationRepository.findById(id);
        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            reservation.setClientName(clientName);
            reservation.setRoomNumber(roomNumber);
            reservation.setStartDate(LocalDate.parse(startDate));
            reservation.setEndDate(LocalDate.parse(endDate));
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
}
