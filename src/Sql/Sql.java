package Sql;
import java.sql.*;

import java.util.ArrayList;

import org.junit.Test;

public class Sql {
	private String url;// Url is for the connection of DBS
	private String DBS;// NAME OF DBS
	private String user;// root or sth else
	private String password;
	private String treeName;// name of the tree being operated.
	private int numofnodes;
	private Connection conn; //The class to connect the DBS.
	private static Sql single = null;
	private Sql(){}
	
	public static Sql getInstance() {  
		if (single == null) {    
		      single = new Sql();  
		 }    
		return single;  
	}
	
	public void ConnectSql(){
		setNumofnodes(0);
		this.setUrl("jdbc:mysql://localhost:3306/" + DBS);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword());
			System.out.println("Conceting now!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SetDBS(String names) {
		// TODO Auto-generated method stub
		DBS = names;
	}

	public void InitTree(String name){
		// New A Table;
		numofnodes = 0;
		String sql = null;
		treeName = name;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		sql = "CREATE TABLE "+name+"(node_id int,node_name varchar(100),parent_id int,num_of_children int,node_value int);";  //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷陆锟斤拷锟斤拷锟�
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Table showed!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SetTreeName(String name){
		treeName = name;
	}
	
	public void DestroyTree(String name){
		// New A Table;
		String sql = null;
		ConnectSql();
		sql = "DROP TABLE "+name+";";  
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Destroy tree successfully!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Addnode(String name, int parent,int value){
		// Insert A Record into Table;
		String sql = null;
		int nodeid = 0;
		nodeid = getNumofnodes() + 1;
		ConnectSql();
		sql = "INSERT INTO "+ treeName +"(node_id, node_name, parent_id, num_of_children, node_value) values("+nodeid+", "+"'"+name+"'"+","+parent+","+ 0+","+ value+");";  //mysql锟斤拷锟�
		if(parent != 0){
			Node parentNode = getNode(parent);
			int ChildnumofParent = parentNode.numofChildren + 1;
			SetNodeChildrenNum(parentNode.nodeName,ChildnumofParent);
		}
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Add node successfully!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public Node getNode(String name){
		// Find the Record with this Name;
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = null;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM "+ treeName +" WHERE node_name ="+ "'"+name+ "'"+";";  //mysql锟斤拷锟�
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
				System.out.println("Node gotten!");
			}
			
			conn.close();
			System.out.println("Database was Colsed successfully");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return node;
	}
	
	
	public ArrayList<String> getTreeS(){
		numofnodes = 0;
		String sql = null;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		ArrayList<String> names = new ArrayList<String>();
		sql = "SHOW TABLES;";  
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String temp = rs.getString(1);
			    names.add(temp);
			}
			System.out.println("Table showed!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	public ArrayList<String> getLeaves(String nameoftree){		//通过树名查询树的叶子节点名 返回ArrayList<String>
		numofnodes = 0;
		String sql = null;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		ArrayList<String> names = new ArrayList<String>();
		sql = "SELECT node_name FROM " + nameoftree +" WHERE num_of_children = 0;";  
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String temp = rs.getString(1);
			    names.add(temp);
			}
			System.out.println("Children showed!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	@Test
	public void testGETTREES(){
		ArrayList<String> names = getLeaves("node");
		System.out.println("有"+names.size()+"个");
		for(int i = 0;i < names.size();i ++){
			int n = i+1;
			System.out.println(n+":"+names.get(i));
		}
	}
	
	public Node getNode(int ID){	
		// Find the Record with this Name;
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = null;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM "+ treeName +" WHERE node_Id ="+ ID +";";  //mysql锟斤拷锟�
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
				System.out.println("Got the NODE!");
			}
			
			conn.close();
			System.out.println("Database was Colsed successfully");
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
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM "+ treeName +" WHERE parent_id =" + parentnode.nodeId + ";";  //mysql锟斤拷锟�
		PreparedStatement pstmt;
		ConnectSql();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(parentnode.nodeName+"children：");
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
			System.out.println("Database was Colsed successfully");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SetNodeValue(String name,double value){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ treeName +" set node_value ="+ value + "WHERE node_name = '"+name+"'"; 
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Update done!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	public void SetNodeChildrenNum(String name,double num){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ treeName +" set num_of_children ="+ num + "WHERE node_name = '"+name+"'"; 
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Update successfully");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	
	public void TestnewSql(){
		@SuppressWarnings("unused")
		Sql sql = new Sql();
	}
	
	
	public void TestAddnode(){
		Sql sql = new Sql();
		sql.Addnode("lalal", 1, 2);
	}
	
	
	public void TestgetNode(){
		Node node = getNode("YT");
		node.ShowNode();
	}
	
	
	public void TestshowChildrenofNode(){
		showChildrenofNode("TreatDegree");
	}
	
	
	public void TestSetNodeValue(){
		SetNodeValue("TreatDegree",100);
	}
	
	
	public void TestInitTree(){
		InitTree("xNewexp");
	}
	
	
	public void testgetNumofnodes(){
		SetDBS("DKS");
		setUser("ROOT");
		setPassword("112358");
		SetTreeName("Node");
		System.out.println(getNumofnodes());
	}
	
	public int getNumofnodes() {
		// Find the Record with this Name;
		ConnectSql();
		String sql = null;
		sql = "SELECT count(0) AS totalCount FROM "+ treeName +";";  
		PreparedStatement pstmt;
		numofnodes = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				numofnodes = rs.getInt("totalCount");
			}
			conn.close();
			System.out.println("Database was Colsed successfully");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return numofnodes;
	}

	public void setNumofnodes(int numofnodes) {
		this.numofnodes = numofnodes;
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

	public String getTreename() {
		// TODO Auto-generated method stub
		return treeName;
	}
}

