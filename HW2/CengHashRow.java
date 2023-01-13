public class CengHashRow {

	private String rowPrefix;
	private CengBucket bucket;


	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public String hashPrefix()
	{
		return rowPrefix;
	}
	
	public CengBucket getBucket()
	{
		return bucket;
	}
	
	public boolean isVisited()
	{
		// TODO: Return whether the row is used while searching.
		return false;		
	}

	public CengHashRow(int rowPrefixToBeAdded){
		rowPrefix = Integer.toBinaryString(rowPrefixToBeAdded);
		bucket = new CengBucket(0);
	}
	public CengHashRow(int rowPrefixToBeAdded, int globalDepth, int localDepth){
		String s = Integer.toBinaryString(rowPrefixToBeAdded);
		int sLen = s.length();
		if(sLen > globalDepth){
			s = s.substring(0, globalDepth);
		}
		else if(sLen < globalDepth){
			int number = globalDepth -  sLen;
			s = "0".repeat(number) + s ;
		}
		rowPrefix = s;
		bucket = new CengBucket(localDepth);
	}
	
	// Own Methods

	public void printRow(int pokeKey){
		if(pokeKey == -1){
			System.out.println("\t\"row\": {");
			System.out.printf("\t\t\"hashPref\": %s,\n", rowPrefix);
			bucket.printBucket(-1);
			System.out.printf("\t}", rowPrefix);
		}
		else{
			System.out.printf(",\n");
			System.out.println("\t\"row\": {");
			System.out.printf("\t\t\"hashPref\": %s,\n", rowPrefix);
			bucket.printBucket(-1);
			System.out.printf("\t}", rowPrefix);
		}

	}

	public void setBucket(CengBucket bucketTobeSet) {
		bucket = bucketTobeSet;
	}
}










