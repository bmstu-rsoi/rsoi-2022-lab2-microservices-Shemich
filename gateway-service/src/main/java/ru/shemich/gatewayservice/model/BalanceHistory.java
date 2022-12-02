package ru.shemich.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class BalanceHistory {
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date date;
    String ticketUid;
    String balanceDiff;
    String operationType;

}

