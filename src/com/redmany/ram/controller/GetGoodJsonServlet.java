package com.redmany.ram.controller;

import com.redmany.ram.dao.TableDao;
import com.redmany.ram.model.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/7/24.
 */
@WebServlet("/GetFood")
public class GetGoodJsonServlet extends HttpServlet{

    private TableDao tableDao = new TableDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("GetFood".equals(method)){
            GetFood(req, resp);
        } else if("confirmOrder".equals(method)){
            confirmOrder(req, resp);
        }
    }

    public void GetFood(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        String result = null;

        String CompanyId = Variable.getCompanyId();
        String goodTypeSql = "select * from GoodType_b where state=0";
        List<Map<String,Object>> goodTypeData = tableDao.getTableData(req,CompanyId,goodTypeSql);
        String goodSql = "select * from Good_b where state=0 ORDER BY GoodType";
        List<Map<String,Object>> goodData = tableDao.getTableData(req,CompanyId,goodSql);

        //[{"type":"小吃","kind":[{"id":"1","image":"a.png","name":"花生","price":"￥8"},{"id":"2","image":"a.png","name":"上校鸡块","price":"￥15"},{"id":"3","image":"a.png","name":"无骨鸡柳","price":"￥15"},{"id":"4","image":"a.png","name":"薯片","price":"￥10"},{"id":"5","image":"a.png","name":"大鸡排","price":"￥15"},{"id":"6","image":"a.png","name":"骨肉相连","price":"￥10"},{"id":"7","image":"a.png","name":"辣条","price":"￥5"},{"id":"8","image":"a.png","name":"鸡肉卷","price":"￥12"},{"id":"10","image":"a.png","name":"汉堡包","price":"￥15"},{"id":"11","image":"a.png","name":"豆腐干","price":"￥8"}]},{"type":"酒水","kind":[{"id":"12","image":"a.png","name":"百威","price":"￥10"},{"id":"13","image":"a.png","name":"雪花","price":"￥6"},{"id":"14","image":"a.png","name":"哈啤","price":"￥8"},{"id":"15","image":"a.png","name":"青岛","price":"￥6"},{"id":"16","image":"a.png","name":"蓝带","price":"￥15"},{"id":"17","image":"a.png","name":"惠泉","price":"￥6"},{"id":"18","image":"a.png","name":"黑啤","price":"￥25"}]},{"type":"饮料","kind":[{"id":"19","image":"a.png","name":"雪碧","price":"￥5"},{"id":"20","image":"a.png","name":"百事可乐","price":"￥5"},{"id":"21","image":"a.png","name":"可口可乐","price":"￥5"},{"id":"22","image":"a.png","name":"尖叫","price":"￥12"},{"id":"23","image":"a.png","name":"七喜","price":"￥5"},{"id":"24","image":"a.png","name":"王老吉","price":"￥8"},{"id":"25","image":"a.png","name":"红牛","price":"￥12"},{"id":"26","image":"a.png","name":"猛虎","price":"￥10"},{"id":"27","image":"a.png","name":"健力宝","price":"￥8"}]}]
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Map<String,Object> goodTypeMap : goodTypeData) {
            String typeId = goodTypeMap.get("Id").toString();
            String typeName = goodTypeMap.get("GoodTypeName").toString();
            sb.append("{");
            sb.append("\"type\":\""+typeName+"\",");
            sb.append("\"kind\":[");
            for(Map<String,Object> goodMap : goodData){
                String goodTypeId = goodMap.get("GoodType").toString();
                if(goodTypeId.equals(typeId)){
                    String goodId = goodMap.get("Id").toString();
                    String goodImage = null;
                    try{
                        goodImage = Variable.getImageHttp() + goodMap.get("GoodImage").toString();
                    }catch (Exception e){
                        goodImage = null;
                    }
                    String goodName = goodMap.get("GoodName").toString();
                    String goodPrice = goodMap.get("GoodPrice").toString();
                    sb.append("{");
                    sb.append("\"id\":\""+goodId+"\",");
                    sb.append("\"image\":\""+goodImage+"\",");
                    sb.append("\"name\":\""+goodName+"\",");
                    sb.append("\"price\":\"￥"+goodPrice+"\"");
                    sb.append("},");
                }
            }
            if(sb.toString().endsWith(",")){
                sb.deleteCharAt(sb.length()-1);
            }
            sb.append("]},");
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("]");

        result = sb.toString();

        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    public void confirmOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        String data = req.getParameter("DataJson");
        data = new String(data.getBytes("ISO-8859-1"),"UTF-8");
        req.setAttribute("DataJson",data);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/UiGenerateWap/confirmOrder.jsp").forward(req,resp);
    }
}
