package com.devsu.operations.repositories;

import com.devsu.operations.models.Account;
import com.devsu.operations.models.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement,Long> {
    List<Movement> findAllByAccount(Account account);
}
