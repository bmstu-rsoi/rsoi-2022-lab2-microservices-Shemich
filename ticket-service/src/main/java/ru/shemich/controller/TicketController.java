package ru.shemich.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shemich.model.Ticket;
import ru.shemich.service.TicketService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/tickets", produces = APPLICATION_JSON_VALUE)
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping()
    public ResponseEntity<List<Ticket>> getAll(@RequestHeader (name = "X-User-Name") String username) {
        List<Ticket> tickets = ticketService.getAllByUsername(username);
        log.info("Fetching all tickets by {}", username);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{ticketUid}")
    public ResponseEntity<Ticket> getByUid(@RequestParam ("ticketUid") UUID ticketUid, @RequestHeader (name = "X-User-Name") String username) {
        Ticket ticket = ticketService.getByUidAndUsername(ticketUid, username);
        log.info("Fetching ticket with id={} by {}", ticketUid, username);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Ticket> create(@RequestHeader (name = "X-User-Name") String username) {
        Ticket ticket = ticketService.create(username);
        log.info("Creating ticket with id={} by user={}", ticket.getTicketUid(), username);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Ticket> refund(@RequestParam ("ticketUid") UUID ticketUid, @RequestHeader (name = "X-User-Name") String username) {
        Ticket ticket = ticketService.refund(ticketUid, username);
        log.info("Deleting ticket with id={} by user={}", ticket.getTicketUid(), username);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

}
