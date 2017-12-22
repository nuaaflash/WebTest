<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Sql.Sql" import="Sql.Node" import="java.util.ArrayList" import="java.util.List"  %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<meta charset="utf-8">
	<title>指标体系 | 系统效能评估软件系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<link rel="shortcut icon" href="otherResource/theicon.ico" >
	
	<link rel="stylesheet" href="zTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
	<!-- SCRIPTS -->
	<!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <!--[if IE]><html class="ie" lang="en"> <![endif]-->
	<script src="white/js/jquery.min.js" type="text/javascript"></script>
	<script src="white/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="white/js/jquery.nicescroll.min.js" type="text/javascript"></script>
	<script src="white/js/superfish.min.js" type="text/javascript"></script>
	<script src="white/js/jquery.flexslider-min.js" type="text/javascript"></script>
	<script src="white/js/owl.carousel.js" type="text/javascript"></script>
	<script src="white/js/animate.js" type="text/javascript"></script>
	<script src="white/js/myscript.js" type="text/javascript"></script>
	<!-- //SCRIPTS -->
	
	<script type="text/javascript" src="zTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="zTree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="zTree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="zTree/js/jquery.ztree.exedit.js"></script>
	<SCRIPT type="text/javascript">

	<!--

	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: showRemoveBtn,
			showRenameBtn: showRenameBtn
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeEditName: beforeEditName,
			beforeRemove: beforeRemove,
			beforeRename: beforeRename,
			onRemove: onRemove,
			onRename: onRename
		}
	};

	var log, className = "dark";
<%
	Sql sql = Sql.getInstance();
	ArrayList <String> trees = sql.getTreeS();
	ArrayList <Node> sqlnode = null;
	int num = 0; 
	int nodenum = 0;
	if(trees != null) {
		sqlnode = sql.getIndexNodetoShow();
		num = trees.size();
	}
	if(sqlnode != null){		
		nodenum = sqlnode.size();
	}
	else{
		sql.Addnode("root", 0, 0);	// 判断是否有根节点来 自动创建根节点
	}
%>
	var zNodes =[];
