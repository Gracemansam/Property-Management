package com.graceman.propertymangement.controller;

import com.graceman.propertymangement.common_constant.CommonConstant;
import com.graceman.propertymangement.dto.LoginRequest;
import com.graceman.propertymangement.dto.UserDTO;
import com.graceman.propertymangement.dto.response.LoginResponse;
import com.graceman.propertymangement.event.RegistrationCompleteEvent;
import com.graceman.propertymangement.model.VerificationToken;
import com.graceman.propertymangement.service.UserService;
import com.graceman.propertymangement.service.impl.AuthServiceImplementation;
import com.graceman.propertymangement.utils.ApplicationUrl;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImplementation authServiceImplementation;



    @ApiOperation(value = "register", notes = "This method is used for user registration")

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request){
        userDTO = authServiceImplementation.register(userDTO, request);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse login  = authServiceImplementation.login(loginRequest.getEmail(), loginRequest.getPassword());
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = authServiceImplementation.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return CommonConstant.EXISTING_USER_VERIFICATION_MESSAGE;
        }
        String verificationResult = authServiceImplementation.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return CommonConstant.VERIFICATION_MESSAGE_C;
        }
        return CommonConstant.TOKEN_NOT_VALID;
    }

}
