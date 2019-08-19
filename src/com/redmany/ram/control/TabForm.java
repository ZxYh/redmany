package com.redmany.ram.control;

import com.redmany.ram.model.Variable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/7/10.
 */
public class TabForm {


    public static String createTabMenuForm(HttpServletRequest req,Map<String, Object> map,List<Map<String,Object>> menuList){
        System.out.println("into createTabMenuForm");
        String view = "";
        String FormName = map.get("FormName").toString();
        String ShowType = map.get("ShowType").toString();
        String id = FormName+"_"+ShowType;

        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();

        if(menuList!=null&&menuList.size()>0){
            int length = menuList.size();
            String divClass = null;
            switch (length){
                case 1:
                    divClass = "col-xs-12";
                    break;
                case 2:
                    divClass = "col-xs-6";
                    break;
                case 3:
                    divClass = "col-xs-4";
                    break;
                case 4:
                    divClass = "col-xs-3";
                    break;
            }
            view += "<ul id=\"tabHeader\" style=\"padding-top:40px\">";
            for (Map<String,Object> menuMap : menuList) {
                view += "<li class=\""+divClass+"\"><a href=\"#\">"+menuMap.get("MenuName").toString()+"</a></li>";
                Map<String,Object> mapData = new HashMap<String,Object>();
                mapData.put("CompanyId", Variable.getCompanyId());
                mapData.put("FormName",menuMap.get("FormName").toString());
                mapData.put("ShowType",menuMap.get("ShowType").toString());
                mapData.put("Attribute","0");
                mapList.add(mapData);
            }
            view += "</ul>";

            view += "<ul id=\"tabContent\">";
            if(mapList!=null&&mapList.size()>0){
                for (Map<String,Object> menuMap : mapList) {
                    view += "<li>";
                    view += ParentForm.singleTemplate(req,menuMap);
                    view += "</li>";
                }
            }
            view += "</ul>";
        }
       /* if(form!=null&&formFieldList!=null&&formFieldList.size()>0){
            view += Control.Control(map,form,formFieldList);
        }*/
        return view;
    }

    public static String createTabBarForm(HttpServletRequest req,Map<String, Object> map,List<Map<String,Object>> menuList){
        System.out.println("into createTabBarForm");
        String view = "";
        String FormName = map.get("FormName").toString();
        String ShowType = map.get("ShowType").toString();
        String id = FormName+"_"+ShowType;

        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();

        if(menuList!=null&&menuList.size()>0){
            int length = menuList.size();
            String divClass = null;
            switch (length){
                case 1:
                    divClass = "col-xs-12";
                    break;
                case 2:
                    divClass = "col-xs-6";
                    break;
                case 3:
                    divClass = "col-xs-4";
                    break;
                case 4:
                    divClass = "col-xs-3";
                    break;
                case 5:
                    divClass = "col5";
                    break;
            }

            view += "<ul id=\"tabBar\">";
            for (Map<String,Object> menuMap : menuList) {
                view += "<li class=\""+divClass+"\"><img src=\""+Variable.getImageHttp()+(String)menuMap.get("Icon")+"\"><a href=\"#\">"+menuMap.get("MenuName").toString()+"</a></li>";
                Map<String,Object> mapData = new HashMap<String,Object>();
                mapData.put("CompanyId", Variable.getCompanyId());
                mapData.put("FormName",menuMap.get("FormName").toString());
                mapData.put("ShowType",menuMap.get("ShowType").toString());
                mapData.put("Attribute","0");
                mapList.add(mapData);
            }
            view += "</ul>";

            view += "<ul id=\"tabContent\">";
            if(mapList!=null&&mapList.size()>0){
                for (Map<String,Object> menuMap : mapList) {
                    view += "<li>";
                    view += ParentForm.singleTemplate(req,menuMap);
                    view += "</li>";
                }
            }
            view += "</ul>";
        }
       /* if(form!=null&&formFieldList!=null&&formFieldList.size()>0){
            view += Control.Control(map,form,formFieldList);
        }*/
        return view;
    }
}
