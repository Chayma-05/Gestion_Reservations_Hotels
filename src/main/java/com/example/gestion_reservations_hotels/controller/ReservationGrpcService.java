package com.example.gestion_reservations_hotels.controller;

import com.example.gestion_reservations_hotels.entity.Reservation;
import com.example.gestion_reservations_hotels.service.ReservationService;
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

    @Override
    public void getReservation(ReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = reservationService.getReservation(request.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        ReservationResponse response = ReservationResponse.newBuilder()
                .setId(reservation.getId())
                .setClientName(reservation.getClientName())
                .setRoomNumber(reservation.getRoomNumber())
                .setStartDate(reservation.getStartDate().toString())
                .setEndDate(reservation.getEndDate().toString())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllReservations(Empty request, StreamObserver<ReservationsResponse> responseObserver) {
        List<Reservation> reservations = reservationService.getAllReservations();

        List<ReservationResponse> responses = reservations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        ReservationsResponse response = ReservationsResponse.newBuilder()
                .addAllReservations(responses)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(CreateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = new Reservation();
        reservation.setClientName(request.getClientName());
        reservation.setRoomNumber(request.getRoomNumber());
        reservation.setStartDate(LocalDate.parse(request.getStartDate()));
        reservation.setEndDate(LocalDate.parse(request.getEndDate()));

        Reservation saved = reservationService.createReservation(reservation);
        responseObserver.onNext(mapToResponse(saved));
        responseObserver.onCompleted();
    }

    @Override
    public void updateReservation(UpdateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = new Reservation();
        reservation.setClientName(request.getClientName());
        reservation.setRoomNumber(request.getRoomNumber());
        reservation.setStartDate(LocalDate.parse(request.getStartDate()));
        reservation.setEndDate(LocalDate.parse(request.getEndDate()));

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
                .setClientName(reservation.getClientName())
                .setRoomNumber(reservation.getRoomNumber())
                .setStartDate(reservation.getStartDate().toString())
                .setEndDate(reservation.getEndDate().toString())
                .build();
    }
}