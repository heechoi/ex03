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
	파일명 : ${filename }<br>
	<!--서버 외부에 저장한 이미지라서 가져올수없음  -->
	<%-- <img src="${filename }"> --%>
	<img src="displayFile?filename=${filename }">
</body>
</html>