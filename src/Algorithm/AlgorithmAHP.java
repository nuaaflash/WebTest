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
		double[][] x=c.eig().getD().getArray();//getD函数为求特征值。
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
		double[][] y=c.eig().getV().getArray();//getV函数为求特征向量，按列算
		c.eig().getV().print(5, 5);
		double[] aVector = new double[linenum];
		//weightVecor为权向量，赋值之后，就应该标准化
		
		for(int i=0;i<linenum;i++) {
			aVector[i]=y[i][Dnum];
		}

		/*for(int i=0;i<linenum;i++) {
			System.out.print(aVector[i]+"  ");
		}*/
		
		//以下为标准化，即保证向量为正，且和为一，求出来的就是权值
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
		weightVector =aVector;
	}
	@Test
	public void TTTEST() {
		AlgorithmAHP ahp = new AlgorithmAHP();
		
		ahp.setLinenum(5);
		linenum = 5;
		System.out.println(linenum);
		double[][] aMatrix = {
				{1,2,7,5,5},
				{-2,1,4,3,3},
				{-7,-4,1,-2,-3},
				{-5,-3,2,1,1},
				{-5,-3,3,5,1}
		};
		System.out.println(linenum);
		for(int i=0; i<linenum; i++){
		    for(int j=0; j<linenum; j++){
		         //由于无法直接输入分数，所以用负数代替，检测到为负数便转化为分数
		         if(aMatrix[i][j]<0) {
		        	 aMatrix[i][j]=1/(-aMatrix[i][j]);
		         }
		         //aMatrix[j][i] = 1/aMatrix[i][j];
		    }
		}
		Matrix temp = new Matrix(aMatrix);
		temp.print(5, 5);
		ahp.setInitialMatrix(aMatrix);
		//以上为输入判别矩阵
		ahp.eigenvalue();
		System.out.println(linenum);
		for(int i=0;i<linenum;i++) {
			System.out.print(weightVector[i]+"  ");
		}
	}
	
	public void Test() {
		AlgorithmAHP ahp = new AlgorithmAHP();
		Scanner in = new Scanner(System.in);
		
		int In = in.nextInt();//输入行数
		ahp.setLinenum(In);
		
		//以下为输入判别矩阵
		double[][] aMatrix = new double[linenum][linenum];
		for(int i=0; i<linenum; i++){
		    for(int j=i+1; j<linenum; j++){
		         aMatrix[i][j] = in.nextDouble();
		         //由于无法直接输入分数，所以用负数代替，检测到为负数便转化为分数
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
		//以上为输入判别矩阵
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
		initialMatrix = inputMatrix;//c是一个Matrix类的，其内容与initialMatrix一致
	}
	public double[] getWeightVector() {
		return weightVector;
	}
	public void setWeightVector(double[] weightVector) {
		this.weightVector = weightVector;
	}
}
