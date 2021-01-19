package com.auto.mail.model;

import lombok.Data;

@Data
public class TaskInfo {

    /**
     * task id
     */
    private String id;

    /**
     * job 执行类名
     */
    private String jobHandler;


    /**
     * 定时任务cron
     */
    private String cron;

    /**
     * job参数
     */
    private String param;

    /**
     * job名称
     */
    private String jobName;

    private Integer limit;

    private Integer offset;

}
