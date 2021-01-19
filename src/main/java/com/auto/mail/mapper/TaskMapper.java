package com.auto.mail.mapper;

import com.auto.mail.config.MyMapper;
import com.auto.mail.model.TaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper extends MyMapper<TaskInfo> {

    List<TaskInfo> selectByTaskDto(@Param("info") TaskInfo taskInfo);

    void deleteByIdKey(@Param("id")  String id);
}
