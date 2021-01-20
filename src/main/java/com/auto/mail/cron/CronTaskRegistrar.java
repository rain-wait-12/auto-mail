package com.auto.mail.cron;

import com.auto.mail.constant.Constant;
import com.auto.mail.model.TaskInfo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author create by rain on 2019-10-29
 * 定时任务新增 移除 类
 */
@Component
public class CronTaskRegistrar implements DisposableBean {

    /**
     * 调度任务集
     *
     * 新增 更新 或者 移除task  都对任务调度集进行更新
     *
     */
    private final Map<String, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(21);

    /**
     * 任务调度器
     */
    @Resource
    private TaskScheduler taskScheduler;

    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }


    /**
     *
     * @param taskInfo 任务信息
     */
    public void addTask(TaskInfo taskInfo){
        //初始化定时任务实例
        Object[] split = taskInfo.getParam().split(",");
        SchedulingRunnable task = new SchedulingRunnable(taskInfo.getJobHandler(), Constant.JOB_EXECUTE_METHOD, split);
        addCronTask(task, taskInfo);
    }

    /**
     * 新增定时任务
     * @param runnable runnable
     * @param taskInfo task info
     */
    public void addCronTask(Runnable runnable,TaskInfo taskInfo) {
        CronTask cronTask = new CronTask(runnable, taskInfo.getCron());

        if (this.scheduledTasks.containsKey(taskInfo.getId())) {
            removeCronTask(taskInfo.getId());
        }
        this.scheduledTasks.put(taskInfo.getId(), scheduleCronTask(cronTask));
    }
    /**
     * 移除定时任务
     * @param taskKey
     */
    public void removeCronTask(String taskKey) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(taskKey);
        if (scheduledTask != null)
            scheduledTask.cancel();
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    @Override
    public void destroy() {
        for (ScheduledTask task : this.scheduledTasks.values()) {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }
}
