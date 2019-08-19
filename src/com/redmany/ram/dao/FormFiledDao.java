package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;
import com.redmany.ram.model.FormFiled;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/15.
 */
public class FormFiledDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 根据FormName查询返回结果集
     * @param CompanyId
     * @param FormName
     * @return
     */
    public List<Map<String,Object>> getFormFiled(String CompanyId, String FormName){
        String sql = "SELECT * FROM [FormFiled] WHERE FormName=? ORDER BY Index_number";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }

    /**
     * 将结果集转为实体
     * @param map
     * @return
     */
    public FormFiled toFormFiled(Map<String,Object> map){
        FormFiled formFiled = new FormFiled();
        if(map!=null&&map.size()>0){
            formFiled.setId(Integer.parseInt(ObjIsNull(map.get("Id"),"0")));
            formFiled.setFormName(ObjIsNull(map.get("FormName"),""));
            formFiled.setName(ObjIsNull(map.get("Name"),""));
            formFiled.setTitle(ObjIsNull(map.get("Title"),""));
            formFiled.setType(ObjIsNull(map.get("Type"),""));
            formFiled.setShowType(ObjIsNull(map.get("ShowType"),""));
            formFiled.setReportShowType(ObjIsNull(map.get("reportShowType"),""));
            formFiled.setValidateExpreesion(ObjIsNull(map.get("ValidateExpreesion"),""));
            formFiled.setValidateErrorMessage(ObjIsNull(map.get("ValidateErrorMessage"),""));
            formFiled.setValidateGroup(ObjIsNull(map.get("ValidateGroup"),""));
            formFiled.setData_replacer(ObjIsNull(map.get("data_replacer"),""));
            formFiled.setData_source(ObjIsNull(map.get("data_source"),""));
            formFiled.setOptioner(ObjIsNull(map.get("optioner"),""));
            formFiled.setDatabase_field(ObjIsNull(map.get("Database_field"),""));
            formFiled.setDatabase_field_type(ObjIsNull(map.get("Database_field_type"),""));
            formFiled.setAutoPostback(Integer.parseInt(ObjIsNull(map.get("AutoPostback"),"0")));
            formFiled.setStatisticsField(ObjIsNull(map.get("StatisticsField"),""));
            formFiled.setIndex_number(Integer.parseInt(ObjIsNull(map.get("Index_number"),"0")));
            formFiled.setIsNull(ObjIsNull(map.get("IsNull"),""));
            formFiled.setPublish(Integer.parseInt(ObjIsNull(map.get("publish"),"0")));
            formFiled.setDefaultContent(ObjIsNull(map.get("defaultContent"),""));
            formFiled.setJsmethod(ObjIsNull(map.get("jsmethod"),""));
            formFiled.setClickbutton(Integer.parseInt(ObjIsNull(map.get("clickbutton"),"0")));
            formFiled.setClickmethod(ObjIsNull(map.get("clickmethod"),""));
            formFiled.setImage(ObjIsNull(map.get("image"),""));
            formFiled.setAttributeId(Integer.parseInt(ObjIsNull(map.get("attributeId"),"0")));
            formFiled.setWapAttribute(ObjIsNull(map.get("wapAttribute"),""));
            formFiled.setTarget(ObjIsNull(map.get("target"),""));
            formFiled.setIsDataItem(Integer.parseInt(ObjIsNull(map.get("isDataItem"),"0")));
            formFiled.setTransferParams(ObjIsNull(map.get("transferParams"),""));
            formFiled.setListAttributeId(ObjIsNull(map.get("listAttributeId"),""));
            formFiled.setShowState(ObjIsNull(map.get("showState"),""));
            formFiled.setShowPage(ObjIsNull(map.get("showPage"),""));
        }
        return formFiled;
    }

    public List<Map<String,Object>> getReplaceFiled(String CompanyId, String FormName){
        String sql = "SELECT * FROM [FormFiled] WHERE FormName=? AND data_replacer<>0";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }

    /**
     * 判断Object是否为null
     * @param obj
     * @param trueValue 若为null返回该值
     * @return
     */
    public String ObjIsNull(Object obj, String trueValue){
        String result;
        if(obj==null){
            result = trueValue;
        }else{
            result = obj.toString();
        }
        return result;
    }
}
