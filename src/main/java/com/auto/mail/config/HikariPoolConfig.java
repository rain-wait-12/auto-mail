package com.auto.mail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariPoolConfig {


    private Integer minimumIdle;

    private Integer maximumPoolSize;

    private String poolName;

    private Integer idleTimeout;

    private Integer connectionTimeout;

    private String connectionTestQuery;

    private Boolean autoCommit;

    private Integer maxLifetime;

    public Integer getMinimumIdle() {
        return minimumIdle;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public String getPoolName() {
        return poolName;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public Integer getMaxLifetime() {
        return maxLifetime;
    }

    public void setMinimumIdle(Integer minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void setMaxLifetime(Integer maxLifetime) {
        this.maxLifetime = maxLifetime;
    }
}
