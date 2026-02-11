package com.example.taskmanager.repository;

import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {


    List<Loan> findAllByStatus(LoanStatus status);


}
