import java.io.*;
import java.util.ArrayList;


public class CengPokeParser {

	public static ArrayList<CengPoke> parsePokeFile(String filename)
	{
		ArrayList<CengPoke> pokeList = new ArrayList<CengPoke>();
		try{
			FileReader file = new FileReader(filename);
			BufferedReader reader = new BufferedReader(file);

			String input = reader.readLine();


			while(input != null) {
				String[] splitted = input.split("\t");
				CengPoke pokemon = new CengPoke(Integer.parseInt(splitted[1]), splitted[2], splitted[3], splitted[4]);
				pokeList.add(pokemon);

				input = reader.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}


		return pokeList;
	}
	
	public static void startParsingCommandLine() throws IOException
	{
		// TODO: Start listening and parsing command line -System.in-.

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String input = reader.readLine();
		String[] splitted = input.split("\t");
		int controller = splitted[0].compareTo("quit");
		String operation = splitted[0];
		while (controller != 0){

			if(operation.compareTo("add") == 0){
				CengPoke pokemon = new CengPoke(Integer.parseInt(splitted[1]), splitted[2], splitted[3], splitted[4]);
				CengPokeKeeper.addPoke(pokemon);
				input = reader.readLine();
				splitted = input.split("\t");
				controller = splitted[0].compareTo("quit");
				operation = splitted[0];
			}

			else if (operation.compareTo("search") == 0){

				int key = Integer.parseInt(splitted[1]);
				CengPokeKeeper.searchPoke(key);
				input = reader.readLine();
				splitted = input.split("\t");
				controller = splitted[0].compareTo("quit");
				operation = splitted[0];
			}

			else if (operation.compareTo("delete") == 0){
				int key = Integer.parseInt(splitted[1]);
				CengPokeKeeper.deletePoke(key);
				input = reader.readLine();
				splitted = input.split("\t");
				controller = splitted[0].compareTo("quit");
				operation = splitted[0];
			}
			else if (operation.compareTo("print") == 0) {
				CengPokeKeeper.printEverything();
				input = reader.readLine();
				splitted = input.split("\t");
				controller = splitted[0].compareTo("quit");
				operation = splitted[0];
			}
		}

		// There are 5 commands:
		// 1) quit : End the app. Print nothing, call nothing.
		// 2) add : Parse and create the poke, and call CengPokeKeeper.addPoke(newlyCreatedPoke).
		// 3) search : Parse the pokeKey, and call CengPokeKeeper.searchPoke(parsedKey).
		// 4) delete: Parse the pokeKey, and call CengPokeKeeper.removePoke(parsedKey).
		// 5) print : Print the whole hash table with the corresponding buckets, call CengPokeKeeper.printEverything().

		// Commands (quit, add, search, print) are case-insensitive.
	}
}
