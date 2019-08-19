package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;
import com.redmany.ram.model.Replacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/19.
 */
public class ReplacerDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 根据 替换器名称 查询 返回结果集
     * @param CompanyId
     * @param Replacername
     * @return
     */
    public Replacer getReplacer(String CompanyId, String Replacername){
        String sql = "SELECT * FROM [Replacer] WHERE Replacername=?";
        String[] parameters = {Replacername};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        Replacer replacer = new Replacer();
        if(datas!=null&&datas.size()>0){
            Map<String,Object> map = datas.get(0);
            replacer = ReplacerDao.toReplacer(map);
        }
        return replacer;
    }

    /**
     * 根据 替换器中配置的sql返回结果集
     * @param CompanyId
     * @param sql
     * @return
     */
    public List<Map<String,Object>> getReplacerWithSql(String CompanyId, String sql){
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,null);
        return datas;
    }

    /**
     * 将结果集转为实体
     * @param map
     * @return
     */
    public static Replacer toReplacer(Map<String,Object> map){
        Replacer replacer = new Replacer();
        if(map!=null&&map.size()>0){
            replacer.setID(Integer.parseInt(ObjIsNull(map.get("ID"),"0")));
            replacer.setReplacername(ObjIsNull(map.get("Replacername"),""));
            replacer.setTxtsource(ObjIsNull(map.get("Txtsource"),""));
            replacer.setDatasql(ObjIsNull(map.get("Datasql"),""));
            replacer.setFid(Integer.parseInt(ObjIsNull(map.get("Fid"),"0")));
            replacer.setChild_Control(ObjIsNull(map.get("Child_Control"),""));
            replacer.setChild_Replacer(ObjIsNull(map.get("Child_Replacer"),""));
            replacer.setDatasql_Two_Level(ObjIsNull(map.get("Datasql_Two_Level"),""));
        }
        return replacer;
    }

    public static String ObjIsNull(Object obj, String trueValue){
        String result;
        if(obj==null){
            result = trueValue;
        }else{
            result = obj.toString();
        }
        return result;
    }
}
