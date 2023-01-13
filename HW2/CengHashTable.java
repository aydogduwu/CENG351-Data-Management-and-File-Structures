import java.util.ArrayList;

public class CengHashTable {

	private static ArrayList<CengHashRow> rowList;
	private static int globalDepth;


	public CengHashTable()
	{
		//  Create a hash table with only 1 row.
		rowList = new ArrayList<>(1);
		globalDepth = 0;
		CengHashRow newrow = new CengHashRow(0);
		rowList.add(newrow);
	}

	public void deletePoke(Integer pokeKey)
	{
		// Empty Implementation
		int hashValue = pokeKey % CengPokeKeeper.getHashMod();
		String s = Integer.toBinaryString(hashValue);
		int sLen = s.length();
		int deneme = (int) (Math.log(CengPokeKeeper.getHashMod()) / Math.log(2));
		if(sLen < deneme){
			int number = deneme -  sLen;
			s = "0".repeat(number) + s;
		}
		sLen = s.length();

		if(sLen > globalDepth){
			s = s.substring(0, globalDepth);
			if(globalDepth == 0)  s = "0";
		}

		else if(sLen < globalDepth){
			int number = globalDepth -  sLen;
			s = "0".repeat(number)+ s;
		}
		int size = rowList.size();
		for (int i = 0; i < size; i++) {
			if(rowList.get(i).hashPrefix().equals(s)){
				CengBucket c = rowList.get(i).getBucket();
				for (int j = 0; j < CengPokeKeeper.getBucketSize(); j++) {
					if(c.pokeAtIndex(j).pokeKey() == pokeKey){
						c.isDeleted[j] = true;
						c.decrementPokeCount();
						break;
					}
				}
				break;
			}
		}

		int size1 = CengBucketList.bucketList.size();
		int count = 0;
		for (int i = 0; i < size1; i++) {
			if(CengBucketList.bucketList.get(i).pokeCount() == 0) {
				count++;
			}
		}

		System.out.println("delete\": {");
		System.out.printf("\t\"emptyBucketNum\": %d\n", count);
		System.out.println("}");


	}

	public void addPoke(CengPoke poke)
	{			
		// TODO: Empty Implementation
		if (globalDepth == 0 && rowList.get(0).getBucket().pokeCount() < CengPokeKeeper.getBucketSize()){
			rowList.get(0).getBucket().addToBucket(poke);
		}
		else{
			int hashValue = poke.pokeKey() % CengPokeKeeper.getHashMod();
			String s = Integer.toBinaryString(hashValue);

			int sLen = s.length();
			int deneme = (int) (Math.log(CengPokeKeeper.getHashMod()) / Math.log(2));
			if(sLen < deneme){
				int number = deneme -  sLen;
				s = "0".repeat(number) + s;
			}
			sLen = s.length();
			if(globalDepth == 0){
				s = "0";
			}
			else if(sLen > globalDepth){
				s = s.substring(0, globalDepth);
			}
			else if(sLen < globalDepth){
				int number = globalDepth -  sLen;
				s = "0".repeat(number)+ s ;
			}
			int rowSize  = rowList.size();
			for (int i = 0; i < rowSize ; i++) {
				if(rowList.get(i).hashPrefix().compareTo(s) == 0 && rowList.get(i).getBucket().pokeCount() < CengPokeKeeper.getBucketSize()){ // bulduğun rowda yer varsa
					rowList.get(i).getBucket().addToBucket(poke);
				}
				else if(rowList.get(i).hashPrefix().compareTo(s) == 0 && rowList.get(i).getBucket().getHashPrefix() == globalDepth){ // bulduğum yer doluysa ve gloabal depth == local depth
					ArrayList<CengHashRow> newRowList = new ArrayList<CengHashRow>(1);
					CengBucket b = rowList.get(i).getBucket();
					globalDepth++;

					for (int j = 0; j < rowSize*2; j+=2) {
						int newLocalDepth = rowList.get(j/2).getBucket().getHashPrefix() + 1;
						CengHashRow row11 = new CengHashRow(j,globalDepth,newLocalDepth);
						CengHashRow row12 = new CengHashRow(j+1,globalDepth,newLocalDepth);

						if(j != 2*i){
							row11.setBucket(rowList.get(j/2).getBucket());
							row12.setBucket(rowList.get(j/2).getBucket());
						}
						newRowList.add(row11);
						newRowList.add(row12);

					}
					rowList = newRowList;
					int zie =  CengPokeKeeper.getBucketSize();
					int count = 0;
					while (zie > 0){
						addPoke(b.pokeAtIndex(count));
						count++;
						zie--;
					}
					addPoke(poke);
					break;
				}

				// IF GLOBALDEPTH GREATER THAN LOCALDEPTH

				else if (rowList.get(i).hashPrefix().compareTo(s) == 0){
					CengBucket b = rowList.get(i).getBucket();
					int localDepth = b.getHashPrefix();
					CengBucket aydo1 = new CengBucket(localDepth + 1);
					CengBucket aydo2 = new CengBucket(localDepth + 1);

					int split = 1;
					for (int j = 0; j < globalDepth-localDepth; j++) {
						split *= 2;
					}
					int loopCount = split/2;

					String s1 = rowList.get(i).hashPrefix().substring(0,localDepth);

					int q = i;
					while (q != 0 ){
						if (rowList.get(q-1).hashPrefix().substring(0,localDepth).compareTo(s1) == 0){
							q--;
						}
						else{
							break;
						}
					}
					for (int j = q; j < q+split; j++) {
						if(j >= q+loopCount){
							rowList.get(j).setBucket(aydo2);
						}
						else {
							rowList.get(j).setBucket(aydo1);
						}
					}
					for (int j = 0; j < b.pokeCount(); j++) {
						addPoke(b.pokeAtIndex(j));
					}
					addPoke(poke);
				}
			}

		} // end of else

		for (int i = 0; i < CengBucketList.bucketList.size(); i++) {
			CengBucket b = CengBucketList.bucketList.get(i);
			int flag = 0;
			for (int j = 0; j < rowList.size(); j++) {
				if(b == rowList.get(j).getBucket()){
					flag = 1;
					break;
				}
			}
			if(flag == 0){
				CengBucketList.bucketList.remove(i);
			}
		}
		
	}
	
