package ru.shemich.flightservice.api.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightResponse {
    String flightNumber;
    Integer fromAirportId;
    Integer toAirportId;
    Date datetime;
    Integer price;
}
