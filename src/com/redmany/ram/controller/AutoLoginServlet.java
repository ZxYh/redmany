package com.redmany.ram.controller;

import com.redmany.ram.common.CookieHelper;
import com.redmany.ram.common.TargetManager;
import com.redmany.ram.common.wechat.CommonUtil;
import com.redmany.ram.control.ParentForm;
import com.redmany.ram.dao.UserDao;
import com.redmany.ram.model.Variable;
import com.redmany.ram.model.wechat.APPConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */
@WebServlet("/AutoLogin")
public class AutoLoginServlet extends HttpServlet{

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        if("login".equals(method)){
            login(req, resp);
        } else if ("getOpenId".equals(method)){
            getOpenId(req,resp);
        }

    }

    public void login (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ProUrl = req.getScheme()+"://";
        ProUrl += req.getHeader("host");
        //ProUrl += "weixin.bigstar.vip";
        ProUrl += "/AutoLogin?method=getOpenId";
        String ToUrl = URLEncoder.encode(ProUrl,"UTF-8");

        String bindUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + APPConfig.APPID
                + "&redirect_uri="
                + ToUrl
                + "&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
        resp.sendRedirect(bindUrl);
    }

    public void getOpenId (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CompanyId = Variable.getCompanyId();
        String code = req.getParameter("code");
        String openId = CommonUtil.getOpenId(code);
        if(openId != null){
            Map<String,Object> userMap = userDao.getUserByOpenId(CompanyId,openId);
            if(userMap != null && userMap.size()>0){
                String userCookie = userMap.get("Id").toString()+"|"+userMap.get("UserName").toString()+"|"+userMap.get("RealName").toString();
                req.getSession().setAttribute("UserId",userMap.get("Id").toString());
                CookieHelper.addCookie(resp,"user",userCookie,0);

                String target = "goto:homePageIndex,copForm";

                List<Map<String,Object>> mapList = TargetManager.judge(target);

                String view = ParentForm.compositeTemplate(req,mapList);
                req.setAttribute("View",view);
                req.getRequestDispatcher("/UiGenerateWap/index.jsp").forward(req,resp);
            } else {
                Date date = new Date();
                resp.sendRedirect("Main?t="+date.getTime());
            }

        } else {
            Date date = new Date();
            resp.sendRedirect("Main?t="+date.getTime());
        }
    }
}
