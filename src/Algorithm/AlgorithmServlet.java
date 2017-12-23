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
import Sql.Node;
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
		String path="view.jsp";// 计算完跳转显示结果
		int submitchoice = 5;
		String indication_name = request.getParameter("indication_name");
		int lnum = Integer.parseInt(request.getParameter("num"));
		
		submitchoice = Integer.parseInt(request.getParameter("submitchoice"));
		System.out.println("submitchoice in AlgorithmServlet:"+submitchoice+" indication_name:"+indication_name+" lnum:"+lnum);
		
		// 连接数据库 并将操作的树（指标体系）设置为前一页面选择的树 并将数据库各节点初始化为0 避免对显示数据产生干扰
		Sql sql=Sql.getInstance();
		sql.SetTreeName(indication_name);
		sql.InitNodeValue(0);
		
		// 读文件部分
		ReadExcelUtils reader = ReadExcelUtils.getInstance();
		reader.setFilepath("E:\\theData.xlsx");
		ArrayList<Object> result = null;
		try {
			result = reader.readExcel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        double [][] leaves = new double[result.size()/2][];
        double [] threatDegree = new double [result.size()/2];
		if(submitchoice != 3) {// 控制台输出Excel表格的内容
			ArrayList<String>title=new ArrayList<String>();
			ArrayList<String>node=new ArrayList<String>();
			try {
				title=reader.readExcelTitle();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			node=sql.getNodes(indication_name);
			System.out.println("node:"+node.toString());
			
			// 控制台输出并将excel内容调整为训练模型所需的两个数组
	        System.out.println("获得Excel表格的内容:");  

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
		}
		switch(submitchoice){
		
			// RBF神经网络算法
			case 1:{
				ArrayList<Double> Input = new ArrayList<Double>();
				ArrayList<String> leavesRBF = sql.getLeaves(indication_name);
				int leaves_num = leavesRBF.size();
				double myinput[] = new double[leaves_num];
				for(int i=0;i<leaves_num;i++) {
					Double temp = new Double(request.getParameter("point_value"+i));
					myinput[i]=temp.doubleValue();
				}
				int hidden_nodes_num = Integer.parseInt(request.getParameter("hidden_nodes_num"));
				int count = Integer.parseInt(request.getParameter("count"));
				Double step_length = new Double(request.getParameter("step_length"));
				System.out.println("hidden_nodes_num:"+hidden_nodes_num+"   count:"+count+"   step_length:"+step_length);
				RBF rbf = new RBF(result.size()/2, hidden_nodes_num, leaves_num, step_length.doubleValue(), leaves, threatDegree);
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
				compute_result = 50.0;
				System.out.println(" rbf compute result: "+compute_result);
				sql.SetNodeValue(indication_name, 1, compute_result);
				// 结果写入数据库
				break;
			}
			
			// Svr支持向量回归算法
			case 2:{	
                Svmr svr = new Svmr();
                svr.Setparameter(Double.parseDouble(request.getParameter("cache")),
                				 Double.parseDouble(request.getParameter("eps")) , 
                				 Double.parseDouble(request.getParameter("C")));
                svr.Train(leaves, threatDegree);
                int num = lnum;	//叶子的个数
                double[] p = new double[num];
                for(int i = 0;i < num; i ++){
                	p[0] = Double.parseDouble(request.getParameter("point_value"+i));
                	System.out.println(p[0]);
                }
                System.out.println(svr.Predict(p));
                
                // 结果写入数据库
                sql.SetNodeValue(indication_name,1, svr.Predict(p));
				break;
			}
			
			// AHP层次分析算法
			case 3:{
				Sql S = Sql.getInstance(); 						
				// 将读入数据写入数据库
				int num = lnum;	//叶子的个数
				String lname = null;
                double value = 0;
                for(int i = 0;i < num; i ++){
                	value = Double.parseDouble(request.getParameter("point_value"+i));
                	lname = request.getParameter("leavesname"+i);
                	sql.SetNodeValue(lname, value);
                	System.out.println(value);
                }
                
				AlgorithmAHP A = new AlgorithmAHP();
				ArrayList<Node> TempN = new ArrayList<Node>();
				for(int i=0;i< result.size();i=i+2) {
					String name = (String) result.get(i);//取出结点名字
					double[] data1 = (double[]) result.get(i+1);//取出重要度数组
					TempN = S.getChildrenofNode(name);//从数据库取子结点
					A.setLinenum(TempN.size());//定义linenum的值
					double[][] data2 = new double[A.getLinenum()][A.getLinenum()];//根据子节点个数创建矩阵
					int count = 0;
					for(int j=0;j<A.getLinenum();j++) {
						for(int k=j+1;k<A.getLinenum();k++) {
							data2[j][k] = data1[count];
							count++;
							if(data2[j][k]<0) {
								data2[j][k] = 1/(-data2[j][k]);
							}
							data2[k][j] = 1/data2[j][k];
						}
						data2[j][j] = 1;
					}
					A.setInitialMatrix(data2);
					//完成判别矩阵导入
					A.eigenvalue();//计算权向量
					double[] TempW = A.getWeightVector();//取出权向量
					for(int b=0;b<A.getLinenum();b++) {
						System.out.print(TempW[b]+"   ");
					}
					double TempV = 0;//最终值
					for(int j=0;j<A.getLinenum();j++) {
						Node x = TempN.get(j);
						TempV = TempV + TempW[j] * x.value;
					}
					S.SetNodeValue(name, TempV);
				}
				break;
			}


			default:{
				break;
			}
		}
        request.getRequestDispatcher(path).forward(request, response); // 计算完跳转显示结果
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
