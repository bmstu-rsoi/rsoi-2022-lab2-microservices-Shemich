package ru.shemich.bonusservice.service;

import ru.shemich.bonusservice.api.response.PrivilegeInfoResponse;

public interface PrivilegeService {
    PrivilegeInfoResponse getAll(String username);
}
