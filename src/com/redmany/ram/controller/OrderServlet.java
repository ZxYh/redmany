package com.redmany.ram.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/7/28.
 */
@WebServlet("/Order")
public class OrderServlet extends HttpServlet {

    private OrderDao orderDao = new OrderDao();
    private TablesDao tablesDao = new TablesDao();
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("saveOrder".equals(method)) {
            saveOrder(req, resp);
        } else if("OrderBalancePay".equals(method)){
            OrderBalancePay(req, resp);
        } else if("OrderCancel".equals(method)){
            OrderCancel(req, resp);
        } else if("OrderFail".equals(method)){
            OrderFail(req, resp);
        }
    }

    public void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getSession().getAttribute("UserId") == null){
            Date date = new Date();
            resp.sendRedirect("AutoLogin?method=login&t="+date.getTime());
            //resp.sendRedirect("Main?t="+date.getTime());
        } else {
            String CompanyId = Variable.getCompanyId();
            String UserId = req.getSession().getAttribute("UserId").toString();

            req.setCharacterEncoding("UTF-8");
            String order = req.getParameter("DataJson");
            order = new String(order.getBytes("ISO-8859-1"), "UTF-8");
            JSONObject json = JSON.parseObject(order);
            String tableName = json.getString("TableNum");
            //String subNum = json.getString("SubNum");
            String subTotal = json.getString("SubTotal");

            Map<String, Object> foundingMap = tablesDao.getFoundingTableInfo(CompanyId, tableName);
            if (foundingMap != null) {
                String FoundingId = foundingMap.get("Id").toString();
                String TableId = foundingMap.get("TableId").toString();
                String OrderType="1";
                Long orderId = orderDao.insertOrder(CompanyId, UserId.toString(),OrderType,subTotal,FoundingId,TableId);
                if (orderId > 0L) {
                    JSONArray tableItem = json.getJSONArray("TableItem");
                    List<String> OrderDV = new ArrayList<String>();
                    for (int i = 0; i < tableItem.size(); i++) {
                        JSONObject itemJson = tableItem.getJSONObject(i);
                        String itemId = itemJson.getString("id");
                        String goodId = itemId.substring(4, itemId.length());
                        String num = itemJson.getString("num");
                        String price = itemJson.getString("price");
                        price = price.substring(1, price.length());

                        String orderDetailsValue = orderId + "," + goodId + ",'" + price + "'," + "'" + num + "'";
                        OrderDV.add(orderDetailsValue);
                    }
                    int orderDId = orderDao.insertOrderDetails(CompanyId,OrderDV);
                    if (orderDId > 0) {
                        req.setAttribute("oid",orderId);
                        //req.getSession().setAttribute("oid", orderId);
                        req.setCharacterEncoding("UTF-8");
                        req.getRequestDispatcher("/WeChatPay?method=pay").forward(req, resp);
                    }
                }
            }
        }
    }


    public void OrderBalancePay(HttpServletRequest req, HttpServletResponse resp) throws  ServletException,IOException{
        if(req.getSession().getAttribute("UserId") == null){
            Date date = new Date();
            resp.sendRedirect("AutoLogin?method=login&t="+date.getTime());
            //resp.sendRedirect("Main?t="+date.getTime());
        } else {
            String CompanyId = Variable.getCompanyId();
            String UserId = req.getSession().getAttribute("UserId").toString();

            req.setCharacterEncoding("UTF-8");
            String orderNum = req.getParameter("orderNum");
            orderNum = new String(orderNum.getBytes("ISO-8859-1"),"UTF-8");
            String money = req.getParameter("money");
            money = new String(money.getBytes("ISO-8859-1"),"UTF-8");

            Map<String,Object> userBalance = userDao.getBalance(CompanyId,UserId);
            if(userBalance.size()>0){
                Float balance = Float.parseFloat(userBalance.get("Balance").toString());
                if(balance > Float.parseFloat(money)){
                    String state = "1";
                    String PayType = "4";
                    int i = orderDao.updateOrderState(CompanyId,orderNum,state,PayType);
                    if(i>0){
                        String BalanceType= "2";
                        Long PayId = orderDao.balancePay(CompanyId,UserId,BalanceType,money);
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

    public void OrderCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CompanyId = Variable.getCompanyId();
        req.setCharacterEncoding("UTF-8");
        String orderNum = req.getParameter("orderNum");
        orderNum = new String(orderNum.getBytes("ISO-8859-1"),"UTF-8");

        int i = orderDao.deleteOrder(CompanyId,orderNum);

        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/Target?target=goto:homePageIndex,copForm").forward(req, resp);
    }

    public void OrderFail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CompanyId = Variable.getCompanyId();
        req.setCharacterEncoding("UTF-8");
        String orderNum = req.getParameter("orderNum");
        orderNum = new String(orderNum.getBytes("ISO-8859-1"),"UTF-8");

        int i = orderDao.deleteOrder(CompanyId,orderNum);

        req.setCharacterEncoding("UTF-8");

        try{
            String tableNum = req.getSession().getAttribute("tablesNum").toString();
            if(tableNum != null && !"".equals(tableNum)){
                req.getRequestDispatcher("/Target?target=goto:foodOrder,foodOrderForm").forward(req, resp);
            } else {
                req.getRequestDispatcher("/Target?target=goto:homePageIndex,copForm").forward(req, resp);
            }
        } catch (Exception e){
            req.getRequestDispatcher("/Target?target=goto:homePageIndex,copForm").forward(req, resp);
        }

    }
}
