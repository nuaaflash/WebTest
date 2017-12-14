package Algorithm;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dataset.ReadExcelUtils;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Algorithm")
public class AlgorithmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlgorithmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");   
		String path="showAlgorithm2.jsp";

		int submitchoice = 5;
		submitchoice = Integer.parseInt(request.getParameter("Submits"));
		switch(submitchoice){
			case 0:{
				
				break;
			}
			case 1:{
				
				break;
			}
			case 2:{
        		ReadExcelUtils reader = ReadExcelUtils.getInstance();
        		reader.setFilepath("E:\\theData.xlsx");
        		ArrayList<Object> result = null;
				try {
					result = reader.readExcel();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.out.println("获得Excel表格的内容:");  
                for (int i = 0; i < result.size(); i++) { 
                	System.out.print(result.get(i)+"  ");
                	i ++;
                	double [] num = (double[])result.get(i);
                	for(int j = 0;j < num.length - 1;j ++){
                		System.out.print(num[j]+"  ");  
                	}
                	System.out.println(num[num.length - 1]);
                }  
                request.getRequestDispatcher("showAlgorithm2.jsp").forward(request, response); 
				break;
			}
			case 3:{
				break;
			}
			case 4:{
				double values = Double.parseDouble(request.getParameter("value"));
				break;
			}
			default:{
				break;
			}
		}
		ArrayList<String> trees = null;
		request.setAttribute("trees", trees); 
		request.getRequestDispatcher(path).forward(request, response);  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
