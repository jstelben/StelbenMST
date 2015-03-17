import java.util.*;
import java.io.*;

class AdjacentVertex
{
	public Vertex v;
	public int weight;
}

public class Graph 
{
	// attributes
    private Vertex[] vertices;  // vertex array
    private int[][] matrix;      // adjacency matrix
    private ArrayList<ArrayList<AdjacentVertex>> list; //Adjacency List
    private int numVertices;    // max number of vertices in the graph
    private ArrayList<Edge> edges; //List of edges
    private Partition part; 

    //Constructor
    public Graph(int count, long seed, float p)
    {
        numVertices = count;
        
        //Set up Vertex array
        vertices = new Vertex[numVertices];
        edges = new ArrayList<Edge>();
        for(int i = 0; i < numVertices; i++)
        {
        	vertices[i] = new Vertex(i);
        }
        
        //Keeps making a random graph until it is connected
        makeGraph(seed, p);
        depthFirst(false);
        while(!isConnected())
        {
        	makeGraph(seed, p);
        	depthFirst(false);
        }
        makeList();
    }

    //Creates a new, random graph
    void makeGraph(long seed, float p)
    {
    	resetVertices();
    	matrix = new int[numVertices][numVertices];
    	edges = new ArrayList<Edge>();
    	Random edgeRand = new Random(seed);
    	Random weightRand = new Random(2 * seed);
    	for(int i = 0; i < numVertices; i++)
    	{
    		for(int j = i + 1; j < numVertices; j++)
    		{
    			if( i != j)
    			{
    				double prob = edgeRand.nextDouble();
    				if(prob <= p)
    				{
    					int weight = 1 + weightRand.nextInt(numVertices);
    					matrix[i][j] = weight;
    			        matrix[j][i] = weight;
    			        Edge e = new Edge(vertices[i], vertices[j], weight);
    			        vertices[i].edges.add(e);
    			        edges.add(e);
    			        e = new Edge(vertices[j], vertices[i], weight);
    			        vertices[j].edges.add(e);
    				}
    			}
    		}
    	}
    }
    
    //Create Adjacency List
    void makeList()
    {
    	list = new ArrayList<ArrayList<AdjacentVertex>>();
    	for(int i = 0; i < numVertices; i++)
    	{
    		ArrayList<AdjacentVertex> temp = new ArrayList<AdjacentVertex>();
    		for(int j = 0; j < numVertices; j++)
    		{
    			if(matrix[i][j] > 0)
    			{
    				AdjacentVertex v = new AdjacentVertex();;
    				v.weight = matrix[i][j];
    				v.v = vertices[j];
    				temp.add(v);
    			}
    		}
    		list.add(temp);
    	}
    }
    
    //Makes an Edge list using the Adjacency list
    void makeEdgesFromList()
    {
    	edges = new ArrayList<Edge>();
    	for(int i = 0; i < list.size(); i++)
    	{
    		for(int j = 0; j < list.get(i).size(); j++)
    		{
    			Edge e = new Edge(vertices[i], list.get(i).get(j).v, list.get(i).get(j).weight);
    			if(!duplicateEdge(e))
    			{
    				edges.add(e);
    			}
    			
    		}
    		
    	}
    }
    
    void makeFullEdgesFromList()
    {
    	edges = new ArrayList<Edge>();
    	for(int i = 0; i < list.size(); i++)
    	{
    		for(int j = 0; j < list.get(i).size(); j++)
    		{
    			Edge e = new Edge(vertices[i], list.get(i).get(j).v, list.get(i).get(j).weight);
    				edges.add(e);

    			
    		}
    		
    	}
    }
    
    //Makes an Edge list using the matrix
    void makeEdgesFromMatrix()
    {
    	edges = new ArrayList<Edge>();
    	for(int i = 0; i < numVertices; i++)
    	{
    		for(int j = 0; j < numVertices; j++)
    		{
    			if(matrix[i][j] != 0)
    			{
    				Edge e = new Edge(vertices[i], vertices[j], matrix[i][j]);
    				if(!duplicateEdge(e))
        			{
        				edges.add(e);
        			}
    			}
    		}
    	}
    }

