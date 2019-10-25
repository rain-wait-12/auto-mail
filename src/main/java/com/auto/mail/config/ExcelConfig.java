package com.auto.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcelConfig {

    @Value("${excel.header.name}")
    private List<String> headers;

    public List<String> getHeaders() {
        return headers;
    }
}
