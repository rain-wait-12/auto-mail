package com.auto.mail.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendModel {

    /**
     * 服务器主机名
     */
    @Value("${mail.smtp.host}")
    private String host;
    /**
     * 邮箱地址ַ
     */
    @Value("${mail.smtp.username}")
    private String username;
    /**
     * 授权码
     */
    @Value("${mail.smtp.password}")
    private String password;
    /**
     * 邮箱服务器端口
     */
    @Value("${mail.smtp.port}")
    private String port;
    /**
     * 编码格式
     */
    @Value("${mail.smtp.defaultEncoding}")
    private String defaultEncoding;
    /**
     * 是否进行用户名密码校验
     */
    @Value("${mail.smtp.auth}")
    private String auth;
    /**
     * 发送邮件日志级别
     */
    @Value("${mail.smtp.debug}")
    private String debug;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }
}
