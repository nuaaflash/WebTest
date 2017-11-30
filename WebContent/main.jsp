<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="shortcut icon" href="otherResource/theicon.ico" >
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>武器装备评估系统</title>
	<link type="text/css" rel="stylesheet" href="layui/css/layui.css" media="all">
	<script src="layui/layui.all.js"></script>
	<style>
	body{margin: 10px;}
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
            	<i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe62c;</i>武器装备评估系统
            </div>
            
         <!--     <ul class="layui-nav layui-layout-left kit-nav">
                <li class="layui-nav-item"><a href="javascript:;">控制台</a></li>
                <li class="layui-nav-item"><a href="javascript:;">商品管理</a></li>
                <li class="layui-nav-item"><a href="javascript:;" id="pay"><i class="fa fa-gratipay" aria-hidden="true"></i> 捐赠我</a></li>
                <li class="layui-nav-item">
                    <a href="javascript:;">其它系统</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">邮件管理</a></dd>
                        <dd><a href="javascript:;">消息管理</a></dd>
                        <dd><a href="javascript:;">授权管理</a></dd>
                    </dl>
                </li>
            </ul>  -->
        </div>

        <div class="layui-side layui-bg-cyan kit-side">
            <div class="layui-side-scroll">
                <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
               <!-- <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar> -->
                <ul class="layui-nav layui-nav-tree" lay-filter="test">
				<!-- 侧边导航: <ul class="layui-nav layui-nav-tree layui-nav-side"> -->
                    <li class="layui-nav-item layui-nav-itemed layui-bg-cyan">
                        <a href="javascript:;" ></i><span> 指标体系选择</span></a>
                    	<dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'手动',id:'8'}"><i class="layui-icon">&#xe658;</i><span> 飞机</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'PCA',id:'9'}"><i class="layui-icon">&#xe658;</i><span> 坦克</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'ICA',id:'10'}"><i class="layui-icon">&#xe658;</i><span> 潜艇</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item layui-nav-itemed layui-bg-cyan">
                      	<a class="" href="javascript:;"></i><span> 数据配置</span></a>
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
                    <li class="layui-nav-item layui-bg-cyan">
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 模型训练</span></a>
                    	<dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'分类',id:'8'}"><i class="layui-icon">&#xe658;</i><span>分类</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'回归',id:'9'}"><i class="layui-icon">&#xe658;</i><span> 回归</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'随机过程',id:'10'}"><i class="layui-icon">&#xe658;</i><span> 随机过程</span></a></dd>
                        </dl>
                    </li>
                     <li class="layui-nav-item layui-bg-cyan">
                        <a href="javascript:;" data-url="" data-name="table" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 模型选择</span></a>
                    </li>
                    <li class="layui-nav-item layui-bg-cyan" layui-bg-cyan>
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 输出结果</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="layui-body" id="container" >
            <!-- 内容主体区域 -->
            <div style="padding: 20px;">
            	<p><font size="5px">系统效能评估是针对系统集成综合一体化的技术特征，面向系统不同的任务，基于不同系统任务的整个过程，以完成整个任务成功为首要目标，结合任务各个阶段的特点，分析系统的组成和功能，结合任务完成过程和系统组成的具体实际，构建出系统效能评估分析软件，用于复杂系统的效能评估。</p>
				<p>系统支持武器装备结构体系、系统指标体系、系统能力及行动的建模；；提供AHP层次分析法、ADC分析法、SEA法、指数法、人工智能（BP、SVM等）探索性仿真评估法等多维方法对系统效能进行整体评估，提供可视化建模、分析工具，能够自动生成效能评估报告，并支持专业评估模块的灵活扩展。</p>
				<p>可应用于作战计划模拟评估、联合作战试验研究、武器装备体系论证、武器装备型号论证、武器装备型号论证及作战效能评估等研究领域。</p>
			</div>
        </div>

        <div class="layui-footer layui-bg-black">
            <!-- 底部固定区域 -->
           	 <font color="#009688" size="3px">这里什么有没有哦~
        </div>
    </div>
    <script src="../layui/layui.js"></script>
<script>
//一般直接写在一个js文件中
layui.use(['layer', 'form'], function(){
  var layer = layui.layer
  ,form = layui.form;
  
  layer.msg('Hello ZDP!');
});
</script> 
</body>
</html>