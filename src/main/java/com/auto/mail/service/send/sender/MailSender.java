package com.auto.mail.service.send.sender;


import com.auto.mail.model.SendModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author create by rain on 2019-10-25
 * 创建邮件发送对象
 */
@Component
public class MailSender {


    /**
     * 邮件对象
     */
    private JavaMailSender javaMailSender;

    @Resource
    SendModel sendModel;

    /**
     * 初始化邮件发送对象
     * @return JavaMailSender
     */
    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(sendModel.getHost());
        javaMailSender.setUsername(sendModel.getUsername());
        javaMailSender.setPassword(sendModel.getPassword());
        String portStr = sendModel.getPort();
        if (StringUtils.isNotBlank(portStr)) {
            javaMailSender.setPort(Integer.parseInt(portStr));
        }
        String defaultEncoding = sendModel.getDefaultEncoding();
        if (StringUtils.isNotBlank(defaultEncoding)) {
            javaMailSender.setDefaultEncoding(defaultEncoding);
        }
        String auth = sendModel.getAuth();
        String debug =sendModel.getDebug();
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", StringUtils.isNotBlank(auth) ? auth : "true");
        properties.setProperty("mail.debug", StringUtils.isNotBlank(debug) ? debug : "true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    /**
     * 获取邮件发送对象
     * @return JavaMailSender
     */
     public JavaMailSender getJavaMail(){
        if(javaMailSender ==null){
            this.javaMailSender = getJavaMailSender();
        }
        return this.javaMailSender;
    }
}
