package ru.shemich.bonusservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shemich.bonusservice.api.response.enums.OperationType;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;


@Data
@Entity
@FieldDefaults(level = PRIVATE)
@Table(name = "privilege_history")
public class PrivilegeHistory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "privilege_id")
    Long privilegeId;
    @Column(name = "ticket_uid")
    UUID ticketUid;
    @Column(name = "datetime")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date datetime;
    @Column(name = "balance_diff")
    Integer balanceDiff;
    @Column(name = "operation_type")
    String operationType;
}


