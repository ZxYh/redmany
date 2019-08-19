package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8.
 */
public class WechatDao {

    private SQLHelper sqlHelper = new SQLHelper();

    public List<Map<String,Object>> getWeChatParams(String CompanyId){
        String sql = "SELECT * FROM [WeChatParams]";
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,null);
        return datas;
    }

    public int setAccessToken (String CompanyId,String Id,String accessToken,String time){
        String sql = "update [WeChatParams] set access_token=?,ticket=?,expires_in=? where Id=?";
        String[] parameters = {accessToken,"",time,Id};
        int datas = sqlHelper.ExecuteNonQuery(CompanyId,sql,parameters);
        return datas;
    }

    public int setTicket (String CompanyId,String Id,String ticket){
        String sql = "update [WeChatParams] set ticket=? where Id=?";
        String[] parameters = {ticket,Id};
        int datas = sqlHelper.ExecuteNonQuery(CompanyId,sql,parameters);
        return datas;
    }
}
