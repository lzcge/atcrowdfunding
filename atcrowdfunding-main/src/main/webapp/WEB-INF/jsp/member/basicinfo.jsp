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
				  <a class="navbar-brand" href="index.html" style="font-size:32px;">人人筹-创意产品众筹平台</a>
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
		  <li role="presentation" class="active"><a href="#"><span class="badge">1</span> 基本信息</a></li>
		  <li role="presentation"><a href="#"><span class="badge">2</span> 资质文件上传</a></li>
		  <li role="presentation"><a href="#"><span class="badge">3</span> 邮箱确认</a></li>
		  <li role="presentation"><a href="#"><span class="badge">4</span> 申请确认</a></li>
		</ul>
        
		<form role="form" style="margin-top:20px;">
		  <div class="form-group">
			<label for="realname">真实名称</label>
			<input type="text" class="form-control" id="realname" value="${member.realname}" placeholder="请输入真实名称">
		  </div>
		  <div class="form-group">
			<label for="cardnum">身份证号码</label>
			<input type="text" class="form-control" id="cardnum" value="${member.cardnum}" placeholder="请输入身份证号码">
		  </div>
		  <%--<div class="form-group">--%>
			<%--<label for="tel">电话号码</label>--%>
			<%--<input type="text" class="form-control" id="tel" value="${member.phone}" placeholder="请输入电话号码">--%>
		  <%--</div>--%>
          <button type="button" onclick="window.location.href='${APP_PATH}/member/realNameAuth.htm'" class="btn btn-default">上一步</button>
		  <button id="nextBtn" type="button"  class="btn btn-success">下一步</button>
		</form>
		<hr>
    </div> <!-- /container -->
        <div class="container" style="margin-top:20px;">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div id="footer">
                        <div class="footerNav">
                            <a rel="nofollow" href="http://www.lzcge.com">关于我们</a> | <a rel="nofollow" href="http://www.lzcge.com">服务条款</a> | <a rel="nofollow" href="http://www.lzcge.com">免责声明</a> | <a rel="nofollow" href="http://www.lzcge.com">网站地图</a> | <a rel="nofollow" href="http://www.lzcge.com">联系我们</a>
                        </div>
                        <div class="copyRight">
                            Copyright ?2017-2017lzcge.com 版权所有
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
        
        
        $("#nextBtn").click(function(){
            var realname = $("#realname");
            var cardnum = $("#cardnum");
            //身份证格式验证正则表达式
            var cardNumReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            var iFlag = cardNumReg.test(cardnum.val());
            if($.trim(realname.val())==""){
                layer.msg("真实姓名不能为空",{time:1000,icon:0,shift:0},function () {
                    realname.val("");
                    realname.focus();
                });
            }else if($.trim(cardnum.val())==""){
                layer.msg("身份证号不能为空",{time:1000,icon:0,shift:0},function () {
                    cardnum.val("");
                    cardnum.focus();
                });
            }else if(!iFlag){
                layer.msg("身份证号格式有误！",{time:1000,icon:0,shift:0},function () {
                    cardnum.focus();
                });
            }else{
                $.ajax({
                    type : "POST",
                    url  : "${APP_PATH}/member/updateBasicinfo.do",
                    data : {
                        "realname" : realname.val(),
                        "cardnum"  : cardnum.val(),
                    },
                    success : function(result) {
                        if ( result.data==true ) {
                            window.location.href = "${APP_PATH}/member/uploadCert.htm";
                        } else {
                            layer.msg("系统错误！基本信息更新失败", {time:2000, icon:5, shift:6});
                        }
                    }
                });
            }

       });

	</script>
  </body>
</html>