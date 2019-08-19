<%@ page import="java.util.logging.Logger" %>
<%@ page import="com.redmany.ram.controller.Log4jServlet" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    String appId = request.getParameter("appid");
    String timeStamp = request.getParameter("timeStamp");
    String nonceStr = request.getParameter("nonceStr");
    String packageValue = request.getParameter("package");
    String paySign = request.getParameter("sign");
%>
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
        <span style="display: block;height: 0.8rem;line-height:0.8rem;text-align: center;color: rgba(255,255,255,0.8);font-size: 16px;">请选择支付方式</span>

        <div style="width: 80%;margin: 0.1rem 10%;height: 0.8rem;font-size: 16px;color: rgba(255,255,255,0.8);">
            <button id="WeChatPay" style="width:100%;height: 0.8rem;background:transparent;font-size: 16px;color: rgba(255,255,255,0.8);" onclick="WeChatPay()">微信支付</button>
        </div>
        <%--<div style="width: 80%;margin: 0.1rem 10%;height: 0.8rem;font-size: 16px;color: rgba(255,255,255,0.8);">
            <button id="BalancePay" style="width:100%;height: 0.8rem;background:transparent;font-size: 16px;color: rgba(255,255,255,0.8);" onclick="BalancePay()">余额支付</button>
        </div>--%>
    </div>
</div>

</body>
<script type="text/javascript">

    $(document).ready(function(){
        WeChatPay()
    })

    function onBridgeReady(){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId" : "<%=appId%>",     //公众号名称，由商户传入
                "timeStamp": "<%=timeStamp%>",         //时间戳，自1970年以来的秒数
                "nonceStr" : "<%=nonceStr%>", //随机串
                "package" : "<%=packageValue%>",
                "signType" : "MD5",         //微信签名方式:
                "paySign" : "<%=paySign%>"    //微信签名
        },function(res){
            var p = "<%=packageValue%>";
            console.log(p);
            var a = JSON.stringify(res);
            console.log(a);
            WeixinJSBridge.log(res.err_msg);
            if(res.err_msg == "get_brand_wcpay_request:ok"){
                //alert("支付成功!");
                location.href="<%= request.getContextPath()%>/Target?target=goto:homePageIndex,copForm";
            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                alert("订单已取消!");
                location.href="<%= request.getContextPath()%>/Order?method=OrderCancel&orderNum="+orderNum;
            }else{
                alert("支付失败,请于5分钟后重试!");
                location.href="<%= request.getContextPath()%>/Order?method=OrderFail&orderNum="+orderNum;
            }
        })
    }

    function WeChatPay(){
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }
    }
    
    //function BalancePay() {
        //var orderNum = '<%=request.getParameter("orderNum")%>';
        //var money = '<%=request.getParameter("money")%>';
        //location.href="<%= request.getContextPath()%>/Order?method=OrderBalancePay&orderNum="+orderNum+"&money="+money;
    //}

</script>
</html>
