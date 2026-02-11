package com.example.taskmanager.controller;

import com.example.taskmanager.dto.CreateSub;
import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.Subscriber;

import com.example.taskmanager.service.SubscriberService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/subscriber")
public class SubscriberController {



    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService){
        this.subscriberService=subscriberService;
    }


    @PostMapping
    public Subscriber createnewSubscriber(@RequestBody CreateSub createSub){
        return subscriberService.createSubscriber(createSub);
    }

    @GetMapping
    public List<Subscriber> getAllSusbcribes(){
        return subscriberService.getAllSubscribes();
    }

    @GetMapping("/{id}")
    public Optional<Subscriber> getSusbcribes(@PathVariable UUID id){
        return subscriberService.getById(id);
    }


    @PatchMapping("/{id}/status")
    public String changeStatus(@PathVariable UUID id){
        return subscriberService.changeStatus(id);
    }

    @GetMapping("/loan/{id}")
    public int calculateTotalDebt(@PathVariable UUID id) {
        return subscriberService.calculateTotalDebt(id);
    }


    @GetMapping("/{id}/loans/active")
    public ResponseEntity<List<Loan>> getActiveLoans(@PathVariable UUID id) {
        return ResponseEntity.ok(subscriberService.getActiveLoans(id));
    }

    @GetMapping("/{id}/loans/all")
    public ResponseEntity<List<Loan>> getAllLoans(@PathVariable UUID id) {
        return ResponseEntity.ok(subscriberService.getAllLoansBySubscriber(id));
    }
}
