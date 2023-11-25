package com.graceman.propertymangement.controller;

import com.graceman.propertymangement.common_constant.CommonConstant;
import com.graceman.propertymangement.dto.UserDTO;
import com.graceman.propertymangement.event.RegistrationCompleteEvent;
import com.graceman.propertymangement.model.VerificationToken;
import com.graceman.propertymangement.service.UserService;


import com.graceman.propertymangement.utils.ApplicationUrl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/user/")
public class UserController {

   @Autowired
   private UserService userService;

}
