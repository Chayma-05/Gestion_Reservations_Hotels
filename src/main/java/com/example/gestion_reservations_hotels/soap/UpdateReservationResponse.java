package com.example.gestion_reservations_hotels.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reservation"
})
@XmlRootElement(name = "updateReservationResponse")
public class UpdateReservationResponse {

    @XmlElement(required = true)
    protected Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation value) {
        this.reservation = value;
    }
} 