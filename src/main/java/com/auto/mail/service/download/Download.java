package com.auto.mail.service.download;

public interface Download {

     /**
      * 连接mysql生产报表
      * @param path 报表保存目录
      * @return  生成结果
      */
     String mysqlDownLoad(String path,String sql);

     /**
      * 连接oracle生成报表
      * @param path 报表保存目录
      * @return 生成结果
      */
     String OracleDownLoad(String path,String sql);

     /**
      * presto连接hive生产报表
      * @param path  报表保存目录
      * @return 生成结果
      */
     String prestoDownLoad(String path,String sql);
}
