/*重要度文件:
1、除了叶子结点外，剩下结点都应该填写重要度
2、应先填写低层结点的重要度，从底层逐渐往上填写。如一个四层的指标体系，应先将第三层的重要度填完，再填第二层   ，最后填顶层。
3、关于重要度数组的排序，假设“X”结点有四个子结点      a,b,c,d.重要度数组为Weight[].那么：
Weight[0]代表a对b的重要度，
Weight[1]代表a对c的重要度，
Weight[2]代表a对d的重要度，
Weight[3]代表b对c的重要度，
Weight[4]代表b对d的重要度，
Weight[5]代表c对d的重要度。
4、空缺的地方应该补0。
5、数组的取值为[-9,0)与(0,9].
a对b的重要度为2到9表示a比b重要，绝对值越大越重要。
a对b的重要度为-2到-9表示b比a重要，绝对值越大越重要。
a对b的重要度为-1或1表示a和b同等重要。
*/
package Algorithm;

import java.util.ArrayList;

import org.junit.Test;

import Sql.Node;
import Dataset.ReadExcelUtils;
import Sql.Sql;

public class AHPLoop {
	public void Calcu() throws Exception {
		ReadExcelUtils E = ReadExcelUtils.getInstance();
		E.setFilepath("E:\\theData.xlsx");
		ArrayList <Object> WeightExcel = new ArrayList<Object>();
		WeightExcel = E.readExcel();
		Sql S = Sql.getInstance(); 
		S.SetTreeName("node");
		S.SetDBS("threatDegree");
		S.setPassword("123");
		S.setUser("root");
		//以上是测试用的，登录本地数据库
		AlgorithmAHP A = new AlgorithmAHP();
		ArrayList<Node> TempN = new ArrayList<Node>();
		for(int i=0;i<WeightExcel.size();i=i+2) {
			String name = (String) WeightExcel.get(i);//取出结点名字
			double[] data1 = (double[]) WeightExcel.get(i+1);//取出重要度数组
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
