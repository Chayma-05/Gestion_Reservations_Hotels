package com.example.gestion_reservations_hotels.controller;

import com.example.gestion_reservations_hotels.entity.Client;
import com.example.gestion_reservations_hotels.entity.Chambre;
import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.service.ReservationService;
import com.example.gestion_reservations_hotels.repository.ClientRepository;
import com.example.gestion_reservations_hotels.repository.ChambreRepository;
import com.example.gestion_reservations_hotels.stubs.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class ReservationGrpcService extends ReservationServiceGrpc.ReservationServiceImplBase {

    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ChambreRepository chambreRepository;

    @Override
    public void getReservation(ReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = reservationService.getReservation(request.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        ReservationResponse response = mapToResponse(reservation);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllReservations(Empty request, StreamObserver<ReservationsResponse> responseObserver) {
        List<Reservation> reservations = reservationService.getAllReservations();
        ReservationsResponse response = ReservationsResponse.newBuilder()
                .addAllReservations(reservations.stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(CreateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Chambre chambre = chambreRepository.findById(request.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre not found"));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(request.getDateDebut()));
        reservation.setDateFin(LocalDate.parse(request.getDateFin()));

        Reservation saved = reservationService.createReservation(reservation);
        responseObserver.onNext(mapToResponse(saved));
        responseObserver.onCompleted();
    }

    @Override
    public void updateReservation(UpdateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Chambre chambre = chambreRepository.findById(request.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre not found"));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(request.getDateDebut()));
        reservation.setDateFin(LocalDate.parse(request.getDateFin()));

        Reservation updated = reservationService.updateReservation(request.getId(), reservation);
        responseObserver.onNext(mapToResponse(updated));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(ReservationRequest request, StreamObserver<DeleteResponse> responseObserver) {
        reservationService.deleteReservation(request.getId());
        responseObserver.onNext(DeleteResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private ReservationResponse mapToResponse(Reservation reservation) {
        return ReservationResponse.newBuilder()
                .setId(reservation.getId())
                .setClient(com.example.gestion_reservations_hotels.stubs.Client.newBuilder()
                        .setId(reservation.getClient().getId())
                        .setNom(reservation.getClient().getNom())
                        .setPrenom(reservation.getClient().getPrenom())
                        .setEmail(reservation.getClient().getEmail())
                        .setTelephone(reservation.getClient().getTelephone())
                        .build())
                .setChambre(com.example.gestion_reservations_hotels.stubs.Chambre.newBuilder()
                        .setId(reservation.getChambre().getId())
                        .setTypeChambre(reservation.getChambre().getType().toString())
                        .setPrix(reservation.getChambre().getPrix())
                        .setDisponible(reservation.getChambre().isDisponible())
                        .build())
                .setDateDebut(reservation.getDateDebut().toString())
                .setDateFin(reservation.getDateFin().toString())
                .build();
    }
}