package com.example.taskmanager.dto;


import java.util.UUID;

public record CreateLoan(UUID subscriberId, int loanAmount) {
}
