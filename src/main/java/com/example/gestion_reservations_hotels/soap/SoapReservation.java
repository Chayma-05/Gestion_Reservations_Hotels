package com.example.gestion_reservations_hotels.soap;

import java.time.LocalDate;

public class SoapReservation {
    private Long id;
    private String clientName;
    private int roomNumber;
    private String startDate;
    private String endDate;

    public SoapReservation(Long id, String clientName, int roomNumber, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.clientName = clientName;
        this.roomNumber = roomNumber;
        this.startDate = startDate.toString(); // Convert LocalDate to String
        this.endDate = endDate.toString();     // Convert LocalDate to String
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
