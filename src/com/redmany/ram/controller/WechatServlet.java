package com.redmany.ram.controller;

import com.redmany.ram.common.wechat.CommonUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/3.
 */
@WebServlet("/Wechat")
public class WechatServlet extends HttpServlet{

    private static Logger logger = Logger.getLogger(Log4jServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("sign".equals(method)) {
            sign(req, resp);
        } else if("bind".equals(method)){
            bind(req, resp);
        }
    }

    public void sign(HttpServletRequest req, HttpServletResponse resp) throws  ServletException,IOException{
        String url = req.getParameter("url");

        Map<String, String> ret = new HashMap<String, String>();
        String accessToken = CommonUtil.getAccessToken();
        String jsapi_ticket = CommonUtil.getJSSDKTicket(accessToken);
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String result = "";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"url\":\""+url+"\",");
        sb.append("\"jsapi_ticket\":\""+jsapi_ticket+"\",");
        sb.append("\"nonceStr\":\""+nonce_str+"\",");
        sb.append("\"timestamp\":\""+timestamp+"\",");
        sb.append("\"signature\":\""+signature+"\"");
        sb.append("}");
        result = sb.toString();

        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private void bind(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        String code = req.getParameter("code");
        logger.info("code:"+code);
        String openid = CommonUtil.getOpenId(code);
        System.out.println(openid);
        req.setCharacterEncoding("UTF-8");
        //req.setAttribute("openid",openid);
        req.getSession().setAttribute("openid",openid);
        req.getRequestDispatcher("/UiGenerateWap/bindWeChat.jsp").forward(req,resp);
    }

}
