package ru.shemich.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserInfoResponse {
    List<TicketResponse> tickets;
    PrivilegeShortInfo privilege;
}
