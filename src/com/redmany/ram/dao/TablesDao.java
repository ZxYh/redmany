package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
public class TablesDao {

    private SQLHelper sqlHelper = new SQLHelper();

    public List<Map<String,Object>> getTablesInfo(String CompanyId, String TableNum){
        String sql = "SELECT * FROM [TableManage_b] where TableNum='"+TableNum+"'";
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,null);
        return datas;
    }

    public Map<String,Object> getFoundingTableInfo(String CompanyId,String TableNum){
        String sql = "SELECT Top 1 * FROM [FoundingNote_b] where TableNum='"+TableNum+"' and state=0 order by Id desc";
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,null);
        Map<String,Object> map = null;
        if(datas.size()>0){
            map = datas.get(0);
        }
        return map;
    }

    public Long openTable(String CompanyId,String TableId,String TableType,String TableNum,String TablePrice){
        String sql = "Insert into [FoundingNote_b] (TableId,TableType,TableNum,TablePrice) VALUES (?,?,?,?)";
        String[] parameters = {TableId,TableType,TableNum,TablePrice};
        Long id = 0L;
        id = sqlHelper.ExecuteInsertGetId(CompanyId,sql,parameters);
        return id;
    }
}
