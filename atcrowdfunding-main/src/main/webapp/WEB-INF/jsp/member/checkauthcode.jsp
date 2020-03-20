<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/theme.css">
	<style>
#footer {
    padding: 15px 0;
    background: #fff;
    border-top: 1px solid #ddd;
    text-align: center;
}
	</style>
  </head>
  <body>
 <div class="navbar-wrapper">
      <div class="container">
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			  <div class="container">
				<div class="navbar-header">
                    <a class="navbar-brand" href="${APP_PAHT}/index.htm" style="font-size:32px;">人人筹-创意产品众筹平台</a>
				</div>
            <div id="navbar" class="navbar-collapse collapse" style="float:right;">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> ${sessionScope.member.username}<span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="${APP_PATH}/member.htm"><i class="glyphicon glyphicon-scale"></i> 会员中心</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                            <li class="divider"></li>
                            <li><a href="${APP_PATH}/logout.htm"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
			  </div>
			</nav>

      </div>
    </div>

    <div class="container theme-showcase" role="main">
      <div class="page-header">
        <h1>实名认证 - 申请</h1>
      </div>

		<ul class="nav nav-tabs" role="tablist">
		  <li role="presentation" ><a href="#"><span class="badge">1</span> 基本信息</a></li>
		  <li role="presentation" ><a href="#"><span class="badge">2</span> 资质文件上传</a></li>
		  <li role="presentation" ><a href="#"><span class="badge">3</span> 邮箱确认</a></li>
		  <li role="presentation" class="active"><a href="#"><span class="badge">4</span> 申请确认</a></li>
		</ul>
        
		<form role="form" style="margin-top:20px;">
		  <div class="form-group">
			<label for="authcode">验证码</label>
			<input type="text" class="form-control" id="authcode" placeholder="请输入你邮箱中接收到的验证码">
		  </div>
          <button type="button" id="reSendCode" class="btn btn-primary">重新发送验证码</button>
		  <button type="button" id="finishBtn"  class="btn btn-success">申请完成</button>
		</form>
		<hr>
    </div> <!-- /container -->
        <div class="container" style="margin-top:20px;">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div id="footer">
                        <div class="footerNav">
                            <a rel="nofollow" href="#">关于我们</a> | <a rel="nofollow" href="#">服务条款</a> | <a rel="nofollow" href="#">免责声明</a> | <a rel="nofollow" href="#">网站地图</a> | <a rel="nofollow" href="#">联系我们</a>
                        </div>
                        <div class="copyRight">
                            Copyright ?2017-2017 lzcge.com 版权所有
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
 <script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
	<script>
        $('#myTab a').click(function (e) {
          e.preventDefault()
          $(this).tab('show')
        });    


        //验证码检验
        $("#finishBtn").click(function(){
            var authcode = $("#authcode");
            if($.trim(authcode.val())==''){
                layer.msg("验证码不能为空",{time:1000,icon:0,shift:0},function () {
                    authcode.val("");
                    authcode.focus();
                });
            }else{
                var loadingIndex = -1 ;
                $.ajax({
                    type : "POST",
                    url  : "${APP_PATH}/member/finishApply.do",
                    data : {
                        "authcode" : authcode.val()
                    },
                    beforeSend: function(){
                        loadingIndex = layer.msg('验证中...', {icon: 6});
                        return true ; //必须返回true,否则,请求终止.
                    },
                    success : function(result) {
                        layer.close(loadingIndex);
                        if ( result.info=="success" ) {
                            layer.msg("验证码验证成功", {time:2000, icon:6});
                            window.location.href = "${APP_PATH}/member.htm";
                        } else {
                            var msg = "实名认证申请失败";
                            if ( result.data ) {
                                msg = result.data;
                            }
                            layer.msg(msg, {time:1000, icon:5, shift:6});
                            authcode.val("");
                            authcode.focus();
                        }
                    }
                });
            }

        });


        //重新发送验证码
        $("#reSendCode").click(function(){
            <%--alert("${member.email}");--%>
            var loadingIndex = -1 ;
            $.ajax({
                type : "POST",
                url  : "${APP_PATH}/member/sendCode.do",
                data : {
                    "email" : "${member.email}"
                },
                beforeSend: function(){
                    loadingIndex = layer.msg('验证码发送中', {icon: 6});
                    return true ; //必须返回true,否则,请求终止.
                },
                success : function(result) {
                    console.log(result);
                    layer.close(loadingIndex);
                    if ( !result.data==false ) {
                        layer.msg("验证码发送成功", {time:2000, icon:6});
                        <%--window.location.href = "${APP_PATH}/member/checkauthcode.htm";--%>
                    } else {
                        layer.msg("发送验证码失败", {time:1000, icon:5, shift:6});
                    }
                }
            });

        });

        
	</script>
  </body>
</html>