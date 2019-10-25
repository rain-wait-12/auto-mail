package com.auto.mail.service.send.annex;

import com.auto.mail.model.AutoMailParameters;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


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
     * 发送不带附件的邮件
     *
     * @return 发送状态 200 发送成功  其他状态码 发送失败
     */
    public String SendNoAnnexMail(AutoMailParameters parameters)  {


        JavaMailSender javaMailSender = mailSender.getJavaMail();

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            initMimeMessageHelper(mimeMessageHelper,parameters);
            javaMailSender.send(mimeMessage);
            logger.info("Meeting [mail] send success...");
            return "200";
        } catch (MessagingException e) {
            logger.info("Meeting [mail] send failed...",e);
        }catch (FileNotFoundException e){
            logger.info("file not found");
        }
        return "500";
    }
    /**
     * 发送带附件的邮件
     *
     * @return 发送状态 200 发送成功  其他状态码 发送失败
     */
    public String SendAnnexMail(AutoMailParameters parameters) throws FileNotFoundException  {

        String defaultPath = "";
        JavaMailSender javaMailSender = mailSender.getJavaMail();

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            initMimeMessageHelper(mimeMessageHelper,parameters);
           if(StringUtils.isNotEmpty(parameters.getAnnexPath())){
                parameters.setAnnexPath(parameters.getAnnexPath());
            }else {
                 defaultPath =  ResourceUtils.getURL("classpath:")+"\\excel";
                parameters.setAnnexPath(defaultPath);
            }
            String excelName = optExcelUtils.getExcelName() + ".xlsx";
            FileSystemResource file = new FileSystemResource(new File(defaultPath+excelName));

            mimeMessageHelper.addAttachment(excelName,file);
            javaMailSender.send(mimeMessage);
            logger.info("Meeting [mail] send success...");
            return "200";
        } catch (MessagingException e) {
            logger.info("Meeting [mail] send failed...",e);
        }
        return "500";
    }

    /**
     * 初始化 MimeMessageHelper
     */
    private MimeMessageHelper initMimeMessageHelper(MimeMessageHelper mimeMessageHelper,AutoMailParameters parameters)throws FileNotFoundException{
        String  defaultPath = "";
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
        List<String> receivers = parameters.getReceivers();
        if (CollectionUtils.isEmpty(receivers)) {
            logger.error("Mail receivers must not be null");
        }
        String[] strings =(String[]) receivers.toArray();


        try {


            //发送者 必须和配置的 mail.smtp.username 一样
            mimeMessageHelper.setFrom(parameters.getSenderName());
            //接收者
            mimeMessageHelper.setTo(strings);
            //邮件主题
            mimeMessageHelper.setSubject(parameters.getSubject());
            //邮件内容
            mimeMessageHelper.setText(parameters.getContent(), true);
            if(StringUtils.isNotEmpty(parameters.getAnnexPath())){
                parameters.setAnnexPath(parameters.getAnnexPath());
            }else {
                defaultPath =  ResourceUtils.getURL("classpath:")+"\\excel";
                parameters.setAnnexPath(defaultPath);
            }
            String excelName = optExcelUtils.getExcelName() + ".xlsx";
            FileSystemResource file = new FileSystemResource(new File(defaultPath+excelName));

            mimeMessageHelper.addAttachment(excelName,file);

            logger.info("init mimeMessage success");
            return mimeMessageHelper;
        } catch (MessagingException e) {
            logger.info("init mimeMessage failed...",e);
        }
        return null;
    }
}
