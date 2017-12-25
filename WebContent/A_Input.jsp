<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Sql.*" import="java.util.ArrayList" import="java.util.List"  %> 
<%@page import="org.lxh.smart.SmartUpload" import="Dataset.*" import="Algorithm.*" import="java.util.ArrayList" import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Input | 系统效能评估软件系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<link rel="shortcut icon" href="otherResource/theicon.ico" >
    
	<!-- CSS -->
	<link href="white/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="white/css/flexslider.css" rel="stylesheet" type="text/css" />
	<link href="white/css/prettyPhoto.css" rel="stylesheet" type="text/css" />
	<link href="white/css/animate.css" rel="stylesheet" type="text/css" media="all" />
    <link href="white/css/owl.carousel.css" rel="stylesheet">
	<link href="white/css/style.css" rel="stylesheet" type="text/css" />
    
	<!-- FONTS -->
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500italic,700,500,700italic,900,900italic' rel='stylesheet' type='text/css'>
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">	
    
	<!-- SCRIPTS -->
	<!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <!--[if IE]><html class="ie" lang="en"> <![endif]-->
	
	<script src="white/js/jquery.min.js" type="text/javascript"></script>
	<script src="white/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="white/js/jquery.prettyPhoto.js" type="text/javascript"></script>
	<script src="white/js/jquery.nicescroll.min.js" type="text/javascript"></script>
	<script src="white/js/superfish.min.js" type="text/javascript"></script>
	<script src="white/js/jquery.flexslider-min.js" type="text/javascript"></script>
	<script src="white/js/owl.carousel.js" type="text/javascript"></script>
	<script src="white/js/animate.js" type="text/javascript"></script>
	<script src="white/js/jquery.BlackAndWhite.js" type="text/javascript"></script>
	<script src="white/js/myscript.js" type="text/javascript"></script>
</head>
<body>

<div class="preloader_hide">

	<!-- PAGE -->
	<div id="page" class="single_page">
	
		<!-- HEADER -->
		<header>
			
			<!-- MENU BLOCK -->
			<div class="menu_block">
			
				<!-- CONTAINER -->
				<div class="container clearfix">
					
					<!-- LOGO -->
					<div class="logo pull-left">
						<a href="MainPage.jsp" ><span class="b1">系统</span><span class="b2">效能</span><span class="b3">评估</span><span class="b4">软件</span><span class="b5">系统</span></a>
					</div><!-- //LOGO -->
					
					<!-- MENU -->
					<div class="pull-right">
						<nav class="navmenu center">
							<ul>
								<li class="scroll_btn"><a href="MainPage.jsp#about" >介绍</a></li>
								<li class="scroll_btn"><a href="MainPage.jsp#projects" >工作</a></li>
								<li class="scroll_btn"><a href="MainPage.jsp#group" >小组</a></li>
								<li class="scroll_btn"><a href="MainPage.jsp#team" >相关</a></li>
								<li class="scroll_btn"><a href="#contacts" >其他</a></li>
								<li class="scroll_btn last"><a href="login.jsp" >注销</a></li>
							</ul>
						</nav>
					</div><!-- //MENU -->
				</div><!-- //MENU BLOCK -->
			</div><!-- //CONTAINER -->
		</header><!-- //HEADER -->

		<!-- BREADCRUMBS -->
		<section class="breadcrumbs_block clearfix parallax">
			<div class="container center">
				<h2><b>Input</b> 输入数据</h2>
				<p>The file you just uploaded is appropriate for this indication system.</p>
			</div>
		</section><!-- //BREADCRUMBS -->
		
		
		<!-- BLOG -->
		<section id="blog">
			
			<!-- CONTAINER -->
			<div class="container">
				
				<!-- ROW -->
				<div class="row">
				<form action="AlgorithmServlet" method="get" enctype="multipart/form-data">
					<!-- BLOG BLOCK -->
					<div class="blog_block col-lg-9 col-md-9 padbot50">
					
<%
						int submitchoice=(Integer)request.getAttribute("submitchoice_from_SelectServlet");
						System.out.println("submitchoice:"+submitchoice);
						int indx=(Integer)request.getAttribute("the_system_index");	//指标体系编号
						String indication_name=null;
						Sql sql = Sql.getInstance();
						ArrayList<String> al = sql.getTreeS();
						indication_name=al.get(indx);
						ArrayList<String> leaves = sql.getLeaves(indication_name);
						int num = leaves.size();
						String s;
						System.out.println("indication_name:"+indication_name+" indx:"+indx+" num:"+num);
%>
						
						<!-- SINGLE BLOG POST -->
						<div class="single_blog_post clearfix" data-animated="fadeInUp">
						<div class="single_blog_post_title">
<%
							if(submitchoice==1) {
%>
							径向基函数神经网络
<%								
							} else if(submitchoice==2) {
%>
							支持向量回归
<%								
							} else if(submitchoice==3) {
%>
							AHP层次分析法
<%								
							}
%>
						</div>	
							<div class="single_blog_post_content">
								
								
								<table border="2" cellspacing="5" cellpadding="5" width="400">
									<thead>
										<tr>
											<th>序号</th>
											<th>名称</th>
											<th>请输入</th>
										</tr>
									</thead>
									<tbody>
