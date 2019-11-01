package com.auto.mail.utils;

import com.auto.mail.config.MailConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author create by rain 2019-10
 */
@Component
public class OptExcelUtils {

    @Resource
    MailConfig mailConfig;

    /**
     * 文件选择策略 选择最新得文件
     * @return 文件名
     */
    public String  getExcelName() {

        String path = mailConfig.getPath();
        if(StringUtils.isEmpty(path)){
            try {
                path =  ResourceUtils.getURL("classpath:")+ "excel";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        File file = new File(path);

        File[] files = file.listFiles();

        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < files.length; i++) {
            list.add(Long.decode(files[i].getName().replace(".xlsx","")));
        }
        System.out.println(list.toString());
        Collections.reverse(list);
        System.out.println(list.get(0) + "--------------------------------------------+++++");
        return list.get(0) + "";
    }


}
