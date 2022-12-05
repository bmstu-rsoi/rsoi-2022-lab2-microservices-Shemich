package ru.shemich.gatewayservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.shemich.gatewayservice.model.*;
import ru.shemich.gatewayservice.service.GatewayService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class GatewayController {

    private final String bonusServiceUrl = "http://bonus:8050/api/v1";
    private final String flightServiceUrl = "http://flight:8060/api/v1";
    private final String ticketServiceUrl = "http://ticket:8070/api/v1";
    private final String headerUsername = "X-User-Name";

    private final GatewayService gatewayService;

    private WebClient clientFlight;

    private WebClient clientTicket;
    private WebClient clientBonus;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostConstruct
    private void setUpWebClient() {
        clientFlight = WebClient.create(flightServiceUrl);
        clientTicket = WebClient.create(ticketServiceUrl);
        clientBonus = WebClient.create(bonusServiceUrl);
    }

    @GetMapping("/flights")
    public Mono<PaginationResponse> getFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        log.info("GATEWAY: Fetching all flights");
        return clientFlight
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToMono(PaginationResponse.class);
    }

    @GetMapping("/privilege")
    public Mono<PrivilegeInfoResponse> getPrivilegeShortInfo (@RequestHeader(headerUsername) String username) {
        return clientBonus
                .get()
                .uri("/privilege")
                .header(headerUsername, username)
                .retrieve()
                .bodyToMono(PrivilegeInfoResponse.class);
    }

    @GetMapping("/me")
    public UserInfoResponse getUserInfo(@RequestHeader(headerUsername) String username) {
        Flux<TicketResponse> ticketResponseFlux = gatewayService.getTicketResponseList(clientTicket, username);
        List<TicketResponse> ticketResponseList = ticketResponseFlux.collect(Collectors.toList()).share().block();
        PrivilegeShortInfo privilegeShortInfo = gatewayService.getPrivilegeShortInfo(clientBonus, username);
        gatewayService.updateTicketResponseList(clientFlight, ticketResponseList);
        return new UserInfoResponse(ticketResponseList, privilegeShortInfo);
    }

    @GetMapping("/tickets/{ticketUid}")
    public TicketResponse getTicketByUidAndUsername(@PathVariable UUID ticketUid,
                                                    @RequestHeader(headerUsername) String username) {
        log.info("GATEWAY: Fetching ticket. UUID: {}, Username: {}", ticketUid, username);
        TicketResponse ticketResponse = clientTicket
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tickets" + "/" + ticketUid)
                        .build())
                .header(headerUsername, username)
                .retrieve()
                .bodyToMono(TicketResponse.class)
                .block();

        FlightResponse flightResponse = gatewayService.getFlightByTicketResponse(clientFlight, ticketResponse);

        ticketResponse.setFromAirport(flightResponse.getFromAirport());
        ticketResponse.setToAirport(flightResponse.getToAirport());
        ticketResponse.setDate(flightResponse.getDate());

        return ticketResponse;

    }
    @DeleteMapping("/tickets/{ticketUid}")
    public ResponseEntity<?> refundTicketByUidAndUsername(@PathVariable UUID ticketUid,
                                                       @RequestHeader(headerUsername) String username) {
        log.info("GATEWAY: Refunding ticket. UUID: {}, Username: {}", ticketUid, username);

        HttpStatus ticketRefundHttpStatus = gatewayService.refundTicket(clientTicket, ticketUid, username);  //  fix cringe
        HttpStatus bonusesRefundHttpStatus = gatewayService.refundBonuses(clientBonus, ticketUid, username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/tickets")
    public List<TicketResponse> getAllTickets(@RequestHeader(headerUsername) String username) {
        log.info("GATEWAY: Fetching all tickets. Username: {}", username);
        Flux<TicketResponse> ticketResponseFlux = gatewayService.getTicketResponseList(clientTicket, username);
        List<TicketResponse> ticketResponseList = ticketResponseFlux.collect(Collectors.toList()).share().block();
        return gatewayService.updateTicketResponseList(clientFlight, ticketResponseList);
    }

    @PostMapping("/tickets")
    public TicketPurchaseResponse purchase(@RequestHeader(headerUsername) String username,
                                           @RequestBody TicketPurchaseRequest request) {
        log.info("GATEWAY: Start purchase");
        String flightNumber = request.getFlightNumber();

        log.info("GATEWAY: Is flight exist?");
        String isExist = gatewayService.getExistResponse(clientFlight, flightNumber).block();

        if (isExist.equals("true")) {
            log.info("GATEWAY: Flight number: {} exist", flightNumber);
            FlightResponse flightResponse = gatewayService.getFlightByNumber(clientFlight, flightNumber);
            TicketPurchaseResponse ticketPurchaseResponse = gatewayService.getTicketPurchaseResponse(clientTicket, username, request);
            String updatePrivilege = gatewayService.updatePrivilege(clientBonus, username, request, ticketPurchaseResponse.getTicketUid());  //  wtf
            log.info("GATEWAY: Ticket purchased");
            return gatewayService.updateTicketPurchaseResponse(clientBonus, username, ticketPurchaseResponse, flightResponse, request);
        } else {
            return null;
        }
    }
}
