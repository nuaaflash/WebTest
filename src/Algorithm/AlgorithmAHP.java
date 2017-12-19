package Algorithm;
import java.util.Scanner;

import org.junit.Test;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import Jama.Matrix;

public class AlgorithmAHP{
	
	private int linenum;
	private double[][]  initialMatrix;
	private double[] weightVector;
	
	
	public AlgorithmAHP() {
		initialMatrix = null;
		weightVector = null;
	}
	
	public void eigenvalue(){
		Matrix c = new Matrix(initialMatrix);
		double[][] x=c.eig().getD().getArray();//getD()函数为求特征值
		c.eig().getD().print(5, 5);
		double[] ev = new double[linenum];
		//取出特征值
		for(int i=0;i<linenum;i++) {
			ev[i]=x[i][i];
		}
		//求最大特征值
		double maxEig=ev[0];
		for(int i=0;i<linenum;i++) {
			if(ev[i]>maxEig) {
				maxEig=ev[i];
			}
		}
		//求出最大特征值所在序列
		int Dnum=0;
		for(int i=0;i<linenum;i++) {
			if(ev[i]==maxEig) {
				Dnum=i;
			}
		}
		//System.out.println(Dnum);
		double[][] y=c.eig().getV().getArray();//getV()函数为求特征向量，每一列都是一个向量
		c.eig().getV().print(5, 5);
		double[] aVector = new double[linenum];
		//weightVecor为权向量，赋值之后就应该标准化
		
		for(int i=0;i<linenum;i++) {
			aVector[i]=y[i][Dnum];
		}

		/*for(int i=0;i<linenum;i++) {
			System.out.print(aVector[i]+"  ");
		}*/
		
		//以下为标准化，即保证向量为正，和为一，求出来的就是权值
		double sum=0;
		for(int i=0;i<linenum;i++) {
			sum=aVector[i]+sum;
		}
		for(int i=0;i<linenum;i++) {
			aVector[i]=aVector[i]/sum;
		}
		/*for(int i=0;i<linenum;i++) {
			System.out.print(aVector[i]+"  ");
		}*/
		setWeightVector(aVector);
	}
	
	
	public void TestSet(){
		AlgorithmAHP ahp = new AlgorithmAHP();
		ahp.setLinenum(123);
		System.out.println(ahp.getLinenum());
	}
	
	@Test
	public void TTTEST() {
		AlgorithmAHP ahp = new AlgorithmAHP();
		ahp.setLinenum(5);
		System.out.println(ahp.getLinenum());
		double[][] aMatrix = {
				{1,2,7,5,5},
				{-2,1,4,3,3},
				{-7,-4,1,-2,-3},
				{-5,-3,2,1,1},
				{-5,-3,3,5,1}
		};
		
		for(int i=0; i<ahp.getLinenum(); i++){
		    for(int j=0; j<ahp.getLinenum(); j++){
		         //鐢变簬鏃犳硶鐩存帴杈撳叆鍒嗘暟锛屾墍浠ョ敤璐熸暟浠ｆ浛锛屾娴嬪埌涓鸿礋鏁颁究杞寲涓哄垎鏁�
		         if(aMatrix[i][j]<0) {
		        	 aMatrix[i][j]=1/(-aMatrix[i][j]);
		         }
		         //aMatrix[j][i] = 1/aMatrix[i][j];
		    }
		}
		Matrix temp = new Matrix(aMatrix);
		temp.print(5, 5);
		ahp.setInitialMatrix(aMatrix);
		//浠ヤ笂涓鸿緭鍏ュ垽鍒煩闃�
		ahp.eigenvalue();
		System.out.println(ahp.getLinenum());
		for(int i=0;i<ahp.getLinenum();i++) {
			System.out.print(ahp.getWeightVector()[i]+"  ");
		}
	}
	
	public void Test() {
		AlgorithmAHP ahp = new AlgorithmAHP();
		Scanner in = new Scanner(System.in);
		
		int In = in.nextInt();//杈撳叆琛屾暟
		ahp.setLinenum(In);
		
		//浠ヤ笅涓鸿緭鍏ュ垽鍒煩闃�
		double[][] aMatrix = new double[linenum][linenum];
		for(int i=0; i<linenum; i++){
		    for(int j=i+1; j<linenum; j++){
		         aMatrix[i][j] = in.nextDouble();
		         //鐢变簬鏃犳硶鐩存帴杈撳叆鍒嗘暟锛屾墍浠ョ敤璐熸暟浠ｆ浛锛屾娴嬪埌涓鸿礋鏁颁究杞寲涓哄垎鏁�
		         if(aMatrix[i][j]<0) {
		        	 aMatrix[i][j]=1/(-aMatrix[i][j]);
		         }
		         aMatrix[j][i] = 1/aMatrix[i][j];
		    }
		}
		for(int i=0;i<linenum;i++) {
			aMatrix[i][i] = 1;
		}
		ahp.setInitialMatrix(aMatrix);
		//浠ヤ笂涓鸿緭鍏ュ垽鍒煩闃�
		ahp.eigenvalue();
	}
	
	public int getLinenum() {
		return linenum;
	}
	public void setLinenum(int linenums) {
		linenum = linenums;
	}
	public double[][] getInitialMatrix() {
		return initialMatrix;
	}
	public void setInitialMatrix(double[][] inputMatrix) {
		initialMatrix = new double[inputMatrix.length][inputMatrix[0].length];
		for(int i = 0;i < inputMatrix.length;i ++){
			for(int j = 0;j < inputMatrix[i].length;j ++){
				initialMatrix[i][j] = inputMatrix[i][j];
			}
		}
	}
	public double[] getWeightVector() {
		return weightVector;
	}
	public void setWeightVector(double[] weightVector) {
		 this.weightVector = new double[weightVector.length];
		 this.weightVector = (double[]) weightVector.clone();
	}
}
