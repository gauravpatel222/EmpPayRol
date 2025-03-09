package com.example.EmployeePayroll.controller;

import com.example.EmployeePayroll.dto.*;
import com.example.EmployeePayroll.interfaces.IAuthInterface;
import com.example.EmployeePayroll.services.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {
    ObjectMapper obj = new ObjectMapper();

    @Autowired
    EmailService emailService;

    @Autowired
    IAuthInterface iAuthInterface;

    //UC9 --> For Registration of a user
    @PostMapping(path = "/register")
    public String register(@RequestBody AuthUserDTO user) throws Exception{
        log.info("Employee tried to register with body: {}", obj.writeValueAsString(user));
        return iAuthInterface.register(user);
    }

    //UC10 --> For User Login
    @PostMapping(path ="/login")
    public String login(@RequestBody LoginDTO user) throws Exception{
        log.info("Employee tried to login with body: {}", obj.writeValueAsString(user));
        return iAuthInterface.login(user);
    }

    //UC11 --> For sending mail to another person
    @PostMapping(path = "/sendMail")
    public String sendMail(@RequestBody MailDTO message) throws Exception{
        log.info("Employee tried to send email with body: {}", obj.writeValueAsString(message));
        emailService.sendEmail(message.getTo(), message.getSubject(), message.getBody());
        return "Mail sent";
    }

    //UC12 --> Added Swagger Config to use Swagger at url(/swagger)


    //UC13 --> Added forgot password functionality
    @PutMapping("/forgotPassword/{email}")
    public AuthUserDTO forgotPassword(@RequestBody PassDTO pass, @PathVariable String email) throws Exception{
        log.info("Employee applied for forgot password with body: {}", obj.writeValueAsString(pass));
        return iAuthInterface.forgotPassword(pass, email);
    }

}
