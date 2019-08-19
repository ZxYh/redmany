package com.redmany.ram.control;

import com.redmany.ram.model.Form;
import com.redmany.ram.model.Variable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/7/5.
 */
public class NavigationForm {

    public static String createNavigationForm(HttpServletRequest req,Map<String, Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into createNavigationForm");
        String view = "";
        String FormName = map.get("FormName").toString();
        String ShowType = map.get("ShowType").toString();
        String attribute = map.get("Attribute").toString();
        Map<String,String> attributeMap = CreateControl.getAttribute(Variable.getCompanyId(),attribute);
        String FormClass = attributeMap.get("FormClass");
        String FormStyle = attributeMap.get("FormStyle");
        String id = FormName+"_"+ShowType;
        view += "<div class=\""+FormClass+"\" style=\""+FormStyle+"\">";
        if(form!=null&&formFieldList!=null&&formFieldList.size()>0){
            view += CreateControl.Control(req,map,form,formFieldList,null,null);
        }
        view += "</div>";

        return view;
    }

    public static String createBlankForm(HttpServletRequest req,Map<String, Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into createBlankForm");
        String view = "";
        String attribute = map.get("Attribute").toString();
        Map<String,String> attributeMap = CreateControl.getAttribute(Variable.getCompanyId(),attribute);
        String FormClass = attributeMap.get("FormClass");
        String FormStyle = attributeMap.get("FormStyle");
        view += "<div class=\""+FormClass+"\" style=\""+FormStyle+"\"></div>";

        return view;
    }
}
