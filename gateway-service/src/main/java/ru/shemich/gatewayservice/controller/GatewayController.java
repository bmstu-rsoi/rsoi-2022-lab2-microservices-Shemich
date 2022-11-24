package ru.shemich.gatewayservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.shemich.gatewayservice.model.*;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class GatewayController {

    private final String bonusServiceUrl = "http://localhost:8050/api/v1";
    private final String flightServiceUrl = "http://localhost:8060/api/v1";
    private final String ticketServiceUrl = "http://localhost:8070/api/v1";
    private final String headerUsername = "X-User-Name";

    private WebClient clientFlight;
    private WebClient clientTicket;
    private WebClient clientBonus;

    @PostConstruct
    private void setUpWebClient() {
        clientFlight = WebClient.create("http://localhost:8060/api/v1");
        clientTicket = WebClient.create("http://localhost:8070/api/v1");
        clientBonus = WebClient.create("http://localhost:8050/api/v1");
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

    @GetMapping("/tickets/{ticketUid}")
    public TicketResponse getTicketByUidAndUsername(@PathVariable UUID ticketUid,
                                                    @RequestHeader(headerUsername) String username) {
        log.info("GATEWAY: Fetching ticket. UUID: {}, Username: {}", ticketUid, username);
        TicketResponse ticketResponse = clientTicket
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tickets/" + ticketUid)
                        .build())
                .header(headerUsername, username)
                .retrieve()
                .bodyToMono(TicketResponse.class)
                .block();

        FlightResponse flightResponse = clientFlight
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/get")
                        .queryParam("flightNumber", ticketResponse.getFlightNumber())
                        .build())
                .retrieve()
                .bodyToMono(FlightResponse.class)
                .block();

        ticketResponse.setFromAirport(flightResponse.getFromAirport());
        ticketResponse.setToAirport(flightResponse.getToAirport());
        ticketResponse.setDate(flightResponse.getDate());

        return ticketResponse;

    }

    @PostMapping("/tickets")
    public Object purchase(@RequestHeader(headerUsername) String username,
                           @RequestBody TicketPurchaseRequest request) {
        log.info("GATEWAY: Start purchase");
        String flightNumber = request.getFlightNumber();
        log.info("GATEWAY: Is flight exist?");
        Mono<String> responseIsExist = clientFlight
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/isExist")
                        .queryParam("flightNumber", flightNumber)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        String isExist = responseIsExist.block();

        if (isExist.equals("true")) {
            log.info("GATEWAY: Flight number: {} exist", flightNumber);

            Mono<FlightResponse> flightResponse;
            flightResponse = clientFlight
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/flights/get")
                            .queryParam("flightNumber", flightNumber)
                            .build())
                    .retrieve()
                    .bodyToMono(FlightResponse.class);

            FlightResponse response = flightResponse.block();

            TicketPurchaseResponse ticketPurchaseResponse;
            ticketPurchaseResponse = clientTicket
                    .post()
                    .uri("/tickets")
                    .header(headerUsername, username)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(TicketPurchaseResponse.class)
                    .block();



            ticketPurchaseResponse.setFromAirport(response.getFromAirport());
            ticketPurchaseResponse.setToAirport(response.getToAirport());
            ticketPurchaseResponse.setDate(response.getDate());
            ticketPurchaseResponse.setPaidByMoney(request.getPrice() - bonusMoney);
            ticketPurchaseResponse.setPrivilege(PrivelegeResponse.get);

            return ticketPurchaseResponse;
        } else {
            return "ohh no";
        }

        /*WebClient webClient = WebClient.create();
        String json = WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/isExist")
                        .queryParam("flightNumber", request.getFlightNumber())
                        .build())
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        log.info("GATEWAY: json: {}", json);



        String isExist = clientFlight.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/isExist")
                        .queryParam("flightNumber", request.getFlightNumber())
                        .build())
                .retrieve()
                .bodyToMono(PaginationResponse.class).toString();

        if (isExist.equals("true")) {
            log.info("GATEWAY: Flight number exist");
        } else {
            log.warn("GATEWAY: Not found flight number: {}", request.getFlightNumber());
        }
        return null;*/
    }
}
