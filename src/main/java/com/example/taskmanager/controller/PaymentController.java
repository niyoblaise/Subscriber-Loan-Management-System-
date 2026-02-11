package com.example.taskmanager.controller;

import com.example.taskmanager.dto.RecordPayment;
import com.example.taskmanager.model.Payment;
import com.example.taskmanager.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {


    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService=paymentService;
    }


    @PostMapping

    public Payment recordLoanPayment(@RequestBody RecordPayment recordPayment){
        return paymentService.recordLoanPayment(recordPayment);

    }


    @GetMapping
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayments();
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<Object> getPaymentPerLoan(@PathVariable UUID loanId){
        List<Payment> payments = paymentService.getPaymentsPerLoan(loanId);

        return ResponseEntity.ok(Map.of(
                "message","Payment History for loan " + loanId,
                "total payments:", payments.size(),
                "data",payments
        ));
    }

}
