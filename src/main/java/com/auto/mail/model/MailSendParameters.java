package com.auto.mail.model;



import java.util.List;

/**
 * 发送邮件参数
 * @author Created by niugang on 2019/10/15/9:54
 */
public class MailSendParameters {
    /**
     * 接收者
     */
    private List<String> receivers;
    /**
     * 会议主题
     */
    private String subject;

    /**
     * 会议描述
     */
    private String description;

    /**
     * 会议时间
     */
    private String time;

    /**
     * 会议地点
     */
    private String address;


    /**
     * 发送者姓名
     */
    private String senderName;





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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


}

