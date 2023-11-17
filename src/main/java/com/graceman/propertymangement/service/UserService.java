package com.graceman.propertymangement.service;


import com.graceman.propertymangement.dto.UserDTO;

public interface UserService {

    UserDTO register(UserDTO userDTO);

    UserDTO login(String email, String password);

}
