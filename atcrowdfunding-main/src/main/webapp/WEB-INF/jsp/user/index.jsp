<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>





<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">人人筹 - 用户维护</a></div>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryButton" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" id="deleteListBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="addBtn" type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="allCheckbox" type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>


                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
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

        page(1);
        //菜单点击标红，并默认展开
        showMenu();

    });
    $("tbody .btn-success").click(function(){
        window.location.href = "assignRole.html";
    });
    $("tbody .btn-primary").click(function(){
        window.location.href = "edit.html";
    });



    function pageChange(pageNo) {
       page(pageNo);
    }

    var loadingIndex = -1;
    var data = {
        "pageNo":1,
        "pageSize":10
    };
    //异步加载数据
    function page(pageNo) {
        data.pageNo = pageNo;
        $.ajax({
            type:"POST",
            url:"${APP_PATH}/user/index.do",
            data:data,
            beforSend:function () {
                loadingIndex = layer.load(2,{time:10*1000});
                return true;
            },
            success:function (result) {
                console.log(result);
                layer.close(loadingIndex);
                if(result.info=='success'){
                    $("tbody").empty();
                    $(".pagination").empty();
                    var pageNo = result.data.pageNo ;
                    var totalNo = result.data.totalNo;

                    //循环拼接用户的数据
                    $.each(result.data.datas,function (index,value) {
                            $("tbody").append(
                                '<tr>'+
                                    '<td>'+(index+1)+'</td>'+
                                    '<td><input id="'+value.id+'" type="checkbox"></td>'+
                                    '<td>'+value.loginacct+'</td>'+
                                    '<td>'+value.username+'</td>'+
                                    '<td>'+value.email+'</td>'+
                                    '<td>'+
                                    '<button id="assignRoleBtn" onclick="window.location.href=\'${APP_PATH}/user/toAssignRole.htm?id='+value.id+'\'" type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>'+
                                    '<button id="updateBtn" onclick=updateUser('+value.id+') type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>'+
                                    '<button id="removeBtn" onclick=deleteUser('+value.id+',\''+value.username+'\')  type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>'+
                                    '</td>'+
                                '</tr>'
                        );
                    });


                    //拼接页码
                    var contentBar = '';

                    if(pageNo==1 ){
                        contentBar+='<li class="disabled"><a href="#">上一页</a></li>';
                    }else{
                        contentBar+='<li><a href="#" onclick="pageChange('+(pageNo-1)+')">上一页</a></li>';
                    }

                    for(var i = 1 ; i<= totalNo ; i++ ){
                        contentBar+='<li';
                        if(pageNo==i){
                            contentBar+=' class="active"';
                        }
                        contentBar+='><a href="#" onclick="pageChange('+i+')">'+i+'</a></li>';
                    }

                    if(pageNo==totalNo ){
                        contentBar+='<li class="disabled"><a href="#">下一页</a></li>';
                    }else{
                        contentBar+='<li><a href="#" onclick="pageChange('+(pageNo+1)+')">下一页</a></li>';
                    }
                    $(".pagination").html(contentBar);

                }else{
                    layer.msg(result.info, {time:2000, icon:4, shift:0});
                }
            },
            error:function () {
                layer.msg("加载失败", {time:2000, icon:5, shift:0});
            }

        })
    }

    //模糊查询
    $("#queryButton").click(function () {
        var queryText = $("#queryText").val();
        data.queryText = queryText;
        page(1);
    });

    //新增用户
    $("#addBtn").click(function () {
        window.location.href="${APP_PATH}/user/toAdd.do"
    });

    function updateUser(id) {
        window.location.href = "${APP_PATH}/user/toUpdate.htm?id="+id
    }

    function deleteUser(id,username) {
        <%--window.location.href = "${APP_PATH}/user/toUpdate.htm?id="+id--%>

        layer.confirm("确认删除"+username+"吗？",{icon: 3, title:'提示'}, function(cindex){
            layer.close(cindex);
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/user/doDelete.do",
                data:{
                    "id": id
                },
                dataType:"JSON",
                success:function (result) {
                    if(result.info=='success'){
                        layer.msg("删除成功",{time:1000,icon:1,shift:0},function () {
                            window.location.href="${APP_PATH}/user/toIndex.htm"
                        });
                    }else{
                        layer.msg("删除失败",{time:1000,icon:0,shift:0});
                    }
                },
                error:function () {
                    layer.msg("网络连接异常",{time:1000,icon:0,shift:0});
                }

            })
        }, function(cindex){
            layer.close(cindex);
        });



    }

    //批量操作选择框
    $("#allCheckbox").click(function () {
       var checkStatus = this.checked;
       $("tbody tr td input[type='checkbox']").prop("checked",checkStatus);


    });

    //批量删除
    $("#deleteListBtn").click(function () {
        var selectedChecks = $("tbody tr td input:checked");
        //封装json传输数据对象
        var list = new Array();
        $.each(selectedChecks,function (i,value) {
            var user = {
                id:value.id
            };
            list[i] = user;
        });
        //发送删除请求
        if(list.length===0){
            layer.msg("请先选中",{time:1000,icon:0,shift:0})
        }else{

            layer.confirm("确认删除这些用户吗？",{icon: 3, title:'提示'}, function(cindex){
                layer.close(cindex);
                var data={
                    "userList":list
                };
                $.ajax({
                    type:"POST",
                    url:"${APP_PATH}/user/doDeleteList.do",
                    data:JSON.stringify(data),
                    dataType:"JSON",
                    contentType:"application/json",
                    success:function (result) {
                        if(result.info=='success'){
                            layer.msg("删除成功",{time:1000,icon:1,shift:0},function () {
                                window.location.href="${APP_PATH}/user/toIndex.htm"
                            });
                        }else{
                            layer.msg("删除失败",{time:1000,icon:0,shift:0});
                        }
                    },
                    error:function () {
                        layer.msg("网络连接异常",{time:1000,icon:0,shift:0});
                    }

                })
            }, function(cindex){
                layer.close(cindex);
            });

        }
    });



</script>
</body>
</html>

