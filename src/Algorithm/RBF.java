package Algorithm;
import java.util.Random;
public class RBF {
	private double[][] center;	//数据中心
	private double[] delta;		//扩展常数（方差）
	private double[] weight;	//权重
	private int s_num;		//样本个数
	private int h_num;		//隐藏层节点个数
	private int d_num;		//输入维度（有几个输入）
	private double eta;		//每次迭代的学习率
	private double[][] input;	//输入样本
	private double[] output;	//输出样本
	private double[] err;	//每个样本引起的误差
	
	/**
	 * @param s_num
	 * @param h_num
	 * @param d_num
	 * @param eta
	 * @param input
	 * @param output
	 */
	public RBF(int s_num, int h_num, int d_num, double eta, double[][] input, double[] output) {
		super();
		this.s_num = s_num;
		this.h_num = h_num;
		this.d_num = d_num;
		this.eta = eta;
		this.input = input;
		this.output = output;
		center = new double [h_num][d_num];
		delta = new double [h_num];
		weight = new double [h_num];
		err = new double [s_num];
	}
/*
	//初始化权值、数据中心和扩展常数
	public void initial() {
		double max, min, d_max;	//d_max为数据中心的最大距离
		Random random = new Random();
		for(int i=0;i<h_num;i++) {
			weight[i]=2*random.nextDouble()-1.0;
			center[i]=input[random.nextInt(s_num)];
		}
		max=min=center[0];
		for(int j=1;j<h_num;j++) {
			if(max<center[j]) max=center[j];
			if(min>center[j]) min=center[j];
		}
		d_max=max-min;
		for(int k=0;k<h_num;k++) {
			delta[k]=d_max/Math.pow(2.0*((double)h_num), 0.5);
		}
	}
	
	public double compute(double x) {
		double y = 0;
		for(int i=0;i<h_num;i++) {
			y+=weight[i]*Math.exp(-1.0*(x-center[i])*(x-center[i])/(2*delta[i]*delta[i]));
		}
		return y;
	}
	
	//计算每个样本的误差
	public void calError() {
		for(int i=0;i<s_num;i++) {
			err[i]=output[i]-compute(input[i]);
		}
	}
	
	public void update() {
		double d_center, d_delta, d_weight;
		double s_center, s_delta, s_weight;
		for(int i=0;i<h_num;i++) {
			d_center=d_delta=d_weight=s_center=s_delta=s_weight=0.0;
			for(int j=0;j<s_num;j++) {
				s_center+=err[j]*Math.exp(-1.0*Math.pow((input[j]-center[i]), 2)/(2.0*Math.pow(delta[i], 2)))*(input[j]-center[i]);
				s_delta+=err[j]*Math.exp(-1.0*Math.pow((input[j]-center[i]), 2)/(2.0*Math.pow(delta[i], 2)))*Math.pow((input[j]-center[i]), 2);
				s_weight+=err[j]*Math.exp(-1.0*Math.pow((input[j]-center[i]), 2)/(2.0*Math.pow(delta[i], 2)));
			}
			d_center=eta*weight[i]/Math.pow(delta[i],2)*s_center;
			d_delta=eta*weight[i]/Math.pow(delta[i],3)*s_delta;
			d_weight=eta*s_weight;
			center[i]+=d_center;
			delta[i]+=d_delta;
			weight[i]+=d_weight;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] a= new double[]{1,2,7,10,23,6,15,20,4,11,24,30,13,14,0};
		double[] b= new double[]{20,41,141,200,457,120,298,400,80,220,480,600,260,280,0};
		RBF rbf = new RBF(15, 10, 0.005, a, b);
		rbf.initial();
		int te=5000;
		while(te>=0) {
			rbf.calError();
			rbf.update();
			te--;
		}
		for(double i=0;i<=35;i++) {
			System.out.println(i+"      "+rbf.compute(i));
		}
		
	}
*/
}
