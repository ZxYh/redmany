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
    <style type="text/css">
        span {display: inline}
        tr {text-align: center;line-height: 0.4rem;font-size: 12px;}
    </style>
</head>
<body>
    <div id="header" style="height: 0.8rem;background-color: #2b2b2b;position: fixed;top: 0;width: 100%;">
        <span style="display: block;color: white;line-height: 0.8rem;text-align: center;font-size: 14px;">结算</span>
    </div>
    <div id="center" style="padding: 0.8rem 0;margin-bottom: 1.2rem;">
        <ul style="padding: 0;margin:0;font-size: 14px;">
            <span style="display: block;padding-left: 0.4rem;height: 0.6rem;line-height: 0.6rem;background-color: #f0f0f0;">桌台结算明细</span>
            <li style="padding-left: 0.4rem;line-height: 0.6rem;display: none">开台号：<span id="foundingId"></span></li>
            <li style="padding-left: 0.4rem;line-height: 0.6rem;display: none">桌台编号：<span id="tableId"></span></li>
            <li style="padding-left: 0.4rem;line-height: 0.6rem;">桌号：<span id="tableNum"></span></li>
            <li style="padding-left: 0.4rem;line-height: 0.6rem;">总计：￥<span id="finalTotal"></span></li>
            <li style="padding-left: 0.4rem;line-height: 0.6rem;">开台时间：<span id="foundingTime"></span></li>
            <li style="padding-left: 0.4rem;line-height: 0.6rem;">结算时间：<span id="endingTime"></span></li>
            <li style="padding-left: 0.4rem;line-height: 0.6rem;">开台单价：<span id="tablePrice"></span></li>
        </ul>
        <div style="padding: 0;font-size: 14px;margin-top: 0.2rem;">
            <span id="needPay" style="display: block;padding-left: 0.4rem;height: 0.6rem;line-height: 0.6rem;background-color: #f0f0f0;">待支付订单项<p style="float: right;margin: 0 0.4rem;font-size: 12px;color: #9b9b9b;">点击查看详情 ></p></span>
            <table id="needPayTable" style="width: 100%;display: none;">
                <tr>
                    <th>商品名称</th>
                    <th>商品单价</th>
                    <th>商品数量</th>
                </tr>
            </table>
        </div>
        <div style="padding: 0;font-size: 14px;margin-top: 0.2rem;">
            <span id="payment" style="display: block;padding-left: 0.4rem;height: 0.6rem;line-height: 0.6rem;background-color: #f0f0f0;">已支付订单项<p style="float: right;margin: 0 0.4rem;font-size: 12px;color: #9b9b9b;">点击查看详情 ></p></span>
            <table id="paymentTable" style="width: 100%;display: none;">
                <tr>
                    <th>商品名称</th>
                    <th>商品单价</th>
                    <th>商品数量</th>
                </tr>
            </table>
        </div>
    </div>
    <div id="footer" style="display:block;position:fixed;width:100%;z-index:3;bottom:0px;color:white;line-height:0.8rem;font-size:14px;border-top:1px solid #e2e2e2;background-color: #2b2b2b;">
        <div style="float:left;margin: 0 0.4rem;">
            <span>应支付：￥<span id="finalPayTotal"></span></span>元
        </div>
        <div style="float:right;">
            <a id="toPay" style="display:block;text-align:center;line-height:0.8rem;background-color:#FFcc00;padding:0 15px;color:#2b2b2b;font-weight:bold;">去支付</a>
        </div>
    </div>

    <div class="tc_big"></div>
    <div id="choosePayment" style="display: none;position: absolute;width: 60%;height: 2.6rem;top: 50%;left: 50%;margin-left:-30%;margin-top: -1.3rem;border: 1px solid #9f9f9f;border-radius:10px;background-color: white;z-index: 9999;">
        <span style="display:block;line-height: 0.6rem;text-align: center;background-color: #2b2b2b;border-top-left-radius: 8px;border-top-right-radius: 8px;color: white;">请选择支付方式</span>
        <div style="height: 2rem;">
            <!--<button>微信支付</button>-->
            <button id="balancePay" style="display:block;height: 0.6rem;margin: 0.7rem auto;width: 60%;">余额支付</button>
        </div>
    </div>
