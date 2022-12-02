package ru.shemich.bonusservice.service;

import ru.shemich.bonusservice.api.response.PrivilegeInfoResponse;
import ru.shemich.bonusservice.api.response.enums.OperationType;
import ru.shemich.bonusservice.model.Privilege;

public interface PrivilegeService {
    PrivilegeInfoResponse getPrivilegeInfo(String username);

    void save(Privilege privilege);

    Privilege getByUsername(String username);

    void payFromBalance(Privilege privilege);

    void giveBonuses(Privilege privilege, int price);


    OperationType setOperationType(boolean paidFromBalance, int balance);

}
