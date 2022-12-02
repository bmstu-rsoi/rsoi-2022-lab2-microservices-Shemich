package ru.shemich.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shemich.api.response.enums.TicketStatus;

import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class TicketResponse {
    String ticketUid;
    String flightNumber;
    String fromAirport;
    String toAirport;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    Date date;
    Integer price;
    TicketStatus status;
}
