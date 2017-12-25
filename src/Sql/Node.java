package Sql;

public class Node{
	public String nodeName;
	public int nodeId;
	public int numofChildren;
	public int parentId;
	public double value;
	public int level;
	public Node(String nodeName,int parentId,double value,int nodeId, int numofChildren,int level){
		this.nodeName = nodeName;
		this.parentId = parentId;
		this.value = value;
		this.nodeId = nodeId;
		this.numofChildren = numofChildren;
		this.level = level;
	}
	public void ShowNode(){
		System.out.println("node_name:"+nodeName+" parent_id:"+parentId+" node_value:"+value+" node_id:"+nodeId+" num_of_children:"+numofChildren+"node_level"+level);
	}
}
