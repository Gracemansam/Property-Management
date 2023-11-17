package com.graceman.propertymangement.service.impl;

import com.graceman.propertymangement.converter.UserConverter;
import com.graceman.propertymangement.dto.UserDTO;
import com.graceman.propertymangement.exception.BusinessException;
import com.graceman.propertymangement.exception.ErrorModel;
import com.graceman.propertymangement.model.Address;
import com.graceman.propertymangement.model.User;
import com.graceman.propertymangement.repository.AddressRepository;
import com.graceman.propertymangement.repository.UserRepository;
import com.graceman.propertymangement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDTO register(UserDTO userDTO) {

        Optional<User> optUe = userRepository.findByOwnerEmail(userDTO.getOwnerEmail());
        if(optUe.isPresent()){
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("EMAIL_ALREADY_EXIST");
            errorModel.setMessage("The Email With Which You Are Trying To Register Already Exist!");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }

        User userEntity = userConverter.convertDTOtoEntity( userDTO);
        userEntity = userRepository.save(userEntity);

        Address addressEntity = new Address();
        addressEntity.setHouseNo(userDTO.getHouseNo());
        addressEntity.setCity(userDTO.getCity());
        addressEntity.setPostalCode(userDTO.getPostalCode());
        addressEntity.setStreet(userDTO.getStreet());
        addressEntity.setCountry(userDTO.getCountry());
        addressEntity.setUser(userEntity);

        addressRepository.save(addressEntity);

        userDTO = userConverter.convertEntityToDTO(userEntity);

        return userDTO;
    }

    @Override
    public UserDTO login(String email, String password) {
        UserDTO userDTO = null;
        Optional<User> optionalUserEntity = userRepository.findByOwnerEmailAndPassword(email, password);

        if(optionalUserEntity.isPresent()){
            userDTO = userConverter.convertEntityToDTO(optionalUserEntity.get());
        }else{

            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_LOGIN");
            errorModel.setMessage("Incorrect Email or Password");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }
        return userDTO;
    }
}
