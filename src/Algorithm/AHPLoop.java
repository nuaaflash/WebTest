package Algorithm;

import java.util.ArrayList;

import org.junit.Test;

import Sql.Node;
import Dataset.ReadExcelUtils;
import Sql.Sql;

public class AHPLoop {
	public void Calcu() throws Exception {
		ReadExcelUtils E = ReadExcelUtils.getInstance();
		E.setFilepath("F:\\1.xlsx");
		ArrayList <Object> WeightExcel = new ArrayList<Object>();
		WeightExcel = E.readExcel();
		Sql S = Sql.getInstance(); 
		/*S.SetTreeName("node");
		S.SetDBS("myh");
		S.setPassword("admin");
		S.setUser("root");*/
		AlgorithmAHP A = new AlgorithmAHP();
		ArrayList<Node> TempN = new ArrayList<Node>();
		for(int i=0;i<WeightExcel.size();i=i+2) {
			String name = (String) WeightExcel.get(i);//取出节点名字
			double[] data1 = (double[]) WeightExcel.get(i+1);//取出重要度数组
			TempN = S.getChildrenofNode(name);//从数据库取子节点
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
			/*System.out.println("**********************************");
			for(int j=0;j<A.getLinenum();j++) {
				for(int k=0;k<A.getLinenum();k++) {
					System.out.print(data2[j][k]+"   ");
				}
				System.out.println();
			}
			System.out.println("**********************************");*/
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
	}
	@Test
	public void caf() throws Exception {
		AHPLoop adfg =new AHPLoop();
		adfg.Calcu();
	}
}
