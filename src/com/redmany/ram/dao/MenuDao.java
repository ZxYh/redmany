package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;
import com.redmany.ram.model.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/18.
 */
public class MenuDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 根据FormName查询返回结果集
     * @param CompanyId 企业Id
     * @param FormName FormName
     * @return
     */
    public List<Map<String,Object>> getMenuList(String CompanyId, String FormName){
        String sql = "SELECT * FROM [Menu] WHERE ParentMenu=(SELECT Id FROM [Menu] WHERE FormName=?)";
        //String sql = "SELECT * FROM [Menu] WHERE FormName=?";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }

    /**
     * 根据FormName查询返回实体
     * @param CompanyId
     * @param FormName
     * @return
     */
    public Menu getMenu(String CompanyId, String FormName){
        String sql = "SELECT * FROM [Menu] WHERE FormName=?";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        Menu menu = new Menu();
        if(datas!=null&&datas.size()>0){
            Map<String,Object> map = datas.get(0);
            menu = toMenu(map);
        }
        return menu;
    }

    /**
     * 将结果集转为实体
     * @param map
     * @return
     */
    public Menu toMenu(Map<String,Object> map){
        Menu menu = new Menu();
        if(map!=null&&map.size()>0){
            menu.setId(Integer.parseInt(ObjIsNull(map.get("Id"),"0")));
            menu.setMenuName(ObjIsNull(map.get("MenuName"),""));
            menu.setFormName(ObjIsNull(map.get("FormName"),""));
            menu.setShowType(ObjIsNull(map.get("ShowType"),""));
            menu.setTemplateFrom(ObjIsNull(map.get("TemplateFrom"),""));
            menu.setParentMenu(Integer.parseInt(ObjIsNull(map.get("ParentMenu"),"0")));
            menu.setIcon(ObjIsNull(map.get("Icon"),""));
            menu.setPublish(ObjIsNull(map.get("Publish"),""));
            menu.setState(Integer.parseInt(ObjIsNull(map.get("State"),"0")));
            menu.setIndex_number(Integer.parseInt(ObjIsNull(map.get("index_number"),"0")));
            menu.setMenuConds(ObjIsNull(map.get("MenuConds"),""));
            menu.setIsTips(ObjIsNull(map.get("IsTips"),""));
            menu.setGoName(ObjIsNull(map.get("GoName"),""));
            menu.setGoType(ObjIsNull(map.get("GoType"),""));
            menu.setParam(ObjIsNull(map.get("Param"),""));
            menu.setNeedState(ObjIsNull(map.get("NeedState"),""));
            menu.setMenuType(ObjIsNull(map.get("MenuType"),""));
            menu.setConfirm(ObjIsNull(map.get("confirm"),""));
            menu.setPanel(Integer.parseInt(ObjIsNull(map.get("panel"),"0")));
            menu.setShortcut(Integer.parseInt(ObjIsNull(map.get("shortcut"),"0")));
            menu.setPageicon(ObjIsNull(map.get("pageicon"),""));
            menu.setLoadWay(ObjIsNull(map.get("loadWay"),""));
            menu.setTransferParams(ObjIsNull(map.get("transferParams"),""));
            menu.setShowPage(ObjIsNull(map.get("showPage"),""));
        }
        return menu;
    }

    /**
     * 判断Object类型是否为null
     * @param obj
     * @param trueValue 若为Null 返回该值
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
