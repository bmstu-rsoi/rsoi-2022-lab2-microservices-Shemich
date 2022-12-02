package ru.shemich.api.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shemich.api.response.enums.TicketStatus;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketPurchaseResponse {
    String ticketUid;
    String flightNumber;
    String fromAirport;
    String toAirport;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    Date date;
    Integer price;
    Integer paidByMoney;
    Integer paidByBonuses;
    TicketStatus status;
    PrivilegeShortInfo privilege;
}
