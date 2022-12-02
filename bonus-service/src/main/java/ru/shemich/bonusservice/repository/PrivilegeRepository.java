package ru.shemich.bonusservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.bonusservice.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByUsername(String username);
}