package com.redmany.ram.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hy on 2017/6/13.
 */
public class CookieHelper {

    /**
     * 设置Cookie
     * @param resp
     * @param name Cookie名字
     * @param value Cookie值
     * @param maxAge Cookie生命周期 （秒）
     */
    public static void addCookie(HttpServletResponse resp, String name, String value, int maxAge) throws UnsupportedEncodingException{
        value = URLEncoder.encode(value,"UTF-8");
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0){
            cookie.setMaxAge(maxAge);
        }
        resp.addCookie(cookie);
    }

    /**
     * 获取Cookie值
     * @param req
     * @param name
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getCookie(HttpServletRequest req,String name) throws UnsupportedEncodingException {
        Map<String,Cookie> cookieMap = ReadCookieMap(req);
        if(cookieMap.containsKey(name)){
            Cookie cookie = cookieMap.get(name);
            String coo = cookie.getValue();
            coo = URLDecoder.decode(coo,"UTF-8");
            return coo;
        }else{
            return null;
        }
    }

    /**
     * 将Cookie封装到Map中
     * @param req
     * @return CookieMap
     */
    public static Map<String,Cookie> ReadCookieMap(HttpServletRequest req){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = req.getCookies();
        if(null != cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
