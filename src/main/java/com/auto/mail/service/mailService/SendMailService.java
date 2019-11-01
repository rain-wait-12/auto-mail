package com.auto.mail.service.mailService;

import com.auto.mail.model.AutoMailParameters;
import com.auto.mail.model.Work;

public interface SendMailService {

    /**
     * 发送不带附件的邮件
     * @return
     */
    String SendNoAnnexMailService( AutoMailParameters mail) throws Exception;

    /**
     * 发送带附件的邮件
     * @return
     */
    String SendAnnexMailService( AutoMailParameters mail) throws Exception;
}
