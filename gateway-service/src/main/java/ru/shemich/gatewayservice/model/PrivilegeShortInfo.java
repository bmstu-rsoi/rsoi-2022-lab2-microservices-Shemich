package ru.shemich.gatewayservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.shemich.gatewayservice.model.enums.PrivilegeStatus;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivilegeShortInfo {
    String balance;
    PrivilegeStatus status;
}