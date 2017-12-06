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
	
	private void InitTable(){
		// New A Table;
	}
	
	public void Addnode(String name, int parent,int value){
		// Insert A Record into Table;
		String sql = null;
		int nodeid = getNumofnodes() + 1;
		ConnectSql();
		sql = "INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value) values("+nodeid+", "+"'"+name+"'"+","+parent+","+ 0+","+ value+");";  //mysql语句
		Node parentNode = getNode(parent);
		int ChildnumofParent = parentNode.numofChildren + 1;
		SetNodeChildrenNum(parentNode.nodeName,ChildnumofParent);
		Statement stmt;
		ConnectSql();
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
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = null;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM node WHERE node_name ="+ "'"+name+ "'"+";";  //mysql语句
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				nodeId = rs.getInt(1);
				nodeName = rs.getString(2);
				parentId = rs.getInt(3);
				numofChildren = rs.getInt(4);
				value = rs.getInt(5);
				node = new Node(nodeName,parentId,value,nodeId, numofChildren);
				System.out.println("新建节点成功");
			}
			
			conn.close();
			System.out.println("关闭数据库成功");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return node;
	}
	
	public Node getNode(int ID){	// 通过ID查找节点
		// Find the Record with this Name;
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = null;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM node WHERE node_Id ="+ ID +";";  //mysql语句
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				nodeId = rs.getInt(1);
				nodeName = rs.getString(2);
				parentId = rs.getInt(3);
				numofChildren = rs.getInt(4);
				value = rs.getInt(5);
				node = new Node(nodeName,parentId,value,nodeId, numofChildren);
				System.out.println("新建节点成功");
			}
			
			conn.close();
			System.out.println("关闭数据库成功");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return node;
	}
	
	public void showChildrenofNode(String name){
		// Show the Children of a Node with this Name
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = null;
		String sql = null;
		Node parentnode = getNode(name);
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM node WHERE parent_id =" + parentnode.nodeId + ";";  //mysql语句
		PreparedStatement pstmt;
		ConnectSql();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(parentnode.nodeName+"的子节点：");
			for(int i = 0;i < parentnode.numofChildren;i ++){
				if(rs.next()){
					nodeId = rs.getInt(1);
					nodeName = rs.getString(2);
					parentId = rs.getInt(3);
					numofChildren = rs.getInt(4);
					value = rs.getInt(5);
					node = new Node(nodeName,parentId,value,nodeId, numofChildren);
					node.ShowNode();
				}
			}
			
			conn.close();
			System.out.println("关闭数据库成功");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SetNodeValue(String name,double value){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE node set node_value ="+ value + "WHERE node_name = '"+name+"'";  //mysql语句
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// 执行sql语句
			System.out.println("更新到数据库成功");
			conn.close();
			System.out.println("关闭数据库成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	public void SetNodeChildrenNum(String name,double num){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE node set num_of_children ="+ num + "WHERE node_name = '"+name+"'";  //mysql语句
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// 执行sql语句
			System.out.println("更新到数据库成功");
			conn.close();
			System.out.println("关闭数据库成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	@Test
	public void TestnewSql(){
		Sql sql = new Sql();
	}
	
	@Test
	public void TestAddnode(){
		Sql sql = new Sql();
		sql.Addnode("lalal", 1, 2);
	}
	
	@Test
	public void TestgetNode(){
		Node node = getNode("YT");
		node.ShowNode();
	}
	
	@Test
	public void TestshowChildrenofNode(){
		showChildrenofNode("TreatDegree");
	}
	
	@Test
	public void TestSetNodeValue(){
		SetNodeValue("TreatDegree",100);
	}

	public int getNumofnodes() {
		// Find the Record with this Name;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM node ORDER BY node_id DESC";  //mysql语句
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				numofnodes = rs.getInt(1);
			}
			conn.close();
			System.out.println("关闭数据库成功");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
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

