package ru.shemich.bonusservice.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.shemich.bonusservice.api.response.enums.Status;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PrivilegeInfoResponse {
    Integer balance;
    Status status;
    List<BalanceHistory> history;
}
