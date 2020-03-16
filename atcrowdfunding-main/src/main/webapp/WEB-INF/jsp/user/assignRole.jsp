<%--
  Created by IntelliJ IDEA.
  User: lzcge
  Date: 2019/8/21
  Time: 1:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">人人筹 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <%--<%@ include file="/WEB-INF/jsp/common/top.jsp" %>--%>
                <jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
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
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select id="leftOptions" class="form-control" multiple size="10" style="width:150px;overflow-y:auto;">
                                <c:forEach items="${unAssiginRoleList}" var="role" >
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="leftToRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="rightToLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select id="rightOptions" class="form-control" multiple size="10" style="width:150px;overflow-y:auto;">
                                <c:forEach items="${assignRoleList}" var="role" >
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
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


    $("#leftToRightBtn").click(function () {
        var leftOptions = $("#leftOptions option:selected");


        //后端返回的用户id封装成user对象
        var user = {
            "id":"${userId}"
        };
        var roleList = new Array();
        $.each(leftOptions,function (index,val) {
            var role = {
                "id":val.value
            };
            roleList[index] = role;
        });
        if(roleList.length>0){
            var data = {
                "assignStatus":1,
                "user":user,
                "roleList":roleList
            };
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/user/doAssignRole.do",
                data:JSON.stringify(data),
                dataType:"JSON",
                contentType:"application/json",
                success:function (result) {
                    if(result.info=='success'){
                        $("#rightOptions").append(leftOptions.clone());
                        leftOptions.remove();
                    }else{
                        layer.msg("操作失败",{time:1000,icon:0,shift:0});
                    }

                },
                error:function () {
                    layer.msg("分配失败",{time:1000,icon:0,shift:0});
                }

            })
        }else{
            layer.msg("请先选中",{time:1000,icon:0,shift:0})
        }

    });

    $("#rightToLeftBtn").click(function () {
        var rightOptions = $("#rightOptions option:selected");


        //后端返回的用户id封装成user对象
        var user = {
            "id":"${userId}"
        };
        var roleList = new Array();
        $.each(rightOptions,function (index,val) {
            var role = {
                "id":val.value
            };
            roleList[index] = role;
        });
        if(roleList.length>0){
            var data = {
                "assignStatus":0,
                "user":user,
                "roleList":roleList
            };
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/user/doAssignRole.do?assigin=0",
                data:JSON.stringify(data),
                dataType:"JSON",
                contentType:"application/json",
                success:function (result) {
                    if(result.info=='success'){
                        $("#leftOptions").append(rightOptions.clone());
                        rightOptions.remove();
                    }else{
                        layer.msg("操作失败",{time:1000,icon:0,shift:0});
                    }

                },
                error:function () {
                    layer.msg("操作失败",{time:1000,icon:0,shift:0});
                }

            })
        }else{
            layer.msg("请先选中",{time:1000,icon:0,shift:0})
        }

    })
</script>
<script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
</body>
</html>

