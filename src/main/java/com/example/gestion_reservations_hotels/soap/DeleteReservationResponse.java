package com.example.gestion_reservations_hotels.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "success"
})
@XmlRootElement(name = "deleteReservationResponse")
public class DeleteReservationResponse {

    protected boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean value) {
        this.success = value;
    }
} 