package ru.shemich.bonusservice.api.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shemich.bonusservice.api.response.enums.Operation;

import java.util.Date;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BalanceHistory {
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date date;
    String ticketUid;
    String balanceDiff;
    Operation operationType;
}

