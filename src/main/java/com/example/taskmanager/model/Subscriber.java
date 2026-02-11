package com.example.taskmanager.model;

import com.example.taskmanager.model.enums.SubStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subscriber")
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID subId;

    @Column(unique = true)
    private String msidn;

    private String name;
    private SubStatus subscriberStatus = SubStatus.SUBSCRIBED;

    @OneToMany(mappedBy = "subscriberId")
    @JsonManagedReference
    private List<Loan> loans = new ArrayList<>();


    public Subscriber() {
    }

    public UUID getSubId() {
        return subId;
    }

    public void setSubId(UUID subId) {
        this.subId = subId;
    }

    public String getMsidn() {
        return msidn;
    }

    public void setMsidn(String msidn) {
        this.msidn = msidn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubStatus getSubscriberStatus() {
        return subscriberStatus;
    }

    public void setSubscriberStatus(SubStatus subscriberStatus) {
        this.subscriberStatus = subscriberStatus;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(Loan loan){
        loans.add(loan);
        loan.setSubscriberId(this);
    }
}
