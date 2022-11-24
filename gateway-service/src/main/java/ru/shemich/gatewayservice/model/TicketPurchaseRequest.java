package ru.shemich.gatewayservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketPurchaseRequest {
    String flightNumber;
    Integer price;
    Boolean paidFromBalance;
}
