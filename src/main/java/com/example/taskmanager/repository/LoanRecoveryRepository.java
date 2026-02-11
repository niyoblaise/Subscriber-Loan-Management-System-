package com.example.taskmanager.repository;

import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.LoanRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanRecoveryRepository extends JpaRepository<LoanRecovery, UUID> {

   LoanRecovery findByLoan(Loan loan);
}
