package com.example.gestion_reservations_hotels.repository;

import com.example.gestion_reservations_hotels.entity.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {} 