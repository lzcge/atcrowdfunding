function showMenu(){
	var href = window.location.href ;
	//var host = window.location.host ;
	var port = window.location.port;//获取端口号
	var index = href.indexOf(port);
	var path = href.substring(index + port.length);//截取端口号后面的路径
	//alert(path);
	// var contextPath = "${APP_PATH}";
	// var pathAddress = path.substring(contextPath.length);
	// alert(pathAddress);
	
	var alink = $(".list-group a[href*='"+path+"']"); //找到包含这个路径的菜单
	
	alink.css("color","red");
	
	alink.parent().parent().parent().removeClass("tree-closed");
	alink.parent().parent().show();
}