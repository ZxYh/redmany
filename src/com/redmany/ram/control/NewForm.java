package com.redmany.ram.control;

import com.redmany.ram.dao.FormFiledDao;
import com.redmany.ram.model.Form;
import com.redmany.ram.model.Variable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/23.
 */
public class NewForm {

    private static FormFiledDao formFiledDao = new FormFiledDao();

    public static String createLoginForm(HttpServletRequest req, Map<String,Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into createLoginForm");
        String view = "";
        String FormName = map.get("FormName").toString();
        String ShowType = map.get("ShowType").toString();
        String id = FormName+"_"+ShowType;
        view += "<form action=\"/Login\" id=\""+id+"\" method=\"post\">";
        if(form!=null&&formFieldList!=null&&formFieldList.size()>0){
            view += CreateControl.Control(req,map,form,formFieldList,null,null);
        }
        view += "</form>";

        return view;
    }

    public static String createNewForm(HttpServletRequest req,Map<String,Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into createNewForm");
        String view = "";
        String FormName = map.get("FormName").toString();
        String ShowType = map.get("ShowType").toString();
        String attribute = map.get("Attribute").toString();
        Map<String,String> attributeMap = CreateControl.getAttribute(Variable.getCompanyId(),attribute);
        String FormClass = attributeMap.get("FormClass");
        String FormStyle = attributeMap.get("FormStyle");
        String id = FormName+"_"+ShowType;
        view += "<div class=\""+FormClass+"\" style=\""+FormStyle+"\">";
        view += "<form action=\"/Submit?FormName=" + FormName + "&ShowType=" + ShowType + "\" id=\""+id+"\" method=\"post\">";
        if(form!=null&&formFieldList!=null&&formFieldList.size()>0){
            view += CreateControl.Control(req,map,form,formFieldList,null,null);
        }
        view += "</form>";
        view += "</div>";

        return view;
    }
}
