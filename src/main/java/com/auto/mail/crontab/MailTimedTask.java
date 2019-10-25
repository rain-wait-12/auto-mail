package com.auto.mail.crontab;


import com.auto.mail.config.MailConfig;
import com.auto.mail.model.MailSendParameters;
import com.auto.mail.utils.MailUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class MailTimedTask {

    @Resource
    private MailConfig mailConfig;

    @Resource
    private MailUtil mailUtil;
    public  void send(){
        System.out.println(mailConfig+"-----------------------");
        //System.out.println(mailConfig.getUsers()+"-----------------------");
        String[] split = mailConfig.getUsers().split(",");
        List<String> list = Arrays.asList(split);
        MailSendParameters mailSendParameters = new MailSendParameters();
        mailSendParameters.setDescription(mailConfig.getTitle());
        mailSendParameters.setSubject("WM-MOTOR");
        mailSendParameters.setReceivers(list);
        mailSendParameters.setSenderName(mailConfig.getName());
        mailSendParameters.setTime(new Date().toString());
        if (mailUtil.isMailEnabled()) {
            mailUtil.sendMail(mailSendParameters);
        }
    }

    public static void main(String[] args) {

        Long no = no();
        System.out.println("return ------"+no);
    }


    private  static  Long no(){
        try {
            return new Date().getTime();
        } finally {
            System.out.println("finally ------"+new Date().getTime());

        }
    }
}
