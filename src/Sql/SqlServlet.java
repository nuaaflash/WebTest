package Sql;

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
@WebServlet("/Sql")
public class SqlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SqlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");   
		Sql sql = Sql.getInstance();
		String path = "IndicationSystem.jsp";
		sql.ConnectSql();
		int submitchoice = 6;
		submitchoice = Integer.parseInt(request.getParameter("Submits"));
		System.out.println(submitchoice+"*********************************");
		switch(submitchoice){
			case 0:{
				sql.InitTree(request.getParameter("Treename"));
				break;
			}
			case 1:{
				int parentID = Integer.parseInt(request.getParameter("parent"));
				sql.Addnode(request.getParameter("Nodename"), parentID, 0);
				break;
			}
			case 2:{
				sql.setUser(request.getParameter("user"));
				sql.setPassword(request.getParameter("passwd"));
				sql.SetDBS(request.getParameter("DBS"));
				System.out.println(request.getParameter("DBS"));
				if(sql.ConnectSql()){
					path = "warning.jsp";   
				}
				else{
					path = "failure.jsp";  
				}
				break;
			}
			case 3:{
				sql.DestroyTree(request.getParameter("nameofTree"));
				break;
			}
			case 4:{
				double values = Double.parseDouble(request.getParameter("value"));
				sql.SetNodeValue(request.getParameter("Nodename"),values);
				break;
			}
			case 5:{
				sql.SetTreeName(request.getParameter("nameofTree"));
				break;
			}
			case 6:{
				sql.Removenode( Integer.parseInt(request.getParameter("deleteID")) );
				break;
			}
			case 7:{
				System.out.println(request.getParameter("name")+">>>>>>>>>>>>>"+request.getParameter("newname"));
				sql.RenameNode(request.getParameter("name"), request.getParameter("newname"));
				break;
			}
			case 8:{
				ArrayList<String> instructions = new ArrayList<String>();
				for(int i=0;i<Integer.parseInt(request.getParameter("length"));i++) {
					String order = request.getParameter("instruct"+i);
					instructions.add(order);
					System.out.println(order);
				}
				sql.setInstruct(instructions);
				sql.doInstructions();
				System.out.println(Integer.parseInt(request.getParameter("length")));
				break;
			}
			default:{
				break;
			}
		}
		ArrayList<String> trees = sql.getTreeS();
		request.setAttribute("trees", trees); 
		request.getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
