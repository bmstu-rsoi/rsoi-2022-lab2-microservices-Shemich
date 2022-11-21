package ru.shemich.flightservice.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shemich.flightservice.api.response.FlightResponse;
import ru.shemich.flightservice.api.response.PaginationResponse;
import ru.shemich.flightservice.model.Flight;
import ru.shemich.flightservice.repository.FlightRepository;
import ru.shemich.flightservice.service.FlightService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/flights", produces = APPLICATION_JSON_VALUE)
public class FlightController {

    private final FlightService flightService;
    private final FlightRepository flightRepository;

    public FlightController(FlightService flightService, FlightRepository flightRepository) {
        this.flightService = flightService;
        this.flightRepository = flightRepository;
    }

//    @GetMapping()
//    public PaginationResponse getAll(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
//            ) {
//        return new PaginationResponse();
//    }

    @GetMapping()
    public PaginationResponse getAll(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "3") int size
    ) {
        log.info("Fetching all tickets");
        Page<Flight> pageFlights = flightService.getAll(page, size);
        log.info("{}",pageFlights);
        List<FlightResponse> items = flightService.make(pageFlights.getContent());
        log.info("{}",items);

        return new PaginationResponse(page, size, pageFlights.getTotalElements(), items);

//        List<Flight> flights;
//        Pageable paging = PageRequest.of(page, size);
//
//        Page<Flight> pageFlights;
//
//        pageFlights = flightRepository.findAll(paging);
//        log.info("{}",pageFlights);
//        flights = pageFlights.getContent();
//        log.info("{}",flights);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("currentPage", pageFlights.getNumber());
//        response.put("totalItems", pageFlights.getTotalElements());
//        response.put("totalPages", pageFlights.getTotalPages());
//        response.put("items", flights);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
