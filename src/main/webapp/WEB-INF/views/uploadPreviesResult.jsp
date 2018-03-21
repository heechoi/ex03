<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>

	작성자 : ${writer}<br>
	파일 이름 : ${filename }<br>
	<img src="displayFile?filename=${filename }" id='thumbImg'>

	<div id="orignalImgBg">
		<img>
	</div>
	<script>
		$("#thumbImg").click(function(){
			var filename = "${filename}";
			
			var front = filename.substring(0,12);
			var end = filename.substring(14);
			
			var orignalFileName = front+end;
		
			$("#orignalImgBg").find("img").attr("src","displayFile?filename="+orignalFileName);
		})
	</script>
</body>
</html>