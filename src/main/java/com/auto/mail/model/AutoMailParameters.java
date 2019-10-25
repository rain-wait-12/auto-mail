package com.auto.mail.model;

import java.util.Date;
import java.util.List;

public class AutoMailParameters {


    /**
     * 发送者用户名
     */
    private String username;

    /**
     * 接收者邮箱
     */
    private List<String> receivers;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件地址
     */
    private  String annexPath;

    /**
     * 发送者姓名
     */
    private String senderName;

    /**
     * 发件时间
     */
    private Date sendDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnnexPath() {
        return annexPath;
    }

    public void setAnnexPath(String annexPath) {
        this.annexPath = annexPath;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
