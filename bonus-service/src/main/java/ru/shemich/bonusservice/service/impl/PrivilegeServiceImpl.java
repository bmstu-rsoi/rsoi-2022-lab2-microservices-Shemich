package ru.shemich.bonusservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shemich.bonusservice.api.response.BalanceHistory;
import ru.shemich.bonusservice.api.response.PrivilegeInfoResponse;
import ru.shemich.bonusservice.api.response.enums.OperationType;
import ru.shemich.bonusservice.model.Privilege;
import ru.shemich.bonusservice.model.PrivilegeHistory;
import ru.shemich.bonusservice.repository.PrivilegeHistoryRepository;
import ru.shemich.bonusservice.repository.PrivilegeRepository;
import ru.shemich.bonusservice.service.PrivilegeHistoryService;
import ru.shemich.bonusservice.service.PrivilegeService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.shemich.bonusservice.api.response.enums.OperationType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    private final PrivilegeHistoryService privilegeHistoryService;
    private final PrivilegeHistoryRepository privilegeHistoryRepository;


    @Override
    public void payFromBalance(Privilege privilege) {
        privilege.setBalance(0);
        privilegeRepository.save(privilege);
    }

    @Override
    public void giveBonuses(Privilege privilege, int price) {
        privilege.setBalance(privilege.getBalance() + price / 100 * 10);
        privilegeRepository.save(privilege);
    }

    @Override
    public OperationType setOperationType(boolean paidFromBalance, int balance) {
        log.info("TYPE: {}", FILL_IN_BALANCE);
        if (!paidFromBalance) {
            return FILL_IN_BALANCE;
        } else {
            if (balance > 0) {
                return DEBIT_THE_ACCOUNT;
            }
            return FILLED_BY_MONEY;
        }
    }

    @Override
    public PrivilegeInfoResponse getPrivilegeInfo(String username) {
        Privilege privilege = getByUsername(username);
        if (privilege == null) {
            log.warn("Privilege is null");
            return null;
        }
        List<PrivilegeHistory> privilegeHistoryList = privilegeHistoryRepository.findAllByPrivilegeId(privilege.getId());
        List<BalanceHistory> balanceHistoryList = privilegeHistoryList.stream()
                .map(privilegeHistory -> new BalanceHistory(
                        privilegeHistory.getDatetime(),
                        privilegeHistory.getTicketUid().toString(),
                        privilegeHistory.getBalanceDiff().toString(),
                        privilegeHistory.getOperationType()))
                .collect(Collectors.toList());

        return new PrivilegeInfoResponse(
                privilege.getBalance(),
                privilege.getStatus(), balanceHistoryList);
    }

    @Override
    public void save(Privilege privilege) {
        privilegeRepository.save(privilege);
    }

    @Override
    public Privilege getByUsername(String username) {
        return privilegeRepository.findByUsername(username);
    }
}
