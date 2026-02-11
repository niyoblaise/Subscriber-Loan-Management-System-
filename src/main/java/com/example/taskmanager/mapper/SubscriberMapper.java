package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.CreateSub;
import com.example.taskmanager.dto.ResponseDto;
import com.example.taskmanager.model.Subscriber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {

    Subscriber toEntity(CreateSub createSub);
    ResponseDto toDto(Subscriber subscriber);
}
