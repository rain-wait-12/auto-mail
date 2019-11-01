package com.auto.mail.constant;

public class Constant {

    //public static String SQL = "select split(cast(activity_time as varchar),' ')[1] activity_time,leads_source,count(superid) as superid from tmp_yu_20191021 group by activity_time,leads_source;";

    public static String SQL = "select superid,crowd_type,batch_id from dw_wm_cdp.tg_cdp_crowd";


    /**
     * 数据源mysql
     * oracle
     * presto（hive）
     */
    public static  String DATASOURCE_MYSQL = "MYSQL";

    public static  String DATASOURCE_ORACLE = "ORACLE";

    public static  String DATASOURCE_HIVE = "HIVE";

    //邮件发送正常or异常
    public  static String OK="200";

    public  static  String ERROR ="500";

    public static  String DATASOURCE = "auto_datasource";

    public static  String MAIL = "auto_mail";

    public static String WORK = "auto_work";

    public static String[] TABLES = {"auto_datasource","auto_mail","auto_work"};

}
