<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <title>武器装备评估系统</title>
  	<link type="text/css" rel="stylesheet" href="layui/css/layui.css" media="all">
  	<script src="layui/layui.all.js"></script>
  <style>
    body{
	    	width:400px; 
			height:auto; 
			margin:0 auto; 
			padding-top:23%; 
			background:url(layui/css/timg.jpg) #f8f6e9;
		}
	form{
			background: rgba(47,64,86,0.5); /*背景透明*/
		    border: 2px solid #0D0D0D; /*边框半透明*/
		    border-radius: 8px;		/*圆角*/
		    box-shadow: inset 0 0 4px rgba(255,255,255,0.5),0 0 4px rgba(255,255,255,0.5);  /*内外渐变阴影*/
	}
	label{
			background: rgba(47,64,86,0.7); /*背景透明*/
	}
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
  </style>
</head>
<body>
	<h1 style="text-align:center"><font color="#FFB80F">系统效能评估软件系统</h1>
	<form class="layui-form" action="login.jsp" >
			</br>
		  <div class="layui-form-item">
		    <p style="text-align:center"><font color="#FFB800">登录失败</font></p>
		  </div>
		  	  
		  <div class="layui-form-item">
		  	<div class="layui-input-block">
			   <button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo">返回</button>
			</div>
		  </div>
		  
	</form>
	
</body>
</html>