package Algorithm;

import java.util.ArrayList;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class svmr {
	private svm_node[][] vectors;									// 训练点 
	svm_node[][] test_vectors;	// 测试点 
	double[] lables;					// 训练点的标签
	double[] test_lables;				// 测试点的标签
	svm_model model;					// 训练后得到的模型
	
	public void Train(double[][] dataset) {
		vectors = new svm_node[dataset.length][1];		// 训练点
		lables=new double[dataset.length];
    	// 设置训练集的向量表和标签
    	for(int i=0;i<dataset.length;i++){
    	// 设置第i+1个训练点的index和value,index=1为点的第一个坐标 （index=-1为最后一个坐标）
    	// 这里只有一个坐标
    		vectors[i][0] = new svm_node();
    		vectors[i][0].index = 1;
    		vectors[i][0].value = 0;
    		
    	// 	设置每个点的标签
    	//  svmr中标签为样本输出值
    		lables[i] = 0;
        }
    	      
        //定义svm_problem对象
        svm_problem problem = new svm_problem();
        problem.l = dataset.length; 						// 向量个数
        problem.x = vectors; 									// 训练集向量表
        problem.y = lables; 									// 对应的lable数组
        
        //定义svm_parameter对象
        svm_parameter param = new svm_parameter();
        param.svm_type = svm_parameter.EPSILON_SVR;				// 用支持向量回归 的 svm
        param.kernel_type = svm_parameter.LINEAR;				// 线性核函数
        //param.kernel_type = svm_parameter.POLY;				// 指数核函数
       /* param.degree = 2;	// for poly
        param.gamma = 0.001;	// for poly/rbf/sigmoid
        param.coef0 = 0.001;	// for poly/sigmoid*/
        param.cache_size = 100;
        param.eps = 0.001;
        param.C = 1.9;
        
        //训练SVM分类模型
        System.out.println(svm.svm_check_parameter(problem, param)); // 如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
        model = svm.svm_train(problem, param); 			 // svm.svm_train()训练出SVM回归模型
        

    }
	
	public double Predict(double[] datapre){
        // 设置预测集的向量表
		test_vectors = new svm_node[datapre.length][2];
		test_lables=new double[datapre.length];
    	 for(int i=0;i<datapre.length;i++){
    	// 设置第i+1个测试点的index和value,index=1为点的第一个坐标 index=-1为最后一个坐标
    	// 这里只有一个坐标
    		test_vectors[i][0] = new svm_node();
    		test_vectors[i][0].index = 1;
    		test_vectors[i][0].value = 0;
    		
        }
    	
        // 预测每个测试数据的lables 即输出
    	for(int i=0;i<datapre.length;i++){
    		test_lables[i] = svm.svm_predict(model, test_vectors[i]);
    		System.out.println(test_vectors[i][0].value+" "+lables[i]+" "+test_lables[i]+"\n");
        }
    	 svm_node[][] predict_vectors = new svm_node [137+(137*10/9)][1];
        double result =	svm.svm_predict(model, predict_vectors[0]);
		return result; 
	}
}
