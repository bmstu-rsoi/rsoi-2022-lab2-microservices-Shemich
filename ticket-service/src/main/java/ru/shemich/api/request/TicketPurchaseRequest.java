package ru.shemich.api.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketPurchaseRequest {
    String flightNumber;
    Integer price;
    Boolean paidFromBalance;
}
