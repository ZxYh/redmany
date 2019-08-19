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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/attribute.css ">
</head>
<body>
    <div id="header" style="height: 0.8rem;background-color: #333333;">
        <img src="http://oa.redmany.com:50002/document/to_left.png" style="display: block;width: 0.48rem;height:0.48rem;margin: 0.16rem;float:left" onclick="history.go(-1)">
        <span style="display: block;height: 100%;width: 100%;text-align: center;line-height: 0.8rem;font-size: 14px;color: #ffcc00;margin-left:-24px;">现场点购</span>
    </div>
    <div id="center">
        <div id="type" class="left" style="height:100%;width:20%;float:left;overflow-y: auto;background-color: #f5f5f5;">

        </div>
        <div id="kind" class="right" style="height:100%;width:80%;overflow-y: auto;">

        </div>
    </div>
    <div id="footer" style="height: 0.8rem;width:100%;z-index: 9999;position: fixed;">
        <div style="float: left;width: 80%;height: 100%;background-color: #333333;">
            <img id='cartimg' src="${pageContext.request.contextPath}/images/cart.png" style="height:0.8rem;width: 0.8rem;margin: -0.2rem 0.2rem;border-radius: 50%;background-color: #ffcc00;float: left;" />
            <p id='subtotal' style='display: block;padding-left: 25%;margin: 0;height: 100%;line-height: 0.8rem;color: white;font-size: 14px;'>￥0</p>
        </div>
        <div id="toSettlement" style="height: 100%;width: 20%;float:right;text-align: center;line-height: 0.8rem;font-size: 14px;background-color: #ffcc00;color:#333333;">
            去结算
        </div>
    </div>

    <div id="cart" style='position: fixed;background:rgba(0,0,0,0.4);filter:progid:DXImageTransform.Microsoft.gradient(startcolorstr=#7F000000,endcolorstr=#7F000000);z-index:1000;margin-top:0.8rem;width: 100%;bottom:0;left:0;top:0;right:0;display: none;'>
        <div id='cartdiv' style='position: absolute;bottom: 0;width: 100%;'>
            <div id="carttitle" style="height: 0.8rem;width: 100%;background-color: #333333;color: #ffcc00;text-align: center;line-height: 0.8rem;">
                <p style="display: block;height: 100%;margin:0;float: left;margin-left: 0.4rem;">我的购物清单</p>
                <p id="clearCart" style="display: block;height: 100%;margin:0;float: right;margin-right: 0.4rem;">清空购物车</p>
            </div>
            <div id="cartitem" style="background-color: white;overflow-y: auto;"></div>
        </div>
    </div>

</body>

