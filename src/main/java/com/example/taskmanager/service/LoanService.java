package com.example.taskmanager.service;

import com.example.taskmanager.dto.CreateLoan;
import com.example.taskmanager.mapper.LoanMapper;
import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.LoanRecovery;
import com.example.taskmanager.model.LoanTransaction;
import com.example.taskmanager.model.Subscriber;
import com.example.taskmanager.model.enums.LoanStatus;
import com.example.taskmanager.model.enums.RecoveryStatus;
import com.example.taskmanager.model.enums.TransactionType;
import com.example.taskmanager.repository.LoanRepository;
import com.example.taskmanager.repository.LoanTransactionRepository;
import com.example.taskmanager.repository.PaymentRepository;
import com.example.taskmanager.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LoanService {


    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriberRepository subscriberRepository;
    private final LoanMapper loanMapper;
    private final LoanTransactionRepository loanTransactionRepository;

    public LoanService(@Autowired LoanRepository loanRepository,LoanMapper loanMapper,SubscriberRepository subscriberRepository,PaymentRepository paymentRepository,LoanTransactionRepository loanTransactionRepository) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.subscriberRepository=subscriberRepository;
        this.paymentRepository=paymentRepository;
        this.loanTransactionRepository = loanTransactionRepository;
    }


    public Loan createLoan(CreateLoan loan) {

        Loan loan1 = loanMapper.toEntity(loan);

        Subscriber sub = subscriberRepository.findById(loan.subscriberId()).orElseThrow(()-> new RuntimeException("Subscriber not found"));

        long activeLoanCount = sub.getLoans().stream()
                .filter(l -> l.getStatus() == LoanStatus.UNPAID)
                .count();
        if (activeLoanCount >= 3) {
            throw new RuntimeException("Access Denied: You have " + activeLoanCount +
                    " active loans. Please clear at least " + (activeLoanCount-2)+" Loans  one before applying for more.");
        }

        if(loan.loanAmount()>1000000){
            throw new RuntimeException("you cant exceed loan amount of 1,000,000");
        }
        loan1.addSUb(sub);
        loan1.setOutstandingLoan(loan.loanAmount());

        LoanTransaction loanTransaction = new LoanTransaction();
        loanTransaction.setLoan(loan1);
        loanTransaction.setAmount(loan.loanAmount());
        loanTransaction.setTransactionType(TransactionType.DISBURSEMENT);
        loanTransactionRepository.save(loanTransaction);

        LoanRecovery loanRecovery = new LoanRecovery();
        loanRecovery.setLoan(loan1);
        loanRecovery.setRemainingBalance(loan.loanAmount());
        loanRecovery.setRecoveredAmount(0);
        loanRecovery.setStatus(RecoveryStatus.ACTIVE);

        loan1.setLoanRecovery(loanRecovery);


        return loanRepository.save(loan1);


    }

    public Loan viewLoanDetails(UUID id){
        return loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));


    }




    public List<Loan> view(){
        return loanRepository.findAll();
    }


    public String outstandingLoan(UUID id){
        Loan loan = loanRepository.findById(id).orElseThrow(()-> new RuntimeException("Loan not found"));

        if (loan.getOutstandingLoan() > 0)
            return "OUTSTANDING LOAN:" + (loan.getLoanAmount() );
        else
            return "NO OUTSTANDING LOAN: " + loan.getOutstandingLoan();
    }

    public int calculateOutstandingLoan(UUID id){
        List<Loan> loans = loanRepository.findById(id).orElseThrow(()-> new RuntimeException("Loan not found")).getSubscriberId().getLoans();

        return loans.stream()
                .mapToInt(loan -> loan.getLoanAmount()-loan.getOutstandingLoan())
                .sum();
    }



    public List<Loan> viewActiveLoans(){
        return loanRepository.findAllByStatus(LoanStatus.UNPAID);
    }
}
