package ru.shemich.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "ticket_uid")
    UUID ticketUid;
    @Column(name = "username", length = 80)
    String username;
    @Column(name = "flight_number", length = 20)
    String flightNumber;
    @Column(name = "price")
    Long price;
    @Column(name = "status")
    String status;
}
