package com.auto.mail;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.auto.mail.mapper")
@EnableScheduling
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
