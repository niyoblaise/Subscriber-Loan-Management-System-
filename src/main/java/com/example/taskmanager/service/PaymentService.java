package com.example.taskmanager.service;

import com.example.taskmanager.dto.RecordPayment;
import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.LoanRecovery;
import com.example.taskmanager.model.LoanTransaction;
import com.example.taskmanager.model.Payment;
import com.example.taskmanager.model.enums.LoanStatus;
import com.example.taskmanager.model.enums.RecoveryStatus;
import com.example.taskmanager.model.enums.TransactionType;
import com.example.taskmanager.repository.LoanRecoveryRepository;
import com.example.taskmanager.repository.LoanRepository;
import com.example.taskmanager.repository.LoanTransactionRepository;
import com.example.taskmanager.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {


    private final PaymentRepository paymentRepository;
    private  final LoanRepository loanRepository;
    private final LoanService service;
    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanRecoveryRepository loanRecoveryRepository;


    public PaymentService(PaymentRepository paymentRepository,LoanRecoveryRepository loanRecoveryRepository,LoanRepository loanRepository,LoanService service,LoanTransactionRepository loanTransactionRepository){
        this.loanRepository=loanRepository;
        this.paymentRepository=paymentRepository;
        this.service=service;
        this.loanTransactionRepository = loanTransactionRepository;
        this.loanRecoveryRepository=loanRecoveryRepository;


    }


    public Payment recordLoanPayment(RecordPayment recordPayment){

        if(recordPayment.payment()<=0){
            throw new RuntimeException("Payment must be greater than zero");
        }


        Loan loanPay = loanRepository.findById(recordPayment.id()).orElseThrow(()-> new RuntimeException("Loan not found"));

        int loanpay = loanPay.getOutstandingLoan();

        if(recordPayment.payment()> loanpay)
            throw new RuntimeException(recordPayment.payment() +" is greater than the money you owe us: " + loanpay);


        int amount = loanpay - recordPayment.payment();





        Payment payment1 = new Payment();
        loanPay.setOutstandingLoan(amount);

        LoanTransaction loanTransaction = new LoanTransaction();
        loanTransaction.setLoan(loanPay);
        loanTransaction.setAmount(recordPayment.payment());
        loanTransaction.setTransactionType(TransactionType.REPAYMENT);


        loanPay.addTransaction(loanTransaction);

        payment1.setAmount(recordPayment.payment());
        payment1.setLoanId(loanPay);
        service.calculateOutstandingLoan(recordPayment.id());




        LoanRecovery loanRecovery = loanRecoveryRepository.findByLoan(loanPay);



        loanRecovery.setRecoveredAmount(loanRecovery.getRecoveredAmount() + recordPayment.payment());
        loanRecovery.setRemainingBalance(loanPay.getOutstandingLoan());

        if(loanPay.getOutstandingLoan()==0)
            loanRecovery.setStatus(RecoveryStatus.COMPLETED);
        loanRecoveryRepository.save(loanRecovery);

        if(loanPay.getOutstandingLoan()<=0)
            loanPay.setStatus(LoanStatus.PAID);


        return paymentRepository.save(payment1);

    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }


    public List<Payment> getPaymentsPerLoan(UUID id){
        Loan loan = loanRepository.findById(id).orElseThrow(()-> new RuntimeException("Loan not found"));
        return paymentRepository.findAllByLoan(loan);
    }

}