<%		
	for(int i = 0;i < nodenum;i ++){
%>			zNodes.push({id:<%=sqlnode.get(i).nodeId%>, pId:<%=sqlnode.get(i).parentId%>, name:"<%=sqlnode.get(i).nodeName%>", open:true });
<%		}
%>			

	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		var deletes = confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		if(deletes){
			var form1 = document.createElement("form"); 
			form1.id = "form1"; 
			form1.name = "form1"; 
			// 添加到 body 中 
			document.body.appendChild(form1); 
			// 创建一个输入 
			
			var input1 = document.createElement("input"); 
			input1.type = "text"; 
			input1.name = "deleteID"; 
			input1.value = treeNode.id; 
			// 将该输入框插入到 form 中 
			form1.appendChild(input1); 
			
			var input2 = document.createElement("input"); 
			input2.type = "text"; 
			input2.name = "Submits"; 
			input2.value = "6"; 						// 删除节点
			// 将该输入框插入到 form 中 
			form1.appendChild(input2); 
			// form 的提交方式 
			form1.method = "POST"; 
			// form 提交路径 
			form1.action="SqlServlet";
			// 对该 form 执行提交 
			form1.submit(); 
			// 删除该 form 
			document.body.removeChild(form1); 
		}
		return deletes;
	}
	function onRemove(e, treeId, treeNode) {
		showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	}
	
	function beforeEditName(treeId, treeNode) {
		oldname = treeNode.name;
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		setTimeout(function() {
			if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
				setTimeout(function() {
					zTree.editName(treeNode);
				}, 0);
			}
		}, 0);
		return false;
	}
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "":"dark");
		showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		if (newName.length == 0) {
			setTimeout(function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.cancelEditName();
				alert("节点名称不能为空.");
			}, 0);
			return false;
		}
		else{ // 提交表单 写入数据库
			var form1 = document.createElement("form"); 
			form1.id = "form1"; 
			form1.name = "form1"; 
			// 添加到 body 中 
			document.body.appendChild(form1); 
			// 创建一个输入 
			
			var input1 = document.createElement("input"); 
			input1.type = "text"; 
			input1.name = "newname"; 
			input1.value = newName; 
			// 将该输入框插入到 form 中 
			form1.appendChild(input1); 
			
			var input2 = document.createElement("input"); 
			input2.type = "text"; 
			input2.name = "name"; 
			input2.value = treeNode.name; 
			// 将该输入框插入到 form 中 
			form1.appendChild(input2); 
			
			var input3 = document.createElement("input"); 
			input3.type = "text"; 
			input3.name = "Submits"; 
			input3.value = "7"; 						// 重命名节点
			// 将该输入框插入到 form 中 
			form1.appendChild(input3); 
			// form 的提交方式 
			form1.method = "POST"; 
			// form 提交路径 
			form1.action="SqlServlet";
			// 对该 form 执行提交 
			form1.submit(); 
			// 删除该 form 
			document.body.removeChild(form1); 
		}
		return true;
	}
	function onRename(e, treeId, treeNode, isCancel) {
		showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
	}

	function onRemove(e, treeId, treeNode) {
		showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	}
	
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	
	function showRemoveBtn(treeId, treeNode) {
		return !treeNode.isFirstNode;
	}
	function showRenameBtn(treeId, treeNode) {
		return !treeNode.isLastNode;
	}
	
	
	function setEdit() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		remove = $("#remove").attr("checked"),
		rename = $("#rename").attr("checked"),
		removeTitle = $.trim($("#removeTitle").get(0).value),
		renameTitle = $.trim($("#renameTitle").get(0).value);
		zTree.setting.edit.showRemoveBtn = remove;
		zTree.setting.edit.showRenameBtn = rename;
		zTree.setting.edit.removeTitle = removeTitle;
		zTree.setting.edit.renameTitle = renameTitle;
		showCode(['setting.edit.showRemoveBtn = ' + remove, 'setting.edit.showRenameBtn = ' + rename,
			'setting.edit.removeTitle = "' + removeTitle +'"', 'setting.edit.renameTitle = "' + renameTitle + '"']);
	}
	function showCode(str) {
		var code = $("#code");
		code.empty();
		for (var i=0, l=str.length; i<l; i++) {
			code.append("<li>"+str[i]+"</li>");
		}
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		setEdit();
		$("#remove").bind("change", setEdit);
		$("#rename").bind("change", setEdit);
		$("#removeTitle").bind("propertychange", setEdit)
		.bind("input", setEdit);
		$("#renameTitle").bind("propertychange", setEdit)
		.bind("input", setEdit);
	});
	
	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		isCopy = $("#copy").attr("checked"),
		isMove = $("#move").attr("checked"),
		prev = $("#prev").attr("checked"),
		inner = $("#inner").attr("checked"),
		next = $("#next").attr("checked");
		zTree.setting.edit.drag.isCopy = isCopy;
		zTree.setting.edit.drag.isMove = isMove;
		showCode(1, ['setting.edit.drag.isCopy = ' + isCopy, 'setting.edit.drag.isMove = ' + isMove]);

		zTree.setting.edit.drag.prev = prev;
		zTree.setting.edit.drag.inner = inner;
		zTree.setting.edit.drag.next = next;
		showCode(2, ['setting.edit.drag.prev = ' + prev, 'setting.edit.drag.inner = ' + inner, 'setting.edit.drag.next = ' + next]);
	}
	function showCode(id, str) {
		var code = $("#code" + id);
		code.empty();
		for (var i=0, l=str.length; i<l; i++) {
			code.append("<li>"+str[i]+"</li>");
		}
	}
<%
	int nodeid = sql.getNumofnodes() + 1;
