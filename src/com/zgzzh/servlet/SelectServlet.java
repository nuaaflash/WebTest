package com.zgzzh.servlet;

import java.io.IOException;
import java.util.ArrayList;

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
		ArrayList<String> leaves = sql.getLeaves(indication_name);
		

		// 读文件并验证部分
		boolean data_is_correct = true;
		ReadExcelUtils reader = ReadExcelUtils.getInstance();
		reader.setFilepath("E:\\theData.xlsx");
		ArrayList<Object> result = null;
		try {
			result = reader.readExcel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(submitchoice != 3) {// 验证部分
			ArrayList<String>title=new ArrayList<String>();
			ArrayList<String>node=new ArrayList<String>();
			try {
				title=reader.readExcelTitle();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			node=sql.getNodes(indication_name);
		/*	for(int i=0;i<title.size();i++){
				if(title.get(i)!=node.get(i)) {
					System.out.println("wrong data,please choose again:"+title.get(i)+" != "+node.get(i));
					data_is_correct=false;
				}
				else 
					System.out.println("well down");
			}*/
		}
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
