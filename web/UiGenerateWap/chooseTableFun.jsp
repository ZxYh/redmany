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
<div>
    <div style="position: absolute;z-index:-9999;background-image: url('/images/loginWap/loginBackground.png');background-size: 100% 100%;background-repeat: no-repeat;height: 100%;width: 100%;"></div>
    <div style="padding-top: 65%;"></div>

    <div style="width: 80%;margin-left: 10%;border: 2px solid rgba(255,255,255,0.8);border-radius: 10px;">
        <span style="display: block;height: 0.8rem;line-height:0.8rem;text-align: center;color: rgba(255,255,255,0.8);font-size: 16px;">当前桌台:<i class="tableNum"></i></span>

        <div style="width: 80%;margin: 0.1rem 10%;height: 0.8rem;font-size: 16px;color: rgba(255,255,255,0.8);">
            <button id="toBuy" style="width:100%;height: 0.8rem;background:transparent;font-size: 16px;color: rgba(255,255,255,0.8);" onclick="toBuy()">去点购</button>
        </div>
        <div style="width: 80%;margin: 0.1rem 10%;height: 0.8rem;font-size: 16px;color: rgba(255,255,255,0.8);">
            <button id="toPay" style="width:100%;height: 0.8rem;background:transparent;font-size: 16px;color: rgba(255,255,255,0.8);" onclick="toPay()">去结算</button>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">

    $(document).ready(function(){
        var tableNum = "<%=request.getSession().getAttribute("tablesNum")%>";
        $(".tableNum").html(tableNum);
    })

    function toBuy(){
        location.href="<%= request.getContextPath()%>/Target?target=goto:foodOrder,foodOrderForm";
    }
    
    function toPay() {
        var tableNum = $(".tableNum").html();
        location.href="<%= request.getContextPath()%>/Settlement?method=settlement&tableNum="+tableNum;
    }

</script>
</html>
