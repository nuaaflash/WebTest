<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Sql.Sql" import="Sql.Node" import="java.util.ArrayList" import="java.util.List"  %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="shortcut icon" href="otherResource/theicon.ico" >
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>系统效能评估软件系统</title>
	<link type="text/css" rel="stylesheet" href="layui/css/layui.css" media="all">
	<script src="layui/layui.all.js"></script>
	<style>
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
    p{text-indent:2em} 
    body{
		background:#009688;
	}
	</style>
</head>

<body>
	<div class="layui-layout layui-layout-admin kit-layout-admin">
        <div class="layui-header layui-bg-black">
            <div class="layui-logo">
            	<i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe62c;</i>系统效能评估软件系统
            </div>
            <ul class="layui-nav layui-layout-right kit-nav">
                <li class="layui-nav-item">
                	<a href="login.jsp"> 注销</a>
                </li>
            </ul>
        </div>

        <div class="layui-side layui-bg-cyan kit-side">
            <div class="layui-side-scroll">
                <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
               <!-- <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar> -->
                <ul class="layui-nav layui-nav-tree" lay-filter="test">
				<!-- 侧边导航: <ul class="layui-nav layui-nav-tree layui-nav-side"> -->
					<li class="layui-nav-item layui-bg-cyan">
                        <a href="intro.jsp" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 系统简介</span></a>
                    </li>
                    <li class="layui-nav-item layui-nav-itemed layui-bg-cyan">
                        <a href="javascript:;" ></i><span> 指标体系选择</span></a>
                    	<dl class="layui-nav-child">
                            <dd><a href="showIndexSystem.jsp" kit-target data-options="{url:'',icon:'&#xe658;',title:'威胁度',id:'8'}"><i class="layui-icon">&#xe658;</i><span> 威胁度（目前只有这一个指标体系）</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item layui-bg-cyan">
                      	<a href="javascript:;"></i><span> 数据配置</span></a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="" data-icon="&#xe658;" data-title="数据导入" kit-target data-id='1'><i class="layui-icon">&#xe658;</i><span>数据导入</span></a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="" data-icon="&#xe658;" data-title="归一化" kit-target data-id='2'><i class="layui-icon">&#xe658;</i><span>归一化</span></a>
							</dd>
							<dd>
                                <a href="javascript:;" data-url="" data-icon="&#xe658;" data-title="量化分类" kit-target data-id='3'><i class="layui-icon">&#xe658;</i><span>量化分类</span></a>
							</dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item layui-nav-itemed layui-bg-cyan">
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><span> 模型训练</span></a>
                    	<dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'分类',id:'8'}"><i class="layui-icon">&#xe658;</i><span> 算法</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'回归',id:'9'}"><i class="layui-icon">&#xe658;</i><span> 算法</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'随机过程',id:'10'}"><i class="layui-icon">&#xe658;</i><span> 还是算法</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item layui-bg-cyan">
                        <a href="javascript:;" data-url="" data-name="table" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 模型选择</span></a>
                    </li>
                    <li class="layui-nav-item layui-bg-cyan">
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 输出结果</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="layui-body" id="container" >
            <!-- 内容主体区域  -->
            <div style="padding: 30px;">
            <form action="SqlServlet" method="post">
				<div>
				树的名字:<input type="text" name="Treename" id="Treename" size="10">	
				节点的父节点编号:<input type="text" name="parent" id="parent" size="10" value="0">				
				节点的名字:<input type="text" name="Nodename" id="Nodename" size="10">
				节点的值:<input type="text" name="value" id="value" size="10" value="0">
				</div>
				<div>
				<button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo" onclick="return checkuser()" name="Submits" value="0">新建树</button>
				<button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo" onclick="return checkuser()" name="Submits" value="1">新建节点</button>
				<button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo" onclick="return checkuser()" name="Submits" value="4">改变节点的值</button>
				</div>
			</form>
			<form action="SqlServlet" method="post" name = "showTree">
				<%
					Sql sql = Sql.getInstance();
					ArrayList <String> trees = sql.getTreeS(); 
					int num = 0;
					if(trees != null) {
						num = trees.size();
					}
				%>
				<tbody>
<%			
					for(int i=0;i<num;i++) {
%>
						<tr>
							<td>第<%= i+1%>个指标体系名字：</td>
							<td><%=trees.get(i).toString() %></td>
							<input type="hidden" name = "Deletename" value="<%=trees.get(i).toString() %>">
							<button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo" onclick="return checkuser()" name="Submits" value="3">删除树</button>
							</br>
						</tr>
<%
					}
%>
				</tbody>
			</form>
			</div>
        </div>

        <div class="layui-footer layui-bg-black">
            <!-- 底部固定区域 -->
           	<font color="#009688" size="3px">样式来源：<a href="http://www.layui.com/">layui.com</a></font>
        </div>
    </div>
    
    <script src="layui/layui.js" charset="utf-8"></script>
	<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
	<script>
	layui.use('element', function(){
	  var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
	  
	  //监听导航点击
	  element.on('nav(demo)', function(elem){
	    //console.log(elem)
	    layer.msg(elem.text());
	  });
	});
	</script>
</body>
</html>