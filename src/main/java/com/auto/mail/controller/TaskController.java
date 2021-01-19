package com.auto.mail.controller;


import com.auto.mail.constant.BaseResponse;
import com.auto.mail.model.TaskInfo;
import com.auto.mail.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author rain.lei
 * task
 * 新增 更新 初始化
 */
@RestController
@RequestMapping("task")
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 新增job
     * @param taskInfo 新增task参数
     * @return baseResponse
     */
    @PostMapping("createJob")
    public BaseResponse createTaskJob(@RequestBody TaskInfo taskInfo){
        taskService.createTaskJob(taskInfo);
        return BaseResponse.successResponnse("success");
    }
    /**
     * 更新job
     * @param taskInfo 更新task参数
     * @return baseResponse
     */
    @PostMapping("updateJob")
    public BaseResponse updateTaskJob(@RequestBody TaskInfo taskInfo){
        taskService.updateTaskJob(taskInfo);
        return BaseResponse.successResponnse("success");
    }
    /**
     * 删除job
     * @param taskInfo 删除
     * @return baseResponse
     */
    @PostMapping("deleteJob")
    public BaseResponse deleteTaskJob(@RequestBody TaskInfo taskInfo){
        taskService.deleteTaskJob(taskInfo);
        return BaseResponse.successResponnse("success");
    }
    /**
     * 获取job
     * @param taskInfo 获取job
     * @return baseResponse
     */
    @GetMapping("init")
    public BaseResponse initTaskJob(@RequestBody TaskInfo taskInfo){

        return BaseResponse.successResponnse(taskService.initTaskJob(taskInfo));
    }

}