	public void searchPoke(Integer pokeKey)
	{
		// Empty Implementation
		int hashValue = pokeKey % CengPokeKeeper.getHashMod();
		String s = Integer.toBinaryString(hashValue);
		int sLen = s.length();
		if(sLen > globalDepth){
			s = s.substring(0, globalDepth);
		}

		else if(sLen < globalDepth){
			int number = globalDepth -  sLen;
			s = "0".repeat(number)+ s ;
		}

		ArrayList<CengHashRow> rows = new ArrayList<>(1);
		CengBucket c = new CengBucket(1);
		for (int i = 0; i < rowList.size(); i++) {
			if(rowList.get(i).hashPrefix().equals(s)){
				for (int j = 0; j < rowList.get(i).getBucket().pokeCount(); j++) {
					if(rowList.get(i).getBucket().pokeAtIndex(j).pokeKey() == pokeKey &&  !rowList.get(i).getBucket().isDeleted[j]){
						c = rowList.get(i).getBucket();
						break;
					}
				}
			}
		}

		for (int i = 0; i < rowList.size(); i++) {
			CengBucket c1 = rowList.get(i).getBucket();
			if(c == c1){
				rows.add(rowList.get(i));
			}
		}
		if(rows.size()==0){
			System.out.println("\"search\": {\n}");
		}
		else{
			System.out.println("\"search\": {");
			int size = rows.size();
			for (int i = 0; i < size; i++) {
				if(i == 0){
					rows.get(i).printRow(-1);
				}
				else{
					rows.get(i).printRow(pokeKey);

				}
			}
			System.out.printf("\n}");
		}
	}


	public void addToRowList(CengHashRow rowToBeAdded){
		rowList.add(rowToBeAdded);
	}
	
	public void print()
	{
		// Empty Implementation
		System.out.println("\"table\": {");
		int size = rowList.size();
		int printedRowCount = 0;
		for (int i = 0; i < size; i++) {
			if(printedRowCount >= 1) {
				System.out.println(",");
			}
			printedRowCount++;
			rowList.get(i).printRow(-1);
		}
		System.out.println("\n}");
	}

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int prefixBitCount()
	{
		//Return table's hash prefix length.
		return globalDepth;
	}
	
	public int rowCount()
	{
		// Return the count of HashRows in table.
		return rowList.size();
	}
	
	public CengHashRow rowAtIndex(int index)
	{
		// Return corresponding hashRow at index.
		return rowList.get(index);
	}
	
	// Own Methods
}
