<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
	작성자 : ${writer }<br>
	<c:forEach var="files" items="${files }">
		<img src="displayFile?filename=${files }" class="thumbImg" data-img="${files }"><br>
	</c:forEach>
	<div id="orignalImg">
		<img>
	</div>
	<script>
		$(".thumbImg").click(function(){
			var filename = $(this).attr("data-img");
			
			var front = filename.substring(0,12);
			var end = filename.substring(14);
			
			var orignalFileName = front+end;
			
			$("#orignalImg").find("img").attr("src","displayFile?filename="+orignalFileName);
		})
	</script>
</body>
</html>