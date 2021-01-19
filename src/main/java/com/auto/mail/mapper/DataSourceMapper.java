package com.auto.mail.mapper;

import org.apache.ibatis.annotations.Param;

public interface DataSourceMapper {

    public int isTableExist(@Param("table_name") String table);

    void createTaskInfo();
}
