package com.example.taskmanager.repository;

import com.example.taskmanager.model.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, UUID> {
}
