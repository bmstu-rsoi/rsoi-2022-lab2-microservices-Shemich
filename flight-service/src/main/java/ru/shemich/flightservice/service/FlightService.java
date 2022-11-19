package ru.shemich.flightservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.shemich.flightservice.model.Flight;

public interface FlightService {
    Page<Flight> getAll(Pageable pageable);

    Page<Flight> findPaginated(int page, int size);
}
