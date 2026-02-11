package com.example.taskmanager.model;

import com.example.taskmanager.model.enums.RecoveryStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "loan_recovery")
public class LoanRecovery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loanRecoveryId;

    @OneToOne
    @JoinColumn(name = "loanId")
    @JsonBackReference
    private Loan loan;

    private int recoveredAmount=0;
    private int remainingBalance;

    @Enumerated(EnumType.STRING)
    private RecoveryStatus status = RecoveryStatus.ACTIVE;

    //private String remarks;



    public UUID getLoanRecoveryId() {
        return loanRecoveryId;
    }

    public void setLoanRecoveryId(UUID loanRecoveryId) {
        this.loanRecoveryId = loanRecoveryId;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public int getRecoveredAmount() {
        return recoveredAmount;
    }

    public void setRecoveredAmount(int recoveredAmount) {
        this.recoveredAmount = recoveredAmount;
    }

    public int getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(int remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public RecoveryStatus getStatus() {
        return status;
    }

    public void setStatus(RecoveryStatus status) {
        this.status = status;
    }

//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }
}
