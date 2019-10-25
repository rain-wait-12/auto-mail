package com.auto.mail.controller;

import com.auto.mail.config.ExcelConfig;
import com.auto.mail.config.MailConfig;
import com.auto.mail.constant.Constant;
import com.auto.mail.presto.PrestoHelper;
import com.auto.mail.utils.ExcelWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("excel")
public class DownloadController {

    @Resource
    MailConfig mailConfig;

    @Resource
    ExcelWriter excelWriter;

    @Resource
    PrestoHelper prestoHelper;

    @Resource
    ExcelConfig excelConfig;

    @GetMapping("/download")
    public void download() {
        try {
            List<Map<String, Object>> list = prestoHelper.queryData(Constant.SQL);
            String fileName = System.currentTimeMillis() + ".xlsx";
            Workbook workbook = new XSSFWorkbook();
            File toFile = new File(mailConfig.getPath()+fileName);
            if(!toFile.exists()){
                toFile.createNewFile();
            }
            OutputStream output = new FileOutputStream(toFile);


           /* List<String> linkedList  = new LinkedList<String>(){{
                add("superid");
                add("crowd_type");
                add("batch_id");
            }};*/
            List<String> headers = excelConfig.getHeaders();
            System.out.println(headers.toString() + "=================*****************");
            excelWriter.write2OutputStream(headers,list,workbook,"sheet1",output);

            output.close();

        } catch (IOException exp) {
            exp.printStackTrace();

        }
    }
}
