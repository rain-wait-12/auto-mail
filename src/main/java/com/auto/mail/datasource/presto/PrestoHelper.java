package com.auto.mail.datasource.presto;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class PrestoHelper {

    private static Logger logger = getLogger(PrestoHelper.class);

    @Resource
    private PrestoConnPool pool;

    public List<Map<String, Object>> queryData(String sql) {
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
                /*map.put("activity_time",resultSet.getString("activity_time"));
                map.put("leads_source",resultSet.getString("leads_source"));
                map.put("superid",resultSet.getString("superid"));*/
                map.put("superid",resultSet.getString("superid"));
                map.put("crowd_type",resultSet.getString("crowd_type"));
                map.put("batch_id",resultSet.getString("batch_id"));
                list.add(map);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            pool.closeResource(connection,statement,resultSet);
        }
        return list;
    }
    public List<Map<String, Object>> queryData(String sql,List<String> header) {
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
