package com.zgzzh.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lxh.smart.SmartUpload;

import Dataset.ReadExcelUtils;
/**
 * Servlet implementation class DisposeDataServlet
 */
//@WebServlet("/DisposeDataServlet")
public class DisposeDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisposeDataServlet() {
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
		SmartUpload su = new SmartUpload();
		su.initialize(this.getServletConfig(), request, response);
		try {
			su.upload();
			String ext = su.getFiles().getFile(0).getFileExt();
			String filename="theInputData."+ext;
			su.getFiles().getFile(0).saveAs("E:\\"+filename);
			//su.save("E:\\"); //使用绝对路径
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int num = Integer.parseInt(su.getRequest().getParameter("num"));
		for(int i=0;i<num;i++) {
			request.setAttribute("leaves_value_from_Servlet"+i, 3.14);
		}
		
		int submitchoice = Integer.parseInt(su.getRequest().getParameter("submitchoice"));
		int indx = Integer.parseInt(su.getRequest().getParameter("the_system_index"));
		System.out.println("DisposeDataServlet-- submitchoice:"+submitchoice+" indx:"+indx);
		request.setAttribute("the_system_index", indx);
		request.setAttribute("submitchoice_from_Servlet", submitchoice);
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
