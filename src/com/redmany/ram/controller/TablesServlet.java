package com.redmany.ram.controller;

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
 * Created by hy on 2017/8/7.
 */
@WebServlet("/Table")
public class TablesServlet extends HttpServlet{

    private TablesDao tablesDao = new TablesDao();
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");

        if("ScanTables".equals(method)){
            scanTables(req, resp);
        } else if("OpenTables".equals(method)){
            openTables(req, resp);
        }
    }

    // http://weixin.bigstar.vip/Table?method=ScanTables&TableNum=FB001
    public void scanTables(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{

         if(req.getSession().getAttribute("UserId") == null){
             Date date = new Date();
             resp.sendRedirect("AutoLogin?method=login&t="+date.getTime());
             //resp.sendRedirect("Main?t="+date.getTime());
         } else {
             String CompanyId = Variable.getCompanyId();
             String TableNum = req.getParameter("TableNum");

             if(TableNum != null && !"".equals(TableNum)){
                 List<Map<String,Object>> tablesList = new ArrayList<Map<String,Object>>();
                 tablesList = tablesDao.getTablesInfo(CompanyId,TableNum);
                 if(tablesList.size()>0){
                     Map<String,Object> tablesMap = tablesList.get(0);
                     String tablesId = tablesMap.get("Id").toString();
                     String tablesState = tablesMap.get("state").toString();
                     String tablesNum = tablesMap.get("TableNum").toString();
                     String tablesType = tablesMap.get("TableType").toString();
                     String tablesPrice = tablesMap.get("TablePrice").toString();
                     if("0".equals(tablesState)){
                         //未使用
                         req.setAttribute("tablesId",tablesId);
                         req.setAttribute("tablesNum",tablesNum);
                         req.setAttribute("tablesType",tablesType);
                         req.setAttribute("tablesPrice",tablesPrice);

                         req.getRequestDispatcher("/UiGenerateWap/openTable.jsp").forward(req,resp);
                     } else if("1".equals(tablesState)){
                         req.getSession().setAttribute("tablesNum",tablesNum);
                         //resp.sendRedirect("Target?target=goto:foodOrder,foodOrderForm");
                         //req.getRequestDispatcher("/Target?target=goto:foodOrder,foodOrderForm").forward(req,resp);
                         req.getRequestDispatcher("/UiGenerateWap/chooseTableFun.jsp").forward(req,resp);
                     } else if("2".equals(tablesState)){
                         Show(resp,"该桌台已被预约，请选择其他桌台！");
                     }
                 }else{
                     Show(resp,"找不到该桌台编号，请联系服务人员！");
                 }
             }
         }
    }

    public void openTables(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{

        if(req.getSession().getAttribute("UserId") == null){
            Date date = new Date();
            resp.sendRedirect("AutoLogin?method=login&t="+date.getTime());
            //resp.sendRedirect("Main?t="+date.getTime());
        } else {
            String CompanyId = Variable.getCompanyId();
            String UserId = req.getSession().getAttribute("UserId").toString();

            req.setCharacterEncoding("UTF-8");
            String TableId = req.getParameter("TableId");
            TableId = new String(TableId.getBytes("ISO-8859-1"),"UTF-8");
            String TableType = req.getParameter("TableType");
            TableType = new String(TableType.getBytes("ISO-8859-1"),"UTF-8");
            String TableNum = req.getParameter("TableNum");
            TableNum = new String(TableNum.getBytes("ISO-8859-1"),"UTF-8");
            String TablePrice = req.getParameter("TablePrice");
            TablePrice = new String(TablePrice.getBytes("ISO-8859-1"),"UTF-8");

            Map<String,Object> userBalance = userDao.getBalance(CompanyId,UserId);
            if(userBalance.size()>0){
                Float balance = Float.parseFloat(userBalance.get("Balance").toString());
                if(balance > Float.parseFloat(TablePrice)){
                    Long obj = tablesDao.openTable(CompanyId,TableId,TableType,TableNum,TablePrice);
                    if (obj != null) {
                        String tablesNum = TableNum;
                        req.getSession().setAttribute("tablesNum",tablesNum);
                        req.getRequestDispatcher("/UiGenerateWap/chooseTableFun.jsp").forward(req,resp);
                    } else {
                        try{
                            resp.setContentType("text/html; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.flush();
                            pw.print("<script>alert('开台失败，请重试');window.history.back();</script>");
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }else {
                    try{
                        resp.setContentType("text/html; charset=UTF-8");
                        PrintWriter pw = resp.getWriter();
                        pw.flush();
                        pw.print("<script>alert('余额不足，请前往收银台进行开台或支付');window.history.back();</script>");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
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

    /**
     * 弹窗提示信息
     * @param response
     * @param msg
     */
    public void Show2(HttpServletResponse response, String msg, String gotoUrl){
        try{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.flush();
            pw.print("<script>alert('"+msg+"');window.location.href='"+gotoUrl+"';</script>");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
