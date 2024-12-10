package com.example.gestion_reservations_hotels.config;

import com.example.gestion_reservations_hotels.ws.ReservationSoapService;
import lombok.AllArgsConstructor;
import org.apache.cxf.Bus;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class CxfConfig {
    @Autowired
    private ReservationSoapService reservationSoapService;
    @Autowired
    private Bus bus;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationSoapService);
        endpoint.publish("/ws");
        return endpoint;
    }
}
