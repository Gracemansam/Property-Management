package com.graceman.propertymangement.service.impl;

import com.graceman.propertymangement.common_constant.CommonConstant;
import com.graceman.propertymangement.converter.UserConverter;
import com.graceman.propertymangement.dto.UserDTO;
import com.graceman.propertymangement.exception.BusinessException;
import com.graceman.propertymangement.exception.ErrorModel;
import com.graceman.propertymangement.model.Address;
import com.graceman.propertymangement.model.User;
import com.graceman.propertymangement.model.VerificationToken;
import com.graceman.propertymangement.repository.AddressRepository;
import com.graceman.propertymangement.repository.UserRepository;
import com.graceman.propertymangement.repository.VerificationTokenRepository;
import com.graceman.propertymangement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {



}
