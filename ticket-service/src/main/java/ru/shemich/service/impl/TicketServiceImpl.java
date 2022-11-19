package ru.shemich.service.impl;

import org.springframework.stereotype.Service;
import ru.shemich.model.Ticket;
import ru.shemich.repository.TicketRepository;
import ru.shemich.service.TicketService;

import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> getAllByUsername(String username) {
        return ticketRepository.findAllByUsername(username);
    }

    @Override
    public Ticket getByUidAndUsername(UUID ticketUid, String username) {
        return ticketRepository.findByTicketUidAndUsername(ticketUid, username);
    }
}
