package com.redmany.ram.controller;

import com.redmany.ram.common.CreateControlsHelper;
import com.redmany.ram.control.ParentForm;
import com.redmany.ram.model.OpType;
import com.redmany.ram.model.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hy on 2017/6/15.
 * 调度中心
 */
@WebServlet("/CommandCenter")
public class CommandCenter extends HttpServlet {

    private CreateControlsHelper createControlsHelper = new CreateControlsHelper();
    private static ParentForm parentForm = new ParentForm();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateForm(req, resp);
    }

    private String CreateForm(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String ShowType = req.getParameter("ShowType");
        System.out.println("CreateFormShowType:..."+ShowType);
        String str = "";

        if(!"".equals(ShowType)){
            switch (ShowType){
                case "MDLISTFORM":
                case "MDLISTMODIFYFORM":
                    break;
                case "MDNEWFORM":
                    break;
                case "LISTFORM":
                case "LISTMODIFYFORM":
                    str = CreateListForm(req);
                    break;
                case "LISTFORMTREE":
                    break;
                case "LISTFORMDEPTETREE":
                    break;
                case "NEWFORM":
                    str = CreateNewForm(req);
                    //System.out.println("str:..."+str);
                    //req.setAttribute("newForm",str);
                    //RequestDispatcher rd = req.getRequestDispatcher("/UiGenerate/insert.jsp");
                    //rd.forward(req,resp);
                    break;
                case "STATELISTFORM":
                    break;
                case "EXPANDABLELISTFORM":
                    break;
                case "COLLECTIONFORM":
                    break;
                case "STATECOLLECTIONFORM":
                    break;
                case "MAPFORM":
                    break;
                case "CALENDARFORM":
                    break;
                case "TIMEAXISFORM":
                    break;
                case "CHARTFORM":
                    break;
                case "COPFORM":
                case "FREEFORM":
                    break;
            }
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(str);
        }
        return str;
    }

    private String CreateNewForm(HttpServletRequest req)throws IOException{
        System.out.println("into CreateNewForm");
        String CompanyId = Variable.getCompanyId();
        System.out.println("CompanyId:..."+CompanyId);
        String FormName = req.getParameter("FormName");
        System.out.println("FormName:..."+FormName);
        String ShowType = req.getParameter("ShowType");
        System.out.println("ShowType:..."+ShowType);
        String divAppend = createControlsHelper.CreateContorlItemsInPage(req,CompanyId,FormName, OpType.Insert,null,null);
        StringBuffer sb = new StringBuffer();
        sb.append("<form action=\"/Submit?FormName="+FormName+"&ShowType="+ShowType+"\" method=\"post\" id=\""+FormName+"_"+ShowType+"\" target=\"contentPage\">");
        sb.append(divAppend);
        sb.append("<input type=\"submit\" value=\"提交\" id=\"btn_dopost\" class=\"btn btn-sm btn-info\">");
        sb.append("<input type=\"button\" value=\"取消\" id=\"bt_cancel\" class=\"btn btn-sm btn-info\" onclick=\"javascript:history.go(-1);\">");
        sb.append("</form>");
        return sb.toString();
    }

    private String CreateListForm(HttpServletRequest req)throws IOException{
        String CompanyId = Variable.getCompanyId();
        System.out.println("CompanyId:..."+CompanyId);
        String FormName = req.getParameter("FormName");
        System.out.println("FormName:..."+FormName);
        String divAppend = createControlsHelper.CreateTableControl(req,CompanyId,FormName);
        StringBuffer sb = new StringBuffer();
        sb.append("<table>");
        sb.append(divAppend);
        sb.append("</table>");
        return sb.toString();
    }
}
