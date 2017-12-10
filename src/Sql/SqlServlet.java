package Sql;

import java.io.IOException;
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
		String path="showIndexSystem.jsp";
		Sql sql = new Sql();
		sql.ConnectSql();
		int submitchoice = Integer.parseInt(request.getParameter("Submits"));
		switch(submitchoice){
			case 0:{
				sql.InitTree(request.getParameter("Treename"));
				break;
			}
			case 1:{
				int parentID = Integer.parseInt(request.getParameter("parent"));
				sql.SetTreeName(request.getParameter("Treename"));
				sql.Addnode(request.getParameter("Nodename"), parentID, 0);
				break;
			}
			case 2:{
				sql.setUser(request.getParameter("user"));
				sql.setPassword(request.getParameter("passwd"));
				sql.SetDBS(request.getParameter("DBS"));
				sql.ConnectSql();
				request.setAttribute("dbs", request.getParameter("DBS")); 
				request.getRequestDispatcher("showAlgorithm1.jsp").forward(request, response);  
				request.getRequestDispatcher("showAlgorithm2.jsp").forward(request, response);  
				break;
			}
			case 3:{
				sql.DestroyTree(request.getParameter("Treename"));
				break;
			}
			case 3:{
				sql.DestroyTree(request.getParameter("Treename"));
				break;
			}
		}
		
		request.getRequestDispatcher(path).forward(request, response);
	}

	/*
	 * public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String path="person_info_modify.jsp";
		
		boolean flag=false;
		String uno=(String)request.getSession().getAttribute("uno");
//		String uno="161530309";//濞村鐦悽锟�
		SmartUpload su = new SmartUpload();
		su.initialize(this.getServletConfig(), request, response);
        try {
			su.upload();
		//	su.save("D:\\鐠囧墽鈻糪\鐠侊紕鐣婚張鍝勵劅缁夋叚\閸楁捁绉洪悵鐠妞ゅ湱娲癨\Weshare\\WebRoot\\images");
			su.save("C:\\Users\\瀵姳绔炬ィ寤玕workspace\\Zz\\WebContent\\headerimg");
		} catch (SmartUploadException e1) {
			e1.printStackTrace();
		}
        
        String img_addr=su.getFiles().getFile(0).getFileName();
		String nickname = su.getRequest().getParameter("new_nickname");
		String phone = su.getRequest().getParameter("new_phone");
        try {
        	if (DaoFactory.getIUserDaoInstance().changeNickname(uno, nickname)&&DaoFactory.getIUserDaoInstance().changePhone(uno, phone)&&DaoFactory.getIUserDaoInstance().changeImgUrl(img_addr, uno)) {
        		flag=true;//new_userinfo.add(true);
        	} else {
        		flag=false;//new_userinfo.add(false);
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		request.setAttribute("flag", flag);
		request.getRequestDispatcher(path).forward(request, response);
	}
	 */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
