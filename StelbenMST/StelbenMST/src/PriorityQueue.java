import java.util.ArrayList;

public class PriorityQueue 
{
	ArrayList<PriorityQueueNode> vertices;
	ArrayList<Edge> edges;
	public PriorityQueue(Vertex[] verts, ArrayList<Edge> e)
	{
		vertices = new ArrayList<PriorityQueueNode>();
		edges = e;
		//0 node
		vertices.add(new PriorityQueueNode(null, 0));
		vertices.add(new PriorityQueueNode(verts[0], 0));
		for(int i = 1; i < verts.length; i++)
		{
			vertices.add(new PriorityQueueNode(verts[i], Integer.MAX_VALUE));
		}
	}
	
	Edge getNextVertex()
	{
		PriorityQueueNode v = vertices.get(1);
		vertices.remove(1);
		for(int i = 1; i < vertices.size(); i++)
		{
			/*Edge e = v.vertex.neighborWeight(vertices.get(i).vertex.id);
			if(e != null && e.weight < vertices.get(i).priority)
			{
				vertices.get(i).priority = e.weight;
				vertices.get(i).edge = e;
			}*/
			for(int j = 0; j < edges.size(); j++)
			{
				if(edges.get(j).hasVertices(v.vertex.id, vertices.get(i).vertex.id))
				{
					if(edges.get(j).weight < vertices.get(i).priority)
					{
						vertices.get(i).priority = edges.get(j).weight;
						vertices.get(i).edge = edges.get(j);
						edges.remove(j);
						j--;
					}
				}
			}
		}
		sortPriorityQueue();
		return v.edge;
	}
	
	void sortPriorityQueue()
	{
		int size = vertices.size();
		while(size > 1)
		{
			if(vertices.get(size - 1).priority < vertices.get(1).priority)
			{
				swap(1, size - 1);
				sink(1, size);
			}
			size--;
		}
		
	}
	
	void sink(int k, int size)
	{
		while(2 * k <= size)
		{
			int j = 2 * k;
			if(j < size && vertices.get(j).priority < vertices.get(k).priority)
			{
				if(vertices.get(j + 1).priority < vertices.get(j).priority)
				{
					swap(k, j + 1);
					k = j + 1;
				}
				else
				{
					swap(k, j);
					k = j;
				}
				
			}
			else
			{
				k = size;
			}
		}
	}
	
	void swap(int i, int k)
	{
		PriorityQueueNode p = vertices.get(i);
		vertices.set(i, vertices.get(k));
		vertices.set(k, p);
	}
	
	boolean empty()
	{
		if(vertices.size() == 1)
		{
			return true;
		}
		return false;
	}
	
	void printQueue()
	{
		System.out.println("QUEUE");
		for(int i = 1; i < vertices.size(); i++)
		{
			System.out.println(vertices.get(i).vertex.id + " " + vertices.get(i).priority);
		}
		System.out.println("END QUEUE");

	}
}