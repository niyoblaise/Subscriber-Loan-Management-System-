package com.example.taskmanager.controller;

import com.example.taskmanager.dto.CreateLoan;
import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.LoanRecovery;
import com.example.taskmanager.model.LoanTransaction;
import com.example.taskmanager.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService=loanService;
    }

    @PostMapping
    public Loan createLoan(@RequestBody CreateLoan loan){
        return loanService.createLoan(loan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> viewLoanDetails(@PathVariable UUID id){
        Loan loan = loanService.viewLoanDetails(id);
        return ResponseEntity.ok(Map.of(
                "message", "Loan details and payment history retrieved",
                "data", loan
        ));
    }

    @GetMapping
    public List<Loan> LoanDetails(){
        return loanService.view();
    }

    @GetMapping("/outstanding/{id}")
    public String calculateOutstandingLoan(@PathVariable UUID id){
        return loanService.outstandingLoan(id) ;
    }

    @GetMapping("/active/total")
    public List<Loan> totalLoanAmount(){
        return loanService.viewActiveLoans();
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<LoanTransaction>> getLoanTransactions(@PathVariable UUID id) {
        return ResponseEntity.ok(loanService.viewLoanDetails(id).getLoanTransactions());
    }

    @GetMapping("/{id}/recovery")
    public ResponseEntity<LoanRecovery> getLoanRecovery(@PathVariable UUID id) {
        return ResponseEntity.ok(loanService.viewLoanDetails(id).getLoanRecovery());
    }
}
