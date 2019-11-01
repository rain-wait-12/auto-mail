package com.auto.mail.datasource.mysql;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author create by rain on 2019-10-28
 */
@Component
public class MysqlHelper {
    private static Logger logger = getLogger(MysqlHelper.class);

    @Resource
    private MysqlConnPool pool;

    public List<Map<String, Object>> queryData(String sql, List<String> header) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet == null){
                return list;
            }
            while (resultSet.next()) {
                Map<String,Object> map = new LinkedHashMap<String, Object>();
                for (String field : header) {
                    map.put(field,resultSet.getString(field));
                }
                list.add(map);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            pool.closeResource(connection,statement,resultSet);
        }
        return list;
    }
}
