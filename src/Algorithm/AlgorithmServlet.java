package Algorithm;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dataset.ReadExcelUtils;
import Algorithm.Svmr;
import Sql.Sql;

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
		
		// 读文件并验证部分
		ReadExcelUtils reader = ReadExcelUtils.getInstance();
		Sql sql=Sql.getInstance();
		
		reader.setFilepath("E:\\theData.xlsx");
		ArrayList<Object> result = null;
		try {
			result = reader.readExcel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 验证部分
		ArrayList<String>title=new ArrayList<String>();
		ArrayList<String>node=new ArrayList<String>();
		try {
			title=reader.readExcelTitle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		node=sql.getNodes(request.getParameter("treename"));
		for(int i=0;i<title.size();i++){
			if(title.get(i)!=node.get(i))
				System.out.println("wrong data,please choose again");
			else 
				System.out.println("well down");
		}
		
		
		// 控制台输出并将excel内容调整为训练模型所需的两个数组
        System.out.println("获得Excel表格的内容:");  
        double [][] leaves = new double[result.size()/2][];
        double [] threatDegree = new double [result.size()/2];
        int il = 0;
        for (int i = 0; i < result.size(); i++) { 
        	System.out.print(result.get(i)+"  ");
        	i ++;
        	double [] num = (double[])result.get(i);
        	leaves[il] = new double[num.length - 1];
        	for(int j = 0;j < num.length - 1;j ++){
        		System.out.print(num[j]+"  ");
        		leaves[il][j] = num[j];
        	}
        	threatDegree[il] = num[num.length - 1];
        	System.out.println(num[num.length - 1]);
        	il ++;
        }  
        
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
                Svmr svr = new Svmr();
                svr.Setparameter(Double.parseDouble(request.getParameter("cache")),
                				 Double.parseDouble(request.getParameter("eps")) , 
                				 Double.parseDouble(request.getParameter("C")));
                svr.Train(leaves, threatDegree);
                int num = Integer.parseInt(request.getParameter("num"));
                double[] p = new double[num];
                for(int i = 0;i < num; i ++){
                	p[0] = Double.parseDouble(request.getParameter("point_value"+i));
                	System.out.println(p[0]);
                }
                System.out.println(svr.Predict(p));
                
                // 结果写入数据库
                sql.SetNodeValue(request.getParameter("treename"),1, svr.Predict(p));
                
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