%>
	var newCount = <%=nodeid%>;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.addNodes(treeNode, {id:newCount, pId:treeNode.id, name:"new node" + newCount});

			var form1 = document.createElement("form"); 
			form1.id = "form1"; 
			form1.name = "form1"; 
			// 添加到 body 中 
			document.body.appendChild(form1); 
			// 创建一个输入 
			
			var input1 = document.createElement("input"); 
			input1.type = "text"; 
			input1.name = "parent"; 
			input1.value = treeNode.id; 
			// 将该输入框插入到 form 中 
			form1.appendChild(input1); 
			
			var input2 = document.createElement("input"); 
			input2.type = "text"; 
			input2.name = "Nodename"; 
			input2.value = "new node"+newCount; 
			// 将该输入框插入到 form 中 
			form1.appendChild(input2); 
			
			var input3 = document.createElement("input"); 
			input3.type = "text"; 
			input3.name = "Submits"; 
			input3.value = "1"; 
			// 将该输入框插入到 form 中 
			form1.appendChild(input3); 
			// form 的提交方式 
			form1.method = "POST"; 
			// form 提交路径 
			form1.action="SqlServlet";
			// 对该 form 执行提交 
			form1.submit(); 
			// 删除该 form 
			document.body.removeChild(form1); 
			return false;
		});
	};
	
	function remove(e) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		var callbackFlag = $("#callbackTrigger").attr("checked");
		zTree.removeNode(treeNode, callbackFlag);
		
	};

	function showLog(str) {
		if (!log) log = $("#log");
		log.append("<li class='"+className+"'>"+str+"</li>");
		if(log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		setCheck();
		$("#copy").bind("change", setCheck);
		$("#move").bind("change", setCheck);
		$("#prev").bind("change", setCheck);
		$("#inner").bind("change", setCheck);
		$("#next").bind("change", setCheck);
	});
	
	
	//-->
	</SCRIPT>
	
	<!-- CSS -->
	<link href="white/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="white/css/flexslider.css" rel="stylesheet" type="text/css" />
	<link href="white/css/animate.css" rel="stylesheet" type="text/css" media="all" />
    <link href="white/css/owl.carousel.css" rel="stylesheet">
	<link href="white/css/style.css" rel="stylesheet" type="text/css" />
	<link href="white/css/colors/" rel="stylesheet" type="text/css" id="colors" />
    
	<!-- FONTS -->
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500italic,700,500,700italic,900,900italic' rel='stylesheet' type='text/css'>
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">	
    
	
	
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
						<a href="index.html" ><span class="b1">系统</span><span class="b2">效能</span><span class="b3">评估</span><span class="b4">软件</span><span class="b5">系统</span></a>
					</div><!-- //LOGO -->
					
					<!-- MENU -->
					<div class="pull-right">
						<nav class="navmenu center">
							<ul>
								<li class="scroll_btn"><a href="MainPage.jsp#about" >介绍</a></li>
								<li class="scroll_btn"><a href="MainPage.jsp#projects" >工作</a></li>
								<li class="scroll_btn"><a href="MainPage.jsp#group" >小组</a></li>
								<li class="scroll_btn"><a href="MainPage.jsp#related_links" >相关</a></li>
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
				<h2>指标体系管理</h2>
				<p>Indication System Management</p>
			</div>
		</section><!-- //BREADCRUMBS -->
		
		
		<!-- BLOG -->
		<section id="blog">
			
			<!-- CONTAINER -->
			<div class="container">
				<h2>管理指标体系</h2>
				
				<!-- ROW -->
				<div class="row">
				
					<!-- BLOG BLOCK -->
					<div class="blog_block col-lg-9 col-md-9 padbot50">
						
						<!-- TREE SHEET -->
						<div class="single_blog_post clearfix" data-animated="fadeInUp">
						
			            
							<div class="content_wrap" id="container">
								<div class="zTreeDemoBackground left">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
								<div class="right" style="display: none" onMouseout="hidden();">
									<ul class="info">
										<li><br><br><br><br>
											<form id="form1" name="form1" method="post">
												<input type="checkbox" id="remove" class="checkbox first"
													checked /> <input type="checkbox" id="rename"
													class="checkbox " checked /> <input type="text"
													id="removeTitle" value="remove" /><br> <input type="text" id="renameTitle" value="rename" />
											</form>
										</li>
									</ul>
								</div>
							</div>
						</div><!-- //TREE SHEET -->
						
					</div><!-- //BLOG BLOCK -->
					
					
					<!-- SIDEBAR -->
					<div class="sidebar col-lg-3 col-md-3 padbot50">
						
						<!-- META WIDGET -->
						<div class="sidepanel widget_meta">
							<form action="SqlServlet" method="post" name = "showTree">
							<table bgcolor="#DEDEDE" border="2" cellspacing="5" cellpadding="5" width="400">
								<thead>
									<tr>
										<th>序号</th>
										<th>指标体系</th>
										<th>.</th>
									</tr>
								</thead>
								<tbody>
			<%
								for(int i=0;i<num;i++) {
			%>
									<tr>
										<td><%= i%></td>
										<td><%=trees.get(i).toString() %></td>
										<td><input type="radio" name = "nameofTree" value="<%=trees.get(i).toString() %>"></td>
									</tr>
			<%
								}
			%>
									<tr>
										<td>新建指标体系</td>
										<td><input type="text" name="Treename" id="Treename" size="20" placeholder="请输入新指标体系的名字">	</td>
									</tr>
								</tbody>
								<thead>
								<tr>
									<th><button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo"  name="Submits" value="3">删除</button></th>
									<th><button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo"  name="Submits" value="5">显示</button></th>
									<th><button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo"  name="Submits" value="0">新建</button></th>
								</tr>
								</thead>
							</table>	
						</form>	
						
						</div><!-- //META WIDGET -->
						
						<hr>
						
						<!-- TEXT WIDGET -->
						<div class="sidepanel widget_text">
							<h3><b>关于指标体系</b></h3>
							<p>指标体系指的是若干个相互联系的统计指标所组成的有机体，它的的建立是进行预测或评价研究的前提和基础，它是将抽象的研究对象按照其本质属性和特征的某一方面的标识分解成为具有行为化、可操作化的结构，并对指标体系中每一构成元素（即指标）赋予相应权重的过程。</p>
						</div><!-- //TEXT WIDGET -->
					</div><!-- //SIDEBAR -->
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
							<img src="white/images/blog/1.jpg" alt="" />
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
							<img src="white/images/blog/2.jpg" alt="" />
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
							<img src="white/images/blog/3.jpg" alt="" />
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
</body>
</html>