<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=1.0" />
    <title></title>
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


</body>
<script type="text/javascript">
    $(document).ready(function () {
        var view = '<%= request.getAttribute("LoginView")%>';
        $("body").html(view);
        var height = $(window).height();
        $("form").height(height);
        $("form").css("margin","0");
        $(".username").prop("placeholder","请输入手机号");
        $(".password").prop("placeholder","请输入密码");

        $(window).resize(function(){
            $("body").height(height);
            $(".resize").height(height);
        })
    })

    function tar(str){
        if(str != null && "" != str){
            location.href="<%= request.getContextPath()%>/Target?target="+str;
        }
    }
</script>
</html>
