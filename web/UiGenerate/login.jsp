<%--
  Created by IntelliJ IDEA.
  User:
  Date: 2017/6/6
  Time: 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit" />
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <link rel="icon" href="../favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="../favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/font_rem.js"></script>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/html5shiv.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/respond.min.js"></script>
    <![endif]-->
    <title>红森林企业云平台</title>
    <script type="text/javascript">
        function loadCSS() {
            if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|wOSBrowser|BrowserNG|WebOS)/i))) {
                document.write('<link href="${pageContext.request.contextPath}/css/login3wap.css" rel="stylesheet" type="text/css" media="screen" />');
            }
            else {
                document.write('<link href="${pageContext.request.contextPath}/css/login3.css" rel="stylesheet" type="text/css" media="screen" />');
            }
        }
        loadCSS();

    </script>
</head>
<body>
<form id="loginForm" action="/Login" method="post" style="width: 100%;height: 100%">
    <div id="loginbg">
        <div class="logo">
            <img alt="..." src="${pageContext.request.contextPath}/images/login/logo2.png" />
        </div>
        <div class="formbg"></div>
        <div class="login">
            <div class="form-group">
                <span>企业ID：</span>
                <input name="txtCompanyId" id="txtCompanyId" type="text" placeholder="请输入企业ID" />
            </div>
            <div class="form-group">
                <span>用户名：</span>
                <input name="txtLoginName" id="txtLoginName" type="text" placeholder="请输入帐号" />
            </div>
            <div class="form-group">
                <span>密码：</span>
                <input name="txtPassword" id="txtPassword" type="password" placeholder="请输入密码" />
            </div>
            <div class="form-validata">
                <span>验证码：</span>
                <input name="txtValidateCode" class="validata"  id="txtValidateCode" type="text" placeholder="请输入验证码" autocomplete="off" />
                <img id="imgCode" alt="验证码" src="" onclick="changeCode()" />
            </div>
            <div class="form-submit">
                <input type="submit" value="" />
            </div>
        </div>
        <div class="footer">
            版权所有： 广州红森林信息科技有限公司
        </div>
    </div>
</form>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        changeCode();

        var height = $(document).height();
        $(window).resize(function () {
            if ($(document).height() < height) {
                $(".footer").css("display", "none");
            } else {
                $(".footer").css("display", "block");
            }
        });
    });

    function getTimestamp(){
        var time = new Date();
        return time.getTime();
    }

    function changeCode(){
        $("#imgCode").attr("src","<%= request.getContextPath()%>/Code?t="+getTimestamp());
    }
</script>
</html>
