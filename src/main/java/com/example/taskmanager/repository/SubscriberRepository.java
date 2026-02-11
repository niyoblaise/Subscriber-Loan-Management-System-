package com.example.taskmanager.repository;

import com.example.taskmanager.model.Loan;
import com.example.taskmanager.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {


//    int countByLoans(UUID id);
}
