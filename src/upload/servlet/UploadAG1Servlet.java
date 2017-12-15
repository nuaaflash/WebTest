package upload.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lxh.smart.SmartUpload;

import Dataset.ReadExcelUtils;

/**
 * Servlet implementation class Upload
 */
//@WebServlet("/Upload")
public class UploadAG1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadAG1Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String path="showAlgorithm1.jsp";
		SmartUpload su = new SmartUpload();
		su.initialize(this.getServletConfig(), request, response);
		try {
			su.upload();
			String ext = su.getFiles().getFile(0).getFileExt();
			String filename="theData."+ext;
			su.getFiles().getFile(0).saveAs("E:\\"+filename);
			//su.save("E:\\"); //使用绝对路径
			ReadExcelUtils reader = ReadExcelUtils.getInstance();
			reader.setFilepath("E:\\"+filename);
		} catch (Exception e) {
			// TODO: handle exception
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
//		String uno="161530309";//测试用
		SmartUpload su = new SmartUpload();
		su.initialize(this.getServletConfig(), request, response);
        try {
			su.upload();
		//	su.save("D:\\课程\\计算机学科\\卓越班\\项目\\Weshare\\WebRoot\\images");
			su.save("C:\\Users\\张万鹏\\workspace\\Zz\\WebContent\\headerimg");
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
