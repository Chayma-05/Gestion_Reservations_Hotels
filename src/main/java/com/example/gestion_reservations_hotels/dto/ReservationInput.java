package com.example.gestion_reservations_hotels.dto;

import lombok.Data;

@Data
public class ReservationInput {
    private Long clientId;
    private Long chambreId;
    private String dateDebut;
    private String dateFin;
    private String preferences;
}


