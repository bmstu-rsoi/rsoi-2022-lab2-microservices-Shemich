package ru.shemich.flightservice.service;

import org.springframework.data.domain.Page;
import ru.shemich.flightservice.api.response.FlightResponse;
import ru.shemich.flightservice.model.Flight;

import java.util.List;

public interface FlightService {
    Page<Flight> getAllToPage(int page, int size);

    Page<Flight> findPaginated(int page, int size);
    List<Flight> getAll();

    List<FlightResponse> make(List<Flight> pageFlights);

    boolean isExistByFlightNumber(String flightNumber);
    Flight getByFlightNumber(String flightNumber);

    FlightResponse toFlightResponse(Flight flight);
}
