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
<form action="/User?method=Register" method="post" onsubmit="return checkPhone()">
    <div class="resize" style="position: absolute;z-index: -9999;background-image: url('/images/loginWap/loginBackground.png');background-size: 100% 100%;background-repeat: no-repeat;height: 100%;width:100%;"></div>
    <div style="padding-top: 65%;"></div>
    <div style="background-image: url('/images/loginWap/username.png');background-repeat: no-repeat;background-size: 100% 100%;width: 80%;height: 0.8rem;margin-left: 10%;">
        <input type="text" class="username" name="UserName" maxlength="11" style="height: 0.8rem;width: 80%;margin-left: 20%;background: transparent;border: none;outline: none;color: white;">
    </div>
    <div style="background-image: url('/images/loginWap/password.png');background-repeat: no-repeat;background-size: 100% 100%;width: 80%;height: 0.8rem;margin-left: 10%;margin-top: 0.2rem;">
        <input type="password" class="password" name="password" style="height: 0.8rem;width: 80%;margin-left: 20%;background: transparent;border: none;outline: none;color: white;">
    </div>

    <input type="hidden" name="RoleId" value="117">
    <input type="hidden" name="DeptId" value="78">

    <div style="width: 80%;height: 0.8rem;margin-left: 10%;margin-top: 0.2rem;">
        <input type="submit" value="" style="height:100%;width:100%;border: none;background-image: url('/images/loginWap/submit.png');background-repeat: no-repeat;background-size: 100% 100%;border-radius: 5px;"/>
    </div>
</form>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $(".username").prop("placeholder","请输入手机号");
        $(".password").prop("placeholder","请输入密码");

        var height = $(window).height();
        $(window).resize(function(){
            $("body").height(height);
            $(".resize").height(height);
        })
    })

    function checkPhone() {
        var phone = $(".username").val();
        var pwd = $(".password").val();
        if(/^1(3|4|5|7|8)\d{9}$/.test(phone)) {
            if(pwd != null && pwd != ""){
                return true;
            } else {
                alert("请输入密码");
                return false;
            }
        } else {
            alert("请输入正确的手机号");
            return false;
        }
    }

</script>
</html>
