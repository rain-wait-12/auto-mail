package com.auto.mail.model;


import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "auto_work")
public class Work {


    @Id
    private Integer id;

    private String sourceSql;

    private  String workName;

    private  String cronTime;

    private Integer sourceId;

    private Datasource datasource;

    private Integer mailId;

    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSql() {
        return sourceSql;
    }

    public void setSql(String sourceSql) {
        this.sourceSql = sourceSql;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getCronTime() {
        return cronTime;
    }

    public void setCronTime(String cronTime) {
        this.cronTime = cronTime;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }
}
