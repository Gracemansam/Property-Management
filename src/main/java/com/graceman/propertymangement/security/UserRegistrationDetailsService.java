package com.graceman.propertymangement.security;

import com.graceman.propertymangement.common_constant.CommonConstant;
import com.graceman.propertymangement.exception.BusinessException;
import com.graceman.propertymangement.exception.ErrorModel;
import com.graceman.propertymangement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
    @RequiredArgsConstructor
    public class UserRegistrationDetailsService implements UserDetailsService {
        private final UserRepository userRepository;





        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return userRepository.findByOwnerEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));
        }



}
