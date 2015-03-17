import java.util.ArrayList;


public class Edge {
	
	public Vertex start, end;
	public int weight;
	
	//Constructor
	public Edge(Vertex v1, Vertex v2, int w)
	{
		start = v1;
		end = v2;
		weight = w;
	}
	
	//if this edge should be before the passed in edge, return true
	public boolean compare(Edge e)
	{
		if(weight == e.weight)
		{
			if(start.id == e.start.id)
			{
				if(end.id < e.end.id)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(start.id < e.start.id)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(weight < e.weight)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//Print the Edge
	void print()
	{
		System.out.println(start.id + " " + end.id + " weight = " + weight);
	}

	boolean hasVertices(int i, int j)
	{
		if(i == start.id)
		{
			if(j == end.id)
			{
				return true;
			}
		}
		return false;
	}

}
