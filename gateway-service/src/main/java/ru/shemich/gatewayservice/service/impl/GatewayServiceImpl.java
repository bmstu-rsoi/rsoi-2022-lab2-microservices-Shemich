package ru.shemich.gatewayservice.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.shemich.gatewayservice.model.*;
import ru.shemich.gatewayservice.service.GatewayService;

import java.util.List;
import java.util.UUID;

@Service
public class GatewayServiceImpl implements GatewayService {
    private final String headerUsername = "X-User-Name";


    public FlightResponse getFlightByTicketResponse(WebClient clientFlight, TicketResponse ticketResponse) {
        String flightNumber = ticketResponse.getFlightNumber();
        return clientFlight
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/get")
                        .queryParam("flightNumber", flightNumber)
                        .build())
                .retrieve()
                .bodyToMono(FlightResponse.class)
                .block();
    }

    public Mono<String> getExistResponse(WebClient clientFlight, String flightNumber) {
        return clientFlight
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/isExist")
                        .queryParam("flightNumber", flightNumber)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public TicketPurchaseResponse getTicketPurchaseResponse(WebClient clientTicket, String username, TicketPurchaseRequest request) {
        return clientTicket
                .post()
                .uri("/tickets")
                .header(headerUsername, username)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TicketPurchaseResponse.class)
                .block();
    }

    @Override
    public String updatePrivilege(WebClient clientBonus, String username, TicketPurchaseRequest request, String ticketUid) {
        return clientBonus
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/privilege")
                        .queryParam("paidFromBalance", request.getPaidFromBalance())
                        .queryParam("price", request.getPrice())
                        .queryParam("ticketUid", ticketUid)
                        .build())
                .header(headerUsername, username)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public PrivilegeShortInfo getPrivilegeShortInfo(WebClient clientBonus, String username) {
        return clientBonus
                .get()
                .uri("/privilege")
                .header(headerUsername, username)
                .retrieve()
                .bodyToMono(PrivilegeShortInfo.class)
                .block();
    }

    @Override
    public TicketPurchaseResponse updateTicketPurchaseResponse(WebClient clientBonus,
                                                               String username,
                                                               TicketPurchaseResponse ticketPurchaseResponse,
                                                               FlightResponse flightResponse,
                                                               TicketPurchaseRequest request) {
        PrivilegeShortInfo privilegeShortInfo  = getPrivilegeShortInfo(clientBonus, username);
        ticketPurchaseResponse.setFromAirport(flightResponse.getFromAirport());
        ticketPurchaseResponse.setToAirport(flightResponse.getToAirport());
        ticketPurchaseResponse.setDate(flightResponse.getDate());
        ticketPurchaseResponse.setPrivilege(privilegeShortInfo);

        if (request.getPaidFromBalance()) {
            ticketPurchaseResponse.setPaidByBonuses(privilegeShortInfo.getBalance());
            ticketPurchaseResponse.setPaidByMoney(ticketPurchaseResponse.getPrice() - (privilegeShortInfo.getBalance()));
        } else {
            ticketPurchaseResponse.setPaidByBonuses(0);
            ticketPurchaseResponse.setPaidByMoney(ticketPurchaseResponse.getPrice());
        }
        return ticketPurchaseResponse;
    }

    @Override
    public Flux<TicketResponse> getTicketResponseList(WebClient clientTicket, String username) {
        return clientTicket
                .get()
                .uri("/tickets")
                .header(headerUsername, username)
                .retrieve()
                .bodyToFlux(TicketResponse.class);
    }

    @Override
    public List<TicketResponse> updateTicketResponseList(WebClient clientFlight, List<TicketResponse> ticketResponseList) {
        ticketResponseList.forEach(ticketResponse -> {
            FlightResponse flightResponse = getFlightByTicketResponse(clientFlight, ticketResponse);
            ticketResponse.setFromAirport(flightResponse.getFromAirport());
            ticketResponse.setToAirport(flightResponse.getToAirport());
            ticketResponse.setDate(flightResponse.getDate());
        });
        return ticketResponseList;
    }

    @Override
    public HttpStatus refundBonuses(WebClient clientBonus, UUID ticketUid, String username) {
        return clientBonus
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/privilege")
                        .queryParam("ticketUid", ticketUid)
                        .build())
                .header(headerUsername, username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .block();
    }

    @Override
    public HttpStatus refundTicket(WebClient clientTicket, UUID ticketUid, String username) {
        return clientTicket
                .delete()
                .uri("/tickets/" + ticketUid)
                .header(headerUsername, username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .block();
    }

    public FlightResponse getFlightByNumber(WebClient clientFlight, String flightNumber) {
        return clientFlight
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/get")
                        .queryParam("flightNumber", flightNumber)
                        .build())
                .retrieve()
                .bodyToMono(FlightResponse.class).
                block();
    }
}
