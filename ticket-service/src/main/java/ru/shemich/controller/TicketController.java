package ru.shemich.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shemich.api.request.TicketPurchaseRequest;
import ru.shemich.api.response.TicketPurchaseResponse;
import ru.shemich.api.response.TicketResponse;
import ru.shemich.model.Ticket;
import ru.shemich.service.TicketService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/tickets", produces = APPLICATION_JSON_VALUE)
public class TicketController {
    private final String headerUsername = "X-User-Name";

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping()
    public ResponseEntity<List<Ticket>> getAll(@RequestHeader (headerUsername) String username) {
        List<Ticket> tickets = ticketService.getAllByUsername(username);
        log.info("Fetching all tickets by {}", username);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{ticketUid}")
    public Ticket getByUidAndUsername(
            @PathVariable ("ticketUid") UUID ticketUid,
            @RequestHeader (headerUsername) String username
    ) {
        log.info("Fetching ticket with id={} by {}", ticketUid, username);
        return ticketService.getByUidAndUsername(ticketUid, username);
    }

    @PostMapping()
    public Ticket create(@RequestHeader (headerUsername) String username,
                                         @RequestBody TicketPurchaseRequest request) {
        log.info("Creating ticket for user: {} by {}", username, request);
        return ticketService.create(username, request);
    }


}
