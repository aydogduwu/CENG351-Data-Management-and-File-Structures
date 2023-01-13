import java.util.ArrayList;

public class CengBucketList {
	public static ArrayList<CengBucket> bucketList;

	public CengBucketList()
	{
		bucketList = new ArrayList<CengBucket>(1);
	}

	public void deletePoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}

	public void addPoke(CengPoke poke)
	{
		// TODO: Empty Implementation
	}
	
	public void searchPoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}
	
	public void print()
	{
		// TODO: Empty Implementation
	}
	
	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int bucketCount()
	{
		// Return all bucket count.
		return bucketList.size();
	}
	
	public CengBucket bucketAtIndex(int index)
	{
		// TODO: Return corresponding bucket at index.
		return bucketList.get(index);
	}

	public void add(CengBucket cengBucket) {
		bucketList.add(cengBucket);
	}

	// Own Methods
}
