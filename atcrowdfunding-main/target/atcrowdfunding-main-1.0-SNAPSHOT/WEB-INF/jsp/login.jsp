<%--
  Created by IntelliJ IDEA.
  User: lzcge
  Date: 2019/7/27
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="loginForm" class="form-signin"  method="post" role="form">
        ${exception.message}
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" value="root" class="form-control" id="floginacct" name="loginacct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" value="root" class="form-control" id="fuserpswd" name="userpswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" value="19802357762" class="form-control" id="fphoneNumber" name="phoneNumber" placeholder="请输入手机号" style="margin-top:10px;">
            <span class="glyphicon glyphicon-phone-alt form-control-feedback "></span>
        </div>
        <div>
            <input id="fverifyCode" value="123456" placeholder="请输入验证码" name="verifyCode" class="form-control form-login-verifyCode">
            <button type="button" class="form-login-verifyCode-button"  onclick="getVerifyCode()">获取</button>
            <br><br>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" id="ftype" name="type">
                <option value="member">会员</option>
                <option value="user" selected>管理</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="reg.html">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
<script>
    function dologin() {

        var floginacct = $("#floginacct");
        var fuserpswd = $("#fuserpswd");
        var ftype = $("#ftype");
        var fverifyCode = $("#fverifyCode");
        if($.trim(floginacct.val())==""){
            // alert("账号不能为空");
            //time显示的时间，icon图标的index，shift  0-6有不同的显示效果，如抖动这些
            layer.msg("账号不能为空",{time:1000,icon:0,shift:0},function () {
                floginacct.val("");
                floginacct.focus();
            });

        }else if($.trim(fuserpswd.val())==""){
            // alert("密码不能为空");
            layer.msg("密码不能为空",{time:1000,icon:0,shift:0},function () {
                fuserpswd.val("");
                fuserpswd.focus();
            });

        }else if($.trim(ftype.val())==""){
            // alert("类型不能为空");
            layer.msg("类型不能为空",{time:1000,icon:0,shift:0},function () {
                ftype.val("");
                ftype.focus();
            });

        }else if($.trim(fverifyCode.val())==""){
            // alert("验证码不能为空");
            layer.msg("验证码不能为空",{time:1000,icon:0,shift:0},function () {
                fverifyCode.val("");
                fverifyCode.focus();
            });

        }else{
            var data={
                "user":{
                    "loginacct":floginacct.val(),
                    "userpswd":fuserpswd.val()
                },
                "type":ftype.val(),
                "verifyCode":fverifyCode.val()
            };
            $.ajax({
                type: "POST",
                url: "${APP_PATH}/doLogin.do",
                contentType: "application/json",
                dataType: "JSON",
                data: JSON.stringify(data),
                success: function (obj) {
                    console.log(obj);
                    if(obj.data=='登录成功'){
                        window.location.href="${APP_PATH}/main.htm";
                    }else{
                        layer.msg(obj.data, {time:2000, icon:4, shift:0});
                        // alert(obj.data);
                        fverifyCode.val("");
                    }
                },
                error: function () {
                    layer.msg("登录失败，检查网络是否正常", {time:2000, icon:5, shift:0});
                }

            })

        }


    }

    /**
     *获取验证码
     */
    function getVerifyCode() {
        var phoneNumber = $("#fphoneNumber");
        if($.trim(phoneNumber.val())==""){
            alert("电话不能为空");
            fphoneNumber.val("");
            fphoneNumber.focus();
        }else{

            $.ajax({
                url: "${APP_PATH}/sendSms.do",
                async : true,
                type: "POST",
                dataType: "json",
                data: {"phoneNumber":phoneNumber.val()},
                success: function (data) {
                    if(data == 'fail'){
                        alert("发送验证码失败");
                        return ;
                    }else{
                        alert("验证码发送成功")
                    }
                }
            });
        }
        // var phoneNumber = $("input[name=phoneNumber]").val();

    }


</script>
</body>
</html>