
public class PriorityQueueNode 
{
	Vertex vertex;
	int priority;
	Edge edge;
	
	public PriorityQueueNode(Vertex v, int p)
	{
		vertex = v;
		priority = p;
		edge = null;
	}
}
