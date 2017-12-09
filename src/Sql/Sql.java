package Sql;
import java.sql.*;

import java.util.ArrayList;

import org.junit.Test;

public class Sql {
	private String url;// 锟斤拷锟捷匡拷锟絬rl
	private String DBS;
	private String user;// 锟斤拷锟捷匡拷锟斤拷没锟斤拷锟�
	private String password;// 锟斤拷锟捷匡拷锟斤拷锟斤拷锟�
	private String treeName;// 锟斤拷锟节碉拷锟斤拷锟斤拷锟�
	private int numofnodes;// 锟节碉拷锟斤拷锟斤拷
	private Connection conn; // 锟斤拷锟捷匡拷锟斤拷锟接讹拷锟斤拷conn
	
	public Sql(){
		treeName = "Node";		// 默锟斤拷锟斤拷胁锟斤拷指锟斤拷锟斤拷系
		ConnectSql();
	}
	
	public void ConnectSql(){
		setNumofnodes(0);
		this.SetDBS("threatDegree");
		this.setUrl("jdbc:mysql://localhost:3306/" + DBS);
		this.setUser("root");
		this.setPassword("123");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword());
			System.out.println("锟斤拷锟斤拷锟斤拷锟捷匡拷晒锟�");
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
			stmt.executeUpdate(sql);// 执锟斤拷sql锟斤拷锟�
			System.out.println("锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷匡拷晒锟�");
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SetTreeName(String name){
		treeName = name;
	}
	
	private void DestroyTree(String name){
		// New A Table;
		String sql = null;
		ConnectSql();
		sql = "DROP TABLE "+name+";";  //锟斤拷锟斤拷锟斤拷锟斤拷锟缴救ワ拷锟斤拷锟�
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// 执锟斤拷sql锟斤拷锟�
			System.out.println("锟斤拷锟斤拷锟捷匡拷删锟斤拷锟斤拷锟斤拷晒锟�");
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 锟斤拷锟斤拷一锟斤拷Statement锟斤拷锟斤拷
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
			stmt.executeUpdate(sql);// 执锟斤拷sql锟斤拷锟�
			System.out.println("锟斤拷锟诫到锟斤拷锟捷匡拷晒锟�");
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 锟斤拷锟斤拷一锟斤拷Statement锟斤拷锟斤拷
		
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
				System.out.println("锟铰斤拷锟节碉拷晒锟�");
			}
			
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return node;
	}
	
	
	public ArrayList getTreeS(){
		numofnodes = 0;
		String sql = null;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		ArrayList names = new ArrayList();
		sql = "SHOW TABLES;";  //锟矫碉拷锟斤拷锟叫憋拷
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);// 执锟斤拷sql锟斤拷锟�
			while (rs.next()) {
				String temp = rs.getString(1);
			    names.add(temp);
			}
			System.out.println("锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷匡拷晒锟�");
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	
	public void testGETTREES(){
		ArrayList names = getTreeS();
		System.out.println("锟斤拷"+names.size()+"锟斤拷锟斤拷");
		for(int i = 0;i < names.size();i ++){
			int n = i+1;
			System.out.println(n+":"+names.get(i));
		}
	}
	
	public Node getNode(int ID){	// 通锟斤拷ID锟斤拷锟揭节碉拷
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
				System.out.println("锟铰斤拷锟节碉拷晒锟�");
			}
			
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
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
			System.out.println(parentnode.nodeName+"锟斤拷锟接节点：");
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
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SetNodeValue(String name,double value){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ treeName +" set node_value ="+ value + "WHERE node_name = '"+name+"'";  //mysql锟斤拷锟�
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// 执锟斤拷sql锟斤拷锟�
			System.out.println("锟斤拷锟铰碉拷锟斤拷锟捷匡拷晒锟�");
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	public void SetNodeChildrenNum(String name,double num){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ treeName +" set num_of_children ="+ num + "WHERE node_name = '"+name+"'";  //mysql锟斤拷锟�
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// 执锟斤拷sql锟斤拷锟�
			System.out.println("锟斤拷锟铰碉拷锟斤拷锟捷匡拷晒锟�");
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	
	public void TestnewSql(){
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
	
	@Test
	public void testgetNumofnodes(){
		System.out.println(getNumofnodes());
	}
	
	public int getNumofnodes() {
		// Find the Record with this Name;
		ConnectSql();
		String sql = null;
		sql = "SELECT count(0) AS totalCount FROM "+ treeName +";";  //mysql锟斤拷锟�
		PreparedStatement pstmt;
		numofnodes = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				numofnodes = rs.getInt("totalCount");
			}
			conn.close();
			System.out.println("锟截憋拷锟斤拷锟捷匡拷晒锟�");
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
}