<script type="text/javascript">

    var bodyHeight = $(window).height();
    var header = $("#header").height();
    var footer = $("#footer").height();
    var h = bodyHeight - header - footer;
    var h2 = bodyHeight - header;

    var _cartdiv = h *0.5;
    var _carttitle = $("#carttitle").height();
    var h3 = _cartdiv - _carttitle;

    function height(){
        $("#center").height(h);
        $("#cartitem").height(h3);
    }

    $(document).resize(function(){
        height();
    })

    $(document).ready(function () {
        getFoodData();
        height();
    })

    function getFoodData(){
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"<%= request.getContextPath()%>/GetFood",
            data:{method:"GetFood"},
            success: function(result){
                //var json = [{"type":"小吃","kind":[{"id":"1","image":"a.png","name":"花生","price":"￥8"},{"id":"2","image":"a.png","name":"上校鸡块","price":"￥15"},{"id":"3","image":"a.png","name":"无骨鸡柳","price":"￥15"},{"id":"4","image":"a.png","name":"薯片","price":"￥10"},{"id":"5","image":"a.png","name":"大鸡排","price":"￥15"},{"id":"6","image":"a.png","name":"骨肉相连","price":"￥10"},{"id":"7","image":"a.png","name":"辣条","price":"￥5"},{"id":"8","image":"a.png","name":"鸡肉卷","price":"￥12"},{"id":"10","image":"a.png","name":"汉堡包","price":"￥15"},{"id":"11","image":"a.png","name":"豆腐干","price":"￥8"}]},{"type":"酒水","kind":[{"id":"12","image":"a.png","name":"百威","price":"￥10"},{"id":"13","image":"a.png","name":"雪花","price":"￥6"},{"id":"14","image":"a.png","name":"哈啤","price":"￥8"},{"id":"15","image":"a.png","name":"青岛","price":"￥6"},{"id":"16","image":"a.png","name":"蓝带","price":"￥15"},{"id":"17","image":"a.png","name":"惠泉","price":"￥6"},{"id":"18","image":"a.png","name":"黑啤","price":"￥25"}]},{"type":"饮料","kind":[{"id":"19","image":"a.png","name":"雪碧","price":"￥5"},{"id":"20","image":"a.png","name":"百事可乐","price":"￥5"},{"id":"21","image":"a.png","name":"可口可乐","price":"￥5"},{"id":"22","image":"a.png","name":"尖叫","price":"￥12"},{"id":"23","image":"a.png","name":"七喜","price":"￥5"},{"id":"24","image":"a.png","name":"王老吉","price":"￥8"},{"id":"25","image":"a.png","name":"红牛","price":"￥12"},{"id":"26","image":"a.png","name":"猛虎","price":"￥10"},{"id":"27","image":"a.png","name":"健力宝","price":"￥8"}]}];
                getType(result);
                getInfo(result);
                css();
            }
        })
    }

    function getType(json){
        var view = "";
        $.each(json,function(index,context){
            var type = context.type;
            var id = "info"+index;
            view += "<div style='height:0.8rem;width:100%;border-bottom:1px solid #dcdcdc'>"
            view += "<a href='#"+index+"' id='"+id+"' class='type_a' style='display:block;height:100%;width:100%;text-align:center;font-size:14px;line-height:0.8rem;text-decoration:none;color:black;'>" + type + "</a>";
            view += "</div>";
            $("#type").html(view);
        })
    }

    function getInfo(json){
        var view = "";
        $.each(json,function(index,context){
            var type = context.type;
            view += "<div id='"+index+"' class='info_div' style='background-color: burlywood;height: 0.4rem;'>"
            view += "<span style='display: block;height: 100%;margin-left:0.2rem ;line-height: 0.4rem;font-size: 12px;'>" + type + "</span>";
            view += "</div>";
            var info = context.kind;
            $.each(info,function(i,kind){
                var image = kind.image;
                var name = kind.name;
                var price = kind.price;
                var kindid = "kind"+kind.id;
                view += "<div id='"+kindid+"' style='height: 1.6rem;border-bottom: 1px solid #dcdcdc;'>";
                view += "<div style='height: 100%;width: 30%;float: left;'>";
                view += "<img src='"+image+"' style='height: 1.2rem;width: 1.2rem;margin: 0.2rem 0.2rem;' />";
                view += "</div>";
                view += "<div style='height: 100%;width:40%;float: left;'>";
                view += "<span class='name' style='display:block;height: 0.6rem;width:100%;line-height: 0.6rem;margin-top: 0.2rem;font-size: 14px;'>"+name+"</span>";
                view += "<span class='price' style='display:block;height: 0.6rem;width:100%;line-height: 0.6rem;margin-bottom: 0.2rem;font-size: 14px;color: red;'>"+price+"</span>";
                view += "</div>";
                view += "<div style='height:100%;width:30%;float:right'>"
                view += "<p class='reduce' style='display:block;height:0.32rem;width:0.32rem;border:1px solid #999999;text-align:center;line-height:0.3rem;border-radius:50%;float:left;margin-left:0.14rem;margin-top:0.64rem;font-weight: bold;font-size:16px;color:#999999'>-</p>";
                view += "<p class='num' style='color:#444444;float:left;width:0.4rem;margin-top:0.68rem;font-size:12px;text-align:center;'>0</p>";
                view += "<p class='add' style='display:block;height:0.32rem;width:0.32rem;background-color:#ffcc00;border:1px solid #ffcc00;text-align:center;line-height:0.3rem;border-radius:50%;float:right;margin-right:0.28rem;margin-top:0.64rem;font-weight: bold;font-size:16px;'>+</p>";
                view += "</div>";
                view += "</div>";
            })
            $("#kind").html(view);
        })
    }

    function css(){
        $(".type_a").eq(0).css("background-color","white");
        $(".reduce").hide();
        $(".num").hide();
        $("#subtotal").hide();

        $(".type_a").click(function(){
            $(".type_a").css("background-color","transparent");
            $(this).css("background-color","white");
        });

        $("#kind").bind("scroll", function(event){
            $(".info_div").each(function(){
                var navH = $(this).offset().top;
                //var scroH = "0.8rem";
                if($(window).height()*0.2 > navH){
                    var id = $(this).attr("id");
                    var tag = "info"+id;
                    $(".type_a").css("background-color","transparent");
                    $(eval(tag)).css("background-color","white");
                }
            });
        });

        $(".add").bind("click",function(event){
            var _parent = $(this).parent();
            var _num = _parent.find(".num");
            var num = parseInt(_num.html());
            num += 1;
            _num.html(num);
            var _reduce = _parent.find(".reduce");
            _reduce.show();
            _num.show();
            subtotal($(this),"add");
            inCart($(this));
        });

        $(".reduce").bind("click",function(event){
            var _parent = $(this).parent();
            var _num = _parent.find(".num");
            var num = parseInt(_num.html());
            if(num > 0){
                num -= 1;
                _num.html(num);
            }
            if(num == 0){
                $(this).hide();
                _num.hide();
            }
            subtotal($(this),"reduce");
            inCart($(this));
        });

    }

    function subtotal(thisview,operation){
        var _parent = thisview.parent().parent();

        var _price = _parent.find(".price").html();
        var price = _price.substr(1,_price.length);
        price = parseFloat(price);

        var subtotal = $("#subtotal").html();
        var _s = subtotal.substr(1,subtotal.length);
        var s = parseFloat(_s);

        if("add" == $.trim(operation)){
            s += price;
            subtotal = "￥"+s;
        }

        if("reduce" == $.trim(operation)){
            s -= price;
            subtotal = "￥"+s;
        }

        $("#subtotal").html(subtotal);
        $("#subtotal").show();
        if(s == 0){
            $("#subtotal").hide();
        }
    }

    function inCart(thisview){
        var _parent = thisview.parent().parent();

        var _name = _parent.find(".name").html();

        var _num = _parent.find(".num").html();
        var num = parseInt(_num);

        var _price = _parent.find(".price").html();
        var price = _price.substr(1,_price.length);
        price = parseFloat(price);

        var sub = num * price;
        var _sub = "￥"+sub;

        var _id = _parent.attr("id");

        var view = "";

        if($("#cartitem").children().hasClass("cartInfo")){
            var hasInfo = false;
            var _this = null;
            $(".cartInfo").each(function(){
                var c_id = $(this).attr("attr_id");
                if($.trim(_id) == $.trim(c_id)){
                    hasInfo = true;
                    _this = $(this);
                    return false;
                }
            })
            if(hasInfo == true && _this != null){
                if(num == 0){
                    _this.remove();
                } else {
                    _this.find(".cartInfoSub").html(_sub);
                    _this.find(".cartInfoNum").html(num);
                }
            } else {
                view += "<div class='cartInfo' attr_id='"+_id+"' style='height:0.8rem;width:100%;line-height:0.8rem;border-bottom:1px solid #cccccc'>";
                view += "<p class='cartInfoName' style='float:left;margin:0;height:100%;line-height:0.8rem;width:45%;padding-left:0.2rem;'>"+_name+"</p>";
                view += "<p class='cartInfoSub' style='float:left;margin:0;width:25%;text-align:right'>"+_sub+"</p>";
                view += "<p class='cartInfoReduce' style='display:block;height:0.32rem;width:0.32rem;border:1px solid #999999;text-align:center;line-height:0.3rem;border-radius:50%;float:left;margin: 0.24rem 0 0 0.34rem;font-weight: bold;font-size:16px;color:#999999' onclick='cartInfoReduce(this)'>-</p>";
                view += "<p class='cartInfoNum' style='color:#444444;float:left;width:0.4rem;margin:0;font-size:12px;text-align:center;'>"+num+"</p>";
                view += "<p class='cartInfoAdd' style='display:block;height:0.32rem;width:0.32rem;background-color:#ffcc00;border:1px solid #ffcc00;text-align:center;line-height:0.3rem;border-radius:50%;float:left;margin:0;margin-top:0.24rem;font-weight: bold;font-size:16px;' onclick='cartInfoAdd(this)'>+</p>";
                view += "</div>";
                $("#cartitem").append(view);
            }
        }else{
            view += "<div class='cartInfo' attr_id='"+_id+"' style='height:0.8rem;width:100%;line-height:0.8rem;border-bottom:1px solid #cccccc'>";
            view += "<p class='cartInfoName' style='float:left;margin:0;height:100%;line-height:0.8rem;width:45%;padding-left:0.2rem;'>"+_name+"</p>";
            view += "<p class='cartInfoSub' style='float:left;margin:0;width:25%;text-align:right'>"+_sub+"</p>";
            view += "<p class='cartInfoReduce' style='display:block;height:0.32rem;width:0.32rem;border:1px solid #999999;text-align:center;line-height:0.3rem;border-radius:50%;float:left;margin: 0.24rem 0 0 0.34rem;font-weight: bold;font-size:16px;color:#999999' onclick='cartInfoReduce(this)'>-</p>";
            view += "<p class='cartInfoNum' style='color:#444444;float:left;width:0.4rem;margin:0;font-size:12px;text-align:center;'>"+num+"</p>";
            view += "<p class='cartInfoAdd' style='display:block;height:0.32rem;width:0.32rem;background-color:#ffcc00;border:1px solid #ffcc00;text-align:center;line-height:0.3rem;border-radius:50%;float:left;margin:0;margin-top:0.24rem;font-weight: bold;font-size:16px;' onclick='cartInfoAdd(this)'>+</p>";
            view += "</div>";
            $("#cartitem").append(view);
        }


    }

    function cartInfoAdd(data){
        var _parent = $(data).parent();
        var id = _parent.attr("attr_id");
        $(eval(id)).find(".add").trigger('click');
    }

    function cartInfoReduce(data){
        var _parent = $(data).parent();
        var id = _parent.attr("attr_id");
        $(eval(id)).find(".reduce").trigger('click');
    }

    $("#cart").height(h);
    $("#cartimg").click(function(){
        $("#cart").slideToggle(300);
    })

    $("#clearCart").click(function(){
        if(confirm("确定清空购物车？")){
            $("#cartitem").empty();
            $(".reduce").hide();
            $(".num").html(0).hide();
            $("#subtotal").html("￥0").hide();
            $("#cart").slideToggle(300);
        }else{
            return false;
        }
    })

    $("#toSettlement").click(function(){

        var content = $("#cartitem").html();

        if(content == null || content.length == 0)
        {
            alert("购物车为空");
        } else {
            var subNum = 0;
            var subTotal = 0;
            var tablesNum = '<%=request.getSession().getAttribute("tablesNum")%>'
            var DataJson = "{\"TableNum\":\""+tablesNum+"\",\"TableItem\":[";

            $(".cartInfo").each(function(){
                var _id = $(this).attr("attr_id");
                var _parent = $(eval(_id));
                var image = _parent.find("img").attr("src");
                var name = _parent.find(".name").html();
                var price = _parent.find(".price").html();
                var num = _parent.find(".num").html();
                subNum += Number(num);
                var newPrice = price.substr(1,price.length) * num;
                subTotal += Number(newPrice);
                DataJson += "{\"id\":\""+_id+"\",\"image\":\""+image+"\",\"name\":\""+name+"\",\"price\":\""+price+"\",\"num\":\""+num+"\"},";
            })

            if(DataJson.indexOf("{") != -1){
                DataJson = DataJson.substr(0,DataJson.length-1);
            }

            DataJson+="],\"SubNum\":\""+subNum+"\",\"SubTotal\":\""+subTotal+"\"}";
            confirmOrder(DataJson);
        }

        function confirmOrder(data){
            var body = $(document.body),
                form = $("<form method='post'></form>"),
                input;
            form.attr("action","${pageContext.request.contextPath}/GetFood?method=confirmOrder");
            //form.attr({"action":"${pageContext.request.contextPath}/UiGenerateWap/confirmOrder.jsp"});
            form.attr("accept-charset","UTF-8");
            form.attr("onsubmit","document.charset='UTF-8'");
            input = $("<input type='hidden'>");
            input.attr({"name":"DataJson"});
            input.val(data);
            form.append(input);

            form.appendTo(document.body);
            console.log(form);
            form.submit();
            document.body.removeChild(form[0]);
        }

    });

</script>
</html>
