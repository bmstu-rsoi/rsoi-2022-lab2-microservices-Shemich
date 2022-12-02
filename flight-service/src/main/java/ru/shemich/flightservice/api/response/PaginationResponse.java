package ru.shemich.flightservice.api.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationResponse {
    int page;
    int pageSize;
    Long totalElements;
    List<FlightResponse> items;
}