</body>

<script type="text/javascript">

    $(function(){
        getInfo();
    })

    function getInfo(){
        var foundingId = '<%=request.getAttribute("foundingId")%>';
        var tableId = '<%=request.getAttribute("tableId")%>';
        var tableNum = '<%=request.getAttribute("tableNum")%>';
        var finalTotal = '<%=request.getAttribute("finalTotal")%>';
        var foundingTime = '<%=request.getAttribute("foundingTime")%>';
        var endingTime = '<%=request.getAttribute("endingTime")%>';
        var tablePrice = '<%=request.getAttribute("tablePrice")%>';
        var finalPayTotal = '<%=request.getAttribute("finalPayTotal")%>';

        $("#foundingId").html(foundingId);
        $("#tableId").html(tableId);
        $("#tableNum").html(tableNum);
        $("#finalTotal").html(finalTotal);
        $("#foundingTime").html(foundingTime);
        $("#endingTime").html(endingTime);
        $("#tablePrice").html(tablePrice);
        $("#finalPayTotal").html(finalPayTotal);
    }

    var needPayFlag = "false";
    $("#needPay").click(function(){
        var foundingId = $("#foundingId").html();
        if(needPayFlag == "false") {
            $(this).find("p").hide();
            $.ajax({
                type: "POST",
                dataType: "text",
                url: "<%= request.getContextPath()%>/Settlement",
                data: {method: "GetOrderDetail", FoundingId: foundingId, state: 0},
                success: function (result) {
                    needPayFlag = "true";
                    $("#needPayTable").append(result).show(600);
                }
            })
        }
    })

    var paymentFlag = "false";
    $("#payment").click(function(){
        var foundingId = $("#foundingId").html();
        if(paymentFlag = "false"){
            $(this).find("p").hide();
            $.ajax({
                type:"POST",
                dataType:"text",
                url:"<%= request.getContextPath()%>/Settlement",
                data:{method:"GetOrderDetail",FoundingId:foundingId,state:1},
                success: function(result){
                    paymentFlag = "true";
                    $("#paymentTable").append(result).show(600);
                }
            })
        }
    })

    $("#toPay").click(function(){
        $(".tc_big").show();
        $("#choosePayment").show(600);
    })

    $("#balancePay").click(function(){
        if(confirm("确定支付?")){
            var foundingId = $("#foundingId").html();
            var tableId = $("#tableId").html();
            var finalTotal = $("#finalTotal").html();
            var endingTime = $("#endingTime").html();
            var finalPayTotal = $("#finalPayTotal").html();

            var body = $(document.body),
                form = $("<form method='post'></form>"),
                input1,input2,input3,input4,input5;
            form.attr("action","${pageContext.request.contextPath}/Settlement?method=BalancePay");
            input1 = $("<input type='hidden'>");
            input1.attr({"name":"FoundingId"});
            input1.val(foundingId);
            form.append(input1);
            input2 = $("<input type='hidden'>");
            input2.attr({"name":"EndTime"});
            input2.val(endingTime);
            form.append(input2);
            input3 = $("<input type='hidden'>");
            input3.attr({"name":"Receivable"});
            input3.val(finalTotal);
            form.append(input3);
            input4 = $("<input type='hidden'>");
            input4.attr({"name":"pay"});
            input4.val(finalPayTotal);
            form.append(input4);
            input5 = $("<input type='hidden'>");
            input5.attr({"name":"TableId"});
            input5.val(tableId);
            form.append(input5);

            form.appendTo(document.body);
            console.log(form);
            form.submit();
            document.body.removeChild(form[0]);
        }
    })
</script>
</html>
