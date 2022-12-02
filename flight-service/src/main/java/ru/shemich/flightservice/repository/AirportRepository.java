package ru.shemich.flightservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shemich.flightservice.model.Airport;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

}
