package com.auto.mail.service;

import com.auto.mail.model.TaskInfo;

import java.util.List;

public interface TaskService {

    void createTaskJob(TaskInfo taskInfo);

    void updateTaskJob(TaskInfo taskInfo);

    void deleteTaskJob(TaskInfo taskInfo);

    List<TaskInfo> initTaskJob(TaskInfo taskInfo);

}
