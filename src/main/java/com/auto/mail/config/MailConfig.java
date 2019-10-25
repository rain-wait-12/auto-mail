package com.auto.mail.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {

    @Value("${mail.users}")
    private String users;
    @Value("${mail.name}")
    private String name;
    @Value("${mail.path}")
    private String path;
    @Value("${mail.title}")
    private String title;
    @Value("${mail.sendUser}")
    private String sendUser;

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }
}
