package ru.shemich.flightservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shemich.flightservice.api.response.FlightResponse;
import ru.shemich.flightservice.model.Flight;
import ru.shemich.flightservice.repository.AirportRepository;
import ru.shemich.flightservice.repository.FlightRepository;
import ru.shemich.flightservice.service.FlightService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public Page<Flight> getAllToPage(int page, int size) {
        Pageable paging = PageRequest.of(--page, size);  //  -- to start from page 1 not 0
        return flightRepository.findAll(paging);
    }
    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public Page<Flight> findPaginated(int page, int size) {
        return null;
    }

    @Override
    public List<FlightResponse> make(List<Flight> pageFlights) {
        return pageFlights.stream()
                .map(flight -> new FlightResponse(
                        flight.getFlightNumber(),
                        airportRepository.findById(flight.getFromAirportId()).get().toCityAndName(),
                        airportRepository.findById(flight.getToAirportId()).get().toCityAndName(),
                        flight.getDatetime(),
                        flight.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isExistByFlightNumber(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        return flight != null;
    }

    @Override
    public Flight getByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    @Override
    public FlightResponse toFlightResponse(Flight flight) {
        return new FlightResponse(
                flight.getFlightNumber(),
                String.valueOf(airportRepository.findById(flight.getFromAirportId()).get().toCityAndName()),
                String.valueOf(airportRepository.findById(flight.getToAirportId()).get().toCityAndName()),
                flight.getDatetime(),
                flight.getPrice()
        );
    }

}
