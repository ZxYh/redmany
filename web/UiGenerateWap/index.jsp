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

    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-datetimepicker.min.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" ></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-datetimepicker.min.css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/attribute.css">
    <style>
        .datetimepicker{top: 50% !important;left: 50% !important;}
    </style>
</head>
<body>

</body>
<script type="text/javascript">
    $(document).ready(function () {
        var view = '<%= request.getAttribute("View")%>';
        $("body").html(view);
    })

    function tar(str){
        location.href="<%= request.getContextPath()%>/Target?target="+str;
    }

</script>

<script type="text/javascript">
    $(document).ready(function(){
        var height = $("body").height();
        var naviHeight = $(".navigation").height();

        $(".devResize").height(height - naviHeight);


        $("#tabHeader li").eq(0).css({"border-bottom":"2px solid #ffcc00","height":"43px"});

        $("#tabHeader li").click(function(){
            $(this).css({
                borderBottom: "2px solid #ffcc00",
                height:"43px"
            }).siblings().css({
                borderBottom: "none",
                height:"45px"
            });
        });

        $("#tabHeader li").click(function(){
            $(this).addClass("action").siblings().removeClass("action");
            var index = $(this).index();
            $("#tabContent li").eq(index).css("display","block").siblings().css("display","none");
        });

        var end = new Date();
        end.setHours(end.getHours()+1);
        $(".Wdate").prop("readonly",true);
        $("#ScheduledTime2765").datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true,
            todayBtn: false,
            todayHighlight: true,
            keyboardNavigation: false,
            minuteStep:5,
            startView: "day",
            startDate: new Date(Date.now()),
            endDate: new Date(end),
            language: "zh-CN"
        });

        var w = $(".datetimepicker").width() / 2 + 20;
        var ww = "margin-left:-"+w+"px !important;";
        var h = $(".datetimepicker").height() / 2;
        var hh = "margin-top:-"+h+"px !important;";

        var newStyle = ww+""+hh+"";
        $(".datetimepicker").prop("style",newStyle);
    })
</script>

</html>
