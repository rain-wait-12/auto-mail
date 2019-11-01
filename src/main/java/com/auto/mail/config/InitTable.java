package com.auto.mail.config;

import com.auto.mail.constant.Constant;
import com.auto.mail.mapper.DataSourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author create by rain on 2019-10
 * 第一次运行时判断表是否存在 不存在则创建表
 */
@Component
public class InitTable {

    Logger logger = LoggerFactory.getLogger(InitTable.class);

    @Resource
    DataSourceMapper dataSourceMapper;


    @PostConstruct
    public void init() {
        logger.info("Database table initialization starts..........................");
        String[] tables = Constant.TABLES;
        for (String table : tables) {
            int exist = dataSourceMapper.isTableExist(table);
            if(exist==0){
                if(Constant.DATASOURCE.equals(table)){
                    dataSourceMapper.createAutoDatasource();
                    logger.info("{} table is created........................................",table);
                }
                if(Constant.MAIL.equals(table)){
                    dataSourceMapper.createAutoMail();
                    logger.info("{} table is created........................................",table);
                }
                if(Constant.WORK.equals(table)){
                    dataSourceMapper.createAutoWork();
                    logger.info("{} table is created........................................",table);
                }
            }else {
                logger.info("{} is exist........................................",table);
            }
        }
    }

}
