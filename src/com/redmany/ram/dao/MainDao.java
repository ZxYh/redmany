package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/12.
 */
public class MainDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 根据企业Id获取系统设置结果集
     * @param Company_Id
     * @return
     */
    public List<Map<String,Object>> GetSystemSetting(String Company_Id,String RoleId){
        String sql = "SELECT * FROM [OaSystemSetting_b] WHERE 1=1";
        if(RoleId != null && !"".equals(RoleId)){
            sql += " and RoleId="+RoleId;
        }
        System.out.println(sql);
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(Company_Id,sql,null);
        System.out.println(datas);
        return datas;
    }
}
