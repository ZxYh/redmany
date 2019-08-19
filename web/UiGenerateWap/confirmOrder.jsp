<%@ page import="javax.xml.crypto.Data" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String DataJson = new String(request.getParameter("DataJson").getBytes("ISO-8859-1"),"UTF-8");
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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/attribute.css ">
</head>
<body>
<div id="header" style="position: fixed;height: 0.8rem;width:100%;background-color: #333333;top:0;">
    <img src="http://oa.redmany.com:50002/document/to_left.png" style="display: block;width: 0.48rem;height:0.48rem;margin: 0.16rem;float:left" onclick="history.go(-1)">
    <span style="display: block;height: 100%;width: 100%;text-align: center;line-height: 0.8rem;font-size: 14px;color: #ffcc00;margin-left: -24px;">提交订单</span>
</div>
<div id="center" style="padding-top: 0.8rem;margin-bottom: 0.8rem;">
    <div id="confirmTable" style="height: 0.6rem;border-bottom: 1px solid #dcdcdc;">
        <span style="display: block;float: left;width: auto;height: 100%;line-height: 0.6rem;padding-left: 0.2rem;">当前桌号：</span>
        <span id="TableNum" style="display: block;width: auto;height: 100%;line-height: 0.6rem;;"></span>
    </div>
    <div id="confirmItem">

    </div>
    </div>
    <div id="footer" style="height: 0.8rem;width:100%;z-index: 9999;position: fixed;bottom: 0;">
        <div style="float: left;width: 80%;height: 100%;background-color: #333333;">
            <p style="display: block;margin: 0;height: 100%;line-height: 0.8rem;color: white;font-size: 14px;width: 30%;text-align: right;float: left;">合计：￥</p>
            <p id='subtotal' style='display: block;margin: 0;height: 100%;line-height: 0.8rem;color: #ffcc00;font-size: 14px;'>0</p>
        </div>
        <div style="height: 100%;width: 20%;float:right;text-align: center;line-height: 0.8rem;font-size: 14px;background-color: #ffcc00;color:#333333;" onclick="saveOrder()">
            提交订单
        </div>
    </div>
</body>

<script type="text/javascript">

    var bodyHeight = $(window).height();
    var header = $("#header").height();
    var footer = $("#footer").height();
    var h = bodyHeight - header - footer;

    function height(){
        $("#center").height(h);
    }

    $(document).resize(function(){
        height();
    })

    $(document).ready(function () {
        var DataJson = <%=DataJson%>;
        height();
        getInfo(DataJson);
        //subtotal();
    })

    function getInfo(json){
        console.log(json);
        var view = "";
            var tableNum = json.TableNum,
                tableItem = json.TableItem,
                subNum = json.SubNum,
                subTotal = json.SubTotal;
            $("#TableNum").html(tableNum);
            $.each(tableItem,function(i,kind){
                var id = "kind"+kind.id,
                    image = kind.image,
                    name = kind.name,
                    price = kind.price,
                    num = kind.num;
                view += "<div id='"+id+"' style='height: 1.6rem;border-bottom: 1px solid #dcdcdc;'>";
                    view += "<div style='height: 100%;width: 30%;float: left;'>";
                    view += "<img src='"+image+"' style='height: 1.2rem;width: 1.2rem;margin: 0.2rem 0.2rem;' />";
                    view += "</div>";
                    view += "<div style='height: 100%;width:70%;float: left;'>";
                    view += "<span class='name' style='display:block;height: 0.6rem;width:80%;line-height: 0.6rem;margin-top: 0.2rem;font-size: 14px;float:left;'>"+name+"</span>";
                    view += "<span class='price' style='display:block;height: 0.6rem;width:20%;line-height: 0.6rem;margin-top: 0.2rem;font-size: 14px;color: red;float:right;'>"+price+"</span>";
                    view += "<span class='num' style='display:block;height: 0.6rem;width:100%;line-height: 0.6rem;margin-bottom: 0.2rem;font-size: 14px;color: gray;'>x"+num+"</span>";
                    view += "</div>";
                view += "</div>";
            })
            $("#confirmItem").html(view);
            $("#subtotal").html(subTotal);
    }

    /*function subtotal(){
        var _parent = $("#confirmItem").children();
        var subtotal = 0;
        $.each(_parent, function(index) {
            var _price = $(this).find(".price").html();
            var price = _price.substr(1,_price.length);
            price = parseFloat(price);
            var _num = $(this).find(".num").html();
            var num = _num.substr(1,_num.length);
            num = parseInt(num);
            var _subtotal = num * price.toFixed(2);
            subtotal += _subtotal;
        });
        var total = subtotal;
        $("#subtotal").html(total);
    }*/

    function saveOrder() {
        var DataJson = '<%=DataJson%>';
        var body = $(document.body),
            form = $("<form method='post'></form>"),
            input;
        form.attr("action","${pageContext.request.contextPath}/Order?method=saveOrder");
        input = $("<input type='hidden'>");
        input.attr({"name":"DataJson"});
        input.val(DataJson);
        form.append(input);

        form.appendTo(document.body);
        console.log(form);
        form.submit();
        document.body.removeChild(form[0]);
    }

</script>
</html>
