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

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">人人筹 - 权限维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
            </ul>
            <%--<form class="navbar-form navbar-right">--%>
                <%--<input type="text" class="form-control" placeholder="Search...">--%>
            <%--</form>--%>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form id="form">
                        <div class="form-group">
                            <label for="fname">权限名称</label>
                            <input type="text" class="form-control" id="fname" placeholder="请输入权限名称">
                        </div>
                        <div class="form-group">
                            <label for="furl">权限url</label>
                            <input type="email" class="form-control" id="furl" placeholder="请输入权限url">
                            <p class="help-block label label-warning">请输入合法的url地址</p>
                        </div>
                        <button id="addBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button id="resetBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
<script type="text/javascript">

    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        showMenu();
    });

    //添加
    $("#addBtn").click(function () {
        var fname = $("#fname");
        var furl = $("#furl");

        if($.trim(fname.val())==''){
            layer.msg("权限名不能为空",{time:1000,icon:0,shift:0},function () {
                fname.val("");
                fname.focus();
            });
        }else if($.trim(furl.val())==''){
            layer.msg("权限地址不能为空",{time:1000,icon:0,shift:0},function () {
                furl.val("");
                furl.focus();
            });
        }else{

            var data={
                "name":fname.val(),
                "url":furl.val(),
                "pid":"${param.id}"
            };
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/permission/doAdd.do",
                data:JSON.stringify(data),
                contentType: "application/json",
                dataType:"JSON",
                success:function (result) {
                    if(result.info=='success'){
                        layer.msg("新增成功",{time:1000,icon:1,shift:0},function () {
                            window.location.href="${APP_PATH}/permission/toIndex.htm"
                        });
                    }else{
                        layer.msg("新增失败",{time:1000,icon:0,shift:0});
                    }
                },
                error:function () {
                    layer.msg("网络连接异常",{time:1000,icon:0,shift:0});
                }

            })



        }
    });
    
    //重置
    $("#resetBtn").click(function () {
        $("#form")[0].reset();
    })



</script>
<script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
</body>
</html>
