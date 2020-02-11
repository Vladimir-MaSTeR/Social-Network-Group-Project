package com.skillbox.sw.controller;

import com.skillbox.sw.domain.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @PostMapping("/register")
    public void add(Person account)
    {
         // put account in database
    }

    @PutMapping("/password/recovery")
    public void sendCodeOnEmail(String email)
    {
        // send code for reset on email
    }

    @PutMapping("/password/set")
    public void changePassword(String token, String password)
    {
        //change Password in database
    }

    @PutMapping("/email")
    public void changeEmail(String email)
    {
        //change email
    }

    @PutMapping("/notifications")
    public void changeSettingsNotifications(String notificationType, boolean enable)
    {
        // change settings
    }







}
