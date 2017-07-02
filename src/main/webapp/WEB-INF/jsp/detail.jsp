<%--
  Created by IntelliJ IDEA.
  User: LiuSitong
  Date: 2017/6/30
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h2>${seckill.name}</h2>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time"/>
                <span class="glyphicon" id="seckill-box"/>
            </h2>
        </div>
    </div>
</div>


<div class="modal fade" id="killPhoneModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>输入电话：
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhone"
                               placeholder="手机号码" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-foot">
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyhpicon glyhpicon-phone"></span>抢
                </button>

            </div>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 包括所有已编译的插件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.js"></script>
<script src="../../resources/js/seckill.js"></script>
<script>
    $(function () {
        seckill.detail.init({
            seckillId:${seckill.seckillid},
            seckillStart:${seckill.starttime.time},
            seckillEnd:${seckill.endtime.time}
        });
    });
</script>
</body>
</html>
