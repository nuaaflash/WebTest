package calculate.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dataset.ReadExcelUtils;
import jdk.internal.org.xml.sax.InputSource;

import Sql.*;
import java.util.ArrayList;
/**
 * Servlet implementation class CalculateServlet
 */

public class CalculateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalculateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String path="main.jsp";
		Sql sql = Sql.getInstance();
		
		ArrayList<Double> Input = new ArrayList<Double>();
		String target_name = request.getParameter("choose_target");
		System.out.println(target_name);
		ArrayList<String> leaves = sql.getLeaves(target_name);
		int num = leaves.size();
		for(int i=0;i<num;i++) {
			Double temp = new Double(request.getParameter("point_value"+i));
			System.out.println(temp);
			Input.add(temp);
		}
		
		ReadExcelUtils reader = ReadExcelUtils.getInstance();
		try {
			ArrayList<Object> re = reader.readExcel();
			for (int i = 0; i < re.size(); i++) { 
	        	System.out.print(re.get(i)+"  ");
	        	i ++;
	        	double [] nums = (double[])re.get(i);
	        	for(int j = 0;j < nums.length - 1;j ++) {
	        		System.out.print(nums[j]+"  ");  
	        	}
	        	System.out.println(nums[nums.length - 1]);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
