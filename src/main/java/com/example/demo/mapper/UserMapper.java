package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
