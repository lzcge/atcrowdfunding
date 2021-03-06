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
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<link rel="stylesheet" href="${APP_PATH }/css/pagination.css">
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
          <div><a class="navbar-brand" style="font-size:32px;" href="#">人人筹 - 流程管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
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
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
				  <div class="panel-heading">
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
				  </div>
					<div class="panel-heading">
						<img id="showimg" alt="流程定义图片" src="${APP_PATH }/jquery/layer/skin/default/loading-1.gif">
				    </div>
          			
			  </div>
			</div>
        </div>
      </div>


    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
	<script src="${APP_PATH}/jquery/pagination/jquery.pagination.js"></script>
	<script src="${APP_PATH }/jquery/jquery-form/jquery-form.min.js"></script>
	
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
				
				$("#showimg").attr("src","${APP_PATH}/process/showimgProDef.do?id=${param.id}");
				
				
            });
            
            
            
            
        </script>
        <script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
  </body>
</html>
    