package com.auto.mail.utils;

import com.auto.mail.model.MailSendParameters;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class MailUtil {

    @Resource
    OptExcelUtils optExcelUtils;


    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private static final String BOOLEAN_REGEX = "^false$|^true$";



    private static final Pattern PATTERN = Pattern.compile(BOOLEAN_REGEX);


    private static Properties mailProperties;
    @Value("${mail.path}")
    private  String path;

    /**
     * 判断邮件功能是否开启,默认邮件功能为关闭的
     * true 开启
     * false 关闭
     *
     * @return boolean
     */
    public  boolean isMailEnabled() {
        readPropertiesFile();
        String mailEnabled = mailProperties.getProperty("mail.enabled");
        if (StringUtils.isNotBlank(mailEnabled)) {
            String mailEnabledLowerCase = mailEnabled.toLowerCase();
            Matcher matcher = PATTERN.matcher(mailEnabledLowerCase);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("mail.enabled parameter format error,parameter true or false, case ignored");
            }
            boolean aBoolean = Boolean.parseBoolean(mailEnabled);
            if (!aBoolean) {
                logger.info("mail.enabled is false ,no need to send email");
            }
            return aBoolean;
        }
        return false;
    }


    /**
     * 读取配置文件
     */
    private  void readPropertiesFile() {
        try {
            mailProperties = readProperties();
        } catch (FileNotFoundException e) {
            logger.error("Properties file not found");
        }
        if (mailProperties == null) {
            throw new NullPointerException("Properties file must not be null");
        }



    }


    /**
     * 发送邮件
     */
    public  void sendMail(final MailSendParameters mailSendParameters) {
        if (mailProperties == null) {
            readPropertiesFile();
        }
        List<String> receivers = mailSendParameters.getReceivers();
        if (receivers.isEmpty()) {
            throw new NullPointerException("Mail receiver must not be null");
        }
        if (mailSendParameters.getSubject() == null) {
            throw new NullPointerException("Mail subject must not be null");
        }

        try {
            String[] receiversArray = mailSendParameters.getReceivers().toArray(new String[0]);
            JavaMailSender javaMailSender = getJavaMailSender(mailProperties);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            //发送者 必须和配置的 mail.smtp.username 一样
            mimeMessageHelper.setFrom(mailProperties.getProperty("mail.smtp.username"));
            //接收者
            mimeMessageHelper.setTo(receiversArray);
            //邮件主题
            mimeMessageHelper.setSubject(mailSendParameters.getSubject());
            //邮件内容
            mimeMessageHelper.setText(getText(mailSendParameters), true);
            System.out.println(path+"----------------------");
            String excelName = optExcelUtils.getExcelName() + ".xlsx";
            FileSystemResource file = new FileSystemResource(new File(path+excelName));

            mimeMessageHelper.addAttachment(excelName,file);
            javaMailSender.send(mimeMessage);
            logger.info("Meeting [mail] send success...");
        } catch (Exception e) {
            logger.info("Meeting [mail] send failed...",e);
        }

    }





    /**
     * 创建发送对象
     *
     * @return JavaMailSender
     */
    private  JavaMailSender getJavaMailSender(Properties paramProp) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(paramProp.getProperty("mail.smtp.host"));
        javaMailSender.setUsername(paramProp.getProperty("mail.smtp.username"));
        javaMailSender.setPassword(paramProp.getProperty("mail.smtp.password"));
        String portStr = paramProp.getProperty("mail.smtp.port");
        if (StringUtils.isNotBlank(portStr)) {
            javaMailSender.setPort(Integer.parseInt(portStr));
        }
        String defaultEncoding = paramProp.getProperty("mail.smtp.defaultEncoding");
        if (StringUtils.isNotBlank(defaultEncoding)) {
            javaMailSender.setDefaultEncoding(defaultEncoding);
        }
        String auth = paramProp.getProperty("mail.smtp.auth");
        String debug = paramProp.getProperty("mail.smtp.debug");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", StringUtils.isNotBlank(auth) ? auth : "true");
        properties.setProperty("mail.debug", StringUtils.isNotBlank(debug) ? debug : "true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    /**
     * 读取freemarker模板的方法
     */
    private  String getText(MailSendParameters mailSendParameters) {
        String txt = "";
        try {
            //freemarker包
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            //设置模板加载文件夹
            configuration.setDirectoryForTemplateLoading(new File(ResourceUtils.getURL("classpath:").getPath() + "templates"));
            Template template = configuration.getTemplate("mail.ftl");

            // 通过map传递动态数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("subject", mailSendParameters.getSubject());
            map.put("time", mailSendParameters.getTime());
            map.put("address", mailSendParameters.getAddress());
            map.put("sponsor", mailSendParameters.getSenderName());
            map.put("description", mailSendParameters.getDescription());
            // 解析模板文件
            txt = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (Exception e) {
            logger.error("Create send [mail] template exception:{}", e);

        }

        return txt;
    }


    /**
     * 读取发送邮件配置，实时读取，为了修改配置不重启服务
     *
     * @return Properties
     * @throws FileNotFoundException FileNotFoundException
     */
    private  Properties readProperties() throws FileNotFoundException {
        Properties properties = new Properties();
        File file = ResourceUtils.getFile("classpath:application.properties");
        if (!file.exists()) {
            logger.error("application.properties  is not exist");
            throw new FileNotFoundException("application.properties  is not exist");
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.error("Read [mail] properties failed:{}", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                logger.error("Close FileInputStream failed:{}", e);
            }
        }
        return properties;
    }

}
