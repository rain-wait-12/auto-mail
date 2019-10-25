package com.auto.mail.controller;


import com.auto.mail.crontab.MailTimedTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("mail")
public class MailController {

    @Resource
    private MailTimedTask mailTimedTask;
    @GetMapping("send")
    public void send(){
        mailTimedTask.send();
    }

}
