package ru.shemich.api.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.shemich.api.response.enums.PrivilegeStatus;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivilegeShortInfo {
    String balance;
    PrivilegeStatus status;
}