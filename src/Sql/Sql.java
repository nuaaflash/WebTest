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
	private double initvalue = 0;
	private static Sql single = null;
	private ArrayList<String> Instruct;	// 用来存储指令 用于批量执行jsp中对树的操作 减少刷新次数
	
	public Sql(){}
	
	public static Sql getInstance() {  
		if (single == null) {    
		      single = new Sql();  
		 }    
		return single;  
	}
	
	public boolean ConnectSql(){
		setNumofnodes(0);
		this.setUrl("jdbc:mysql://localhost:3306/" + DBS);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword());
			System.out.println("Conceting now!");
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setInstruct(ArrayList<String> instructions){
		Instruct = instructions;
	}
	
	public void doInstructions(){
		String sql = null;
		for(int i = 0;i < Instruct.size();i ++){
			ConnectSql();
			sql = Instruct.get(i);  
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				System.out.println((i+1)+":"+sql+" done!");
				conn.close();
				System.out.println("Database was Colsed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		sql = "CREATE TABLE "+name+"(node_id int,node_name varchar(100),parent_id int,num_of_children int,node_value numeric(18,2));";  //合成sql命令 添加记录
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
	
	public void Removenode(int deleteID){
		// Insert A Record into Table;
		String sql = null;
		ConnectSql();
		sql = "DELETE FROM "+ treeName +" WHERE node_id = " + deleteID +" OR parent_id = " + deleteID +";";  //mysql锟斤拷锟�
		Node pnode = getNode(deleteID);
		int parent = 0;
		if(pnode!= null){
			parent = pnode.parentId;							// 将父节点的子节点数目减一
		}
		if(parent != 0){
			Node parentNode = getNode(parent);
			if(parentNode != null){
				int ChildnumofParent = parentNode.numofChildren - 1;
				SetNodeChildrenNum(parentNode.nodeName,ChildnumofParent);
			}
		}
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Remove node successfully!");
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
		System.out.println("name"+"1231232424444444444444444444444444444444444444444444444444");
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
		sql = "SELECT node_name FROM " + nameoftree +" WHERE num_of_children = 0 ORDER BY node_id;";  
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
	
	public ArrayList<String> getNodes(String nameoftree){		//通过树名查询树的所有节点名 返回ArrayList<String>
		numofnodes = 0;
		String sql = null;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		ArrayList<String> names = new ArrayList<String>();
		sql = "SELECT node_name FROM " + nameoftree + " ORDER BY node_id";  
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
	
	public ArrayList<Node> getIndexNodetoShow(){		//得到需要在指标体系里展示的节点 返回ArrayList<Node>
		numofnodes = 0;
		String sql = null;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		ArrayList<Node> cnode = new ArrayList<Node>();
		 int notempty = 0;
		sql = "SELECT node_name FROM " + treeName ; 
		System.out.println(sql);
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Node temp = getNode(rs.getString(1));
			    cnode.add(temp);
			    notempty = 1;
			    ConnectSql();
			}
			System.out.println("Node not ZERO gotten!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(notempty == 0){
			cnode = null;
		}
		return cnode;
	}
	
	public ArrayList<Node> getChildrenofNode(String nameofnode){		//通过节点得到树的节点的子节点 返回ArrayList<Node>
		numofnodes = 0;
		String sql = null;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		System.out.println("here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+nameofnode);
		Node thenode = getNode(nameofnode);
		thenode.ShowNode();
		ConnectSql();
		ArrayList<Node> cnode = new ArrayList<Node>();
		sql = "SELECT node_name FROM " + treeName +" WHERE parent_id = "+ thenode.nodeId +";"; 
		System.out.println(sql);
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Node temp = getNode(rs.getString(1));
			    cnode.add(temp);
			    ConnectSql();
			}
			System.out.println("Children gotten!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnode;
	}
	
	public ArrayList<Node> getNodetoShow(){		//得到树的要在view中展示的节点 返回ArrayList<Node>
		numofnodes = 0;
		String sql = null;
		//ConnectSql();
		//DestroyTree(name);
		ConnectSql();
		ArrayList<Node> cnode = new ArrayList<Node>();
		sql = "SELECT node_name FROM " + treeName +" WHERE node_value != " + initvalue;  // 不为默认值的时候 这个节点的值有写入 需要显示
		System.out.println(sql);
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Node temp = getNode(rs.getString(1));
			    cnode.add(temp);
			    ConnectSql();
			}
			System.out.println("Node not ZERO gotten!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnode;
	}
	
	@Test
	public void testGETTREES(){
		Sql sql = Sql.getInstance();
		sql.SetTreeName("node");
		sql.SetDBS("threatDegree");
		sql.setUser("root");
		sql.setPassword("123");
		Node xx = sql.getNode("威胁度");
		xx.ShowNode();
		sql.InitNodeValue(0);

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
	
	public void RenameNode(String name,String newname){				// 重命名节点
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ treeName +" set node_name = '"+ newname + "' WHERE node_name = '"+name+"'"; 
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Rename done!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	
	public void InitNodeValue(double value){				// 将所有Node的value初始化为 参数value
		// Show the Children of a Node with this Name
		initvalue = value;
		String sql = null;
		sql = "UPDATE "+ treeName +" set node_value ="+ value; 
		Statement stmt;
		ConnectSql();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Initialization done!");
			conn.close();
			System.out.println("Database was Colsed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	public void SetNodeValue(String tree_name, int ID,double value){
		// Show the Children of a Node with this Name
		String sql = null;
		sql = "UPDATE "+ tree_name +" set node_value ="+ value + "WHERE node_id = "+ID; 
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
	
	public double getinitvalue() {
		// TODO Auto-generated method stub
		return initvalue;
	}
}

