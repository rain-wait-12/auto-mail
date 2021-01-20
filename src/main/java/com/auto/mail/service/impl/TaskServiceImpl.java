package com.auto.mail.service.impl;

import com.auto.mail.constant.Constant;
import com.auto.mail.cron.CronTaskRegistrar;
import com.auto.mail.cron.SchedulingRunnable;
import com.auto.mail.mapper.TaskMapper;
import com.auto.mail.model.TaskInfo;
import com.auto.mail.service.TaskService;
import com.auto.mail.utils.CommonUtils;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void createTaskJob(TaskInfo taskInfo) {

        taskInfo.setId(CommonUtils.getUUID());
        //insert job info
        taskMapper.insert(taskInfo);

        //job 注册
        //addCronTask(taskInfo);
        cronTaskRegistrar.addTask(taskInfo);

    }

    @Override
    public void updateTaskJob(TaskInfo taskInfo) {
        //更新job
        taskMapper.updateByPrimaryKey(taskInfo);

        //重新注册
        //addCronTask(taskInfo);
        cronTaskRegistrar.addTask(taskInfo);
    }

    @Override
    public void deleteTaskJob(TaskInfo taskInfo) {
        //删除job
        taskMapper.deleteByIdKey(taskInfo.getId());
        //清除job
        //removeCronTask(taskInfo);
        cronTaskRegistrar.removeCronTask(taskInfo.getId());
    }

    @Override
    public List<TaskInfo> initTaskJob(TaskInfo taskInfo) {
        return taskMapper.selectByTaskDto(taskInfo);
    }

    @Override
    public Object restartTaskJob(TaskInfo taskInfo) {
        Object[] split = taskInfo.getParam().split(",");

        SchedulingRunnable runnable = new SchedulingRunnable(taskInfo.getJobHandler(),
                taskInfo.getJobName(),split
                );
        runnable.run();
        return "success";
    }

}
