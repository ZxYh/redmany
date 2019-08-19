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

    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<body>

    <button id="scan" style="display: none">扫一扫</button>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        var url=location.href.split('#')[0];
        //url = encodeURIComponent(url);
        console.log(url);
        var timestamp = "";
        var nonceStr = "";
        var signature = "";

        $.ajax({
            type:"POST",
            dataType:"json",
            url:"<%=request.getContextPath()%>/Wechat",
            data:{method:"sign",url:url},
            success: function(result){
                console.log(result);
                timestamp = result.timestamp;
                nonceStr = result.nonceStr;
                signature = result.signature;
                console.log(timestamp);
                console.log(nonceStr);
                console.log(signature);

                wx.config({
                    debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId : 'wx8973076fb8dee5f4', // 必填，公众号的唯一标识
                    timestamp : timestamp, // 必填，生成签名的时间戳
                    nonceStr : nonceStr, // 必填，生成签名的随机串
                    signature : signature,// 必填，签名
                    jsApiList : ['scanQRCode']
                    // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });

                //$("#scan").click();
            }
        })
    })

    wx.ready(function(){
        scanQRCode()
    })

    function scanQRCode(){
        console.log("into scan")
        wx.scanQRCode({
            // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
            needResult : 0,
            desc : 'scanQRCode desc',
            success : function(res) {
                var result = res.resultStr;
            }
        });
    }

    wx.error(function(res){
        alert("出错了：" + res.errMsg);
    })
</script>
</html>
