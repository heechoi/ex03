<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#dropBox{
		width:400px;
		height:300px;
		border:1px dotted gray;
		overflow: auto;
	
	}
	
	#dropBox img{
		max-width:100%;
		max-height:100%;
	}
	
	#dropBox div.item{
		width:100px;
		height:130px;
		margin:5px;
		float:left;
		position: relative;
		border:1px solid #ccc;
		text-align: center;
		line-height: 130px;
	}
	
	#dropBox input.del{
		position:absolute;
		right:1px;
		top:1px;
	}
</style>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
</head>
<body>
	<form id="f1" action="uploadDrag" method="post" enctype="multipart/form-data">
		텍스트 : <input type="text" name="test">
		<input type="submit" value="전송">
	</form>
	
	<div id="dropBox"></div>
	
	<script>
		//전송방식을 form의 데이터를 전송하는 방식으로
		var formData = new FormData();
		
		$("#dropBox").on("dragenter dragover",function(e){
			e.preventDefault();
		})
		
		$("#dropBox").on("drop",function(e){
			e.preventDefault();
			
			var files = e.originalEvent.dataTransfer.files;
			var file = files[0];//하나만
			console.log(file);
			
			var reader= new FileReader();
			reader.addEventListener("load",function(){
				var divObj = $("<div>").addClass("item");
				var imgObj = $("<img>").attr("src",reader.result);
				divObj.html(imgObj);
				
				$("#dropBox").append(divObj);
			},false);
			//파일을 읽는것 load event 후에 읽기가 가능
			if(file){
				reader.readAsDataURL(file);
			}
			
			if(formData ==null){
				formData = new FormData();
				
			}
			//<input type="file" name="files" value="file">형식으로 전송된다.
			formData.append("files",file);
		})
		
		$("#f1").submit(function(event){
			event.preventDefault();
			console.log("제출 버튼 클릭");
			formData.append("test",$("input[name='test']").val());
			//데이터를 일반적으로 query string으로 변환할 것인지를 결정
			//formdata로 보낼때는 안의 file data를 query string으로 변경되면 안되므로, false 처리 : processData:false
			//contentType:false : type값 변화, formdata를 보낼때 사용해 주어야한다.
			//contentType:true : application/x-www-form-urlencoded
			//contentType:false : multipart/form-data
			$.ajax({
				url:"uploadDrag",
				data:formData,
				type:"post",
				processData:false,
				contentType:false,
				dataType:"json",
				success:function(data){
					console.log(data);
					
					$("#dropBox").empty();
					
					if(data==null){
						alert("전송 실패");
					}else{
						$(data).each(function(i,obj){
							var divObj = $("<div>").addClass("item");
							var imgObj = $("<img>").attr("src","displayFile?filename="+obj);
							divObj.append(imgObj);
							
							var inputObj = $("<input>").val("X").addClass("del").attr("type","button").attr("data-del",obj);
							divObj.append(inputObj);
							
							$("#dropBox").append(divObj);
						})
					}
				}
			})
		})
		
		$(document).on("click",".del",function(){
			
			var filename = $(this).attr("data-del");
			var parent = $(this).parent();
			$.ajax({
				url:"deleteFile",
				data:{filename:filename},
				type:"get",
				dataType:"text",
				success:function(result){
					console.log(result);
					
					alert(result);
					if(result=="delete success"){
						parent.remove();
					}
				}
			})
		})
	</script>
</body>
</html>