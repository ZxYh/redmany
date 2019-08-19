package com.redmany.ram.controller;

import com.alibaba.fastjson.JSON;
import com.redmany.ram.common.CookieHelper;
import com.redmany.ram.common.MD5;
import com.redmany.ram.common.TargetManager;
import com.redmany.ram.control.ParentForm;
import com.redmany.ram.dao.UserDao;
import com.redmany.ram.model.Variable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by hy on 2017/6/12.
 */
@WebServlet("/User")
public class UserServlet extends HttpServlet{

    private Logger logger = Logger.getLogger(Log4jServlet.class);

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("GetUserInfo".equals(method)){
            GetUserInfo(req, resp);
        } else if("GetUserPanels".equals(method)){
            GetUserPanels(req,resp);
        } else if("GetSysManageTree".equals(method)){
            GetSysManageTree(req,resp);
        } else if("BindWeChat".equals(method)){
            toBindWeChat(req,resp);
        } else if("Register".equals(method)){
            register(req,resp);
        }
    }

    /**
     * 获取用户信息
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private String GetUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        String result = null;
        String userCookie = CookieHelper.getCookie(req,"user");
        if(userCookie != null){
            String[] userInfo = userCookie.split("\\|");
            result = userInfo[2];
        }
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result.toString());
        return result;
    }

    /**
     * 获取用户一级菜单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void GetUserPanels(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        String result = null;
        String userCookie = CookieHelper.getCookie(req,"user");

        if(userCookie != null){
            String CompanyId = Variable.getCompanyId();
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            String[] userInfo = userCookie.split("\\|");
            //UserDao userDao = new UserDao();
            Map<String,Object> userMap = userDao.getUserById(CompanyId,userInfo[0]).get(0);
            String RoldId = userMap.get("RoleId").toString();
            list = userDao.GetUserPanels(CompanyId,RoldId);
            if(list != null) {
                result = JSON.toJSONString(list);
            }
        }
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result.toString());
    }

    /**
     * 获取用户菜单项
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void GetSysManageTree(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        String result = null;
        String userCookie = CookieHelper.getCookie(req,"user");

        if(userCookie != null){
            String CompanyId = Variable.getCompanyId();
            String userId = userCookie.split("\\|")[0];
            String panelId = req.getParameter("panelId");

            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            UserDao userDao = new UserDao();
            list = userDao.GetSysManageTree(CompanyId,userId,panelId);

            if(list != null){
                result = TableToTreeJson(list,"ParentMenu","0","Id","MenuName");
                int a = result.indexOf("[");
                if(a!=-1){
                    result = result.substring(result.indexOf("["),result.length());
                }
            }
        }
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result.toString());
    }

    /**
     * 将菜单项结果集转为Json格式
     * @param list
     * @param pField
     * @param pValue
     * @param kField
     * @param TextField
     * @return
     */
    public String TableToTreeJson(List<Map<String,Object>> list, String pField, String pValue, String kField, String TextField){
        StringBuilder sb = new StringBuilder();
        List<Map<String,Object>> listArr = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> map : list) {
            if(pValue.equals(map.get(pField).toString())){
                listArr.add(map);
            }
        }
        if(listArr.size()<1)return "";
        sb.append(",\"children\":[");
        for (Map<String, Object> mapArr : listArr) {
            String pcv = mapArr.get(kField).toString();
            String state = mapArr.get(pField).toString();
            sb.append("{");
            sb.append("\"id\":\""+mapArr.get(kField).toString()+"\",");
            sb.append("\"name\":\""+mapArr.get(TextField).toString()+"\",");
            sb.append("\"icon\":\""+ObjIsNull(mapArr.get("Pageicon"),"")+"\",");
            sb.append("\"target\":\"contentPage\",");
            sb.append("\"shortcut\":\""+ObjIsNull(mapArr.get("shortcut"),"0")+"\",");
            if("0".equals(state.toString())){
                sb.append("\"state\":\"closed\",");
            }
            String TemplateFrom = ObjIsNull(mapArr.get("TemplateFrom"),"");
            String ShowType = ObjIsNull(mapArr.get("ShowType"),"");
            String FormName = ObjIsNull(mapArr.get("FormName"),"");
            String NeedState = ObjIsNull(mapArr.get("needState"),"");
            String url = "";
            if(!"".equals(TemplateFrom)){
                sb.append("\"target\":\""+TemplateFrom+"\",");
            }else if(!"".equals(FormName)){
                if(!"".equals(ShowType)){
                    String type = ShowType.toUpperCase();
                    switch (type){
                        case "MDLISTFORM":
                        case "MDLISTMODIFYFORM":
                            url = "/UiGenerate/DList.jsp?FormName=" + FormName + "&ShowType=" + type + "&NeedState=" + NeedState;
                            break;
                        case "MDNEWFORM":
                            url = "/UiGenerate/DInsert.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "LISTFORM":
                        case "LISTMODIFYFORM":
                            url = "/UiGenerate/list.jsp?FormName=" + FormName + "&ShowType=" + type + "&NeedState=" + NeedState;
                            break;
                        case "LISTFORMTREE":
                            url = "/UiGenerate/ListTree.jsp?FormName=" + FormName + "&ShowType=" + type + "&NeedState=" + NeedState;
                            break;
                        case "LISTFORMDEPTETREE":
                            url = "/UiGenerate/ListDepteTree.jsp?FormName=" + FormName + "&ShowType=" + type + "&NeedState=" + NeedState;
                            break;
                        case "NEWFORM":
                            url = "/UiGenerate/insert.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "STATELISTFORM":
                            url = "/UiGenerate/StateList.jsp?FormName=" + FormName + "&ShowType=" + type + "&NeedState=" + NeedState;
                            break;
                        case "EXPANDABLELISTFORM":
                            url = "/UiGenerate/ExpandableList.jsp?FormName=" + FormName + "&ShowType=" + type + "&NeedState=" + NeedState;
                            break;
                        case "COLLECTIONFORM":
                            url = "/UiGenerate/Collection.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "STATECOLLECTIONFORM":
                            url = "/UiGenerate/StateCollection.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "BAIDUMAPFORM":
                            url = "/UiGenerate/BaiduMap.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "GAODEMAPFORM":
                            url = "/UiGenerate/GaodeMap.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "CALENDARFORM":
                            url = "/UiGenerate/Calender.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "TIMEAXISFORM":
                            url = "/UiGenerate/TimeAxis.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "CHARTFORM":
                            url = "/UiGenerate/Chart.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                        case "COPFORM":
                        case "FREEFORM":
                            url = "/UiGenerate/Blank.jsp?FormName=" + FormName + "&ShowType=" + type;
                            break;
                    }
                }
                sb.append("\"url\":\""+url+"\"");
            }
            sb.append(TableToTreeJson(list, pField, pcv, kField, TextField));
            sb.append("},");
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 判断Object是否为null
     * @param obj
     * @param trueValue 若为null 返回该值
     * @return
     */
    public String ObjIsNull(Object obj,String trueValue){
        String result;
        if(obj==null){
            result = trueValue;
            return result;
        } else{
            result = obj.toString();
            return result;
        }
    }

    /**
     * 绑定微信
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void toBindWeChat(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{

        String CompanyId = Variable.getCompanyId();
        String UserName = req.getParameter("UserName");
        String Password = req.getParameter("password");
        String openid = req.getParameter("openid");
        String MD5Pass = MD5.MD5(Password);

        logger.info("into tobindwechat");
        logger.info("CompanyId:"+CompanyId);
        logger.info("UserName:"+UserName);
        logger.info("Password:"+Password);
        logger.info("openid:"+openid);
        logger.info("MD5Pass:"+MD5Pass);

        List<Map<String,Object>> userList = userDao.login(CompanyId,UserName,MD5Pass);
        logger.info("userList:"+userList.size());
        if(userList.size()==0){
            Show(resp,"用户名或密码错误,请重新输入！");
        } else {
            if(openid != null && !"".equals(openid)){
                Map<String,Object> userMap = userList.get(0);
                String userid = userMap.get("Id").toString();
                logger.info("userid:"+userid);
                Object openidByUser = userMap.get("openid");
                logger.info("openidByUser:"+openidByUser);
                if(openidByUser != null && !"".equals(openidByUser.toString()) && openid.equals(openidByUser.toString())){
                    Show(resp,"您已经绑定过微信！");
                } else{
                    int result = userDao.bindWeChat(CompanyId,userid,openid);
                    logger.info("result:"+result);
                    if(result>0){
                        Show(resp,"绑定微信成功！");
                    } else{
                        Show(resp,"绑定微信失败，请重试！");
                    }
                }
            } else {
                Show(resp,"获取用户信息失败,请重试！");
            }
        }
    }

    public void register(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{

        String CompanyId = Variable.getCompanyId();
        String UserName = req.getParameter("UserName");
        String Password = req.getParameter("password");
        String RoleId = req.getParameter("RoleId");
        String DeptId = req.getParameter("DeptId");
        //String openid = req.getParameter("openid");
        String MD5Pass = MD5.MD5(Password);

        List<Map<String,Object>> userList = userDao.login(CompanyId,UserName);
        if(userList.size()==0){
            Long Id = userDao.register(CompanyId,UserName,MD5Pass,DeptId,RoleId);
            if (Id > 0) {
                //Show2(resp,"注册成功！");
                List<Map<String,Object>> user = userDao.getUserById(CompanyId,Id.toString());
                Map<String,Object> userMap = user.get(0);
                String userCookie = userMap.get("Id").toString()+"|"+userMap.get("UserName").toString();
                req.getSession().setAttribute("UserId",userMap.get("Id").toString());
                req.getSession().setAttribute("DeptId",userMap.get("DeptId").toString());
                CookieHelper.addCookie(resp,"user",userCookie,0);

                String target = "goto:homePageIndex,copForm";
                List<Map<String,Object>> mapList = TargetManager.judge(target);

                String view = ParentForm.compositeTemplate(req,mapList);
                req.setAttribute("View",view);
                req.getRequestDispatcher("/UiGenerateWap/index.jsp").forward(req,resp);
            } else {
                Show(resp,"注册失败,请重试！");
            }
        } else {
            Show(resp,"帐号已存在！");
        }
    }

    /**
     * 弹窗提示信息
     * @param response
     * @param msg
     */
    public void Show(HttpServletResponse response, String msg){
        try{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.flush();
            pw.print("<script>alert('"+msg+"');window.history.back();</script>");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void Show2(HttpServletResponse response, String msg){
        try{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.flush();
            pw.print("<script>alert('"+msg+"');</script>");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
