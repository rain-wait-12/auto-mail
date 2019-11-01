package com.auto.mail.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SqlUtils {

    /**
     * 根据sql抽取需要导出的列头
     *
     * @param sql 导出sql
     * @return excel 列头
     */
    public static List<String> getSqlHeaderList(String sql) {

        int select = sql.indexOf("select") + 6;
        int from = sql.indexOf("from");

        String substring = sql.substring(select, from);
        String[] split = substring.split(",");
        List<String> lists = Arrays.asList(split);

        List<String> result = new ArrayList<>();
        for (String list : lists) {
            //去除空格
            String remove = remove(list);

            System.out.println(remove);
            //若有as
            String as = removeAs(remove);

            result.add(as);
        }
        return result;
    }

    private static String remove(String resource) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(resource);
        return  m.replaceAll("");
    }

    private  static String removeAs(String resource){
        if(resource.indexOf("as")>-1){
            int as = resource.indexOf("as");
            return resource.substring(as+"as".length());
        }
        if(resource.indexOf("AS")>-1){
            int as = resource.indexOf("AS");
            return resource.substring(as+"AS".length());
        }
        return resource;
    }
    public static void main(String[] args) {
        String sql = "select id as ID,name AS username,gender as abc,cl from user";


        System.out.println(getSqlHeaderList(sql));
    }
}
