package Algorithm;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String path="showIndexSystem.jsp";

		int submitchoice = 5;
		submitchoice = Integer.parseInt(request.getParameter("Submits"));
		System.out.println("~~~");
		switch(submitchoice){
			case 0:{
				
				break;
			}
			case 1:{
				int parentID = Integer.parseInt(request.getParameter("parent"));
				
				break;
			}
			case 2:{
				
				System.out.println(request.getParameter("DBS"));
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
		request.getRequestDispatcher("showIndexSystem.jsp").forward(request, response);  
		request.getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
