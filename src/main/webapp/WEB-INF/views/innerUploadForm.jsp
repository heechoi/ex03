<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!--file을 upload할려면 src-main-webapp-resource에 파일을 하나 만든다.  -->
</head>
<body>
	<form action="innerUpload" method="post" enctype="multipart/form-data">
		텍스트 : <input type="text" name="test"><br>
		파일 : <input type="file" name="file"><br>
		<input type="submit" value="전송">
	</form>
</body>
</html>