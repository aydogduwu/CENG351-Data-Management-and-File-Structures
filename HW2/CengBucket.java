public class CengBucket {

	private CengPoke[] pokeList;
	private int pokeCount;
	private int localDepth;
	public Boolean[] isDeleted;


	// GUI-Based Methods
	// These methods are required by GUI to work properly.

	public int pokeCount() {
		return pokeCount;
	}

	public CengPoke pokeAtIndex(int index) {
		//Return the corresponding pokemon at the index.
		return pokeList[index];
	}

	public int getHashPrefix() {
		//Return hash prefix length.
		return localDepth;
	}

	public Boolean isVisited() {
		// TODO: Return whether the bucket is found while searching.
		return false;
	}

	// Own Methods

	public CengBucket(int depth) {
		localDepth = depth;
		pokeCount = 0;
		int size = CengPokeKeeper.getBucketSize();
		pokeList = new CengPoke[size];
		isDeleted = new Boolean[size];
		for (int i = 0; i < size; i++) {
			isDeleted[i] = false;
		}
		CengBucketList.bucketList.add(this);

	}

	public void addToBucket(CengPoke toBeAdded) {
		int size = CengPokeKeeper.getBucketSize();
		int flag = 0;
		for (int i = 0; i < size; i++) {
			if (isDeleted[i]) {
				pokeList[i] = toBeAdded;
				isDeleted[i] = false;
				flag = 1;
				pokeCount++;
				break;
			}
		}
		if (flag == 0) {
			pokeList[pokeCount] = toBeAdded;
			pokeCount++;
		}
	}

	void decrementPokeCount() {
		pokeCount--;
	}

	void printBucket(int pokeKey) {
		if (pokeKey == -1) {
			System.out.printf("\t\t\"bucket\": {\n");
			System.out.printf("\t\t\t\"hashLength\": %d,\n", getHashPrefix());
			System.out.printf("\t\t\t\"pokes\": [");

			int size = CengPokeKeeper.getBucketSize();
			int printedPokeCount = 0;
			for (int i = 0; i < size; i++) {
				if (pokeList[i] != null) {
					if (!isDeleted[i]) {
						if (printedPokeCount >= 1) {
							System.out.print(",\n");
						}
						else{
							System.out.printf("\n");
						}
						printedPokeCount++;
						System.out.printf("\t\t\t\t\"poke\": {\n");
						int hashValue = pokeList[i].pokeKey() % CengPokeKeeper.getHashMod();
						String s = Integer.toBinaryString(hashValue);
						int hashLength = Integer.toBinaryString(CengPokeKeeper.getHashMod()).length() - 1;
						int sLen = s.length();

						if (sLen > hashLength) {
							s = s.substring(0, hashLength);
						} else if (sLen < hashLength) {
							int number = hashLength - sLen;
							s = "0".repeat(number) + s;
						}

						System.out.printf("\t\t\t\t\t\"hash\": %s,\n", s);
						System.out.printf("\t\t\t\t\t\"pokeKey\": %d,\n", pokeList[i].pokeKey());
						System.out.printf("\t\t\t\t\t\"pokeName\": %s,\n", pokeList[i].pokeName());
						System.out.printf("\t\t\t\t\t\"pokePower\": %s,\n", pokeList[i].pokePower());
						System.out.printf("\t\t\t\t\t\"pokeType\": %s\n", pokeList[i].pokeType());
						System.out.print("\t\t\t\t}");
					}
				}

			}
			System.out.print("\n\t\t\t]\n");
			System.out.print("\t\t}\n");
		}

		else{
			int size = CengPokeKeeper.getBucketSize();
			for (int i = 0; i < size; i++) {
				if (pokeList[i] != null) {
					if (!isDeleted[i]) {
						if (pokeList[i].pokeKey() == pokeKey) {
							System.out.printf("\t\t\t\t\"poke\": {\n");
							int hashValue = pokeList[i].pokeKey() % CengPokeKeeper.getHashMod();
							String s = Integer.toBinaryString(hashValue);
							int hashLength = Integer.toBinaryString(CengPokeKeeper.getHashMod()).length() - 1;
							int sLen = s.length();
							if (sLen > hashLength) {
								s = s.substring(0, hashLength);
							} else if (sLen < hashLength) {
								int number = hashLength - sLen;
								s = "0".repeat(number) + s;
							}

							System.out.printf("\t\t\t\t\t\"hash\": %s,\n", s);
							System.out.printf("\t\t\t\t\t\"pokeKey\": %d,\n", pokeList[i].pokeKey());
							System.out.printf("\t\t\t\t\t\"pokeName\": %s,\n", pokeList[i].pokeName());
							System.out.printf("\t\t\t\t\t\"pokePower\": %s,\n", pokeList[i].pokePower());
							System.out.printf("\t\t\t\t\t\"pokeType\": %s\n", pokeList[i].pokeType());
							System.out.print("\t\t\t\t}");
						}
					}
				}

			}
			System.out.print("\n\t\t\t]\n");
			System.out.print("\t\t}\n");
		}
	}


}
