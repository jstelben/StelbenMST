import java.io.*;
public class MST {

	public static void main(String[] args) throws IOException
	{
		String fileName = args[0];
		//String outputName = args[1];
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(inputStream);
		File f = new File(fileName);
		if(!f.exists())
		{
			System.out.println("Input file not found");
			System.exit(0);
		}
		FileReader file = new FileReader(fileName);
		
	    buffer = new BufferedReader(file);
		
		int numVertices = 0;
		long seed = 0;
		float p = 0;
		try
		{
			String input = buffer.readLine();
			try
			{
				numVertices = Integer.parseInt(input);
				if(numVertices < 2)
				{
					System.out.println("n must be greater than 1");
					System.exit(0);
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("n and seed must be integers");
				System.exit(0);
			}
			input = buffer.readLine();
			try
			{
				seed = Long.parseLong(input);
			}
			catch(NumberFormatException e)
			{
				System.out.println("n and seed must be integers");
				System.exit(0);
			}
			input = buffer.readLine();
			try
			{
				p = Float.parseFloat(input);
				if(p < 0 || p > 1)
				{
					System.out.println("p must be between 0 and 1");
					System.exit(0);
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("p must be a real number");
				System.exit(0);
			}
			
		}
		catch(IOException err)
		{
			System.out.println("Input error.  Using default values 7, 100000, 0.5");
			numVertices = 7;
			seed = 100000;
			p = 0.5f;
		}

		file.close();
		
		long startTime = System.currentTimeMillis();
		Graph g = new Graph(numVertices, seed, p);
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		//FileWriter outputFile = new FileWriter(outputName);
		//PrintWriter out = new PrintWriter(outputFile);
		System.out.println("TEST: n=" + numVertices + ", seed=" + seed + ", p=" + p);
		System.out.println("Time to generate graph: " + duration + " milliseconds\n");
		if(numVertices <= 10)
		{
			g.printGraph();
		}
		
		/*
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.insertionSortMatrix();
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("SORTED EDGES WITH MATRIX USING INSERTION SORT");
		if(numVertices <= 10)
		{
			g.printEdges();
		}
		System.out.println("\nTotal weight = " + g.getTotalEdgeWeight());
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.countSortMatrix();
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("SORTED EDGES WITH MATRIX USING COUNT SORT");
		if(numVertices <= 10)
		{
			g.printEdges();
		}
		System.out.println("\nTotal weight = " + g.getTotalEdgeWeight());
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.startQuickSortMatrix();
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("SORTED EDGES WITH MATRIX USING QUICK SORT");
		if(numVertices <= 10)
		{
			g.printEdges();
		}
		System.out.println("\nTotal weight = " + g.getTotalEdgeWeight());
		System.out.println("Runtime: " + duration + " milliseconds");
		
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.insertionSortList();
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("SORTED EDGES WITH LIST USING INSERTION SORT");
		if(numVertices <= 10)
		{
			g.printEdges();
		}
		System.out.println("\nTotal weight = " + g.getTotalEdgeWeight());
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.countSortList();
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("SORTED EDGES WITH LIST USING COUNT SORT");
		if(numVertices <= 10)
		{
			g.printEdges();
		}
		System.out.println("\nTotal weight = " + g.getTotalEdgeWeight());
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.startQuickSortList();
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("SORTED EDGES WITH LIST USING QUICK SORT");
		if(numVertices <= 10)
		{
			g.printEdges();
		}
		System.out.println("\nTotal weight = " + g.getTotalEdgeWeight());
		System.out.println("Runtime: " + duration + " milliseconds");
		*/
		
		
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.insertionSortMatrix();
		System.out.println("KRUSKAL WITH MATRIX USING INSERTION SORT");
		if(numVertices <= 10)
		{
			g.kruskalAlgortithmWithPrinting();
		}
		else
		{
			g.kruskalAlgortithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.countSortMatrix();
		System.out.println("KRUSKAL WITH MATRIX USING COUNT SORT");
		if(numVertices <= 10)
		{
			g.kruskalAlgortithmWithPrinting();
		}
		else
		{
			g.kruskalAlgortithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.startQuickSortMatrix();
		System.out.println("KRUSKAL WITH MATRIX USING QUICKSORT");
		if(numVertices <= 10)
		{
			g.kruskalAlgortithmWithPrinting();
		}
		else
		{
			g.kruskalAlgortithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.insertionSortList();
		System.out.println("KRUSKAL WITH LIST USING INSERTION SORT");
		if(numVertices <= 10)
		{
			g.kruskalAlgortithmWithPrinting();
		}
		else
		{
			g.kruskalAlgortithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.countSortList();
		System.out.println("KRUSKAL WITH LIST USING COUNT SORT");
		if(numVertices <= 10)
		{
			g.kruskalAlgortithmWithPrinting();
		}
		else
		{
			g.kruskalAlgortithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.startQuickSortList();
		System.out.println("KRUSKAL WITH LIST USING QUICKSORT");
		if(numVertices <= 10)
		{
			g.kruskalAlgortithmWithPrinting();
		}
		else
		{
			g.kruskalAlgortithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		

		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.insertionSortMatrix();
		System.out.println("PRIM WITH ADJACENCY MATRIX");
		if(numVertices <= 10)
		{
			g.matrixPrimAlgorithmWithPrinting();
		}
		else
		{
			g.matrixPrimAlgorithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		
		g.makeGraph(seed, p);
		System.out.println("\n===================================");
		startTime = System.currentTimeMillis();
		g.countSortMatrix();
		System.out.println("PRIM WITH ADJACENCY LIST");
		if(numVertices <= 10)
		{
			g.listPrimAlgorithmWithPrinting();
		}
		else
		{
			g.listPrimAlgorithm();
		}
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		System.out.println("Runtime: " + duration + " milliseconds");
		
		
		//out.close();
		

		
	}

}
