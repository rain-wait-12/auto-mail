package com.auto.mail.controller;


import com.alibaba.fastjson.JSONObject;
import com.auto.mail.config.MailConfig;
import com.auto.mail.crontab.AutoMailTimeTask;
import com.auto.mail.datasource.presto.PrestoConnPool;
import com.auto.mail.mapper.AutoMailMapper;
import com.auto.mail.mapper.DataSourceMapper;
import com.auto.mail.mapper.WorkMapper;
import com.auto.mail.model.AutoMailParameters;
import com.auto.mail.model.Datasource;
import com.auto.mail.model.Work;
import com.auto.mail.service.download.Download;
import com.auto.mail.service.mailService.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("mail")
public class MailController {

    @Resource
    SendMailService sendMailService;
    @Resource
    AutoMailMapper autoMailMapper;
    @Resource
    WorkMapper workMapper;
    @Resource
    DataSourceMapper dataSourceMapper;
    @Resource
    PrestoConnPool prestoConnPool;
    @Resource
    Download download;
    @Resource
    MailConfig config;
    @Autowired
    AutoMailTimeTask autoMailTimeTask;
    @PostMapping("no")
    @ResponseBody
    public String sendMailNoAnnex(@RequestBody JSONObject json) {
        try {
            //先写入work mail表中
            AutoMailParameters mail = new AutoMailParameters();
            mail.setSubject(json.getString("subject"));
            mail.setContent(json.getString("content"));
            mail.setUsername(json.getString("username"));
            mail.setReceivers(json.getString("receivers"));

           // autoMailMapper.insertSelective(mail);
            autoMailMapper.insertUseGeneratedKeys(mail);
            Work work = new Work();
            work.setMailId(mail.getId());
            work.setCronTime(json.getString("cron"));
            work.setWorkName(json.getString("workName"));
            workMapper.insertSelective(work);

           sendMailService.SendNoAnnexMailService(mail);
            return "200";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "500";
    }
    @PostMapping("annex")
    @ResponseBody
    public String sendMailAnnex(@RequestBody JSONObject json) {
        try {
            //获取数据连接信息
            Datasource datasource = dataSourceMapper.selectByPrimaryKey(1);
            Work work = workMapper.selectByPrimaryKey(5);
            //初始化连接池
            prestoConnPool.PrestoConnPool(datasource);
            prestoConnPool.initialize();
            //下载报表
            download.prestoDownLoad(config.getPath(),work.getSql());
            AutoMailParameters parameters = autoMailMapper.selectByPrimaryKey(5);
            sendMailService.SendAnnexMailService(parameters);
            return "200";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "500";
    }
    @GetMapping("test")
    public void test(){
        autoMailTimeTask.TimeTask();
    }
}
