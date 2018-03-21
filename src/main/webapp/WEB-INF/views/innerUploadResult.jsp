<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	텍스트 : ${test }<br>
	파일이름  : ${filename }<br>
	<!--서버에 로우데이터를 가져가서 처리한것  -->
	<img src="${filename }">
</body>
</html>