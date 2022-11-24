package ru.shemich.bonusservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shemich.bonusservice.api.response.BalanceHistory;
import ru.shemich.bonusservice.api.response.PrivilegeInfoResponse;
import ru.shemich.bonusservice.model.Privilege;
import ru.shemich.bonusservice.model.PrivilegeHistory;
import ru.shemich.bonusservice.repository.PrivilegeHistoryRepository;
import ru.shemich.bonusservice.repository.PrivilegeRepository;
import ru.shemich.bonusservice.service.PrivilegeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeHistoryRepository privilegeHistoryRepository;

    @Override
    public PrivilegeInfoResponse getAll(String username) {
        Privilege privilege = privilegeRepository.findByUsername(username);
        Long privilegeId = privilege.getId();
        List<PrivilegeHistory> privilegeHistoryList = privilegeHistoryRepository.findAllByPrivilegeId(privilegeId);
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
}
