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

	//初始化权值、数据中心和扩展常数
	public void initial() {
		double d_max = -1.0, temp = 0;	//d_max为数据中心的最大距离
		Random random = new Random();
		for(int i=0;i<h_num;i++) {
			weight[i]=2*random.nextDouble()-1.0;
			center[i]=input[random.nextInt(s_num)];
		}
		for(int i=0;i<s_num;i++) {
			for(int j=0;j<s_num;j++) {
				if(i!=j) {
					temp=calDistance_sq(input[i], input[j]);
					if(temp>d_max)
						d_max=temp;
				}
			}
		}
		for(int k=0;k<h_num;k++) {
			delta[k]=Math.pow(d_max/(2.0*((double)h_num)), 0.5);
		}
	}
	
	
	//计算两个向量（输入）之间的距离平方的函数，仅仅在确定初始扩展常数时使用
	private double calDistance_sq(double a[], double b[]) {
		double d = 0.0;
		for(int i=0;i<d_num;i++) {
			d+=Math.pow((a[i]-b[i]), 2.0);
		}
		return d;
	}
	
	public double compute(double x[]) {
		double y = 0;
		double temp = 0;
		for(int i=0;i<h_num;i++) {
			temp = cal_x_minus_center_sq(x, center[i]);
			y+=weight[i]*Math.exp(-1.0*temp/(2.0*delta[i]*delta[i]));
		}
		return y;
	}
	
	//计算每个样本的误差
	public void calError() {
		for(int i=0;i<s_num;i++) {
			err[i]=output[i]-compute(input[i]);
		}
	}
	
	private double cal_x_minus_center_sq(double x[], double center[]) {
		double temp = 0;
		for(int i=0;i<d_num;i++) {
			temp+=Math.pow((x[i]-center[i]),2.0);
		}
		return temp;
	}
	
	public void update() {
		double d_center[], d_delta, d_weight;
		double s_center[], s_delta, s_weight;
		d_center = new double[d_num];
		s_center = new double[d_num];
		double temp;
		for(int i=0;i<h_num;i++) {
			d_delta=d_weight=s_delta=s_weight=0.0;
			for(int k=0;k<d_num;k++) {
				d_center[k] = 0.0;
				s_center[k] = 0.0;
			}
			for(int j=0;j<s_num;j++) {
				temp = cal_x_minus_center_sq(input[j], center[i]);
				for(int k=0;k<d_num;k++) {
					s_center[k]+=err[j]*Math.exp(-1.0*temp/(2.0*Math.pow(delta[i], 2)))*(input[j][k]-center[i][k]);
				}
				//s_center+=err[j]*Math.exp(-1.0*Math.pow((input[j]-center[i]), 2)/(2.0*Math.pow(delta[i], 2)))*temp;
				s_delta+=err[j]*Math.exp(-1.0*temp/(2.0*Math.pow(delta[i], 2.0)))*temp;
				s_weight+=err[j]*Math.exp(-1.0*temp/(2.0*Math.pow(delta[i], 2.0)));
			}
			for(int k=0;k<d_num;k++) {
				d_center[k]=eta*weight[i]/Math.pow(delta[i],2.0)*s_center[k];
				center[i][k]+=d_center[k];
			}
			//d_center=eta*weight[i]/Math.pow(delta[i],2)*s_center;
			d_delta=eta*weight[i]/Math.pow(delta[i],3)*s_delta;
			d_weight=eta*s_weight;
			//center[i]+=d_center;
			delta[i]+=d_delta;
			weight[i]+=d_weight;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] a= new double[][] { {1,2,3}, {4,5,6}, {7,8,9}, {10,11,12} };
		double[] b= new double[]{6,61,84,95};
		RBF rbf = new RBF(4, 4, 3, 0.001, a, b);
		rbf.initial();
		int te=20000;
		while(te>=0) {
			rbf.calError();
			rbf.update();
			te--;
		}
		double[] c= new double[3];
		System.out.println("      "+rbf.compute(new double[]{4,5,6}));
		System.out.println("done");
	}

}
