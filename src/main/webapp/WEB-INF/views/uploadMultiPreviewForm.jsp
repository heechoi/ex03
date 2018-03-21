<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<style>
	#dropBox{
		width:600px;
		height:600px;
		border:1px solid #ccc;
		overflow: auto;
		margin-top: 10px;
	}
	#dropBox img{
		max-width:100%;
		max-height:100%;
	}
</style>

</head>
<body>
	<form id="f1" action="uploadMultiPreview" method="post" enctype="multipart/form-data">
		작성자 이름 : <input type="text" name="writer" placeholder="작성자 이름"><br>
		파일 선택 : <input type="file" name="files" id="files" multiple="multiple"><br>
		<input type="submit" value="전송">
	</form>
	
	<div id="dropBox">
		
	</div>
	
	<script>
	var index =1;
	$("#files").change(function(){
	
		$("#dropBox").empty();
		
		var file = document.getElementById("files");
		var reader = new FileReader();
		
		reader.onload = function(e){
				var imgObj = $("<img>").attr("src",e.target.result);
				$("#dropBox").append(imgObj);
			}
		
		reader.readAsDataURL(file.files[0]);
		reader.onloadend = function(e){
			if(index>=file.files.length){
				index==1;
				return;
			}
			reader.readAsDataURL(file.files[index]);
			index++;
		}

	})
	</script>
</body>
</html>