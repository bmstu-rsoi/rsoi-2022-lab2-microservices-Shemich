package ru.shemich.gatewayservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.shemich.gatewayservice.model.*;

import java.util.List;
import java.util.UUID;


public interface GatewayService {
    FlightResponse getFlightByTicketResponse(WebClient clientFlight, TicketResponse ticketResponse);
    FlightResponse getFlightByNumber(WebClient clientFlight, String flightNumber);
    Mono<String> getExistResponse(WebClient clientFlight, String flightNumber);

    TicketPurchaseResponse getTicketPurchaseResponse(WebClient clientTicket, String username, TicketPurchaseRequest request);

    String updatePrivilege(WebClient clientBonus, String username, TicketPurchaseRequest request, String ticketUid);

    PrivilegeShortInfo getPrivilegeShortInfo(WebClient clientBonus, String username);

    TicketPurchaseResponse updateTicketPurchaseResponse(WebClient clientBonus, String username, TicketPurchaseResponse ticketPurchaseResponse, FlightResponse flightResponse, TicketPurchaseRequest request);

    Flux<TicketResponse> getTicketResponseList(WebClient clientTicket, String username);

    List<TicketResponse> updateTicketResponseList(WebClient clientFlight, List<TicketResponse> ticketResponseList);

    HttpStatus refundTicket(WebClient clientTicket, UUID ticketUid, String username);

    HttpStatus refundBonuses(WebClient clientBonus, UUID ticketUid, String username);
}
