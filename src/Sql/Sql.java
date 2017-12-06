package Sql;
import java.sql.*;

public class Sql {
	private String url;// 数据库的url
	private String user;// 数据库的用户名
	private String password;// 数据库的密码
	private String script;// 建表的命令脚本
	private int numofnodes;// 节点数量
	public Sql(String url, String user, String password){
		script = "sdfads";
		numofnodes = 0;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	private void InitTable(){
		// New A Table;
	}
	public void Addnode(String name, int parent,double value){
		// Insert A Record into Table;
	}
	public Node getNode(String name){
		// Find the Record with this Name;
		String nodeName;int parentId;double value;int nodeId;int numofChildren;
		Node node = new Node(nodeName,parentId,value,nodeId, numofChildren);
		return node;
	}
	
	public void showChildrenofNode(String name){
		// Show the Children of a Node with this Name
		int num = getNode(name).numofChildren;
		for(int i = 0; i < num; i ++){
			
		}
	}
}

