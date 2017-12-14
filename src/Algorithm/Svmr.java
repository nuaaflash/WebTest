package Algorithm;

import java.util.ArrayList;

import org.junit.Test;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Svmr {
	private svm_node[][] vectors;									// 训练点 
	svm_node[] predict_vectors;	// 测试点 
	double[] lables;					// 训练点的标签
	double predict__lable;					// 测试点的标签
	svm_model model;					// 训练后得到的模型
	
	public Svmr(){}
	
	public void Train(double[][] leaves,double[] threatDegree) {		// 输入训练数据
		vectors = new svm_node[leaves.length][leaves[0].length];		// 训练点
		lables=new double[leaves.length];
    	// 设置训练集的向量表和标签
    	for(int i=0;i<leaves.length;i++){
    	// 设置第i+1个训练点的index和value,index=1为点的第一个坐标 （index=-1为最后一个坐标）
    	// 这里有leaves.length个坐标
    		for(int j=0;j<leaves[i].length;j ++){
        		vectors[i][j] = new svm_node();
        		vectors[i][j].index = j+1;
        		vectors[i][j].value = leaves[i][j];
    		}
    		vectors[i][vectors[0].length-1].index = -1;			// index=-1为最后一个坐标
    	// 	设置每个点的标签
    	//  svmr中标签为样本输出值
    		lables[i] = threatDegree[i];
        }
    	      
        //定义svm_problem对象
        svm_problem problem = new svm_problem();
        problem.l = leaves.length; 								// 向量个数
        problem.x = vectors; 									// 训练集向量表
        problem.y = lables; 									// 对应的lable数组
        
        //定义svm_parameter对象
        svm_parameter param = new svm_parameter();
        param.svm_type = svm_parameter.EPSILON_SVR;				// 用支持向量回归 的 svm
        param.kernel_type = svm_parameter.LINEAR;				// 线性核函数
        //param.kernel_type = svm_parameter.POLY;				// 指数核函数
        param.cache_size = 100;
        param.eps = 0.001;
        param.C = 1.9;
        
        //训练SVM分类模型
        System.out.println(svm.svm_check_parameter(problem, param)); // 如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
        model = svm.svm_train(problem, param); 			 // svm.svm_train()训练出SVM回归模型
    }
	
	public double Predict(double[] datapre){
        // 设置预测集的向量表
		predict_vectors = new svm_node[datapre.length];
    	 for(int i=0;i<datapre.length;i++){
    	// 设置第i+1个测试点的index和value,index=1为点的第一个坐标 index=-1为最后一个坐标
    	// 这里只有一个坐标
    		predict_vectors[i] = new svm_node();
    		predict_vectors[i].index = i+1;
    		predict_vectors[i].value = datapre[i];
        }
    	predict_vectors[predict_vectors.length - 1].index = -1;
    	
        // 预测每个测试数据的lables 即输出
    	predict__lable = svm.svm_predict(model, predict_vectors);
    	System.out.println(" "+lables+" "+predict__lable+"\n");
        double result =	svm.svm_predict(model, predict_vectors);
		return result; 
	}
	
	@Test
	public void test(){
		Svmr svmr = new Svmr();
		double[][] leaves = { {1,3,4,6} , { 2, 3, 3,7}, {2, 3, 4, 9}};
		double[] threatDegree = { 14, 15, 18};
		svmr.Train(leaves, threatDegree);
		double p[] = {2, 3, 4, 9};
		System.out.println(svmr.Predict(p));
	}
}
