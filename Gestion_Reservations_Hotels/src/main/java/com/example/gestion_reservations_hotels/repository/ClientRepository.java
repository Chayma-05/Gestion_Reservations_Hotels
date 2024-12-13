package com.example.gestion_reservations_hotels.repository;

import com.example.gestion_reservations_hotels.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {} 