package com.graceman.propertymangement.service.impl;

import com.graceman.propertymangement.common_constant.CommonConstant;
import com.graceman.propertymangement.converter.UserConverter;
import com.graceman.propertymangement.dto.UserDTO;
import com.graceman.propertymangement.dto.response.LoginResponse;
import com.graceman.propertymangement.event.RegistrationCompleteEvent;
import com.graceman.propertymangement.exception.BusinessException;
import com.graceman.propertymangement.exception.ErrorModel;
import com.graceman.propertymangement.model.Address;
import com.graceman.propertymangement.model.Role;
import com.graceman.propertymangement.model.User;
import com.graceman.propertymangement.model.VerificationToken;
import com.graceman.propertymangement.repository.AddressRepository;
import com.graceman.propertymangement.repository.RoleRepository;
import com.graceman.propertymangement.repository.UserRepository;
import com.graceman.propertymangement.repository.VerificationTokenRepository;
import com.graceman.propertymangement.security.TokenService;
import com.graceman.propertymangement.utils.ApplicationUrl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImplementation {
   @Autowired
    private UserRepository userRepository;
   @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ApplicationUrl applicationUrl;



    public UserDTO register(UserDTO userDTO, HttpServletRequest request) {

        Optional<User> optUe = userRepository.findByOwnerEmail(userDTO.getOwnerEmail());
        if (optUe.isPresent()) {
            //  List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode( userDTO.getOwnerEmail() + " " + CommonConstant.USER_ALREADY_EXIST_CODE);
            errorModel.setMessage(CommonConstant.USER_ALREADY_EXIST);
            // errorModelList.add(errorModel);
            throw new BusinessException(errorModel);
        }


        User userEntity = userConverter.convertDTOtoEntity(userDTO);
        Role role = new Role();
        role.setAuthority("USER");
        roleRepository.save(role);
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        userEntity.setAuthorities(authorities);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);



        Address addressEntity = new Address();
        addressEntity.setHouseNo(userDTO.getHouseNo());
        addressEntity.setCity(userDTO.getCity());
        addressEntity.setPostalCode(userDTO.getPostalCode());
        addressEntity.setStreet(userDTO.getStreet());
        addressEntity.setCountry(userDTO.getCountry());
        addressEntity.setUser(userEntity);
        addressRepository.save(addressEntity);

        try {

            publisher.publishEvent(new RegistrationCompleteEvent(userEntity, applicationUrl.applicationUrl(request)));
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BusinessException(new ErrorModel("500", "Error while sending  verification email"));
        }

        userDTO = userConverter.convertEntityToDTO(userEntity);

        return userDTO;
    }


    public LoginResponse login(String email, String password) {
       // UserDTO userDTO = null;
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);
            User user = userRepository.findByOwnerEmail(email).get();
        //    UserDTO returnUserDTO = userConverter.convertEntityToDTO(user);

            return new LoginResponse(user.getOwnerName(), user.getOwnerEmail(), token);

        } catch(AuthenticationException e){
            e.printStackTrace();
            throw new BusinessException(new ErrorModel("401", "Invalid username or password"));
        }
    }


    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return CommonConstant.TOKEN_NOT_VALID;
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return CommonConstant.TOKEN_ALREADY_EXPIRED;
        }
        user.setEnabled(true);
        userRepository.save(user);
        return CommonConstant.TOKEN_VALID;
    }

    public VerificationToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }


}
