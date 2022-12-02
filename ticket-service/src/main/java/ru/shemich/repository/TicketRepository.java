package ru.shemich.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.model.Ticket;

import java.util.List;
import java.util.UUID;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUsername(String username);
    Ticket findByTicketUidAndUsername(UUID ticketUid, String username);
}
