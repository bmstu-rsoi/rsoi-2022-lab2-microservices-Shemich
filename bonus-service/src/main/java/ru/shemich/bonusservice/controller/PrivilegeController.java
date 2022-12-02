package ru.shemich.bonusservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shemich.bonusservice.api.response.PrivilegeInfoResponse;
import ru.shemich.bonusservice.api.response.enums.OperationType;
import ru.shemich.bonusservice.model.Privilege;
import ru.shemich.bonusservice.model.PrivilegeHistory;
import ru.shemich.bonusservice.service.PrivilegeHistoryService;
import ru.shemich.bonusservice.service.PrivilegeService;

import java.util.List;

import static ru.shemich.bonusservice.api.response.enums.OperationType.FILL_IN_BALANCE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/privilege")
public class PrivilegeController {
    private final PrivilegeService privilegeService;
    private final PrivilegeHistoryService privilegeHistoryService;

    private final String headerUsername = "X-User-Name";


    @GetMapping()
    public PrivilegeInfoResponse getPrivilegeInfo(@RequestHeader(headerUsername) String username) {
        log.info("Fetching privilege. User: {}", username);
        return privilegeService.getPrivilegeInfo(username);
    }


    @PostMapping()
    public String updatePrivilege(@RequestHeader(headerUsername) String username,
                                  @RequestParam String paidFromBalance, int price, String ticketUid) {
        log.info("Updating privilege. User: {}, ticketUid: {}, paidFromBalance: {}, ", username, ticketUid, paidFromBalance);
        Privilege privilege = privilegeService.getByUsername(username);
        int balance = privilege.getBalance();
        OperationType operationType = privilegeService.setOperationType(Boolean.parseBoolean(paidFromBalance), balance);
        if (Boolean.parseBoolean(paidFromBalance)) {
            privilegeService.payFromBalance(privilege);
            privilegeService.giveBonuses(privilege, price - balance);
        } else {
            privilegeService.giveBonuses(privilege, price);
        }
        privilegeHistoryService.create(privilege, ticketUid, balance, operationType);
        return "Updated";
    }

    @DeleteMapping()
    public HttpStatus refundBonuses(
            @RequestHeader(headerUsername) String username,
            @RequestParam("ticketUid") String ticketUid) {
        log.info("Refunding privilege. User: {}, ticketUid: {}", username, ticketUid);
        Privilege privilege = privilegeService.getByUsername(username);
        List<PrivilegeHistory> privilegeHistoryList = privilegeHistoryService.getListByPrivilegeId(privilege.getId());
        PrivilegeHistory result = privilegeHistoryList.stream()
                        .filter(privilegeHistory -> ticketUid.equals(privilegeHistory.getTicketUid().toString()))
                        .findAny()
                        .orElse(null);
        if (result.getOperationType().equals(FILL_IN_BALANCE.toString())) {
            privilege.setBalance(privilege.getBalance() - result.getBalanceDiff());
        } else {
            privilege.setBalance(privilege.getBalance() + result.getBalanceDiff());
        }
        privilegeService.save(privilege);
        //  какой-то костыль все таки должен респонс нормальный быть
        return HttpStatus.NO_CONTENT;
    }
}
