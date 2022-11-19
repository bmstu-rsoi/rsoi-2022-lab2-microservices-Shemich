package ru.shemich.flightservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shemich.flightservice.api.response.PaginationResponse;
import ru.shemich.flightservice.model.Flight;
import ru.shemich.flightservice.service.FlightService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/flights", produces = APPLICATION_JSON_VALUE)
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping()
    public PaginationResponse getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
            ) {
        return new PaginationResponse();
    }

}
