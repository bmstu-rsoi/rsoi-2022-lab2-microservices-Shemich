package ru.shemich.service;

import ru.shemich.api.request.TicketPurchaseRequest;
import ru.shemich.api.response.TicketPurchaseResponse;
import ru.shemich.api.response.TicketResponse;
import ru.shemich.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    List<Ticket> getAllByUsername(String username);

    Ticket getByUidAndUsername(UUID ticketUid, String username);

    Ticket create(String username, TicketPurchaseRequest request);

    Ticket refundTicket(Ticket ticket);
}
