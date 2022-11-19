package ru.shemich.bonusservice.api.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.shemich.bonusservice.api.response.enums.Status;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivilegeInfoResponse {
    String balance;
    Status status;
    List<BalanceHistory> history;
}
