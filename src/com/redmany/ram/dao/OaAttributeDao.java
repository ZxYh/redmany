package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/15.
 */
public class OaAttributeDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 根据企业Id，样式Id 返回样式结果集
     * @param CompanyId
     * @param id
     * @return
     */
    public List<Map<String,Object>> getOaAttribute(String CompanyId, String id){
        String sql = "SELECT * FROM [OaAttribute_b] WHERE Id=?";
        String[] parameters ={id};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }
}
