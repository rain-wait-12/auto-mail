package com.auto.mail.constant;

public class Constant {

    //public static String SQL = "select split(cast(activity_time as varchar),' ')[1] activity_time,leads_source,count(superid) as superid from tmp_yu_20191021 group by activity_time,leads_source;";

    public static String SQL = "select superid,crowd_type,batch_id from dw_wm_cdp.tg_cdp_crowd";
}
