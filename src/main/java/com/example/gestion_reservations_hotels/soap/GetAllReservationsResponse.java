package com.example.gestion_reservations_hotels.soap;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reservations"
})
@XmlRootElement(name = "getAllReservationsResponse")
public class GetAllReservationsResponse {

    @XmlElement(required = true)
    protected List<Reservation> reservations;

    public List<Reservation> getReservations() {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        return this.reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
} 