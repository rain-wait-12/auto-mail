package com.auto.mail.service.download.impl;

import com.auto.mail.datasource.mysql.MysqlHelper;
import com.auto.mail.datasource.oracel.OracleHelper;
import com.auto.mail.datasource.presto.PrestoHelper;
import com.auto.mail.service.download.Download;
import com.auto.mail.utils.ExcelWriter;
import com.auto.mail.utils.SqlUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author create by rain by 2019-10-28
 * excel  download  class
 */
@Service
public class DownloadImpl implements Download {

    Logger logger = LoggerFactory.getLogger(DownloadImpl.class);

    /**
     * excel写入工具类
     */
    @Resource
    private ExcelWriter excelWriter;

    /**
     * presto sql 执行类
     */
    @Resource
    PrestoHelper prestoHelper;
    @Resource
    OracleHelper oracleHelper;
    @Resource
    MysqlHelper mysqlHelper;

    /**
     * 根据sql获取excel列头
     */
    @Resource
    private SqlUtils sqlUtils;

    /**
     * mysql
     * 下载报表附件
     *
     * @return 返回下载结果（报表名称）
     */
    public String mysqlDownLoad(String path, String sql) {

        List<String> headers = sqlUtils.getSqlHeaderList(sql);
        List<Map<String, Object>> list = mysqlHelper.queryData(sql, headers);
        return writeExcel(headers, list, path);
    }

    /**
     * oracle
     * 下载报表附件
     *
     * @return 返回下载结果（报表名称）
     */
    public String OracleDownLoad(String path, String sql) {
        List<String> headers = sqlUtils.getSqlHeaderList(sql);
        List<Map<String, Object>> list = oracleHelper.queryData(sql, headers);
        return writeExcel(headers, list, path);
    }

    /**
     * presto连接hive
     * 下载报表附件
     *
     * @return 返回下载结果（报表名称）
     */
    public String prestoDownLoad(String path, String sql) {

        List<String> headers = sqlUtils.getSqlHeaderList(sql);
        List<Map<String, Object>> list = prestoHelper.queryData(sql,headers);

        return writeExcel(headers, list, path);
    }


    /**
     *  写入excel
     * @param headers 列头
     * @param list   数据
     * @param path  写入目录
     * @return  文件名称
     */
    private String writeExcel(List<String> headers, List<Map<String, Object>> list, String path) {
        String fileName = "null";
        try {
            fileName = System.currentTimeMillis() + ".xlsx";
            Workbook workbook = new XSSFWorkbook();
            File toFile = new File(path + fileName);
            if (!toFile.exists()) {
                boolean newFile = toFile.createNewFile();
                if (newFile) {
                    logger.info("创建文件成功!");
                }
            }
            OutputStream output = new FileOutputStream(toFile);

            excelWriter.write2OutputStream(headers, list, workbook, "sheet1", output);

            output.close();
            return fileName;
        } catch (IOException exp) {
            exp.printStackTrace();

        }
        return null;
    }
}
