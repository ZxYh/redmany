<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/16
  Time: 9:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>红森林企业云平台</title>
    <!-- ace styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/alertBox.js"></script>
    <!--[if !IE]> -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.3.min.js"></script>
    <!-- <![endif]-->

    <!--[if IE]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-1.10.2.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-datetimepicker.min.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" ></script>

    <script type="text/javascript">

        $(document).ready(function () {
            windowResize();
            $(window).resize(function () {
                windowResize();
            });
        });

        function getWindowHeight() {
            return $(window).height();
        }
        function getWindowWidth() {
            return $(window).width();
        }
        function windowResize() {
            var width = getWindowWidth();
            var height = getWindowHeight();
            //$('body').width(width);
            $('body').height(height);
        }
    </script>
</head>
<body>
    <%-- 顶部导航条 --%>
    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) { }
        </script>

        <ul class="breadcrumb">
            <%--<li>
                <i class="icon-home home-icon"></i>
                <a href="javascript:void(0)">首页</a>
            </li>
            <li>
                <a href="javascript:void(0)">打卡登记管理</a>
            </li>
            <li class="active">上下班登记</li>--%>
        </ul>
        <!-- .breadcrumb -->
    </div>

</body>
<script type="text/javascript">
    $(document).ready(function(){
        var FormName = '<%=request.getParameter("FormName")%>';
        console.log("FormName:..."+FormName);
        var ShowType = '<%=request.getParameter("ShowType")%>';
        console.log("ShowType:..."+ShowType);
        var NeedState = '<%=request.getParameter("NeedState")%>';
        console.log("NeedState:..."+NeedState);

        $.ajax({
            type:"POST",
            dataType:"text",
            url:"<%= request.getContextPath()%>/CommandCenter",
            data:{FormName:FormName,ShowType:ShowType,NeedState:NeedState},
            success:function(result){
                $("body").append(result);
            }
        })

        $(document).ready(function () {
            $(".Wdate").each(function () {
                var id = $(this).attr("id");
                $(eval(id)).datetimepicker({
                    format: 'yyyy-mm-dd hh:ii',
                    autoclose: true,
                    todayBtn: "linked",
                    todayHighlight: true,
                    keyboardNavigation: true,
                    language: "zh-CN"
                });
            })
        });
    });

</script>
</html>