<%
									for(int i=0;i<num;i++) {
%>
										<tr>
											<td><%= i%></td>
											<td><%=leaves.get(i).toString() %></td>
											<td>
												<input type="range" id="range<%= i%>" name="point_value<%= i%>" min="1" max="100" step="1" value="1" oninput="change()">
												<input type="hidden" id="leavesname<%= i%>" name="leavesname<%= i%>" value="<%=leaves.get(i).toString() %>">
												<input id="show<%= i%>" type="number" value="1" readonly>
											</td>
										</tr>
<%
									}
%>
									</tbody>	
								</table>
								
							</div>
							
						</div><!-- //SINGLE BLOG POST -->
						
					</div><!-- //BLOG BLOCK -->
					
					
					<!-- SIDEBAR -->
					<div class="sidebar col-lg-3 col-md-3 padbot50">
						
						<!-- POPULAR TAGS WIDGET -->
						<div class="sidepanel widget_tags">
							<h3><b>相关的参数</b> Tags</h3>
<%
							if(submitchoice==1) {
%>
							输入RBFNN的隐藏层节点个数<br><input type="text" name="hidden_nodes_num"><br>
							输入算法的<abbr title="重复反馈过程">迭代</abbr>次数<br><input type="text" name="count" placeholder="推荐输入5000~100000"><br>
							输入梯度下降法的<abbr title="每一次求偏导结果作加前乘的系数">步长</abbr><br><input type="text" name="step_length" placeholder="推荐输入0.00001~0.001"><br>
<%								
							} else if(submitchoice==2) {
%>
							cache大小:<br><input type= "text" name="cache" value="100"><br>
							终止判据eps:<br><input type="text" name="eps" value="0.001"><br>
							损失函数C:<br><input type="text" name="C" value="1.9"><br>
<%								
							} else if(submitchoice==3) {
%>
							.
<%								
							}
%>							
							<input type="hidden" name="indication_name" value="<%=indication_name%>"/> <!-- 指标体系名称 -->
							<input type="hidden" name="submitchoice" value="<%=submitchoice%>"/> <!-- 算法编号 -->
							<input type ="hidden" name="num" value="<%=num %>"> <!-- 指标体系的叶子个数 -->
            				<button name="Submits" value="1">开始计算</button>
						</div><!-- POPULAR TAGS WIDGET -->
						
						<hr>
						
						<!-- TEXT WIDGET -->
					<!--	<div class="sidepanel widget_text">
							<h3><b>About</b> Blog</h3>
							<p>I must admit this particular defense set me on edge a little bit, for two reasons. The first is that she’s being held to a completely different standard than male politicians are held to.</p>-->
						</div><!-- //TEXT WIDGET -->
					</div><!-- //SIDEBAR -->
				</form>
				</div><!-- //ROW -->
			</div><!-- //CONTAINER -->
		</section><!-- //BLOG -->
	</div><!-- //PAGE -->

	
	<!-- CONTACTS -->
	<section id="contacts">
	</section><!-- //CONTACTS -->
	
	<!-- FOOTER -->
	<footer>
			
		<!-- CONTAINER -->
		<div class="container">
			
			<!-- ROW -->
			<div class="row" data-appear-top-offset="-200" data-animated="fadeInUp">
				
				<div class="col-lg-4 col-md-4 col-sm-6 padbot30">
					<h4><b>Special</b> thanks</h4>
					<div class="recent_posts_small clearfix">
						<div class="post_item_img_small">
							<img src="white/images/footer/a.jpg" alt="" />
						</div>
						<div class="post_item_content_small">
							<a class="title" href="http://www.layui.com/" >Layui</a>
							<ul class="post_item_inf_small">
								<li>结果展示的进度条</li>
							</ul>
						</div>
					</div>
					<div class="recent_posts_small clearfix">
						<div class="post_item_img_small">
							<img src="white/images/footer/b.jpg" alt="" />
						</div>
						<div class="post_item_content_small">
							<a class="title" href="http://www.treejs.cn/v3/main.php#_zTreeInfo" >zTree</a>
							<ul class="post_item_inf_small">
								<li>以树状结构形象显示指标体系</li>
							</ul>
						</div>
					</div>
					<div class="recent_posts_small clearfix">
						<div class="post_item_img_small">
							<img src="white/images/footer/c.jpg" alt="" />
						</div>
						<div class="post_item_content_small">
							<a class="title" href="https://w3layouts.com/" >w3layouts</a>
							<ul class="post_item_inf_small">
								<li>精美的网站模板</li>
							</ul>
						</div>
					</div>
				</div>
				
				<div class="col-lg-4 col-md-4 col-sm-6 padbot30 foot_about_block">
					<h4><b>About</b> us</h4>
					<p>We value people over profits, quality over quantity, and keeping it real. As such, we deliver an unmatched working relationship with our clients.</p>
					<p>Life is half spent before we know what it is.</p>
				</div>
				
				<div class="respond_clear"></div>
				
			</div><!-- //ROW -->
		</div><!-- //CONTAINER -->
	</footer><!-- //FOOTER -->

</div>

<script>
      function change(){ 
    	  var num;
    	  var location;
<%
	for(int i=0;i<num;i++) {
%>
      num=document.getElementById("range<%= i%>"); 
      location=document.getElementById("show<%= i%>");
      location.value=num.value; 
<%
	}
%>
  } 
</script>
</body>
</html>