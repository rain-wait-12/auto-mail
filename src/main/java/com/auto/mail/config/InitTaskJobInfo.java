package com.auto.mail.config;

import com.auto.mail.constant.Constant;
import com.auto.mail.crontab.CronTaskRegistrar;
import com.auto.mail.crontab.SchedulingRunnable;
import com.auto.mail.mapper.DataSourceMapper;
import com.auto.mail.model.TaskInfo;
import com.auto.mail.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author create by rain on 2019-10
 * 第一次运行时判断表是否存在 不存在则创建表
 */
@Component
public class InitTaskJobInfo {

    Logger logger = LoggerFactory.getLogger(InitTaskJobInfo.class);

    @Resource
    DataSourceMapper dataSourceMapper;

    @Resource
    private TaskService taskService;
    @Resource
    private CronTaskRegistrar cronTaskRegistrar;
    /**
     * job task 表初始化
     * 以及初始化job
     */
    @PostConstruct
    public void init() {
        logger.info("Database table initialization starts");
        String[] tables = Constant.TABLES;
        for (String table : tables) {
            int exist = dataSourceMapper.isTableExist(table);
            if(exist==0){
                    dataSourceMapper.createTaskInfo();
                    logger.info("{} table is created",table);
            }else {
                logger.info("{} is exist",table);
            }
        }
        logger.info("Database table initialization end");


        logger.info("task info  initialization starts");
        List<TaskInfo> taskInfos = taskService.initTaskJob(new TaskInfo());

        taskInfos.forEach(task->{
            addCronTask(task);
        });
        logger.info("task info  initialization end");
    }

    /**
     * 注册任务
     * @param taskInfo job参数
     */
    private void addCronTask(TaskInfo taskInfo){
        //初始化定时任务实例
        SchedulingRunnable task = new SchedulingRunnable(taskInfo.getJobHandler(), Constant.JOB_EXECUTE_METHOD, taskInfo.getId());
        cronTaskRegistrar.addCronTask(task, taskInfo.getCron());
    }
}
