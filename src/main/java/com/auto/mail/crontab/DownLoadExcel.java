package com.auto.mail.crontab;


import com.auto.mail.config.ExcelConfig;
import com.auto.mail.config.MailConfig;
import com.auto.mail.constant.Constant;
import com.auto.mail.presto.PrestoHelper;
import com.auto.mail.utils.ExcelWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
@Component
public class DownLoadExcel {

    private Logger logger = LoggerFactory.getLogger(DownLoadExcel.class);

    @Resource
    MailConfig mailConfig;

    @Resource
    ExcelWriter excelWriter;

    @Resource
    PrestoHelper prestoHelper;

    @Resource
    ExcelConfig excelConfig;

    @Scheduled(cron = "* */1 * * * ?")
    public void downLoad(){
        try {
            List<Map<String, Object>> list = prestoHelper.queryData(Constant.SQL);
            String fileName = System.currentTimeMillis() + ".xlsx";
            Workbook workbook = new XSSFWorkbook();
            File toFile = new File(mailConfig.getPath()+fileName);
            if(!toFile.exists()){
                boolean newFile = toFile.createNewFile();
                if(newFile){
                    logger.info("创建文件成功!");
                }
            }
            OutputStream output = new FileOutputStream(toFile);

            List<String> headers = excelConfig.getHeaders();
            System.out.println("---------------" + headers.toString());
            excelWriter.write2OutputStream(headers,list,workbook,"sheet1",output);

            output.close();

        } catch (IOException exp) {
            exp.printStackTrace();

        }
    }

}
