package ru.shemich.flightservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shemich.flightservice.api.response.FlightResponse;
import ru.shemich.flightservice.model.Airport;
import ru.shemich.flightservice.model.Flight;
import ru.shemich.flightservice.repository.AirportRepository;
import ru.shemich.flightservice.repository.FlightRepository;
import ru.shemich.flightservice.service.FlightService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public Page<Flight> getAll(int page, int size) {
        Pageable paging = PageRequest.of(--page, size);  //  -- to start from page 1 not 0
        List<Flight> flights = new ArrayList<>();
        return flightRepository.findAll(paging);
    }

    @Override
    public Page<Flight> findPaginated(int page, int size) {
        return null;
    }

    @Override
    public List<FlightResponse> make(List<Flight> pageFlights) {
        List<FlightResponse> items = pageFlights.stream()
                .map(flight -> new FlightResponse(
                        flight.getFlightNumber(),
                        airportRepository.findById(flight.getFromAirportId()).get().toCityAndName(),
                        airportRepository.findById(flight.getToAirportId()).get().toCityAndName(),
                        flight.getDatetime(),
                        flight.getPrice()))
                .collect(Collectors.toList());
        return items;
    }
}
