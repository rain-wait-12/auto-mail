package com.auto.mail.service;

import com.auto.mail.constant.Constant;
import com.auto.mail.crontab.CronTaskRegistrar;
import com.auto.mail.crontab.SchedulingRunnable;
import com.auto.mail.mapper.TaskMapper;
import com.auto.mail.model.TaskInfo;
import com.auto.mail.utils.CommonUtils;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TaskServiceImpl implements TaskService{

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
        addCronTask(taskInfo);

    }

    @Override
    public void updateTaskJob(TaskInfo taskInfo) {
        //更新job
        taskMapper.updateByPrimaryKey(taskInfo);

        //重新注册
        addCronTask(taskInfo);
    }

    @Override
    public void deleteTaskJob(TaskInfo taskInfo) {
        //删除job
        taskMapper.deleteByIdKey(taskInfo.getId());
        //清除job
        removeCronTask(taskInfo);
    }

    @Override
    public List<TaskInfo> initTaskJob(TaskInfo taskInfo) {
        return taskMapper.selectByTaskDto(taskInfo);
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

    /**
     * 删除任务
     * @param taskInfo job参数
     */
    private void removeCronTask(TaskInfo taskInfo){
        //初始化定时任务实例
        SchedulingRunnable task = new SchedulingRunnable(taskInfo.getJobHandler(), Constant.JOB_EXECUTE_METHOD, taskInfo.getId());
        CronTask cronTask = new CronTask(task, taskInfo.getCron());
        cronTaskRegistrar.removeCronTask(cronTask.getRunnable());
    }
}
