package ru.shemich.bonusservice.service;

import ru.shemich.bonusservice.api.response.enums.OperationType;
import ru.shemich.bonusservice.model.Privilege;
import ru.shemich.bonusservice.model.PrivilegeHistory;

import java.util.List;
import java.util.UUID;

public interface PrivilegeHistoryService {

    List<PrivilegeHistory> getListByPrivilegeId(Long privilegeId);


    void create(Privilege privilege, String ticketUid, Integer balance, OperationType operationType);
}
