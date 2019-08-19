package com.redmany.ram.controller;

import com.redmany.ram.dao.OrderDao;
import com.redmany.ram.dao.TablesDao;
import com.redmany.ram.dao.UserDao;
import com.redmany.ram.model.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/20.
 */
@WebServlet("/Settlement")
public class SettlementServlet extends HttpServlet {

    private TablesDao tablesDao = new TablesDao();
    private OrderDao orderDao = new OrderDao();
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("settlement".equals(method)){
            settlement(req, resp);
        } else if("GetOrderDetail".equals(method)){
            GetOrderDetail(req,resp);
        } else if("BalancePay".equals(method)){
            BalancePay(req, resp);
        }
    }

    public void settlement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getSession().getAttribute("UserId") == null){
            Date date = new Date();
            resp.sendRedirect("AutoLogin?method=login&t="+date.getTime());
            //resp.sendRedirect("Main?t="+date.getTime());
        } else {
            String tableNum = req.getParameter("tableNum");
            if(tableNum != null || !"".equals(tableNum)){
                String CompanyId = Variable.getCompanyId();
                Map<String,Object> foundingTableInfo = tablesDao.getFoundingTableInfo(CompanyId,tableNum);
                if(foundingTableInfo.size()>0){
                    String TablePrice = foundingTableInfo.get("TablePrice").toString();
                    String FoundingTime = foundingTableInfo.get("FoundingTime").toString();
                    String FoundingId = foundingTableInfo.get("Id").toString();
                    String TableId = foundingTableInfo.get("TableId").toString();
                    Date EndingTime = new Date();

                    String time = "";
                    float tablesTotal = 0f;
                    float needPayOrder = 0f;
                    float paymentOrder = 0f;
                    float finalTotal = 0f;
                    float finalPayTotal = 0f;

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    try{
                        Long foundingDate = sdf.parse(FoundingTime).getTime();
                        Long endingDate = EndingTime.getTime();
                        time = getHour(endingDate-foundingDate);

                        tablesTotal = Float.parseFloat(time) * Float.parseFloat(TablePrice);


                    List<Map<String,Object>> getNeedPayPrice = orderDao.getNeedPayOrderPrice(CompanyId,FoundingId);
                    if(getNeedPayPrice.size()>0){
                        for (Map<String,Object> needPayMap:getNeedPayPrice){
                            needPayOrder += Float.parseFloat(needPayMap.get("OrderPrice").toString());
                        }
                    }

                    List<Map<String,Object>> getPaymentPrice = orderDao.getPaymentOrderPrice(CompanyId,FoundingId);
                    if(getPaymentPrice.size()>0){
                        for (Map<String,Object> paymentMap:getPaymentPrice){
                            paymentOrder += Float.parseFloat(paymentMap.get("OrderPrice").toString());
                        }
                    }

                    finalTotal = tablesTotal + needPayOrder + paymentOrder;
                    finalPayTotal = tablesTotal + needPayOrder;

                    req.setAttribute("foundingId",FoundingId);
                    req.setAttribute("tableId",TableId);
                    req.setAttribute("tableNum",tableNum);
                    req.setAttribute("finalTotal",finalTotal);
                    req.setAttribute("foundingTime",sdf.format(sdf.parse(FoundingTime)));
                    req.setAttribute("endingTime",sdf.format(EndingTime));
                    req.setAttribute("tablePrice",TablePrice);
                    req.setAttribute("finalPayTotal",finalPayTotal);

                    req.getRequestDispatcher("/UiGenerateWap/settlement.jsp").forward(req,resp);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public String GetOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = null;
        StringBuilder sb = new StringBuilder();

        String CompanyId = Variable.getCompanyId();
        String FoundingId = req.getParameter("FoundingId").toString();
        String State = req.getParameter("state").toString();

        List<Map<String,Object>> getNeedPayOrder = orderDao.getOrderId(CompanyId,FoundingId,State);
        if(getNeedPayOrder.size()>0){
            String OrderId = "";
            for (Map<String,Object> map:getNeedPayOrder){
                String Id = map.get("Id").toString();
                OrderId += Id+",";
            }
            if(!"".equals(OrderId) && OrderId.endsWith(",")){
                OrderId = OrderId.substring(0,OrderId.length()-1);
                List<Map<String,Object>> getOrderDetails = orderDao.getOrderDetails(CompanyId,OrderId);
                if(getOrderDetails.size()>0){
                    for (Map<String,Object> orderDetailMap:getOrderDetails){
                        String GoodName = orderDetailMap.get("GoodName").toString();
                        String GoodPrice = orderDetailMap.get("GoodPrice").toString();
                        String GoodNum = orderDetailMap.get("GoodNum").toString();
                        sb.append("<tr>");
                            sb.append("<td>"+GoodName+"</td>");
                            sb.append("<td>"+GoodPrice+"</td>");
                            sb.append("<td>"+GoodNum+"</td>");
                        sb.append("</tr>");
                    }
                }
            }
        }

        result = sb.toString();

        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
        return result;
    }

    public void BalancePay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("UserId") == null){
            Date date = new Date();
            resp.sendRedirect("AutoLogin?method=login&t="+date.getTime());
            //resp.sendRedirect("Main?t="+date.getTime());
        } else {
            String CompanyId = Variable.getCompanyId();
            String UserId = req.getSession().getAttribute("UserId").toString();

            req.setCharacterEncoding("UTF-8");
            String FoundingId = req.getParameter("FoundingId");
            FoundingId = new String(FoundingId.getBytes("ISO-8859-1"),"UTF-8");
            String TableId = req.getParameter("TableId");
            TableId = new String(TableId.getBytes("ISO-8859-1"),"UTF-8");
            String EndTime = req.getParameter("EndTime");
            EndTime = new String(EndTime.getBytes("ISO-8859-1"),"UTF-8");
            String Receivable = req.getParameter("Receivable");
            Receivable = new String(Receivable.getBytes("ISO-8859-1"),"UTF-8");
            String pay = req.getParameter("pay");
            pay = new String(pay.getBytes("ISO-8859-1"),"UTF-8");
            String Payment = "4";
            String BalanceType = "2";

            Map<String,Object> userBalance = userDao.getBalance(CompanyId,UserId);
            if(userBalance.size()>0){
                Float balance = Float.parseFloat(userBalance.get("Balance").toString());
                if(balance > Float.parseFloat(pay)){
                    Long id = orderDao.settlement(CompanyId,FoundingId,TableId,EndTime,Receivable,Payment,UserId);
                    if(id>0){
                        Long PayId = orderDao.balancePay(CompanyId,UserId,BalanceType,pay);
                        if(PayId>0){
                            resp.setContentType("text/html; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.flush();
                            pw.print("<script>alert('支付成功');window.location.href='/Target?target=goto:homePageIndex,copForm';</script>");
                        }
                    }
                } else {
                    try{
                        resp.setContentType("text/html; charset=UTF-8");
                        PrintWriter pw = resp.getWriter();
                        pw.flush();
                        pw.print("<script>alert('余额不足，请前往收银台进行充值或支付');window.history.back();</script>");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }



        }
    }

    public static String getHour(Long time){
        long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
        if(minutes >= 30){
            hours += 1;
        }
        if(hours == 0){
            hours = 1;
        }
        return hours+"";
    }
}
