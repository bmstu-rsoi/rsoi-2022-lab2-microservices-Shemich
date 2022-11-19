package ru.shemich.flightservice.api.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationResponse {
    int page;
    int pageSize;
    int totalElements;
    List<FlightResponse> items;
}
