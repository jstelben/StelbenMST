import java.util.ArrayList;


public class Vertex {

    public int id;       
    public boolean wasVisited;    
    public Vertex predecessor;
    public ArrayList<Edge> edges;
    
    //Constructor
    public Vertex(int name)
    {
        id = name;
        wasVisited = false;
        predecessor = null;
        edges = new ArrayList<Edge>();
    }

    //Resets some data
    public void reset()
    {
    	wasVisited = false;
    	predecessor = null;
    	edges = new ArrayList<Edge>();
    }
    
    public Edge neighborWeight(int neighborID)
    {
    	for(int i = 0; i < edges.size(); i++)
    	{
    		if(edges.get(i).end.id == neighborID)
    		{
    			return edges.get(i);
    		}
    	}
    	return null;
    }
   
}
