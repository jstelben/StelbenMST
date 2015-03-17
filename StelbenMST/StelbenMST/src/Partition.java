
public class Partition 
{
	int partition[];
	int rank[];
	int size;
	
	public Partition(int s)
	{
		size = s;
		partition = new int[size];
		rank = new int[size];
		for(int i = 0; i < size; i++)
		{
			rank[i] = 0;
			partition[i] = i;
		}
	}
	
	public int find(int index)
	{
		if(index != partition[index])
		{
			partition[index] = find(partition[index]);
		}
		return partition[index];
	}
	
	public void union(int x, int y)
	{
		if(rank[x] > rank[y])
		{
			partition[y] = x;
		}
		else
		{
			partition[x] = y;
			if(rank[x] == rank[y])
			{
				rank[y] = rank[y] + 1;
			}
		}
	}
}
