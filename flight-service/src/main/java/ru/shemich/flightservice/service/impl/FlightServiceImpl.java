package ru.shemich.flightservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shemich.flightservice.model.Flight;
import ru.shemich.flightservice.repository.FlightRepository;
import ru.shemich.flightservice.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Page<Flight> getAll(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    @Override
    public Page<Flight> findPaginated(int page, int size) {
        return flightRepository.findAll();
    }
}
