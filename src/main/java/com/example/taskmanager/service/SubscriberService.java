package com.example.taskmanager.service;

import com.example.taskmanager.dto.CreateSub;
import com.example.taskmanager.mapper.SubscriberMapper;
import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.Subscriber;
import com.example.taskmanager.model.enums.LoanStatus;
import com.example.taskmanager.model.enums.SubStatus;
import com.example.taskmanager.repository.SubscriberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;

    public SubscriberService(SubscriberRepository subscriberRepository,SubscriberMapper subscriberMapper) {
        this.subscriberRepository = subscriberRepository;
        this.subscriberMapper=subscriberMapper;
    }


    //Create subscriber

    public Subscriber createSubscriber(CreateSub createSub){

        Subscriber sub = subscriberMapper.toEntity(createSub);

        return subscriberRepository.save(sub);
    }

    //R

    public List<Subscriber> getAllSubscribes(){
        return subscriberRepository.findAll();
    }

    public Optional<Subscriber> getById(UUID id){
        return subscriberRepository.findById(id);
    }

    public String changeStatus(UUID id){
        Subscriber sub = getById(id).orElseThrow();


        sub.setSubscriberStatus(SubStatus.NOT_SUBSCRIBED);
        subscriberRepository.save(sub);
        return "Status changed successfully";
    }

    public int calculateTotalDebt(UUID subscriberId) {
        Subscriber sub = subscriberRepository.findById(subscriberId).orElseThrow();
        return sub.getLoans().stream()
                .mapToInt(Loan::getOutstandingLoan)
                .sum();
    }

    public List<Loan> getActiveLoans(UUID subscriberId) {
        Subscriber sub = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));


        return sub.getLoans().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.UNPAID)
                .toList();
    }

    public List<Loan> getAllLoansBySubscriber(UUID subscriberId) {
        Subscriber sub = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        return sub.getLoans();
    }
}
