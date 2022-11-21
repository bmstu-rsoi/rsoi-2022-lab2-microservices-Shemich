package ru.shemich.flightservice.service;

import org.springframework.data.domain.Page;
import ru.shemich.flightservice.api.response.FlightResponse;
import ru.shemich.flightservice.model.Flight;

import java.util.List;

public interface FlightService {
    Page<Flight> getAll(int page, int size);

    Page<Flight> findPaginated(int page, int size);

    List<FlightResponse> make(List<Flight> pageFlights);
}
