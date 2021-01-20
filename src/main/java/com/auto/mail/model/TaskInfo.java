package com.auto.mail.model;

import com.auto.mail.config.CronValid;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskInfo {

    /**
     * task id
     */
    private String id;

    /**
     * job 执行类名
     */
    @NotNull(message = "jobHandler is not null")
    private String jobHandler;


    /**
     * 定时任务cron
     */
    @NotNull(message = "task cron is not null")
    @CronValid(message = "cron  error")
    private String cron;

    /**
     * job参数
     */
    private String param;

    /**
     * job名称
     */
    @NotNull(message = "task name is not null")
    private String jobName;

    private Integer limit;

    private Integer offset;

}
