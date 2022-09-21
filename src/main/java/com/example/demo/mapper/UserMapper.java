package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = IgnoreMapperConfig.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User user);
    User fromDTO(UserDTO userDTO);
}
