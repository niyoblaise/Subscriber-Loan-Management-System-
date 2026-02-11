package com.example.taskmanager.model;

import com.example.taskmanager.model.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loanId;

    @ManyToOne
    @JoinColumn(name = "subId")
    @JsonBackReference
    private Subscriber subscriberId;

    private int loanAmount;
    private int outstandingLoan=0;

    //    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.UNPAID;

    private LocalDateTime createdAt;

    @OneToMany
    private List<Payment> payments= new ArrayList<>();

    @OneToMany(mappedBy = "loan",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LoanTransaction> loanTransactions = new ArrayList<>();

    @OneToOne(mappedBy = "loan",cascade = CascadeType.ALL)
    @JsonManagedReference
    private LoanRecovery loanRecovery;


    public LoanRecovery getLoanRecovery() {
        return loanRecovery;
    }

    public void addTransaction(LoanTransaction transaction){
        loanTransactions.add(transaction);
        transaction.setLoan(this);
    }

    public void setLoanRecovery(LoanRecovery loanRecovery) {
        this.loanRecovery = loanRecovery;
    }

    public List<LoanTransaction> getLoanTransactions() {
        return loanTransactions;
    }

    public void setLoanTransactions(List<LoanTransaction> loanTransactions) {
        this.loanTransactions = loanTransactions;
    }

    @PrePersist
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    public UUID getLoanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public Subscriber getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Subscriber subscriberId) {
        this.subscriberId = subscriberId;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getOutstandingLoan() {
        return outstandingLoan;
    }

    public void setOutstandingLoan(int outstandingLoan) {
        this.outstandingLoan = outstandingLoan;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void addSUb(Subscriber subscriber){
        this.subscriberId.addLoan(this);

    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
