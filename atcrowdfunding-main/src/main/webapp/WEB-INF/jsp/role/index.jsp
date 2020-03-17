<%--
  Created by IntelliJ IDEA.
  User: lzcge
  Date: 2019/8/25
  Time: 10:14
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">人人筹 - 角色维护</a></div>
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
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="deleteBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="addBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="allCheckbox" type="checkbox"></th>
                                <th>名称</th>
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
<script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
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

        page(1);
        //菜单点击标红，并默认展开
        showMenu();
    });

    $("tbody .btn-success").click(function(){
        window.location.href = "assignPermission.html";
    });

    function pageChange(pageNo) {
        page(pageNo);
    }

    /**
     * 异步加载所有的角色信息
     * @param pageNo
     */
    var data={
        "pageNo":1,
        "pageSize":10
    };
    function page(pageNo) {
        data.pageNo = pageNo;
        $.ajax({
            type:"POST",
            url:"${APP_PATH}/role/doIndex.do",
            data:data,
            dataType:"JSON",
            success:function (result) {
                if(result.info=='success'){
                    $("tbody").empty();
                    var pageNo = result.data.pageNo ;
                    var totalNo = result.data.totalNo;
                    var roles = result.data.datas;
                    console.log(roles);
                    $.each(roles,function (index,value) {
                        $("tbody").append(
                            '<tr>'+
                                '<td>'+(index+1)+'</td>'+
                                '<td><input id="'+value.id+'" type="checkbox"></td>'+
                                    '<td>'+value.name+'</td>'+
                                '<td>'+
                                    '<button onclick="window.location.href=\'${APP_PATH}/role/assignPermission.htm?roleId='+value.id+'\' " type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>'+
                                    '<button onclick="window.location.href=\'${APP_PATH}/role/toUpdate.htm?id='+value.id+'\' " type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>'+
                                    '<button onclick=deleteRole('+value.id+',\''+value.name+'\') type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>'+
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
    $("#searchBtn").click(function () {
        data.queryText = $("#queryText").val();
        page(1);

    });

    //新增角色
    $("#addBtn").click(function () {
        window.location.href="${APP_PATH}/role/toAdd.htm"
    });

    //删除角色
    function deleteRole(id,name) {
        <%--window.location.href = "${APP_PATH}/user/toUpdate.htm?id="+id--%>

        layer.confirm("确认删除"+name+"吗？",{icon: 3, title:'提示'}, function(cindex){
            layer.close(cindex);
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/role/doDelete.do",
                data:{
                    "id": id
                },
                dataType:"JSON",
                success:function (result) {
                    if(result.info=='success'){
                        layer.msg("删除成功",{time:1000,icon:1,shift:0},function () {
                            window.location.href="${APP_PATH}/role/toIndex.htm"
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


    //复选框
    $("#allCheckbox").click(function () {
        var check = this.checked;
        $("tbody tr td input[type='checkbox']").prop("checked",check);
    });


    //批量删除操作
    $("#deleteBtn").click(function () {
        var deleRoles = $("tbody tr td input:checked");
        var roles = new Array();
        $.each(deleRoles,function (index,value) {
            var role={
                "id":value.id
            };
            roles[index] = role;
        });

        if(roles.length<=0){
            layer.msg("请先选中",{time:1000,icon:0,shift:0});
        }else{
            var data={
                "roleList":roles
            };
            layer.confirm("确认删除这些数据吗？",{icon: 3, title:'提示'}, function (cindex) {
                layer.close(cindex);
                $.ajax({
                    type:"POST",
                    url:"${APP_PATH}/role/doDeleteList.do",
                    data:JSON.stringify(data),
                    dataType:"JSON",
                    contentType:"application/json",
                    success:function (result) {
                        if(result.info=='success'){
                            layer.msg("删除成功",{time:1000,icon:1,shift:0},function () {
                                window.location.href="${APP_PATH}/role/toIndex.htm"
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
    })




</script>
</body>
</html>
