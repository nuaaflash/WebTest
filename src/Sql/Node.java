package Sql;

public class Node{
	public String nodeName;
	public int nodeId;
	public int numofChildren;
	public int parentId;
	public double value;
	public Node(String nodeName,int parentId,double value,int nodeId, int numofChildren){
		this.nodeName = nodeName;
		this.parentId = parentId;
		this.value = value;
		this.nodeId = nodeId;
		this.numofChildren = numofChildren;
	}
}
