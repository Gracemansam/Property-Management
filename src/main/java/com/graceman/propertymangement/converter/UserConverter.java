package com.graceman.propertymangement.converter;

import com.graceman.propertymangement.dto.UserDTO;
import com.graceman.propertymangement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {


    public User convertDTOtoEntity(UserDTO userDTO){
        User userEntity = new User();
        userEntity.setOwnerEmail(userDTO.getOwnerEmail());
        userEntity.setOwnerName(userDTO.getOwnerName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setPhoneNumber(userDTO.getPhone());
        return userEntity;
    }

    public UserDTO convertEntityToDTO(User userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setOwnerEmail(userEntity.getOwnerEmail());
        userDTO.setOwnerName(userEntity.getOwnerName());
        userDTO.setPhone(userEntity.getPhoneNumber());
        return userDTO;
    }
}