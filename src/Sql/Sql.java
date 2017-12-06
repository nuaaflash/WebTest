package Sql;
import java.sql.*;

import org.junit.Test;

public class Sql {
	private String url;// 数据库的url
	private String user;// 数据库的用户名
	private String password;// 数据库的密码
	private String script;// 建表的命令脚本
	private int numofnodes;// 节点数量
	private Connection conn; // 数据库连接对象conn
	
	public Sql(){
		ConnectSql();
	}
	
	public void ConnectSql(){
		setScript("sdfads");
		setNumofnodes(0);
		this.setUrl("jdbc:mysql://localhost:3306/threatDegree");
		this.setUser("root");
		this.setPassword("123");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword());
			System.out.println("连接数据库成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void TestnewSql(){
		Sql sql = new Sql();
	}
	
	@Test
	public void TestAddnode(){
		Sql sql = new Sql();
		sql.Addnode("lalal", 1, 2);
	}
	
	private void InitTable(){
		// New A Table;
	}
	public void Addnode(String name, int parent,int value){
		// Insert A Record into Table;
		String sql = null;
		sql = "INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value) values("+numofnodes+", "+"'"+name+"'"+","+parent+","+ 0+","+ value+");";  //mysql语句
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// 执行sql语句
			System.out.println("插入到数据库成功");
			conn.close();
			System.out.println("关闭数据库成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 创建一个Statement对象
		
	}
	public Node getNode(String name){
		// Find the Record with this Name;
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM node order by node_id";  //mysql语句
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				for(int i=0;i<10;i++){
					String[] record;
					record[i]=rs.getString(i+1);
				}
			}
			System.out.println("插入到数据库成功");
			conn.close();
			System.out.println("关闭数据库成功");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = new Node(nodeName,parentId,value,nodeId, numofChildren);
		return node;
	}
	
	public void showChildrenofNode(String name){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "insert into target(ID,parent,childrenNum) values(10,8,0)";  //mysql语句
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// 执行sql语句
			System.out.println("插入到数据库成功");
			conn.close();
			System.out.println("关闭数据库成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 创建一个Statement对象
		int num = getNode(name).numofChildren;
		for(int i = 0; i < num; i ++){
			
		}
	}

	public int getNumofnodes() {
		return numofnodes;
	}

	public void setNumofnodes(int numofnodes) {
		this.numofnodes = numofnodes;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

