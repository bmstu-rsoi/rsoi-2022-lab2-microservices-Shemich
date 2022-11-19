package ru.shemich.service;

import ru.shemich.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    List<Ticket> getAllByUsername(String username);

    Ticket getByUidAndUsername(UUID ticketUid, String username);
}
