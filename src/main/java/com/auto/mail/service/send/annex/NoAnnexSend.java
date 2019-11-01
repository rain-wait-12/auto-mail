package com.auto.mail.service.send.annex;

import com.auto.mail.model.AutoMailParameters;
import com.auto.mail.model.SendModel;
import com.auto.mail.service.send.Send;
import com.auto.mail.service.send.sender.MailSender;
import com.auto.mail.utils.OptExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author create by rain on 2019-20-30
 * 发送邮件实现类
 * SendNoAnnexMail 发送不带附件得邮件
 * SendAnnexMail 发送带附件邮件
 */
@Component
public class NoAnnexSend implements Send {

    Logger logger = LoggerFactory.getLogger(NoAnnexSend.class);

    /**
     * 获取邮件对象
     */
    @Resource
    MailSender mailSender;

    /**
     * 附件选择策略
     */
    @Resource
    OptExcelUtils optExcelUtils;

    /**
     * 邮件配置
     */
    @Resource
    SendModel sendModel;

    /**
     * 发送不带附件的邮件
     *
     * @return 发送状态 200 发送成功  其他状态码 发送失败
     */
    public String SendNoAnnexMail(AutoMailParameters parameters) {


        JavaMailSender javaMailSender = mailSender.getJavaMail();

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            initMimeMessageHelper(mimeMessageHelper, parameters);
            javaMailSender.send(mimeMessage);
            logger.info("Meeting [mail not have annex] send success...");
            return "200";
        } catch (MessagingException e) {
            logger.info("Meeting [mail not have annex] send failed...", e);
        } catch (FileNotFoundException e) {
            logger.info("file not found");
        }
        return "500";
    }

    /**
     * 发送带附件的邮件
     *
     * @return 发送状态 200 发送成功  其他状态码 发送失败
     */
    public String SendAnnexMail(AutoMailParameters parameters) throws FileNotFoundException {

        String defaultPath = "";
        JavaMailSender javaMailSender = mailSender.getJavaMail();

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            initMimeMessageHelper(mimeMessageHelper, parameters);
            String excelName = optExcelUtils.getExcelName() + ".xlsx";

            if (StringUtils.isNotEmpty(parameters.getAnnexPath())) {
                defaultPath = parameters.getAnnexPath();

            } else {
                defaultPath = ResourceUtils.getURL("classpath:") + "excel/" + excelName;

            }
            FileSystemResource file = new FileSystemResource(new File(defaultPath));
            if (file.exists()) {
                logger.info("file is exists");
            } else {
                logger.info("file is not exists");
            }


            logger.info(excelName + "-------------------------------");
            mimeMessageHelper.addAttachment(excelName, file);
            javaMailSender.send(mimeMessage);
            logger.info("Meeting [mail hive annex] send success...");
            return "200";
        } catch (MessagingException e) {
            logger.info("Meeting [mail hive annex] send failed...", e);
        }
        return "500";
    }

    /**
     * 初始化 MimeMessageHelper
     */
    private MimeMessageHelper initMimeMessageHelper(MimeMessageHelper mimeMessageHelper, AutoMailParameters parameters) throws FileNotFoundException {

        /**
         * 邮件主题
         */
        String subject = parameters.getSubject();
        if (StringUtils.isEmpty(subject)) {
            logger.error("Mail subject must not be null");
        }
        /**
         * 收件人集合
         */
        String receivers = parameters.getReceivers();
        if (StringUtils.isEmpty(receivers)) {
            logger.error("Mail receivers must not be null");
        }
        String[] strings = receivers.split(",");

        try {
            //发送者 必须和配置的 mail.smtp.username 一样
            mimeMessageHelper.setFrom(sendModel.getUsername());
            //接收者
            mimeMessageHelper.setTo(strings);
            //邮件主题
            mimeMessageHelper.setSubject(parameters.getSubject());
            //邮件内容
            mimeMessageHelper.setText(parameters.getContent(), true);

            logger.info("init mimeMessage success");
            return mimeMessageHelper;
        } catch (MessagingException e) {
            logger.info("init mimeMessage failed...", e);
        }
        return null;
    }
}
