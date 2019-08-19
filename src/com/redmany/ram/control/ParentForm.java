package com.redmany.ram.control;

import com.redmany.ram.dao.*;
import com.redmany.ram.model.Form;
import com.redmany.ram.model.FormFiled;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by hy on 2017/6/23.
 */
public class ParentForm {

    private static String CompanyId = null;
    private static String FormName = null;
    private static String ShowType = null;

    private static Form form = new Form();
    private static FormFiled formFiled = new FormFiled();
    private static MenuDao menuDao = new MenuDao();
    private static FormDao formDao = new FormDao();
    private static FormFiledDao formFiledDao = new FormFiledDao();
    private static OaCopModelDao oaCopModelDao = new OaCopModelDao();
    private static OaAttributeDao oaAttributeDao = new OaAttributeDao();


    public static String singleTemplate(HttpServletRequest req, Map<String,Object> map){
        System.out.println("into singleTemplate");
        String view = "";
        //view += "<div>";
        if(map!=null&&map.size()>0){
            view += createView(req,map);
        }
        //view += "</div>";
        return view;
    }

    public static String compositeTemplate(HttpServletRequest req,List<Map<String,Object>> copModelMaps){
        System.out.println("into compositeTemplate");
        String view = "";

        for (Map<String,Object> copModelMap : copModelMaps){
            view += singleTemplate(req,copModelMap);
        }

        return view;
    }

    public static String createView(HttpServletRequest req,Map<String, Object> map){
        System.out.println("into createView");
        String view = "";
        init(map);

        Form form = new Form();
        List<Map<String,Object>> formFieldList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> copModelList = new ArrayList<Map<String,Object>>();

        switch (ShowType){
            case "LOGINFORM":
                System.out.println("ShowType is LOGINFORM");
                form = getForm();
                formFieldList = getFormFieldList();
                view += NewForm.createLoginForm(req,map,form,formFieldList);
                break;
            case "NEWFORM":
                System.out.println("ShowType is NEWFORM");
                form = getForm();
                formFieldList = getFormFieldList();
                view += NewForm.createNewForm(req,map,form,formFieldList);
                break;
            case "LISTFORM":
                System.out.println("ShowType is LISTFORM");
                form = getForm();
                formFieldList = getFormFieldList();
                view += ListForm.createListForm(req,map,form,formFieldList);
                break;
            case "COLLECTIONFORM":
                System.out.println("ShowType is COLLECTIONFORM");
                form = getForm();
                formFieldList = getFormFieldList();
                view += ListForm.createCollectionForm(req,map,form,formFieldList);
                break;
            case "NAVIGATIONFORM":
                System.out.println("ShowType is NAVIGATIONFORM");
                form = getForm();
                formFieldList = getFormFieldList();
                view += NavigationForm.createNavigationForm(req,map,form,formFieldList);
                break;
            case "FREEFORM":
                System.out.println("ShowType is FREEFORM");
                form = getForm();
                formFieldList = getFormFieldList();
                view += ListForm.createCollectionForm(req,map,form,formFieldList);
                break;
            case "TABMENUFORM":
                System.out.println("ShowType is TABMENUFORM");
                menuList = getMenuList();
                view += TabForm.createTabMenuForm(req,map,menuList);
                break;
            case "TABBARFORM":
                System.out.println("ShowType is TABBARFORM");
                menuList = getMenuList();
                view += TabForm.createTabBarForm(req,map,menuList);
                break;
            case "FOODORDERFORM":
                System.out.println("ShowType is FOODORDERFORM");
                view += "";
                break;
            case "BLANKFORM":
                System.out.println("ShowType is BlankForm");
                form = getForm();
                formFieldList = getFormFieldList();
                view += NavigationForm.createBlankForm(req,map,form,formFieldList);
                break;
            case "MENUFORM":
                break;
            case "COPFORM":
                copModelList = getCopModelList();
                view += ParentForm.compositeTemplate(req,copModelList);
                break;
        }
        return view;
    }

    private static void init(Map<String,Object> map){
        CompanyId = map.get("CompanyId").toString();
        FormName = map.get("FormName").toString();
        ShowType = map.get("ShowType").toString().toUpperCase();
    }

    public static Form getForm(){
        form = formDao.getForm(CompanyId,FormName);
        return form;
    }

    public static List<Map<String,Object>> getFormFieldList(){
        List<Map<String,Object>> formFieldList = formFiledDao.getFormFiled(CompanyId,FormName);
        return formFieldList;
    }

    public static List<Map<String,Object>> getMenuList(){
        List<Map<String,Object>> menuList = menuDao.getMenuList(CompanyId,FormName);
        return menuList;
    }

    public static List<Map<String,Object>> getCopModelList(){
        List<Map<String,Object>> copModelList = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> data = oaCopModelDao.getOaCopModel(CompanyId,FormName);
        String naviTitle = data.get(0).get("title").toString();
        if(data != null && data.size() >0){
            for (Map<String,Object> mapData : data){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("CompanyId", CompanyId);
                map.put("FormName",mapData.get("copFormName").toString());
                map.put("ShowType",mapData.get("showType").toString());
                map.put("Attribute",ObjIsNull(mapData.get("attributeId"),"0"));
                map.put("naviTitle",naviTitle);
                copModelList.add(map);
            }
        }
        return copModelList;
    }

    public static String ObjIsNull(Object obj, String trueValue){
        String result;
        if(obj==null){
            result = trueValue;
        }else{
            result = obj.toString();
        }
        return result;
    }
}
