package ru.shemich.bonusservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.bonusservice.model.PrivilegeHistory;

import java.util.List;

public interface PrivilegeHistoryRepository extends JpaRepository<PrivilegeHistory, Long> {
    List<PrivilegeHistory> findAllByPrivilegeId(Long privilegeId);
}
