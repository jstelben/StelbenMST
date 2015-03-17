
public class Node {
	Node parent;
	int value;
	
	public Node(int val)
	{
		value = val;
	}
	
	public Node(int val, Node n)
	{
		value = val;
		parent = n;
	}
	
	public void changeParent(Node n)
	{
		parent = n;
	}
}
