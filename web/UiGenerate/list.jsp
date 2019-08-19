<%--
  Created by IntelliJ IDEA.
  User: hy
  Date: 2017/6/18
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" ></script>
    <title>红森林企业云平台</title>
</head>
<body>

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
    });

</script>
</html>
