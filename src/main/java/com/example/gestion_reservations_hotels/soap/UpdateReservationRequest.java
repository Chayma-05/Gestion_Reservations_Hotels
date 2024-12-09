package com.example.gestion_reservations_hotels.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "clientName",
    "roomNumber",
    "startDate",
    "endDate"
})
@XmlRootElement(name = "updateReservationRequest")
public class UpdateReservationRequest {

    protected long id;
    @XmlElement(required = true)
    protected String clientName;
    protected int roomNumber;
    @XmlElement(required = true)
    protected XMLGregorianCalendar startDate;
    @XmlElement(required = true)
    protected XMLGregorianCalendar endDate;

    public long getId() {
        return id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String value) {
        this.clientName = value;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int value) {
        this.roomNumber = value;
    }

    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }
} 