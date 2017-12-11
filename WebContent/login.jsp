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
   <script type="text/javascript">
      function checkuser() {
         if($('uname' == "admin") && $('pwd') == "admin") {
           return true;
         }else {
            return false;
         }
      }

      function $(id) {
        return document.getElementById(id).value;
      }
     </script>
 </head>
 
<body>
	<h1 style="text-align:center"><font color="#FFB80F">系统效能评估软件系统</h1>
	<form class="layui-form" action="SqlServlet" >
			</br>
			<div class="layui-form-item">
		    <label class="layui-form-label"><font color="#FFB800">EVA机型</font> </label>
		    <div class="layui-input-inline">
		      <input type="text" name="DBS" id="DBS" required  lay-verify="required" placeholder="请输入数据库名" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><font color="#FFB800">驾驶员</font> </label>
		    <div class="layui-input-inline">
		      <input type="text" name="user" id="user" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><font color="#FFB800">口令</font></label>
		    <div class="layui-input-inline">
		      <input type="password" name="passwd" id="passwd" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  </script>
		  <div class="layui-form-item">
		  	<div class="layui-input-block">
			   <button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo" onclick="return checkuser()" name="Submits" value="2">作战</button>
			   <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		  </div>
		  
	</form>
	
</body>
</html>