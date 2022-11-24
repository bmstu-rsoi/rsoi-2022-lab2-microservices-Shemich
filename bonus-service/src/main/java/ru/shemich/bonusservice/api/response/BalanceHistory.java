package ru.shemich.bonusservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shemich.bonusservice.api.response.enums.Operation;

import java.util.Date;
import java.util.UUID;

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
    Operation operationType;

}

