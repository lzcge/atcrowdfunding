<%@page pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<link rel="stylesheet" href="${APP_PATH }/css/doc.min.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">人人筹 - 项目管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
				<%--<%@include file="/WEB-INF/jsp/common/userinfo.jsp" %>--%>
                    <jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
			</li>

            <%--<li style="margin-left:10px;padding-top:8px;">--%>
				<%--<button type="button" class="btn btn-default btn-danger">--%>
				  <%--<span class="glyphicon glyphicon-question-sign"></span> 帮助--%>
				<%--</button>--%>
			<%--</li>--%>
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
				<%@include file="/WEB-INF/jsp/common/menu.jsp" %>
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="${APP_PAHT}/projectManager/index.htm">数据列表</a></li>
				  <li class="active">修改</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form id="updateForm">
				  <div class="form-group">
					<label for="projectName">项目名称</label>
					<input type="text" class="form-control" id="projectName" value="${project.projectName }" placeholder="请输入项目名称">
				  </div>
				  <div class="form-group">
					<label for="projectDescription">项目描述</label>
					<input type="text" class="form-control" id="projectDescription" value="${project.projectDescription }" placeholder="请输入项目描述信息">
				  </div>
				  <button id="updateBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 修改</button>
				  <button id="resetBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script src="${APP_PATH }/jquery/layer/layer.js"></script>
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
            $(function(){
            	$("#updateBtn").click(function(){
                	var loadingIndex = -1 ;
                	var projectName = $("#projectName").val();
                	var projectDescription = $("#projectDescription").val();
                	if($.trim(projectName)==''){
                        layer.msg("项目名称不能为空",{time:2000,icon:0,shift:0},function () {
                            $("#projectName").val("");
                            $("#projectName").focus();
                        });
                    }else if($.trim(projectDescription)==''){
                        layer.msg("项目描述不能为空",{time:2000,icon:0,shift:0},function () {
                            $("#projectDescription").val("");
                            $("#projectDescription").focus();
                        });
                    }else{

                        $.ajax({

                            url : "${APP_PATH}/projectManager/update.do",
                            type : "POST",
                            data : {
                                projectName : projectName,
                                projectDescription : projectDescription,
                                id : ${project.id}
                            },
                            beforeSend : function(){
                                loadingIndex = layer.msg('数据正在修改中', {icon: 6});
                                return true ;
                            },
                            success : function(result){
                                layer.close(loadingIndex);
                                if(result.info=="success"){
                                    layer.msg("数据修改成功", {time:2000, icon:6});
                                    setTimeout(2000);
                                    window.location.href="${APP_PATH}/projectManager/index.htm?pageNo=${param.pageNo}";
                                }else{
                                    layer.msg("数据修改失败", {time:2000, icon:5, shift:6});
                                }

                            },
                            error : function(){
                                layer.msg("数据修改失败", {time:2000, icon:5, shift:6});
                            }

                        });

                    }

                	
                });	
            	
            	
            	$("#resetBtn").click(function(){
            		$("#updateForm")[0].reset();
            	});
            	
            });
            
            
            
        </script>
    <script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
  </body>
</html>
