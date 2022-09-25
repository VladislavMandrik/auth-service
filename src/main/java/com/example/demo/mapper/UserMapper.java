package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreMapperConfig.class)
public interface UserMapper {

    UserDTO toDTO(User user);
    User fromDTO(UserDTO userDTO);
}
