package com.zgzzh.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dataset.ReadExcelUtils;
import Algorithm.Svmr;
import Sql.Node;
import Sql.Sql;

/**
 * Servlet implementation class SelectServlet
 */

public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String path="A_Input.jsp";
		int submitchoice = 5;	//算法编号 1=RBF 2=SVR 3=AHP
		submitchoice = Integer.parseInt(request.getParameter("submitchoice"));
		
		String indication_name=request.getParameter("choose_target");
		int indx = Integer.parseInt(indication_name);	//指标体系编号
		
		// 连接数据库 并将操作的树（指标体系）设置为前一页面选择的树 并将数据库各节点初始化为0 避免对显示数据产生干扰
		Sql sql=Sql.getInstance();
		ArrayList<String> al = sql.getTreeS();
		indication_name=al.get(indx);
		System.out.println("indication_name:"+indication_name);
		sql.SetTreeName(indication_name);
		sql.InitNodeValue(0);
		HashSet<String> leaves_set = sql.getNameSetOfLeaves(indication_name);
		
		ReadExcelUtils reader = ReadExcelUtils.getInstance();
		reader.setFilepath("E:\\theData.xlsx");
		ArrayList<String> title = new ArrayList<String>();
		HashSet<Object> excel_title = new HashSet<Object>();	//excel文件的标题
		try {
			title = reader.readExcelTitle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String str:title)
			excel_title.add(str);
		// 读文件并验证部分
		boolean data_is_correct = true;
		data_is_correct = data_is_correct && excel_title.containsAll(leaves_set);
		System.out.println("leaves_set: "+leaves_set.toString());
		System.out.println("excel_title: "+excel_title.toString());
		
		/*if(submitchoice != 3) {// 验证REF,SVR部分
			ArrayList<String>title=new ArrayList<String>();			
			HashSet<String>excelname=new HashSet<String>();//文件内容
			HashSet<String>leavesname=new HashSet<String>();//叶子节点
		
			boolean datatest;
			try {
				title=reader.readExcelTitle();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			leavesname=sql.getNameSetOfLeaves(indication_name);			
			for(int i=0;i<title.size(); i++){
				excelname.add(title.get(i));	
			}
			datatest=excelname.contains(leavesname);
			datatest=leavesname.contains(excelname);	
			if(!datatest){
				System.out.println("Data wrong");	
			}
			else
				System.out.println("Data well done");
		}
		if(submitchoice == 3) {// 验证AHP部分
			ArrayList<String>title=new ArrayList<String>();
			ArrayList<Object>node=new ArrayList<Object>();
			HashSet<String>excelname=new HashSet<String>();//文件内容
		
			HashSet<String>treename=new HashSet<String>();//非叶子节点
			boolean datatest;
			try {
				node=reader.readExcel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			treename=sql.getNameSetOfNoleaves(indication_name);
			
			for(int i=0;i<node.size(); i++){
				if(i%2==0)
					title.add((String) node.get(i));//不知道对不对，可能是i%2==1
			}
			for(int i=0;i<title.size();i++){
				excelname.add(title.get(i));
			}
			datatest=excelname.contains(treename);
			datatest=treename.contains(excelname);	
			if(!datatest){
				System.out.println("Data wrong");
				
			}
			else
				System.out.println("Data well done");
			
		}*/
		if(data_is_correct) {
			path="A_Input.jsp";
			request.setAttribute("submitchoice_from_Servlet", submitchoice);
			request.setAttribute("the_system_index", indx);
		}
		else {
			switch (submitchoice) {
			case 1:	//返回RBF
				path="A_RBF_Select.jsp";
				break;
			case 2: //返回SVR
				path="A_SVR_Select.jsp";
				break;
			default: //非法进入，返回主页
				path="MainPage.jsp";
				break;
			}
		}
		request.getRequestDispatcher(path).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
