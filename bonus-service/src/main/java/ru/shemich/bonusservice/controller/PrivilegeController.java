package ru.shemich.bonusservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shemich.bonusservice.api.response.PrivilegeInfoResponse;
import ru.shemich.bonusservice.service.PrivilegeService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/privilege")
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @GetMapping()
    public PrivilegeInfoResponse getPrivilegeInfo(@RequestHeader("X-User-Name") String username) {
        log.info("Fetching privilege by {}", username);
        return privilegeService.getAll(username);
    }
}
