package com.redmany.ram.common;

import com.redmany.ram.dao.ReplacerDao;
import com.redmany.ram.model.Replacer;
import com.redmany.ram.model.Variable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/19.
 */
public class DSourceHelper {

    private ReplacerDao replacerDao = new ReplacerDao();

    /**
     * 获取替换器数据
     * @param req
     * @param replacer
     * @return
     * @throws IOException
     */
    public List<Map<String,Object>> GetReplacerBind(HttpServletRequest req,Replacer replacer)throws IOException{
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        if(!"".equals(replacer.getTxtsource())){
            String[] reptxt = replacer.getTxtsource().split("#");
            if(reptxt.length>0){
                for (String str : reptxt){
                    Map<String,Object> map = new HashMap<String, Object>();
                    String[] item = str.split(":");
                    if(item.length>0){
                        map.put("value",item[0]);
                        map.put("name",item[1]);
                    }
                    if (map.size()>0){
                        data.add(map);
                    }
                }
            }
        }else{
            String OpreateSql = "";
            if(!"-9999".equals(replacer.getCurrent_father_value())){
                OpreateSql = replacer.getDatasql_Two_Level();
            }else{
                OpreateSql = replacer.getDatasql();
            }
            if(!"".equals(OpreateSql)){
                OpreateSql = OpreateSql.replace("[Current_Father_Value]",replacer.getCurrent_father_value());

                OpreateSql = OpreateSql.replace("[userid]",req.getSession().getAttribute("UserId").toString());
                String CompanyId = Variable.getCompanyId();
                data = replacerDao.getReplacerWithSql(CompanyId,OpreateSql);
            }
        }
        return data;
    }

}
