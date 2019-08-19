package com.redmany.ram.controller;

import com.redmany.ram.common.CookieHelper;
import com.redmany.ram.common.wechat.CommonUtil;
import com.redmany.ram.common.wechat.GetWxOrderno;
import com.redmany.ram.common.wechat.RequestHandler;
import com.redmany.ram.common.wechat.Sha1Util;
import com.redmany.ram.dao.OrderDao;
import com.redmany.ram.model.Variable;
import com.redmany.ram.model.wechat.APPConfig;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/8.
 */
@WebServlet("/WeChatPay")
public class WeChatPayServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(Log4jServlet.class);
    private OrderDao orderDao = new OrderDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("pay".equals(method)) {
            pay(req, resp);
        } else if ("toPay".equals(method)) {
            toPay(req, resp);
        }
    }

    public void pay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("==========into method:pay==========");

        String userId = "";

        String userCookie = CookieHelper.getCookie(req, "user");
        if (userCookie != null) {
            String[] userInfo = userCookie.split("\\|");
            userId = userInfo[0];
        }

        String orderId = req.getAttribute("oid").toString();
        String describe = "飓星俱乐部";

        String CompanyId = Variable.getCompanyId();
        List<Map<String, Object>> orderList = orderDao.getOrderInfo(CompanyId, orderId);
        Map<String, Object> orderMap = orderList.get(0);
        String orderPrice = orderMap.get("OrderPrice").toString();
        String money = orderPrice;

        String backUrl = "http://" + req.getHeader("host") + "/WeChatPay?method=toPay";
        backUrl += "&userId=" + userId + "&orderNum=" + orderId + "&describe=" + describe + "&money=" + money;
        //localhost:8080/WeChatPay?method=toPay&userId=661&orderNum=56&describe=&money=93

        backUrl = URLEncoder.encode(backUrl, "UTF-8");
        String Url = CommonUtil.getCode_url.replace("APPID", APPConfig.APPID).replace("REDIRECTURL", backUrl);

        logger.info("==========into method:pay getUrl:" + Url + "==========");
        resp.setCharacterEncoding("UTF-8");
        resp.sendRedirect(Url);
    }

    public void toPay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("==========into method:toPay==========");

        String userId = req.getParameter("userId");
        String orderNum = req.getParameter("orderNum");
        String describe = req.getParameter("describe");
        String money = req.getParameter("money");
        String code = req.getParameter("code");

        float moneyF = Float.parseFloat(money);
        String finalMoney = String.format("%.2f", moneyF);
        finalMoney = finalMoney.replace(".", "");

        String openId = CommonUtil.getOpenId(code);
        logger.info("==========code:" + code + "==========");
        logger.info("==========openId:" + openId + "==========");

        String appid = APPConfig.APPID;
        String appsecret = APPConfig.APPSECRET;
        String partner = APPConfig.PARTNER;
        String partnerKey = APPConfig.PARTNERKEY;
        //商户号
        String mch_id = partner;
        //设备号
        String device_info = "";
        //随机数
        String nonce_str = getCurrTime() + getRandom(4);
        //商品描述
        String body = describe;
        //附加数据
        String attach = userId;
        //商户订单号
        String out_trade_no = orderNum+"_"+getCurrTime();
        logger.info("==========商户订单号:"+out_trade_no+":==========");
        //总金额(分)
        int total_fee = Integer.parseInt(finalMoney);
        //订单生成的机器IP
        String spbill_create_ip = req.getRemoteAddr();
        //支付后回调地址
        String notify_url = "http://" + req.getHeader("host") + "/WeChatPayBack";
        String trade_type = "JSAPI";
        //用户openId
        String openid = openId;

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", attach);
        packageParams.put("body", body);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("notify_url", notify_url);
        packageParams.put("openid", openid);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        //测试1分
        //packageParams.put("total_fee", "1");
        packageParams.put("total_fee",finalMoney);
        packageParams.put("trade_type", trade_type);

        RequestHandler requestHandler = new RequestHandler(req, resp);
        requestHandler.init(appid, appsecret, partnerKey);

        String sign = requestHandler.createSign(packageParams);
        String xml = "<xml>"
                + "<appid>" + appid + "</appid>"
                + "<attach>" + attach + "</attach>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<mch_id>" + mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + notify_url + "</notify_url>"
                + "<openid>" + openid + "</openid>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<sign>" + sign + "</sign>"
                + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                //这里先给1分测试
                //+ "<total_fee>" + 1 + "</total_fee>"
                + "<total_fee>" + finalMoney + "</total_fee>"
                + "<trade_type>" + trade_type + "</trade_type>"
                + "</xml>";

        logger.info("==========into method:toPay getXml" + xml + "==========");
        /*String allParameters = "";
        try {
            allParameters = requestHandler.genPackage(packageParams);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String createOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = GetWxOrderno.getPayNo(createOrderUrl, xml);
            if (prepay_id.equals("")) {
                req.getSession().setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        //String nonceStr2 = nonce_str;
        String nonceStr2 = getCurrTime() + getRandom(4);
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id2;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        String finalsign = requestHandler.createSign(finalpackage);
        // System.out.println("pay.jsp?appid="+appid2+"&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);
        try {
            resp.sendRedirect(
                    req.getScheme() + "://"
                            + req.getHeader("host")
                            + "/UiGenerateWap/pay.jsp?appid=" + appid2
                            + "&timeStamp=" + timestamp + "&nonceStr="
                            + nonceStr2 + "&package=" + packages + "&sign="
                            + finalsign + "&orderNum="+orderNum+"&money="+money);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getCurrTime() {
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(dateNow);
        return str;
    }

    public int getRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) (random * num);
    }
}
