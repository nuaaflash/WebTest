package Sql;
import java.sql.*;
import java.util.ArrayList;

import org.junit.Test;

public class Sql {
	private String url;// ���ݿ��url
	private String user;// ���ݿ���û���
	private String password;// ���ݿ������
	private String treeName;// ���ڵ������
	private int numofnodes;// �ڵ�����
	private Connection conn; // ���ݿ����Ӷ���conn
	
	public Sql(){
		treeName = "Node";		// Ĭ����в��ָ����ϵ
		ConnectSql();
	}
	
	public void ConnectSql(){
		setNumofnodes(0);
		this.setUrl("jdbc:mysql://localhost:3306/dks");
		this.setUser("root");
		this.setPassword("112358");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword());
			System.out.println("�������ݿ�ɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void InitTree(String name){
		// New A Table;
		numofnodes = 0;
		String sql = null;
		treeName = name;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		sql = "CREATE TABLE "+name+"(node_id int,node_name varchar(100),parent_id int,num_of_children int,node_value int);";  //����������½�����
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// ִ��sql���
			System.out.println("�����������ݿ�ɹ�");
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void DestroyTree(String name){
		// New A Table;
		String sql = null;
		ConnectSql();
		sql = "DROP TABLE "+name+";";  //���������ɾȥ����
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// ִ��sql���
			System.out.println("�����ݿ�ɾ������ɹ�");
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ����һ��Statement����
	}
	
	public void Addnode(String name, int parent,int value){
		// Insert A Record into Table;
		String sql = null;
		int nodeid = 0;
		if(numofnodes != 0){
			nodeid = getNumofnodes() + 1;
		}
		ConnectSql();
		sql = "INSERT INTO "+ treeName +"(node_id, node_name, parent_id, num_of_children, node_value) values("+nodeid+", "+"'"+name+"'"+","+parent+","+ 0+","+ value+");";  //mysql���
		if(parent != 0){
			Node parentNode = getNode(parent);
			int ChildnumofParent = parentNode.numofChildren + 1;
			SetNodeChildrenNum(parentNode.nodeName,ChildnumofParent);
		}
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// ִ��sql���
			System.out.println("���뵽���ݿ�ɹ�");
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ����һ��Statement����
		
	}
	public Node getNode(String name){
		// Find the Record with this Name;
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = null;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM "+ treeName +" WHERE node_name ="+ "'"+name+ "'"+";";  //mysql���
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
				System.out.println("�½��ڵ�ɹ�");
			}
			
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
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
		sql = "SHOW TABLES;";  //�õ����б�
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);// ִ��sql���
			while (rs.next()) {
				String temp = rs.getString(1);
			    names.add(temp);
			}
			System.out.println("�����������ݿ�ɹ�");
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	@Test
	public void testGETTREES(){
		ArrayList names = getTreeS();
		System.out.println("��"+names.size()+"����");
		for(int i = 0;i < names.size();i ++){
			int n = i+1;
			System.out.println(n+":"+names.get(i));
		}
	}
	
	public Node getNode(int ID){	// ͨ��ID���ҽڵ�
		// Find the Record with this Name;
		String nodeName = null;int parentId = 0;double value = 0;int nodeId = 0;int numofChildren = 0;
		Node node = null;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM "+ treeName +" WHERE node_Id ="+ ID +";";  //mysql���
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
				System.out.println("�½��ڵ�ɹ�");
			}
			
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
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
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM "+ treeName +" WHERE parent_id =" + parentnode.nodeId + ";";  //mysql���
		PreparedStatement pstmt;
		ConnectSql();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(parentnode.nodeName+"���ӽڵ㣺");
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
			System.out.println("�ر����ݿ�ɹ�");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SetNodeValue(String name,double value){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ treeName +" set node_value ="+ value + "WHERE node_name = '"+name+"'";  //mysql���
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// ִ��sql���
			System.out.println("���µ����ݿ�ɹ�");
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	public void SetNodeChildrenNum(String name,double num){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ treeName +" set num_of_children ="+ num + "WHERE node_name = '"+name+"'";  //mysql���
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);// ִ��sql���
			System.out.println("���µ����ݿ�ɹ�");
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
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
	

	public int getNumofnodes() {
		// Find the Record with this Name;
		ConnectSql();
		String sql = null;
		sql = "SELECT node_id, node_name, parent_id, num_of_children, node_value FROM"+ treeName +"ORDER BY node_id DESC";  //mysql���
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				numofnodes = rs.getInt(1);
			}
			conn.close();
			System.out.println("�ر����ݿ�ɹ�");
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

