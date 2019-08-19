package com.redmany.ram.controller;

import com.redmany.ram.common.SQLHelper;
import com.redmany.ram.dao.FormDao;
import com.redmany.ram.model.Form;
import com.redmany.ram.model.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/6/20.
 */
@WebServlet("/Submit")
public class SubmitServlet extends HttpServlet{

    private FormDao formDao = new FormDao();
    private SQLHelper sqlHelper = new SQLHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String FormName = req.getParameter("FormName");
        String ShowType = req.getParameter("ShowType");
        String NeedState = req.getParameter("NeedState");
        String Key = req.getParameter("id");
        System.out.println("Key:..."+Key);
        boolean istrue = DoSubmit(req,FormName,ShowType,NeedState,Key);

        if(istrue){
            if(!"".equals(Key) && Key != null){
                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter pw = resp.getWriter();
                pw.flush();
                pw.print("<script>alert('提交成功！点击确定刷新！');window.parent.contentPage.location.href='/UiGenerate/list.jsp?FormName="+FormName+"&ShowType="+ShowType+"&NeedState=" + NeedState + "';</script>");
            }else{
                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter pw = resp.getWriter();
                pw.flush();
                pw.print("<script>alert('提交成功！点击确定刷新！');window.parent.contentPage.location.href='/UiGenerate/insert.jsp?FormName="+FormName+"&ShowType="+ShowType+"';</script>");
            }
        }
    }

    public boolean DoSubmit(HttpServletRequest req,String FormName,String ShowType, String NeedState,String Key){
        int result = 0;
        Enumeration allKey = req.getParameterNames();
        String fields = "";
        String values = "";
        String OpreateSql = "";
        while (allKey.hasMoreElements()){
            String paraName = allKey.nextElement().toString();
            if(!"FormName".equals(paraName) && !"ShowType".equals(paraName) & !"NeedState".equals(paraName)){
                fields += paraName + ",";
                values += "'"+req.getParameter(paraName) + "'" + ",";
            }
        }

        if("NEWFORM".equals(ShowType.toUpperCase())){
            String CompanyId = Variable.getCompanyId();
            Form form = formDao.getForm(CompanyId,FormName);
            String TableName = form.getTable_name();
            String KeyFiled = form.getKey_field();

            if(!"".equals(form.getPut_data_sql_set_field()) && !"".equals(form.getPut_data_sql_set_value())){
                fields += form.getPut_data_sql_set_field();
                values += form.getPut_data_sql_set_value();
            }

            if(!"".equals(fields) && !"".equals(values)){
                if(fields.endsWith(",")){
                    fields = fields.substring(0,fields.length()-1);
                }
                if(values.endsWith(",")){
                    values = values.substring(0,values.length()-1);
                }

                OpreateSql = "INSERT INTO "+TableName+" ("+fields+") "+"VALUES("+values+");SELECT SCOPE_IDENTITY()";
                OpreateSql = ReplaceSql(req,OpreateSql);
            }

            Object obj = sqlHelper.ExecuteNonQuery(CompanyId,OpreateSql,null);
            if(obj != null){
                result = Integer.parseInt(obj.toString());
            }

            System.out.println("FormName:..."+FormName);
            System.out.println("ShowType:..."+ShowType);
            System.out.println("fields:..."+fields);
            System.out.println("values:..."+values);
            System.out.println("OpreateSql:..."+OpreateSql);
        }else if("ListModifyForm".equals(ShowType.toUpperCase())){

        }else{

        }

        return result != -1 ? true:false;
    }

    public String ReplaceSql(HttpServletRequest req, String Sql){
        if(Sql.indexOf("[userid]") != -1){
            Sql = Sql.replace("[userid]", req.getSession().getAttribute("UserId").toString());
        }
        if(Sql.indexOf("[IP]") != -1){
            Sql = Sql.replace("[IP]", GetIp(req));
        }
        if(Sql.indexOf("[Masterid]") != -1){
            Sql = Sql.replace("[Masterid]", req.getSession().getAttribute("Masterid").toString());
        }
        if(Sql.indexOf("[Area]") != -1){
            Sql = Sql.replace("[Area]", req.getSession().getAttribute("Area").toString());
        }
        if(Sql.indexOf("[DeptId]") != -1){
            Sql = Sql.replace("[DeptId]", req.getSession().getAttribute("DeptId").toString());
        }
        return Sql;
    }

    public String GetIp(HttpServletRequest req){
        String ip = req.getHeader("X-Forwarded-For");
        if(!"".equals(ip) && ip!=null && !"unKnown".equalsIgnoreCase(ip)){
            int index = ip.indexOf(",");
            if(index != -1){
                ip = ip.substring(0,index);
                if("0:0:0:0:0:0:0:1".equals(ip)){
                    ip = "127.0.0.1";
                }
                return ip;
            }else {
                if("0:0:0:0:0:0:0:1".equals(ip)){
                    ip = "127.0.0.1";
                }
                return ip;
            }
        }
        ip = req.getHeader("X-Real-IP");
        if(!"".equals(ip) && ip!=null && !"unKnown".equalsIgnoreCase(ip)){
            if("0:0:0:0:0:0:0:1".equals(ip)){
                ip = "127.0.0.1";
            }
            return ip;
        }
        ip = req.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        return ip;
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
}
