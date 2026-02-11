package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.RecordPayment;
import com.example.taskmanager.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


        Payment toEntity(RecordPayment recordPayment);
}