    void makeFullEdgesFromMatrix()
    {
    	edges = new ArrayList<Edge>();
    	for(int i = 0; i < numVertices; i++)
    	{
    		for(int j = 0; j < numVertices; j++)
    		{
    			if(matrix[i][j] != 0)
    			{
    				Edge e = new Edge(vertices[i], vertices[j], matrix[i][j]);

        				edges.add(e);

    			}
    		}
    	}
    }

    
    //Check if the mirrored edge already exists
    boolean duplicateEdge(Edge e)
    {
    	for(int i = 0; i < edges.size(); i++)
    	{
    		if(edges.get(i).weight == e.weight)
    		{
    			if(edges.get(i).start.id == e.end.id && edges.get(i).end.id == e.start.id)
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    //Depth First Search
    //The boolean was used for debugging
    void depthFirst(boolean print)
    {
    	resetVisitedVertices();
    	String predecessors = "";
    	Stack<Vertex> stack = new Stack<Vertex>();

        stack.push(vertices[0]);
        vertices[0].wasVisited = true;
        
        if(print)
        {
        	System.out.print(" " + vertices[0].id);
        	predecessors += "-1";
        }

        while (stack.size() > 0)
        {
            Vertex currentVertex = stack.peek();
            Vertex v = GetAdjacentUnvisitedVertex(currentVertex);
            if (v == null)
            {
                stack.pop();
            }
            else
            {
                v.wasVisited = true;
                stack.push(v);
                v.predecessor = currentVertex;
                if(print)
                {
                	System.out.print(" " + v.id);
                	predecessors += " " + v.predecessor.id;
                }
            }
        }
        
        if(print)
        {
        	System.out.println("\nPredecessors:");
        	System.out.println(predecessors);
        }
    }
    
    //Checks if every Vertex was visited in the search
    boolean isConnected()
    {
    	for(int i = 0; i < numVertices; i++)
    	{
    		if(!vertices[i].wasVisited)
    		{
    			return false;
    		}
    	}
    	return true;
    }

    //Returns a Vertex connected to v that was not visited
    Vertex GetAdjacentUnvisitedVertex(Vertex v)
    {
        for (int i = 0; i < numVertices; i++)
        {
            if (matrix[v.id][i] > 0 && !vertices[i].wasVisited)
            {
                return vertices[i];
            }
        }
        return null;
    }

    //Resets all vertices
    void resetVertices()
    {
        for (int i = 0; i < numVertices; i++)
        {
            vertices[i].reset();
            
        }
    }
    
    //Makes all vertices unvisited
    void resetVisitedVertices()
    {
    	for (int i = 0; i < numVertices; i++)
        {
            vertices[i].wasVisited = false;
        }
    }
    
    //Print the graph as an adjacency matrix and list
    public void printGraph()
    {
    	System.out.println("The graph as an adjacency matrix:");
    	for(int i = 0; i < numVertices; i++)
    	{
    		for(int j = 0; j < numVertices; j++)
    		{
    			if(matrix[i][j] != 10)
    			{
    				System.out.print(" ");
    			}
    			System.out.print("  " + matrix[i][j]);
    		}
    		System.out.print("\n\n");
    	}
    	
    	System.out.println("The graph as an adjacency list:");
    	for(int i = 0; i < numVertices; i++)
    	{
    		System.out.print(i + "->");
    		for(int j = 0; j < list.get(i).size(); j++)
    		{
    			System.out.print("  " + list.get(i).get(j).v.id + "(" + list.get(i).get(j).weight + ")");
    		}
    		System.out.print("\n");
    	}
    	
    	System.out.println("\nDepth-First Search:\nVertices");
    	//depthFirst(true);
    	System.out.println("Vertices:");
    	for(int i = 0; i < numVertices; i++)
    	{
    		System.out.print(" " + vertices[i].id);
    	}
    	System.out.println("\nPredecessors:");
    	for(int i = 0; i < numVertices; i++)
    	{
    		if(vertices[i].predecessor != null)
    		{
    			System.out.print(" " + vertices[i].predecessor.id);
    		}
    		else
    		{
    			System.out.print("-1");
    		}
    	}
    }
    
    //Insertionsort for List
    public void insertionSortList()
    {
    	makeEdgesFromList();
    	insertionSort();
    }
    
    //InsertionSort for Matrix
    public void insertionSortMatrix()
    {
    	makeEdgesFromMatrix();
    	insertionSort();
    }
    
    //Performs InsertionSort on EdgeList
    void insertionSort()
    {
    	for(int i = 0; i < edges.size(); i++)
    	{
    		for(int j = i; j > 0; j--)
    		{
    			if(edges.get(j).compare(edges.get(j-1)))
    			{
    				Edge temp = edges.remove(j);
    				edges.add(j - 1, temp);
    			}
    		}
    	}
    }
    
    //Countsort for List
    public void countSortList()
    {
    	makeEdgesFromList();
    	countSort();
    }
    
    //Countsort for Matrix
    public void countSortMatrix()
    {
    	makeEdgesFromMatrix();
    	countSort();
    }
    
    //Performs CountSort on EdgeList
    void countSort()
    {
    	int size = edges.size();
    	int[] aux = new int[size];
    	int max = 0;
    	for(int i = 0; i < size; i++)
    	{
    		if(edges.get(i).weight > max)
    		{
    			max = edges.get(i).weight;
    		}
    		aux[i] = 0;
    	}
    	max += 2;
    	int[] count = new int[max];
    	for(int i = 0; i < max; i++)
    	{
    		count[i] = 0;
    	}
    	for(int i = 0; i < size; i++)
    	{
    		count[edges.get(i).weight + 1] += 1;
    	}
    	for(int i = 1; i < max - 1; i++)
    	{
    		count[i + 1] += count[i];
    	}
    	for(int i = 0; i < size; i++)
    	{
    		if(count[edges.get(i).weight] < size)
    		{
    			aux[count[edges.get(i).weight]] = edges.get(i).weight;
    		}
    	}
    	for(int i = 1; i < size; i++)
    	{
    		if(aux[i] == 0)
    		{
    			aux[i] = aux[i-1];
    		}
    	}
    	ArrayList<Edge> tempEdges = new ArrayList<Edge>();
    	
    	for(int i = 0; i < size; i++)
    	{
    		boolean found = false;
    		int j = 0;
    		while(!found)
    		{
    			if(edges.get(j).weight == aux[i])
    			{
    				found = true;
    				Edge temp = edges.remove(j);
    				tempEdges.add(temp);
    			}
    			j++;
    		}
    	}
    	edges = new ArrayList<Edge>();
    	while(!tempEdges.isEmpty())
    	{
    		edges.add(tempEdges.remove(0));
    	}
    }
    
    //Quicksort for List
    void startQuickSortList()
    {
    	makeEdgesFromList();
    	quickSort();
    }
    
    //Quicksort for Matrix
    void startQuickSortMatrix()
    {
    	makeEdgesFromMatrix();
    	quickSort();
    }
    
    //Performs QuickSort for EdgeList
    void quickSort()
    {
    	Edge[] e = new Edge[edges.size()];
    	for(int i = 0; i < edges.size(); i++)
    	{
    		e[i] = edges.get(i);
    	}
    	shuffleArray(e);
    	quickSort(e, 0, edges.size() - 1);
    	
    	ArrayList<Edge> tempEdges = new ArrayList<Edge>();
    	for(int i = 0; i < e.length; i++)
    	{
    		boolean found = false;
    		int j = 0;
    		while(!found)
    		{
    			if(edges.get(j).weight == e[i].weight)
    			{
    				found = true;
    				Edge temp = edges.remove(j);
    				tempEdges.add(temp);
    			}
    			j++;
    		}
    	}
    	edges = new ArrayList<Edge>();
    	while(!tempEdges.isEmpty())
    	{
    		edges.add(tempEdges.remove(0));
    	}	
    }
    
    //Partition for Quicksort
    int partition(Edge[] e, int low, int high)
    {
    	int i = low;
    	int j = high + 1;
    	while(true)
    	{
    		while(e[++i].compare(e[low]))
    		{
    			if(i == high)
    			{
    				break;
    			}
    				
    		}
    		while(e[low].compare(e[--j]))
    		{
    			
    			if(j == low)
    			{
    				break;
    			}
    			
    		}
    		if( i >= j)
    		{
    			break;
    		}
    		Edge temp = e[j];
    		e[j] = e[i];
    		e[i] = temp;
    	}
    	Edge temp = e[j];
		e[j] = e[low];
		e[low] = temp;
		return j;
    }
    
    //Recursive Quick Sort Function
    void quickSort(Edge[] e, int low, int high)
    {
    	if(high <= low) return;
    	int j = partition(e, low, high);
    	quickSort(e, low, j - 1);
    	quickSort(e, j + 1, high);
    }
    
    //Knuth Shuffle
    void shuffleArray(Edge[] e)
    {
    	Random r = new Random(System.currentTimeMillis());
    	for(int i = e.length - 1; i > 0; i--)
    	{
    		int j = r.nextInt(i);
    		Edge temp = e[j];
    		e[j] = e[i];
    		e[i] = temp;
    	}
    }
    
    //Prints all edges from EdgeList
    void printEdges()
    {
    	for(int i = 0; i < edges.size(); i++)
    	{
    		edges.get(i).print();
    	}
    }
    
    void printEdges(ArrayList<Edge> e)
    {
    	for(int i = 0; i < e.size(); i++)
    	{
    		e.get(i).print();
    	}
    }
    
    //Used for debugging
    void printEdgeArray(Edge[] e)
    {
    	System.out.println("\nEdge Array Weights");
    	for(int i = 0; i < e.length; i++)
    	{
    		e[i].print();
    	}
    }
    
    
    int getTotalEdgeWeight()
    {
    	int total = 0;
    	for(int i = 0; i < edges.size(); i++)
    	{
    		total += edges.get(i).weight;
    	}
    	return total;
    }
    
    int getTotalEdgeWeight(ArrayList<Edge> e)
    {
    	int total = 0;
    	for(int i = 0; i < e.size(); i++)
    	{
    		total += e.get(i).weight;
    	}
    	return total;
    }

    public void kruskalAlgortithm()
    {
    	ArrayList<Edge> mst = new ArrayList<Edge>();
    	int edgeSize = edges.size();
    	part = new Partition(edgeSize);
    	while(edges.size() > 0)
    	{
    		checkCycle(mst, edges.remove(0));
    	}
    	System.out.println("\nTotal weight of MST using Kruskal: " + getTotalEdgeWeight(mst));
    }
    
    public void kruskalAlgortithmWithPrinting()
    {
    	ArrayList<Edge> mst = new ArrayList<Edge>();
    	int edgeSize = edges.size();
    	part = new Partition(edgeSize);
    	while(edges.size() > 0)
    	{
    		checkCycle(mst, edges.remove(0));
    	}
    	printEdges(mst);
    	System.out.println("\nTotal weight of MST using Kruskal: " + getTotalEdgeWeight(mst));
    }
    
    void checkCycle(ArrayList<Edge> mst, Edge e)
    {
    	int root1 = part.find(e.start.id);
    	int root2 = part.find(e.end.id);
    	if(root1 != root2)
    	{
    		mst.add(e);
    		part.union(root1, root2);
    	}
    }
    
    void matrixPrimAlgorithmWithPrinting()
    {
    	makeFullEdgesFromMatrix();
    	primAlgorithmWithPrinting();
    }
    
    void listPrimAlgorithmWithPrinting()
    {
    	makeFullEdgesFromList();
    	primAlgorithmWithPrinting();
    }
    
    void matrixPrimAlgorithm()
    {
    	makeEdgesFromMatrix();
    	primAlgorithm();
    }
    
    void listPrimAlgorithm()
    {
    	makeEdgesFromList();
    	primAlgorithm();
    }
    
    /*void primAlgorithm()
    {
    	
    	heap = new BinaryHeap(edges, vertices.length);
    	ArrayList<Edge> mst;
    	mst = new ArrayList<Edge>();
    	while(!heap.chooseVertex(mst));
    	System.out.println("Total weight of MST using Prim: " + getTotalEdgeWeight(mst));
    }
    
    void primAlgorithmWithPrinting()
    {
    	System.out.println("EDGES");
    	for(int i = 0; i < edges.size(); i++)
    	{	
    		edges.get(i).print();
    	}
    	System.out.println("ENDEDGES");
    	heap = new BinaryHeap(edges, vertices.length);
    	ArrayList<Edge> mst;
    	mst = new ArrayList<Edge>();
    	while(!heap.chooseVertex(mst));
    	printEdges(mst);
    	System.out.println("Total weight of MST using Prim: " + getTotalEdgeWeight(mst));
    }*/
    PriorityQueue queue;
    
    void primAlgorithm()
    {
    	queue = new PriorityQueue(vertices, edges);
    	ArrayList<Edge> mst = new ArrayList<Edge>();
    	while(!queue.empty())
    	{
    		mst.add(queue.getNextVertex());	
    	}
    	System.out.println("\nTotal weight of MST using Prim: " + getTotalEdgeWeight(mst));
    }
    
    void primAlgorithmWithPrinting()
    {
    	queue = new PriorityQueue(vertices, edges);
    	ArrayList<Edge> mst = new ArrayList<Edge>();
    	while(!queue.empty())
    	{
    		Edge e = queue.getNextVertex();
    		if(e != null)
    		{
    			mst.add(e);	
    		}
    	}
    	sortEdgeList(mst);
    	for(int i = 0; i < mst.size(); i++)
    	{
    		mst.get(i).print();
    	}
    	System.out.println("\nTotal weight of MST using Prim: " + getTotalEdgeWeight(mst));
    }
    
    void sortEdgeList(ArrayList<Edge> e)
    {
    	for(int i = 0; i < e.size(); i++)
    	{
    		for(int j = i; j > 0; j--)
    		{
    			if(e.get(j).end.id < e.get(j-1).end.id)
    			{
    				Edge temp = e.remove(j);
    				e.add(j - 1, temp);
    			}
    		}
    	}
    }
}
