package com.redmany.ram.controller;

import com.redmany.ram.control.ParentForm;
import com.redmany.ram.dao.MainDao;
import com.redmany.ram.model.Variable;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017Altered by hy on 2017/6/23.
 */
@WebServlet("/Main")
public class MainServlet extends HttpServlet{

    private String FormName = null;
    private String ShowType = null;

    private MainDao mainDao = new MainDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("into main");
        String view = "";

        String CompanyId = Variable.getCompanyId();
        System.out.println("getCompanyId:..."+CompanyId);

        if(StringUtils.isNotBlank(CompanyId)){
            boolean isGetSystemSetting = getSystemSetting(req,CompanyId);
            if(isGetSystemSetting) {
                String isForceLogin = StringUtils.trimToNull(req.getSession().getAttribute("isForceLogin").toString());
                String loginType = StringUtils.trimToNull(req.getSession().getAttribute("loginType").toString());
                String homePage = StringUtils.trimToNull(req.getSession().getAttribute("homePage").toString());
                if(isForceLogin != null){
                    List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
                    switch (isForceLogin){
                        case "0":
                            if(homePage != null){
                                String[] HParam = homePage.split(",");
                                if(HParam.length>1){
                                    FormName = HParam[0];
                                    ShowType = HParam[1];
                                    Map<String,Object> HMap = new HashMap<String,Object>();
                                    HMap.put("CompanyId",CompanyId);
                                    HMap.put("FormName",FormName);
                                    HMap.put("ShowType",ShowType);
                                    mapList.add(HMap);
                                }
                            }
                            break;
                        case "1":
                            if(loginType != null){
                                String[] LParam = loginType.split(",");
                                if(LParam.length>1){
                                    FormName = LParam[0];
                                    ShowType = LParam[1];
                                    Map<String,Object> LMap = new HashMap<String,Object>();
                                    LMap.put("CompanyId",CompanyId);
                                    LMap.put("FormName",FormName);
                                    LMap.put("ShowType",ShowType);
                                    mapList.add(LMap);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    System.out.println("mapList:..."+mapList);
                    view = ParentForm.compositeTemplate(req,mapList);
                    System.out.println(view);

                    switch (isForceLogin){
                        case "0":
                            req.setAttribute("View",view);
                            req.getRequestDispatcher("/UiGenerateWap/index.jsp").forward(req,resp);
                            break;
                        case "1":
                            req.setAttribute("LoginView",view);
                            req.getRequestDispatcher("/UiGenerateWap/login.jsp").forward(req,resp);
                            break;
                    }
                }
            } else {
                view = "<span>获取系统参数设置错误</span>";
                System.out.println(view);
                req.setAttribute("View",view);
                req.getRequestDispatcher("/UiGenerateWap/index.jsp").forward(req,resp);
            }
        }
    }

    private boolean getSystemSetting(HttpServletRequest req,String CompanyId){
        String RoleId = Variable.getRoleId();
        List<Map<String,Object>> systemSetting = mainDao.GetSystemSetting(CompanyId,RoleId);
        if(systemSetting != null && systemSetting.size()>0){
            for(Map<String,Object> map : systemSetting){
                String key = StringUtils.trimToNull(map.get("keyStr").toString());
                String value = StringUtils.trimToNull((String)map.get("valueStr"));
                switch (key.toLowerCase()){
                    case "preferredlocationcondition":
                        req.getSession().setAttribute("preferredLocationCondition",value);
                        break;
                    case "logintype":
                        req.getSession().setAttribute("loginType",value);
                        break;
                    case "homepage":
                        req.getSession().setAttribute("homePage",value);
                        break;
                    case "areasensivity":
                        req.getSession().setAttribute("areaSensivity",value);
                        break;
                    case "allowofflinelogin":
                        req.getSession().setAttribute("allowOffLineLogin",value);
                        break;
                    case "autosubmitofflinedata":
                        req.getSession().setAttribute("autoSubmitOffLineData",value);
                        break;
                    case "bottombarform":
                        req.getSession().setAttribute("bottomBarForm",value);
                        break;
                    case "topbarform":
                        req.getSession().setAttribute("topBarForm",value);
                        break;
                    case "iskeepalive":
                        req.getSession().setAttribute("isKeepAlive",value);
                        break;
                    case "companyid":
                        req.getSession().setAttribute("companyId",value);
                        break;
                    case "isforcelogin":
                        req.getSession().setAttribute("isForceLogin",value);
                        break;
                    case "theme":
                        req.getSession().setAttribute("theme",value);
                        break;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
