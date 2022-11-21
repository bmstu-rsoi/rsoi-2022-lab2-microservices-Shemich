package ru.shemich.flightservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.flightservice.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Page<Flight>  findAll(Pageable pageable);
}
