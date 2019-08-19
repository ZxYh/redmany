package com.redmany.ram.controller;

import com.redmany.ram.common.TargetManager;
import com.redmany.ram.control.ParentForm;
import com.redmany.ram.model.wechat.APPConfig;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/7/4.
 */
@WebServlet("/Target")
public class TargetServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String target = req.getParameter("target");

        if(StringUtils.isBlank(target)){
            return;
        }

        List<Map<String,Object>> mapList = TargetManager.judge(target);

        String turnUrl = "/UiGenerateWap/index.jsp";
        String type = "forward";

        if(mapList!=null&&mapList.size()>0){
            String view = ParentForm.compositeTemplate(req,mapList);
            req.setAttribute("View",view);
            for(Map<String,Object> map : mapList){
                String showType = map.get("ShowType").toString().toUpperCase();
                if("FOODORDERFORM".equals(showType)){
                    turnUrl = "/UiGenerateWap/foodOrder.jsp";
                }else if("REGIESTFORM".equals(showType)){
                    turnUrl = "/UiGenerateWap/regiest.jsp";
                }else if("SCANFORM".equals(showType)){
                    String ProUrl = req.getScheme()+"://";
                    ProUrl += req.getHeader("host");
                    ProUrl += "/UiGenerateWap/scan.jsp";
                    turnUrl = ProUrl;
                    type = "sendRedirect";
                } else if("BINDWECHATFORM".equals(showType)){
                    String ProUrl = req.getScheme()+"://";
                    ProUrl += req.getHeader("host");
                    //ProUrl += "weixin.bigstar.vip";
                    ProUrl += "/Wechat?method=bind";
                    String ToUrl = URLEncoder.encode(ProUrl,"UTF-8");

                    String bindUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                            + APPConfig.APPID
                            + "&redirect_uri="
                            + ToUrl
                            + "&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";

                    turnUrl = bindUrl;
                    type = "sendRedirect";
                }
            }

            if("forward".equals(type)){
                req.getRequestDispatcher(turnUrl).forward(req,resp);
            } else if("sendRedirect".equals(type)){
                resp.sendRedirect(turnUrl);
            } else {
                req.getRequestDispatcher(turnUrl).forward(req,resp);
            }

        }
    }
}
