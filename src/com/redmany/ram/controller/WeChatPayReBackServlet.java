package com.redmany.ram.controller;

import com.redmany.ram.common.wechat.RequestHandler;
import com.redmany.ram.dao.OrderDao;
import com.redmany.ram.model.Variable;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8.
 */
@WebServlet("/WeChatPayBack")
public class WeChatPayReBackServlet extends HttpServlet{

    private OrderDao orderDao = new OrderDao();
    private Logger logger = Logger.getLogger(Log4jServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String returnResutStr = "";

        String out_trade_no= null;
        String return_code = null;
        String result_code = null;
        String orderNum = null;

        try{
            InputStream inStream = req.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String resultStr  = new String(outSteam.toByteArray(),"UTF-8");
            logger.info("支付成功的回调："+resultStr);
            Map<String, Object> resultMap = parseXmlToList(resultStr);

            String is_subscribe = (String) resultMap.get("is_subscribe");
            String transaction_id = (String) resultMap.get("transaction_id");
            String sign = (String) resultMap.get("sign");
            String time_end = (String) resultMap.get("time_end");
            String bank_type = (String) resultMap.get("bank_type");

            out_trade_no = (String) resultMap.get("out_trade_no");
            return_code = (String) resultMap.get("return_code");
            result_code = (String) resultMap.get("result_code");

            //req.setAttribute("out_trade_no", out_trade_no);
            //通知微信.异步确认成功.必写.不然微信会一直通知后台.八次之后就认为交易失败了.

            //resp.getWriter().write(RequestHandler.setXML("SUCCESS", "OK"));

            if(return_code.equals("SUCCESS") && result_code.equals("SUCCESS")){

                if(out_trade_no != null){
                    int index = out_trade_no.indexOf("_");
                    if(index > 0){
                        orderNum = out_trade_no.substring(0,index);

                        String CompanyId = Variable.getCompanyId();
                        String state = "1";
                        String PayType = "1";

                        int oid = orderDao.updateOrderState(CompanyId,orderNum,state,PayType);
                        if(oid > 0){
                            logger.info("微信支付回调更新订单支付状态，更新订单支付状态成功，订单号：" + orderNum);
                            returnResutStr = RequestHandler.setXML("SUCCESS", "OK");
                        } else {
                            logger.info("微信支付回调更新订单支付状态，更新订单支付状态失败，订单号：" + orderNum);
                            returnResutStr = RequestHandler.setXML("FAIL", "error");
                        }
                        //System.out.println(s);
                    } else {
                        logger.info("微信支付回调更新订单支付状态，查无此单，订单号：" + out_trade_no);
                    }
                }
                //支付成功的业务逻辑
            /*
            <xml>
                <appid><![CDATA[wx8973076fb8dee5f4]]></appid>
                <attach><![CDATA[661]]></attach>
                <bank_type><![CDATA[CFT]]></bank_type>
                <cash_fee><![CDATA[1]]></cash_fee>
                <fee_type><![CDATA[CNY]]></fee_type>
                <is_subscribe><![CDATA[Y]]></is_subscribe>
                <mch_id><![CDATA[1486312702]]></mch_id>
                <nonce_str><![CDATA[201708281505355827]]></nonce_str>
                <openid><![CDATA[ogDY80vrJvn_lmcCzpLfr6wJXQzo]]></openid>
                <out_trade_no><![CDATA[80_20170828150535]]></out_trade_no>
                <result_code><![CDATA[SUCCESS]]></result_code>
                <return_code><![CDATA[SUCCESS]]></return_code>
                <sign><![CDATA[E8958BE999BBE5B9EC20B46C4B7AA84A]]></sign>
                <time_end><![CDATA[20170828150558]]></time_end>
                <total_fee>1</total_fee>
                <trade_type><![CDATA[JSAPI]]></trade_type>
                <transaction_id><![CDATA[4009832001201708288729171159]]></transaction_id>
            </xml>
            */
                //req.getRequestDispatcher("/UiGenerateWap/paySuccess.jsp").forward(req,resp);
            }else{
                logger.error("微信支付回调更新订单支付状态，验证失败，回调参数是非法的");
                returnResutStr = RequestHandler.setXML("FAIL", "error");
                //支付失败的业务逻辑
            }
        } catch (Exception e){
            logger.error("微信支付回调更新订单支付状态，出现异常:" + e.getMessage() + "，堆栈：" + e.getStackTrace());
            //resp.getWriter().write(RequestHandler.setXML("FAIL", "error"));
            returnResutStr = RequestHandler.setXML("FAIL", "error");
        }

        try {
            resp.getWriter().write(returnResutStr);
        } catch (Exception ee){
            ee.printStackTrace();
        }
    }

    /**
     * description: 解析微信通知xml
     *
     * @param xml
     * @return
     * @author ex_yangxiaoyi
     * @see
     */
    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
    private static Map parseXmlToList(String xml) {
        Map retMap = new HashMap();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();// 指向根节点
            List<Element> es = root.getChildren();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }
}
