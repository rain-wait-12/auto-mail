package com.auto.mail.service.mailService.impl;


import com.auto.mail.config.MailConfig;
import com.auto.mail.model.AutoMailParameters;
import com.auto.mail.model.SendModel;

import com.auto.mail.model.Work;
import com.auto.mail.service.mailService.SendMailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.auto.mail.service.send.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SendMailServiceImpl implements SendMailService {
    /**
     * 发送邮件接口
     */
    @Resource
    private Send send;
    /**
     * 邮件发送信息配置对象
     */
    @Resource
    private MailConfig mailConfig;

    /**
     * 邮件服务器基本配置
     */
    @Resource
    private SendModel sendModel;

    private String defaultPath;

    /**
     *  发送不带附件的邮件于指定的用户集
     *  发送对象来源于配置文件
     * @return  状态码 200 ok
     */
    public String SendNoAnnexMailService( AutoMailParameters mail) throws Exception{

        mail.setSendDate(new Date());

        return send.SendNoAnnexMail(mail);
    }

    /**
     *  发送不带附件的邮件于指定的用户集
     *  发送对象来源于配置文件
     * @return  状态码 200 ok
     */
    public String SendAnnexMailService( AutoMailParameters mail) throws Exception{

        if(StringUtils.isNotEmpty(mailConfig.getPath())){
            mail.setAnnexPath(mailConfig.getPath());
        }else {
          this.defaultPath =  ResourceUtils.getURL("classpath:")+"\\excel";
            mail.setAnnexPath(this.defaultPath);
        }
        /**
         * 内容待植入
         */

        mail.setSendDate(new Date());

        return send.SendAnnexMail(mail);

    }
}
