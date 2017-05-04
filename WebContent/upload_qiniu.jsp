<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/js/webuploader/webuploader.css" />
</head>
<body>
	<div id="picker">选择文件</div>
	<button id="startBtn">开始上传</button>
	<ul id="fileList"></ul>


	<script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/webuploader/webuploader.js"></script>
	
	<script type="text/template" id="template">
	<div class="progress">
  		<div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" id="{{id}}" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
    		<span class="sr-only"></span>
  		</div>
	</div>
	</script>
	
	<script>
		$(function() {
			var uploader = WebUploader.create({
				swf : "/static/js/webuploader/Uploader.swf",
				server : "http://up-z1.qiniu.com/",
				fileVal : "file",
				formData : {"token":"${token}","x:id":"${id}"},
				pick : "#picker",
				/* auto:true */
			});
			

			//将文件加入队列
			uploader.on("fileQueued",function(file){
				var html = "<li id='"+file.id+"'>"+file.name+"</li>";
				$("#fileList").append($(html));
			});
			
			
			//上传进度条
			uploader.on("uploadProgress", function(file,percentage) {
				var num = parseInt(percentage*100);
				var $bar = $("#"+file.id).find("#bar_"+file.id);

				if (!$bar[0]) {
					var template = $("#template").html();
					template = template.replace("{{id}}", "bar_"+file.id);
					$("#"+file.id).append($(template));
				} else {
					$bar.css("width", num+"%");
				}
			});
			

			//上传成功
			uploader.on("uploadSuccess", function(file, data) {
				var img = $("#result").find("img");
				if (img[0]) {
					img.remove();
				}
				
				var url = "http://ohwtzb4xv.bkt.clouddn.com/"+data.key+"?imageView2/1/w/150/h/150";
								
				$("<img>").attr("src", url).addClass("img-circle").appendTo("#result");
				
				$("#"+file.id).find("#bar_"+file.id).parent().remove();
			});

			//上传失败
			uploader.on("uploadError", function(file) {
				alert("上传失败");
			});
			
			//开始上传
			$("#startBtn").click(function(){
				uploader.upload();
			});
		});
	</script>

</body>
</html>