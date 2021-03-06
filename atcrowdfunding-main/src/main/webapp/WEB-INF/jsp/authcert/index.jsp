<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet"
	href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
<link rel="stylesheet" href="${APP_PATH}/css/main.css">
<link rel="stylesheet" href="${APP_PATH}/css/pagination.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" style="font-size: 32px;" href="#">人人筹 - 实名认证审核</a>
				</div>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li style="padding-top: 8px;">
                        <%--<%@include	file="/WEB-INF/jsp/common/top.jsp"%>--%>
                        <jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
					</li>
					<%--<li style="margin-left: 10px; padding-top: 8px;">--%>
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
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" id="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="queryText" class="form-control has-success" type="text"
										placeholder="请输入登录账号/手机号/邮箱">
								</div>
							</div>
							<button type="button" class="btn btn-warning" onclick="queryMember()">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<%--<button id="batchDelete" type="button" class="btn btn-danger"--%>
							<%--style="float: right; margin-left: 10px;">--%>
							<%--<i class="glyphicon glyphicon-remove"></i> 删除--%>
						<%--</button>--%>
						<%--<button type="button" class="btn btn-primary"--%>
							<%--style="float: right;" onclick="window.location.href='${APP_PATH}/advert/add.htm'">--%>
							<%--<i class="glyphicon glyphicon-plus"></i> 新增--%>
						<%--</button>--%>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<%--<th width="30"><input type="checkbox"></th>--%>
										<th style='font-size: 12px'>登录账号</th>
										<th style='font-size: 12px'>用户名</th>
										<th style='font-size: 12px'>邮箱</th>
										<th style='font-size: 12px'>电话</th>
										<th style='font-size: 12px'>用户类型</th>
										<th style='font-size: 12px'>真实姓名</th>
										<th style='font-size: 12px'>身份证号</th>
										<th style='font-size: 12px'>操作</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
								<tfoot>
									<tr>
										<td colspan="9" align="center">
											<!-- <ul class="pagination">
												
											</ul> -->
											<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
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
	<script src="${APP_PATH}/jquery/layer/layer.js"></script>
	<script src="${APP_PATH}/jquery/pagination/jquery.pagination.js"></script>
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
			    
			    <c:if test="${empty param.pageNo}">
			    queryPage(0);
			    </c:if>
			    <c:if test="${not empty param.pageNo}">
			    queryPage(${param.pageNo}-1);
			    </c:if>
                //菜单点击标红，并默认展开
                showMenu();
            });
            
            <%--function changepageNo( pageNo ) {--%>
            	<%--//window.location.href = "<%=request.getContextPath() %>/advert/index.htm?pageNo="+pageNo;--%>
            	<%--queryPage(pageNo-1);--%>
            <%--}--%>
            
            //使用Ajax异步查询数据
            function queryPage( pageIndex ){
            	var dataObj = {
        			"pageNo" : pageIndex+1 ,   //pageNo 是属性名称,是否增加双引号无所谓
        			"pageSize" : 8
        		};
            	if(condition){
            		dataObj.queryText = $("#queryText").val(); //增加模糊查询条件
            	}
            	
            	var loadingIndex = -1 ;
            	$.ajax({
            		
            		url : "${APP_PATH}/authcert/pageQueryMember.do",
            		type : "POST",
            		data : dataObj,
                    dataType:"JSON",
            		beforeSend : function(){
            			loadingIndex = layer.msg('数据查询中', {icon: 6});
            			return true ;
            		},
            		success : function(result){
            		    console.log(result);
            			//显示结果
            			layer.close(loadingIndex);
            			if(result.info=="success"){
            				//循环遍历,显示数据
            				var pageObj = result.data;
            				var list = pageObj.datas ;
            				var content = "";
            				$("tbody").html(content);
            				$.each(list,function(i,memberDetail){
            					
            					content+="<tr>";
								content+="	<td>"+(i+1)+"</td>";
								content+="	<td style='font-size: 8px'>"+memberDetail.member.loginacct+"</td>";
								content+="	<td style='font-size: 8px'>"+memberDetail.member.username+"</td>";
								content+="	<td style='font-size: 8px'>"+memberDetail.member.email+"</td>";
								content+="	<td style='font-size: 8px'>"+memberDetail.member.phone+"</td>";
								if(memberDetail.member.usertype==0){

                                    content+="	<td style='font-size: 8px'>个人</td>";
                                }else{
                                    content+="	<td style='font-size: 8px'>企业</td>";
                                }
								content+="	<td style='font-size: 8px'>"+memberDetail.member.realname+"</td>";
								content+="	<td style='font-size: 8px'>"+memberDetail.member.cardnum+"</td>";

								
								content+="	<td>";
                                content+="	    <button type='button' onclick=\"window.location.href='${APP_PATH}/authcert/show.do?id="+memberDetail.member.id+"'\" class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-eye-open'></i></button>";
								content+="		<button id='assignRoleBtn' onclick='authMember("+memberDetail.member.id+",1)' type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
								// content+="			<i class=' glyphicon glyphicon-pencil'></i>";
								content+="		</button>";
								content+="		<button type='button' class='btn btn-danger btn-xs' onclick='authMember("+memberDetail.member.id+",2)'>";
								content+="			<i class=' glyphicon glyphicon-remove'></i>";
								content+="		</button>";
								content+="	</td>";
								content+="</tr>";
            					
            				});
            				//$("tbody").append(content);
        					$("tbody").html(content);
            				
            				// 创建分页，使用JQuery的pagination插件实现
            				var num_entries = pageObj.totalSize;
            				$("#Pagination").pagination(num_entries, {
            					num_edge_entries: 1, //边缘页数
            					num_display_entries: 2, //主体页数
            					callback: queryPage, //查询当前页的数据.
            					items_per_page:pageObj.pageSize, //每页显示1项
            					current_page:(pageObj.pageNo-1), //当前页,索引从0开始
            					prev_text:"上一页",
            					next_text:"下一页"
            				});
            				
            			}else{
            				
            				layer.msg("查询数据失败", {time:2000, icon:5, shift:6});
            				
            			}
            		},
            		error : function(){
            			layer.msg("查询数据错误", {time:2000, icon:5, shift:6});
            		}
            		
            	});
            }
            
            var condition = false ;

            //条件查询
            function queryMember(){
            	//var queryText = $("#queryText");
            	
            	//if($.trim(queryText.val())==""){
            		// layer.msg("查询条件不能为空", {time:2000, icon:5, shift:6}, function(){
            		// 	queryText.focus();
            		// });
            		// return ;
            	//}
            	condition = true ;
            	queryPage(0);
            }

            //审核不通过/通过
            function authMember(id,index){
                //审核通过
                if(index==1){
                    layer.confirm("确定审核通过吗?",  {icon: 3, title:'提示'}, function(cindex){

                        $.ajax({
                            url : "${APP_PATH}/authcert/update.do",
                            type : "POST",
                            data : {
                                "id":id,
                                "authstatus":'2'

                            },
                            success : function(result){
                                console.log(result);
                                if(result.info=="success"){
                                    layer.msg("操作成功!", {time:2000, icon:6}, function(){
                                        queryPage(0);
                                    });
                                }else{
                                    layer.msg("操作失败!", {time:2000, icon:5, shift:6});
                                }
                            }
                        });

                        layer.close(cindex);
                    }, function(cindex){
                        layer.close(cindex);
                    });
                }
                //审核不通过
                if(index==2){
                    layer.confirm("确定审核失败吗?",  {icon: 3, title:'提示'}, function(cindex){

                        $.ajax({
                            url : "${APP_PATH}/authcert/update.do",
                            type : "POST",
                            data : {
                                "id":id,
                                "authstatus":'3'

                            },
                            success : function(result){
                                console.log(result);
                                if(result.info=="success"){
                                    layer.msg("操作成功!", {time:2000, icon:6}, function(){
                                        queryPage(0);
                                    });
                                }else{
                                    layer.msg("操作失败!", {time:2000, icon:5, shift:6});
                                }
                            }
                        });

                        layer.close(cindex);
                    }, function(cindex){
                        layer.close(cindex);
                    });
                }

            	
            }
            

            

        </script>
       <script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>
</body>

</html>