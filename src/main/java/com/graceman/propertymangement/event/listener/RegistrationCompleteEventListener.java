package com.graceman.propertymangement.event.listener;

import com.graceman.propertymangement.converter.UserConverter;
import com.graceman.propertymangement.dto.UserDTO;
import com.graceman.propertymangement.event.RegistrationCompleteEvent;
import com.graceman.propertymangement.model.User;

import com.graceman.propertymangement.service.impl.AuthServiceImplementation;
import com.graceman.propertymangement.service.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
@RequiredArgsConstructor
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    private final AuthServiceImplementation authServiceImplementation ;
    private final UserConverter userConverter;

        private  final JavaMailSender mailSender;
        private User theUser;
        @Override
        public void onApplicationEvent(RegistrationCompleteEvent event) {
            // 1. Get the newly registered user
             theUser = event.getUser();
         //   theUser = userConverter.convertDTOtoEntity(user);
            //2. Create a verification token for the user
            String verificationToken = UUID.randomUUID().toString();
            //3. Save the verification token for the user
            authServiceImplementation.saveUserVerificationToken(theUser, verificationToken);
            //4 Build the verification url to be sent to the user
            String url = event.getApplicationUrl()+"/auth/verifyEmail?token="+verificationToken;
            //5. Send the email.
            try {
                sendVerificationEmail(url);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            //log.info("Click the link to verify your registration :  {}", url);
        }
        public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException, MessagingException {
            String subject = "Email Verification";
            String senderName = "User Registration Portal Service";
            String mailContent = "<p> Hi, "+ theUser.getOwnerName()+ ", </p>"+
                    "<p>Thank you for registering with us,"+"" +
                    "Please, follow the link below to complete your registration.</p>"+
                    "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                    "<p> Thank you <br> Users Registration Portal Service";
            MimeMessage message = mailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("adetoyesamuel63@gmail.com", senderName);
            messageHelper.setTo(theUser.getOwnerEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
        }
    }

