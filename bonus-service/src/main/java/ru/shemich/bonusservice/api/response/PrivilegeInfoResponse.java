package ru.shemich.bonusservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.shemich.bonusservice.api.response.enums.Status;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PrivilegeInfoResponse {
    Integer balance;
    Status status;
    List<BalanceHistory> history;
}
