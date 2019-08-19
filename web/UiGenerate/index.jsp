<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/9
  Time: 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>红森林企业云平台</title>
    <meta name="keywords" content="红森林企业云平台,红森林,移动OA,管理系统,企业云平台，云平台" />
    <meta name="description" content="广州红森林信息科技有限公司-移动时代的企业应用专家" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit" />
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <link rel="icon" href="../favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="../favicon.ico" type="image/x-icon" />
    <!-- basic styles -->
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css" />
    <!--[if IE 7]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <!-- page specific plugin styles -->
    <!-- ace styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-ie.min.css" />
    <![endif]-->
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <!--[if !IE]> -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.3.min.js"></script>
    <!-- <![endif]-->

    <!--[if IE]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-1.10.2.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ace-extra.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/assets/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/respond.min.js"></script>
    <![endif]-->

    <script type="text/javascript">
        var panelID = 1;
        var panelIndex = 0;
        var panelName = new Array();
        $(document).ready(function () {
            GetUserinfo();
            GetMenuTreeAjax();
        });

        function GetUserinfo(){
            $.ajax({
                type:"POST",
                dataType:"text",
                url:"<%= request.getContextPath()%>/User",
                data:{method:"GetUserInfo"},
                success:function(result){
                    $("#showuserinfo").html(result);
                }
            })
        }

        function GetMenuTreeAjax() {
            $.ajax({
                type:"POST",
                dataType:"json",
                url:"<%= request.getContextPath()%>/User",
                data:{method:"GetUserPanels"},
                success: function(result){
                    PanelLoad(result);
                }
            })
        }

        function PanelLoad(PanelData) {
            panelIndex = 0;
            if (PanelData == '' || PanelData == 'undefined') return;
            //循环json对象，生成panel
            /*var JsonData = eval('(' + PanelData + ')'); //转换为json对象;
            //获取数据对象
            var data = JsonData["Table"];*/
            $.each(PanelData, function (index, dd) {
                panelID = dd.ID.toString();
                var icon = dd.icon;
                var litag = 'LI_' + panelID;
                panelLi = "<li id='" + litag + "'>";
                panelLi += "<a href='#' class='dropdown-toggle'>";
                panelLi += "<i class='" + icon + "'></i><span class='menu-text'>" + dd.PanelName + "</span>";
                panelLi += "<b class='arrow icon-angle-down'></b>";
                panelLi += "</a>";
                $('#leftmenu').append(panelLi);
                $.ajax({
                    type: "POST",
                    //async: false,
                    url: "<%= request.getContextPath()%>/User",
                    data: {method:"GetSysManageTree",panelId: panelID },
                    datatype: "json",
                    success: function (data) {
                        var panelChild = "";
                        var ultag = 'UL_' + panelID;
                        panelChild += "<ul id='" + ultag + "' class='submenu' >";
                        //alert(data);
                        var JsonTreeData = eval('(' + data + ')');
                        $.each(JsonTreeData, function (i, obj) {
                            var url = obj["url"];
                            var name = obj["name"];
                            if (("children" in obj)) {
                                panelChild += "<li><a href='#' class='dropdown-toggle'><i class='icon-double-angle-right'></i>" + name + "<b class='arrow icon-angle-down'></b></a>";
                                panelChild += "<ul class='submenu'>";
                                $.each(obj.children, function (j, obj2) {
                                    var url2 = obj2["url"];
                                    var name2 = obj2["name"];
                                    panelChild += "<li onclick='endChild()'><a href='" + url2 + "' target='contentPage'><i class='icon-leaf'></i>" + name2 + "</a></li>";
                                });
                                panelChild += "</ul>";
                            } else {
                                panelChild += "<li onclick='endChild()'><a href='" + url + "' target='contentPage'><i class='icon-double-angle-right'></i>" + name + "</a></li>";
                            }

                        });
                        panelChild += "</ul>";
                        $(eval(litag)).append(panelChild);
                        $('#leftmenu').append("</li>");
                    }
                });
            })
        }

        function endChild() {
            $("#menu-toggler").removeClass("display");
            $("#sidebar").removeClass("display");
            $(".tc_big").show();
            $(".tc_img").show();
            var frm = document.getElementById('contentPage');
            $(frm).load(function () {                             //  等iframe加载完毕
                $(".tc_big").hide();
                $(".tc_img").hide();
            });
        }
    </script>
</head>
<body>
<div class="navbar navbar-default" id="navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) { }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <%--<i class="icon-leaf"></i>--%>
                    <i><img src="../images/LOGO2.png" alt="..." style="height: 30px;" /></i>
                    红森林企业云平台
                </small>
            </a>
            <!-- /.brand -->
        </div>

        <!-- /.navbar-header -->

        <div class="navbar-buttons navbar-header pull-right collapse navbar-collapse" role="navigation">

            <ul class="nav ace-nav">

                <li class="transparent">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="../assets/avatars/user2.jpg" alt="Jason's Photo" /><%--assets/avatars/user2.jpg--%>
                        <span class="user-info">
									<small>Welcome,</small>
                                    <%--<span>
                                        <c:out value="${user.RealName}"></c:out>
                                    </span>--%>
                                    <span id="showuserinfo"></span>
								</span>

                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                        <li>
                            <a href="#">
                                <i class="icon-user"></i> 个人资料
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <i class="icon-dashboard"></i> 皮肤
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <i class="icon-cog"></i> 设置
                            </a>
                        </li>

                        <li class="divider"></li>

                        <li>
                            <a href="login.jsp">
                                <i class="icon-off"></i> 注销
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- /.ace-nav -->
        </div>

        <div class="navbar-buttons navbar-header pull-right collapse navbar-collapse">
            <ul id="shortcut" class="nav ace-nav" runat="server" role="navigation">

            </ul>
        </div>
        <!-- /.navbar-header -->
    </div>
    <!-- /.container -->
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) { }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'fixed')
                } catch (e) { }
            </script>
            <!-- #sidebar-shortcuts -->

            <ul class="nav nav-list" id="leftmenu" fit="true">
                <li>
                    <a href="index.jsp">
                        <i class="icon-home"></i>
                        <span class="menu-text"> 首页 </span>
                    </a>
                </li>
            </ul>
            <!-- /.nav-list -->

            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
            </div>

            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'collapsed')
                } catch (e) { }
            </script>
        </div>

        <div class="main-content">
            <div id="iframediv">
                <iframe id="contentPage" name="contentPage" src="" width="100%" height="100%"
                        frameborder="0" marginheight="0" marginwidth="0" scrolling="auto"></iframe>
            </div>
        </div>
        <!-- 遮罩层 -->
        <div class="tc_big"></div>
        <div class="tc_img">
            <img alt="wait" src="../images/logo4.gif">
            <span>数据加载中</span>
        </div>

        <!-- /.main-content -->

    </div>
    <!-- /.main-container-inner -->

</div>
<!-- /.main-container -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>

<!--[if lte IE 8]>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/excanvas.min.js"></script>
<![endif]-->

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>

<!-- ace scripts -->

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>

<script type="text/javascript">
    jQuery(document).ready(function ($) {

        change();
        $(window).resize(function () {
            change();
        })

        function change() {
            var winHig = $(window).height();
            var navbar = $("#navbar").height();
            $("#leftmenu").height(winHig - navbar);
            $("#leftmenu").slimscroll({
                height: winHig - navbar - 40,
                size: '5px',
                color: '#cfcfcf'
            });
            $("#iframediv").height(winHig - navbar);
        }

    });
</script>

</body>
</html>
