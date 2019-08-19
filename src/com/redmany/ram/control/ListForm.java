package com.redmany.ram.control;

import com.redmany.ram.dao.FormFiledDao;
import com.redmany.ram.model.Form;
import com.redmany.ram.model.Variable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/3.
 */
public class ListForm {

    private static FormFiledDao formFiledDao = new FormFiledDao();

    public static String createListForm(HttpServletRequest req, Map<String,Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into createListForm");
        String view = "";
        String FormName = map.get("FormName").toString();
        String ShowType = map.get("ShowType").toString();
        String attribute = map.get("Attribute").toString();
        Map<String,String> attributeMap = CreateControl.getAttribute(Variable.getCompanyId(),attribute);
        String FormClass = attributeMap.get("FormClass");
        String FormStyle = attributeMap.get("FormStyle");
        String ListClass = attributeMap.get("ListClass");
        String ListStyle = attributeMap.get("ListStyle");
        String id = FormName+"_"+ShowType;
        view += "<div class=\""+FormClass+"\" style=\""+FormStyle+"\">";
        if(form!=null&&formFieldList!=null&&formFieldList.size()>0){
            view += CreateControl.Control(req,map,form,formFieldList,ListClass,ListStyle);
        }
        view += "</div>";

        return view;
    }

    public static String createCollectionForm(HttpServletRequest req, Map<String, Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into createCollectionForm");
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

}
