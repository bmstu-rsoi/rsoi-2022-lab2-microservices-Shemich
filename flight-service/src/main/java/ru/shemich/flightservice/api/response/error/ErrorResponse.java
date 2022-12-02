package ru.shemich.flightservice.api.response.error;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class ErrorResponse {
    String message;
}
