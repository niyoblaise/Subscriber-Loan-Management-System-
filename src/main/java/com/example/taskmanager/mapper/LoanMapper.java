package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.CreateLoan;
import com.example.taskmanager.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LoanMapper {


    @Mapping(source = "subscriberId",target = "subscriberId.subId")
    Loan toEntity(CreateLoan createLoan);
}
