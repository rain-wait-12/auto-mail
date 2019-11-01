package com.auto.mail.mapper;

import com.auto.mail.config.MyMapper;
import com.auto.mail.model.Datasource;

public interface DataSourceMapper extends MyMapper<Datasource> {

    //根据表名判断该表是否存在
    int isTableExist(String table_name);

    //创建数据源表
    void createAutoDatasource();

    //创建邮件主表
    void createAutoMail();

    //创建job主表
    void  createAutoWork();
}
