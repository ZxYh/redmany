<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=1.0" />
    <title>飓星俱乐部</title>
    <!--[if !IE]> -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.3.min.js"></script>
    <!-- <![endif]-->

    <!--[if IE]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-1.10.2.min.js"></script>
    <![endif]-->

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/font_rem.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/attribute.css">

</head>
<body>
    <form action="/Table?method=OpenTables" method="post">
        <div style="position: absolute;z-index:-9999;background-image: url('/images/loginWap/loginBackground.png');background-size: 100% 100%;background-repeat: no-repeat;height: 100%;width: 100%;"></div>
        <div style="padding-top: 65%;"></div>
        <div style="width: 80%;margin-left: 10%;border: 2px solid rgba(255,255,255,0.8);border-radius: 10px;">
            <span style="display: block;height: 0.8rem;line-height:0.8rem;text-align: center;color: rgba(255,255,255,0.8);font-size: 16px;">当前桌台</span>
            <input type="hidden" name="TableId" id="tablesId">
            <div style="margin-left: 10%;height: 0.8rem;font-size: 16px;color: rgba(255,255,255,0.8);">
                <label>桌台类型：</label>
                <input type="text" id="tablesType" value="" name="TableType" readonly="true" style="background: transparent;border: none;height: 0.8rem;width: 40%;font-size: 16px;color: rgba(255,255,255,0.8);outline: none;">
            </div>
            <div style="margin-left: 10%;height: 0.8rem;font-size: 16px;color: rgba(255,255,255,0.8);">
                <label>桌台编号：</label>
                <input type="text" id="tablesNum" value="" name="TableNum" readonly="true" style="background: transparent;border: none;height: 0.8rem;width: 40%;font-size: 16px;color: rgba(255,255,255,0.8);outline: none;">
            </div>
            <div style="margin-left: 10%;height: 0.8rem;font-size: 16px;color: rgba(255,255,255,0.8);">
                <label>桌台收费：</label>
                <input type="text" id="tablesPrice" value="" name="TablePrice" readonly="true" style="background: transparent;border: none;height: 0.8rem;width: 40%;font-size: 16px;color: rgba(255,255,255,0.8);outline: none;">
            </div>
            <div style="width: 80%;margin: 0.1rem 10%;height: 50px;font-size: 16px;color: rgba(255,255,255,0.8);">
                <input type="submit" value="立即开台" style="width:100%;height: 40px;background:transparent;font-size: 16px;color: rgba(255,255,255,0.8);">
            </div>
        </div>
        <%--<div>
            <span>当前桌台</span>
            <input type="hidden" name="TableId" id="tablesId">
            <div>
                <label>桌台类型:</label>
                <input type="text" id="tablesType" value="" name="TableType" readonly="true">
            </div>
            <div>
                <label>桌台编号:</label>
                <input type="text" id="tablesNum" value="" name="TableNum" readonly="true">
            </div>
            <div>
                <label>桌台收费:</label>
                <input type="text" id="tablesPrice" name="TablePrice" readonly="true">
            </div>
        </div>
        <input type="submit"  value="我要开台">--%>
    </form>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        var tablesId = '<%=request.getAttribute("tablesId")%>';
        var tablesNum = '<%=request.getAttribute("tablesNum")%>';
        var tablesType = '<%=request.getAttribute("tablesType")%>';
        if(tablesType == 0){
            tablesType = "飞镖机";
        } else if(tablesType == 1){
            tablesType = "台球";
        }
        var tablesPrice = '<%=request.getAttribute("tablesPrice")%>';
        $("#tablesId").val(tablesId);
        $("#tablesNum").val(tablesNum);
        $("#tablesType").val(tablesType);
        $("#tablesPrice").val(tablesPrice);
    })
</script>
</html>
