package com.example.gestion_reservations_hotels.controller;

import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.exception.ReservationNotFoundException;
import com.example.gestion_reservations_hotels.service.ReservationService;
import com.example.gestion_reservations_hotels.soap.*;
import com.example.gestion_reservations_hotels.soap.UpdateReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Endpoint
public class ReservationSoap {

    private static final String NAMESPACE_URI = "http://example.com/reservations";

    @Autowired
    private ReservationService reservationService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservation(@RequestPayload GetReservationRequest request) {
        try {
            Reservation reservation = reservationService.getReservation(request.getId())
                    .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + request.getId()));

            GetReservationResponse response = new GetReservationResponse();
            response.setReservation(mapToSoapReservation(reservation));
            return response;
        } catch (ReservationNotFoundException e) {
            throw new RuntimeException("Reservation not found: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage());
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createReservationRequest")
    @ResponsePayload
    public CreateReservationResponse createReservation(@RequestPayload CreateReservationRequest request) {
        try {
            // Create new reservation entity
            Reservation reservation = new Reservation();
            reservation.setClientName(request.getClientName());
            reservation.setRoomNumber(request.getRoomNumber());
            
            // Convert XMLGregorianCalendar to LocalDate
            XMLGregorianCalendar startXmlDate = (XMLGregorianCalendar) request.getStartDate();
            XMLGregorianCalendar endXmlDate = (XMLGregorianCalendar) request.getEndDate();
            
            LocalDate startDate = startXmlDate.toGregorianCalendar()
                    .toZonedDateTime()
                    .toLocalDate();
            LocalDate endDate = endXmlDate.toGregorianCalendar()
                    .toZonedDateTime()
                    .toLocalDate();
            
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            
            // Save the reservation
            reservation = reservationService.createReservation(reservation);
            
            // Create response
            CreateReservationResponse response = new CreateReservationResponse();
            response.setReservation(mapToSoapReservation(reservation));
            return response;
            
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid date format. Expected XMLGregorianCalendar: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error creating reservation: " + e.getMessage());
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllReservationsRequest")
    @ResponsePayload
    public GetAllReservationsResponse getAllReservations(@RequestPayload GetAllReservationsRequest request) {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            
            GetAllReservationsResponse response = new GetAllReservationsResponse();
            for (Reservation reservation : reservations) {
                response.getReservations().add(mapToSoapReservation(reservation));
            }
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching reservations: " + e.getMessage());
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateReservationRequest")
    @ResponsePayload
    public UpdateReservationResponse updateReservation(@RequestPayload UpdateReservationRequest request) {
        try {
            // Create reservation object with updated values
            Reservation updatedReservation = new Reservation();
            updatedReservation.setClientName(request.getClientName());
            updatedReservation.setRoomNumber(request.getRoomNumber());
            
            // Convert XMLGregorianCalendar to LocalDate
            XMLGregorianCalendar startXmlDate = (XMLGregorianCalendar) request.getStartDate();
            XMLGregorianCalendar endXmlDate = (XMLGregorianCalendar) request.getEndDate();
            
            LocalDate startDate = startXmlDate.toGregorianCalendar()
                    .toZonedDateTime()
                    .toLocalDate();
            LocalDate endDate = endXmlDate.toGregorianCalendar()
                    .toZonedDateTime()
                    .toLocalDate();
            
            updatedReservation.setStartDate(startDate);
            updatedReservation.setEndDate(endDate);
            
            // Update the reservation
            Reservation reservation = reservationService.updateReservation(request.getId(), updatedReservation);
            
            // Create response
            UpdateReservationResponse response = new UpdateReservationResponse();
            response.setReservation(mapToSoapReservation(reservation));
            return response;
            
        } catch (ReservationNotFoundException e) {
            throw new RuntimeException("Reservation not found: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error updating reservation: " + e.getMessage());
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteReservationRequest")
    @ResponsePayload
    public DeleteReservationResponse deleteReservation(@RequestPayload DeleteReservationRequest request) {
        try {
            reservationService.deleteReservation(request.getId());
            
            DeleteReservationResponse response = new DeleteReservationResponse();
            response.setSuccess(true);
            return response;
            
        } catch (ReservationNotFoundException e) {
            throw new RuntimeException("Reservation not found: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting reservation: " + e.getMessage());
        }
    }

    private com.example.gestion_reservations_hotels.soap.Reservation mapToSoapReservation(Reservation reservation) {
        try {
            com.example.gestion_reservations_hotels.soap.Reservation soapReservation =
                    new com.example.gestion_reservations_hotels.soap.Reservation();
            soapReservation.setId(reservation.getId());
            soapReservation.setClientName(reservation.getClientName());
            soapReservation.setRoomNumber(reservation.getRoomNumber());

            // Handle null dates
            if (reservation.getStartDate() != null) {
                GregorianCalendar startGc = GregorianCalendar.from(
                        reservation.getStartDate().atStartOfDay(ZoneId.systemDefault())
                );
                soapReservation.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(startGc));
            }

            if (reservation.getEndDate() != null) {
                GregorianCalendar endGc = GregorianCalendar.from(
                        reservation.getEndDate().atStartOfDay(ZoneId.systemDefault())
                );
                soapReservation.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(endGc));
            }

            return soapReservation;

        } catch (Exception e) {
            throw new RuntimeException("Error converting dates: " + e.getMessage());
        }
    }

}
