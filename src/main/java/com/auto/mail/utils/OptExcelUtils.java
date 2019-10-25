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

@Component
public class OptExcelUtils {

    @Resource
    MailConfig mailConfig;
    
    
    public String  getExcelName() {

        String path = mailConfig.getPath();
        if(StringUtils.isNotEmpty(path)){
            try {
                path =  ResourceUtils.getURL("classpath:")+ "\\excel";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        File file = new File(path);
        //String[] list = file.list();

        File[] files = file.listFiles();
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < files.length; i++) {
            list.add(Long.decode(files[i].getName().replace(".xlsx","")));
        }
        System.out.println(list.toString());
        Collections.reverse(list);

        return list.get(0) + "";
    }


}
