package com.example.gestion_reservations_hotels.repository;

import com.example.gestion_reservations_hotels.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {}

