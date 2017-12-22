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
import Algorithm.*;
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
		String target_name = request.getParameter("choose_target");		
		System.out.println("target_name:"+target_name);
		ArrayList<String> leaves = sql.getLeaves(target_name);
		int leaves_num = leaves.size();
		double myinput[] = new double[leaves_num];
		for(int i=0;i<leaves_num;i++) {
			Double temp = new Double(request.getParameter("point_value"+i));
			myinput[i]=temp.doubleValue();
		}
		int hidden_nodes_num = Integer.parseInt(request.getParameter("hidden_nodes_num"));
		int count = Integer.parseInt(request.getParameter("count"));
		Double step_length = new Double(request.getParameter("step_length"));
		System.out.println("hidden_nodes_num:"+hidden_nodes_num+"   count:"+count+"   step_length:"+step_length);
		
		ReadExcelUtils reader = ReadExcelUtils.getInstance();
		try {
			ArrayList<Object> re = reader.readExcel();
			double inputarray[][];	//传入算法的输入样本 chuan ru suan fa de shu ru yang ben
			double outputarray[];	//传入算法的输出样本 chuan ru suan fa de shu chu yang ben
			inputarray = new double[re.size()/2][leaves_num];
			outputarray = new double[re.size()/2];
			System.out.println("re.size():"+re.size()+" leaves_num: "+leaves_num);
			for (int i = 0; i < re.size(); i++) {
				if(i%2==0) continue;
				double [] nums = (double[])re.get(i);
				for(int j=0;j<nums.length-1;j++) {
					inputarray[i/2][j]=nums[j];
				}
				outputarray[i/2]=nums[nums.length-1];
			}
			/*for(int i=0;i<re.size()/2;i++) {
				for(int j=0;j<leaves_num;j++)
					System.out.print(inputarray[i][j]+" ");
				System.out.println(outputarray[i]);
			}*/
			RBF rbf = new RBF(re.size()/2, hidden_nodes_num, leaves_num, step_length.doubleValue(), inputarray, outputarray);
			while(count>=0) {
				rbf.calError();
				rbf.update();
				count--;
			}
			double compute_result = rbf.compute(myinput);
			/*if(compute_result<0.00001)
				compute_result=0;
			if(compute_result>100.0)
				compute_result=100.0;*/
			System.out.println(" rbf compute result: "+compute_result);
			sql.SetNodeValue(target_name, 1, compute_result);
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
