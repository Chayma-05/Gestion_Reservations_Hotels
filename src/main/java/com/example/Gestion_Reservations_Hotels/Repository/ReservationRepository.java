package com.example.Gestion_Reservations_Hotels.Repository;

import com.example.Gestion_Reservations_Hotels.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {}

