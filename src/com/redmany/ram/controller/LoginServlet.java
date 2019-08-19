package com.redmany.ram.controller;

import com.redmany.ram.common.CookieHelper;
import com.redmany.ram.common.MD5;
import com.redmany.ram.common.TargetManager;
import com.redmany.ram.control.ParentForm;
import com.redmany.ram.dao.UserDao;
import com.redmany.ram.model.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/8.
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    private UserDao userDao = new UserDao();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        String CompanyId = Variable.getCompanyId();
        String UserName = request.getParameter("UserName");
        String target = request.getParameter("target");
        String Password = request.getParameter("password");

        if(CompanyId != "" && CompanyId != null){
            //String companyStatus = userDao.checkCompany(CompanyId);
            //if(companyStatus.equals("no")){
            //    Show(response,"企业帐号不存在，请重新输入，谢谢！");
            //}else if(companyStatus.equals("expire")){
            //    Show(response,"企业帐号已经过期，请联系客服中心处理，谢谢！");
            //}else if(companyStatus.equals("muser_over")){
            //    Show(response,"手机客户端数量超过授权，请联系客服中心处理，谢谢！");
            //}else if(companyStatus.equals("cuser_over")){
            //    Show(response,"系统用户数量超过授权，请联系客服中心处理，谢谢！");
            //}else{
            try{
                HttpSession session = request.getSession();
                session.setAttribute("CompanyId",CompanyId);
                String MD5Pass = MD5.MD5(Password);
                List<Map<String,Object>> userList = userDao.login(CompanyId,UserName,MD5Pass);
                if(userList.size()==0){
                    Show(response,"用户名或密码错误,请重新输入！");
                }else{
                    Map<String,Object> userMap = userList.get(0);

                    String state = userMap.get("State").toString();
                    if(!"0".equals(state)){
                        Show(response,"帐号已停用！");
                    } else {
                        String userCookie = userMap.get("Id").toString()+"|"+userMap.get("UserName").toString()+"|"+userMap.get("RealName").toString();
                        session.setAttribute("UserId",userMap.get("Id").toString());
                        CookieHelper.addCookie(response,"user",userCookie,0);

                        List<Map<String,Object>> mapList = TargetManager.judge(target);

                        String view = ParentForm.compositeTemplate(request,mapList);
                        request.setAttribute("View",view);
                        request.getRequestDispatcher("/UiGenerateWap/index.jsp").forward(request,response);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //}
        }else{
            Show(response,"企业ID不能为空！");
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
}
