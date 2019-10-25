package com.auto.mail.service.send;

import com.auto.mail.model.AutoMailParameters;

import java.io.FileNotFoundException;


/**
 * @author  create by rain on 2019-10-25
 * 发送邮件接口
 *
 */
public interface Send {

    /**
     * 发送不带附件的邮件
     */
     String SendNoAnnexMail(AutoMailParameters parameters) throws FileNotFoundException;

    /**
     * 发送带附件的邮件
     */
    String SendAnnexMail(AutoMailParameters parameters) throws FileNotFoundException;
}
