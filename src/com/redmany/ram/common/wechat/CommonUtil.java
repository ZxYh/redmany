package com.redmany.ram.common.wechat;

import com.redmany.ram.controller.Log4jServlet;
import com.redmany.ram.dao.WechatDao;
import com.redmany.ram.model.Variable;
import com.redmany.ram.model.wechat.APPConfig;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/8/2.
 */
public class CommonUtil {

    private static Logger logger = Logger.getLogger(Log4jServlet.class);
    private static WechatDao wechatDao = new WechatDao();

    //access_token
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //ticket
    public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    //code
    public final static String getCode_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
    //openid
    public final static String getOpen_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";




    /**
     * 获取微信JSSDK的access_token
     * @return
     */
    public static String getAccessToken() {
        String result = "";

        String CompanyId = Variable.getCompanyId();
        List<Map<String,Object>> weChatParamsList = wechatDao.getWeChatParams(CompanyId);
        Map<String,Object> map = weChatParamsList.get(0);

        //Singleton singleton = Singleton.getInstance();
        //Map<String, String> map = singleton.getMap();
        //String expires_in = map.get("expires_in");
        //String accessToken = map.get("access_token");
        Object paramId = map.get("Id");
        Object expires_in = map.get("expires_in");
        Object accessToken = map.get("access_token");
        Long nowDate = new Date().getTime();
        //这里设置过期时间
        if (accessToken != null && expires_in != null && nowDate - Long.parseLong(expires_in.toString()) < 7000 * 1000) {
            result = accessToken.toString();
        } else {
            String requestUrl = access_token_url.replace("APPID", APPConfig.APPID).replace("APPSECRET", APPConfig.APPSECRET);
            JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    accessToken = jsonObject.getString("access_token");
                    int i = wechatDao.setAccessToken(CompanyId,paramId.toString(),accessToken.toString(),nowDate.toString());
                    //map.put("expires_in", nowDate + "");
                    //map.put("access_token", jsonObject.getString("access_token"));
                    //Singleton.initThread();
                } catch (JSONException e) {
                    accessToken = "";
                    // 获取token失败
                    System.out.println("获取token失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
                }
            }
            result = accessToken.toString();
        }
        return result;
    }

    /**
     * 获取ticket
     * @param accessToken
     * @return
     */
    public static String getJSSDKTicket(String accessToken){
        String result = "";

        String CompanyId = Variable.getCompanyId();
        List<Map<String,Object>> weChatParamsList = wechatDao.getWeChatParams(CompanyId);
        Map<String,Object> map = weChatParamsList.get(0);

        //Singleton singleton = Singleton.getInstance();
        //Map<String, String> map = singleton.getMap();
        //String expires_in = map.get("expires_in");
        //String jsApiTicket = map.get("ticket");
        Object paramId = map.get("Id");
        Object expires_in = map.get("expires_in");
        Object jsApiTicket = map.get("ticket");
        Long nowDate = new Date().getTime();
        //这里设置过期时间
        if (!"".equals(jsApiTicket) && jsApiTicket != null && expires_in != null && nowDate - Long.parseLong(expires_in.toString()) < 7000 * 1000) {
            result = jsApiTicket.toString();
        } else {
            String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken);
            JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    jsApiTicket = jsonObject.getString("ticket");
                    //时间与token的共用
                    //map.put("ticket", jsonObject.getString("ticket"));
                    int i = wechatDao.setTicket(CompanyId,paramId.toString(),jsApiTicket.toString());
                } catch (JSONException e) {
                    jsApiTicket = "";
                    // 获取jsApiTicket失败
                    System.out.println("获取jsApiTicket失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
                }
            }
            result = jsApiTicket.toString();
        }
        return result;
    }

    /**
     * 发送https请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
        } catch (Exception e) {
        }
        return jsonObject;
    }

    public static String getCode(String url) throws IOException{
        String result = "";
        String code = "";
        String newUrl = URLEncoder.encode(url,"UTF-8");
        String requestUrl = getCode_url.replace("APPID", APPConfig.APPID).replace("REDIRECTURL", newUrl);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                code = jsonObject.getString("code");
            } catch (JSONException e) {
                code = null;
            }
        }
        result = code;
        return result;
    }

    /**
     * 获取用户openid
     * @param code
     * @return
     */
    public static String getOpenId(String code){
        String result = "";
        String openid = "";
        //这里设置过期时间
        if (code != null && !"".equals(code)) {
            String requestUrl = getOpen_url.replace("APPID", APPConfig.APPID).replace("APPSECRET", APPConfig.APPSECRET).replace("CODE",code);
            JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
            if (null != jsonObject) {
                try {
                    openid = jsonObject.getString("openid");
                } catch (JSONException e) {
                    openid = null;
                    logger.info("获取openid失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
                    System.out.println("获取openid失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
                }
            }
            result = openid;
        } else {
            result = null;
        }
        return result;
    }



}
