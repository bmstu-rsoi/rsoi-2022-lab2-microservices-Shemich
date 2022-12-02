package ru.shemich.flightservice.api.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightResponse {
    String flightNumber;
    String fromAirport;
    String toAirport;
    Date date;
    Long price;
}
