package ru.shemich.bonusservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shemich.bonusservice.api.response.enums.OperationType;
import ru.shemich.bonusservice.model.Privilege;
import ru.shemich.bonusservice.model.PrivilegeHistory;
import ru.shemich.bonusservice.repository.PrivilegeHistoryRepository;
import ru.shemich.bonusservice.service.PrivilegeHistoryService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivilegeHistoryServiceImpl implements PrivilegeHistoryService {

    private final PrivilegeHistoryRepository privilegeHistoryRepository;
    @Override
    public List<PrivilegeHistory> getListByPrivilegeId(Long privilegeId) {
        return privilegeHistoryRepository.findAllByPrivilegeId(privilegeId);
    }

    @Override
    public void create(Privilege privilege, String ticketUid, Integer balance, OperationType operationType) {
        PrivilegeHistory privilegeHistory = new PrivilegeHistory();
        privilegeHistory.setPrivilegeId(privilege.getId());
        privilegeHistory.setTicketUid(UUID.fromString(ticketUid));
        privilegeHistory.setDatetime(new Date());
        privilegeHistory.setBalanceDiff(balance);
        privilegeHistory.setOperationType(operationType.toString());
        privilegeHistoryRepository.save(privilegeHistory);
    }
}
