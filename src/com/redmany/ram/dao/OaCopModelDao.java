package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/7/4.
 */
public class OaCopModelDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 根据compoundName，返回复合模板集
     * @param CompanyId
     * @param compoundName
     * @return
     */
    public List<Map<String,Object>> getOaCopModel(String CompanyId, String compoundName){
        String sql = "SELECT * FROM [OaCopModel_b] WHERE compoundName=? ORDER BY Index_number";
        String[] parameters ={compoundName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }
}
