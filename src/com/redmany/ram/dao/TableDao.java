package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/18.
 */
public class TableDao {

    private SQLHelper sqlHelper = new SQLHelper();

    public List<Map<String,Object>> getTable(String CompanyId, String FormName, String List_fields, String TableName, String Sort_fields){
        String sql="";
        if(!"".equals(Sort_fields)){
            sql = "SELECT "+List_fields+" FROM ["+TableName+"] ORDER BY "+Sort_fields+" DESC";
        }else {
            sql = "SELECT "+List_fields+" FROM ["+TableName+"] ORDER BY Id DESC";
        }
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,null);
        return datas;
    }

    public List<Map<String,Object>> getTableData(HttpServletRequest req, String CompanyId, String Data_sql){
        String sql= "";
        sql = ReplaceSql(req,Data_sql);
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,null);
        return datas;
    }

    public String ReplaceSql(HttpServletRequest req, String Sql){
        String userid = null;
        try{
            userid = req.getSession().getAttribute("UserId").toString();
        }catch (Exception e){
            userid = "-1";
        }
        if(Sql.indexOf("[userid]") != -1){
            Sql = Sql.replace("[userid]", userid);
        }
        return Sql;
    }

}
