package com.redmany.ram.model;

import java.io.Serializable;

/**
 * Created by hy on 2017/6/19.
 * Replacer[替换器] 表 实体类
 */
public class Replacer implements Serializable {
    private Integer ID;
    private String Replacername;
    private String Txtsource;
    private String Datasql;
    private Integer Fid;
    private String Child_Control;
    private String Child_Replacer;
    private String Datasql_Two_Level;
    private String current_father_value = "-9999";

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getReplacername() {
        return Replacername;
    }

    public void setReplacername(String replacername) {
        Replacername = replacername;
    }

    public String getTxtsource() {
        return Txtsource;
    }

    public void setTxtsource(String txtsource) {
        Txtsource = txtsource;
    }

    public String getDatasql() {
        return Datasql;
    }

    public void setDatasql(String datasql) {
        Datasql = datasql;
    }

    public Integer getFid() {
        return Fid;
    }

    public void setFid(Integer fid) {
        Fid = fid;
    }

    public String getChild_Control() {
        return Child_Control;
    }

    public void setChild_Control(String child_Control) {
        Child_Control = child_Control;
    }

    public String getChild_Replacer() {
        return Child_Replacer;
    }

    public void setChild_Replacer(String child_Replacer) {
        Child_Replacer = child_Replacer;
    }

    public String getDatasql_Two_Level() {
        return Datasql_Two_Level;
    }

    public void setDatasql_Two_Level(String datasql_Two_Level) {
        Datasql_Two_Level = datasql_Two_Level;
    }

    public String getCurrent_father_value() {
        return current_father_value;
    }

    public void setCurrent_father_value(String current_father_value) {
        this.current_father_value = current_father_value;
    }
}
