
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
            <div><a class="navbar-brand" href="${APP_PATH}/index.htm" style="font-size:32px;">人人筹-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="loginacct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="username" placeholder="请输入昵称" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="password" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="surepassword" placeholder="确认密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="email" placeholder="请输入邮箱地址" style="margin-top:10px;">
            <span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" value="19802357762" class="form-control" id="phone" name="phone" placeholder="请输入手机号" style="margin-top:10px;">
            <span class="glyphicon glyphicon-phone-alt form-control-feedback "></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control"  id="usertype">
                <option>企业</option>
                <option>个人</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="${APP_PATH}/login.htm">我有账号</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="doReg()" > 注册</a>
    </form>
</div>


<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
<script type="text/javascript">

    function doReg() {
        //获取表单数据
        var loginacct = $("#loginacct");
        var username = $("#username");
        var userpswd = $("#password");
        var surepassword = $("#surepassword");
        var type = $("#usertype");
        var email = $("#email");
        var phone = $("#phone");
        if($.trim(loginacct.val())==""){
            // alert("账号不能为空");
            //time显示的时间，icon图标的index，shift  0-6有不同的显示效果，如抖动这些
            layer.msg("账号不能为空",{time:1000,icon:0,shift:0},function () {
                loginacct.val("");
                loginacct.focus();
            });

        }else if($.trim(username.val())==""){
            layer.msg("昵称不能为空",{time:1000,icon:0,shift:0},function () {
                username.val("");
                username.focus();
            });

        }else if($.trim(userpswd.val())==""){
            // alert("密码不能为空");
            layer.msg("密码不能为空",{time:1000,icon:0,shift:0},function () {
                userpswd.val("");
                userpswd.focus();
            });

        }else if($.trim(type.val())==""){
            // alert("类型不能为空");
            layer.msg("类型不能为空",{time:1000,icon:0,shift:0},function () {
                type.val("");
                type.focus();
            });

        }else if($.trim(email.val())==""){
            // alert("验证码不能为空");
            layer.msg("邮箱不能为空",{time:1000,icon:0,shift:0},function () {
                email.val("");
                email.focus();
            });

        }else if($.trim(phone.val())==""){
            // alert("验证码不能为空");
            layer.msg("电话不能为空",{time:1000,icon:0,shift:0},function () {
                phone.val("");
                phone.focus();
            });

        }else if(!(surepassword.val()==userpswd.val())){
            layer.msg("输入密码不一致",{time:1000,icon:0,shift:0},function () {
                userpswd.val("");
                surepassword.val("");
                userpswd.focus();
            });

        }else{
            var data={

                "loginacct":loginacct.val(),
                "username":username.val(),
                "userpswd":userpswd.val(),
                "email":email.val(),
                "usertype":type.val(),
                "phone":phone.val()
            };
            $.ajax({
                type: "POST",
                url: "${APP_PATH}/member/doRegist.do",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "JSON",
                success: function (result) {
                    console.log(result);
                    if(result.data=='success'){
                        layer.msg("注册成功", {time:2000, icon:0, shift:0},function () {
                            window.location.href="${APP_PATH}/login.htm"
                        });
                    }else{
                        layer.msg(result.data, {time:2000, icon:4, shift:0});
                        // alert(obj.data);
                    }
                },
                error: function () {
                    layer.msg("注册失败，检查网络是否正常", {time:2000, icon:5, shift:0});
                }

            })

        }


    }





</script>


</body>
</html>