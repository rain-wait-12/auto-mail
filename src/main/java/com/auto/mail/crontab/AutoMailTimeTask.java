package com.auto.mail.crontab;

import com.auto.mail.config.MailConfig;
import com.auto.mail.constant.Constant;
import com.auto.mail.datasource.mysql.MysqlConnPool;
import com.auto.mail.datasource.oracel.OracleConnPool;
import com.auto.mail.datasource.presto.PrestoConnPool;
import com.auto.mail.mapper.AutoMailMapper;
import com.auto.mail.mapper.DataSourceMapper;
import com.auto.mail.mapper.WorkMapper;
import com.auto.mail.model.AutoMailParameters;
import com.auto.mail.model.Datasource;
import com.auto.mail.model.Work;
import com.auto.mail.service.download.Download;
import com.auto.mail.service.send.Send;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * @author  rain
 * 定时任务初始化入口
 */
@Component
public class AutoMailTimeTask {
    Logger logger = LoggerFactory.getLogger(AutoMailTimeTask.class);
    @Resource
    private DataSourceMapper dataSourceMapper;

    @Resource
    private WorkMapper workMapper;


    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @Resource
    Download download;

    @Resource
    MailConfig mailConfig;

    @Resource
    Send send;
    @Resource
    AutoMailMapper autoMailMapper;

    @Resource
    PrestoConnPool prestoConnPool;
    @Resource
    MysqlConnPool mysqlConnPool;
    @Resource
    OracleConnPool oracleConnPool;

    /**
     * 每天凌晨获取当天要执行的定时任务，并创建定时任务实例
     */
    @Scheduled(cron = "0 0/9 * * * ?")
    public void TimeTask(){
         logger.info("定时任务开启-----------------------");
        List<Work> works = workMapper.selectAll();
        System.out.println(works);
        works.forEach(work -> {

            //初始化定时任务实例
            SchedulingRunnable task = new SchedulingRunnable("autoMailTimeTask", "optAnnex",work.getId());
            cronTaskRegistrar.addCronTask(task, work.getCronTime());
        });
    }
    /**
     * 判断定时是否需要下载附件
     * 若sql为空且数据源id为空则表示不需要生成报表，直接根据content发送邮件
     * 反之则需要下载报表，发送带附件的邮件
     */
    public void optAnnex(Integer id){
        Work work = workMapper.selectByPrimaryKey(id);

        String sql = work.getSql();
        Integer sourceId = work.getSourceId();

        if(StringUtils.isEmpty(sql)&&sourceId==null){
            logger.info("not have annex mail job start,work id is {}------------------------",id);
            sendMailContent(work);
        }else {
            logger.info(" have annex mail job start,work id is {}------------------------",id);
            Datasource datasource = dataSourceMapper.selectByPrimaryKey(work.getSourceId());
            work.setDatasource(datasource);
            sendMailHaveAnnex(work);
        }

    }

    /**
     * 发送带附件的邮件
     */
    private void sendMailHaveAnnex(Work work){
        try {
            String excelName = downloadExcel(work.getDatasource().getType(), work.getSql());

            //根据work id获取发送对象（未完成）
            AutoMailParameters parameters = autoMailMapper.selectByPrimaryKey(work.getMailId());

            parameters.setAnnexPath(mailConfig.getPath()+excelName);
            parameters.setSendDate(new Date());
            String mail = send.SendAnnexMail(parameters);
            if("200".equals(mail)){
                logger.info("mail send success,job id si {}",work.getId());
            }
            if("500".equals(mail)){
                logger.info("mail send fail,job id si {}",work.getId());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载报表
     * @param source_type 数据源类型
     * @param sql  sql语句
     * @return  报表名称
     */
    private String downloadExcel(String source_type,String sql){
        String path = mailConfig.getPath();
        String downLoad = "";
        if(Constant.DATASOURCE_MYSQL.equals(source_type)){
            //初始化连接池
            Example example = new Example(Datasource.class);
            example.createCriteria().andEqualTo("type",Constant.DATASOURCE_MYSQL);
            Datasource datasource = dataSourceMapper.selectOneByExample(example);
            mysqlConnPool.MysqlConnPool(datasource);
            mysqlConnPool.initialize();
            downLoad= download.mysqlDownLoad(path, sql);
        }
        if(Constant.DATASOURCE_ORACLE.equals(source_type)){
            //初始化连接池
            Example example = new Example(Datasource.class);
            example.createCriteria().andEqualTo("type",Constant.DATASOURCE_ORACLE);
            Datasource datasource = dataSourceMapper.selectOneByExample(example);
            oracleConnPool.OracleConnPool(datasource);
            oracleConnPool.initialize();
            downLoad=download.OracleDownLoad(path,sql);
        }
        if(Constant.DATASOURCE_HIVE.equals(source_type)){
            //初始化连接池
            Example example = new Example(Datasource.class);
            example.createCriteria().andEqualTo("type",Constant.DATASOURCE_HIVE);
            Datasource datasource = dataSourceMapper.selectOneByExample(example);
            prestoConnPool.PrestoConnPool(datasource);
            prestoConnPool.initialize();
            downLoad = download.prestoDownLoad(path,sql);
        }
       return downLoad;
    }

    /**
     * 发送不带附件的邮件
     */
    private void sendMailContent(Work work){
        try {

            //根据work id获取发送对象
            AutoMailParameters parameters = autoMailMapper.selectByPrimaryKey(work.getMailId());
            parameters.setSendDate(new Date());
            String mail = send.SendNoAnnexMail(parameters);
            if("200".equals(mail)){
                logger.info("mail send success,job id si {}",work.getId());
            }
            if("500".equals(mail)){
                logger.info("mail send fail,job id si {}",work.getId());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
