import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class Main
{
	private static Random random = new Random();
	private static ArrayList<Player> players;
	private static ArrayList<WaterGroup> waterGroups = new ArrayList<>();

	public static void main(String[] args)
	{
		//		players = createTestCountry();
		players = JsonReader.createPlayers();
		//		System.out.println(players);
		createWaterGroups();
		int round = 1;
		while (players.size() > 1)
		{
			System.out.println("Round " + round++);
			singleRound();
			//todo add status quo message
			try
			{
				Thread.sleep(10000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println(players.get(0) + " has won!!!");
	}

	private static void singleRound()
	{
		Player attackingPlayer = players.get(random.nextInt(players.size()));
		//		Player attackingPlayer = players.get(0);

		String attackedCountryName;
		ArrayList<String> landBorders = attackingPlayer.getLandBorders();
		ArrayList<String> waterBorders = attackingPlayer.getWaterBorders(waterGroups);
		if (landBorders.isEmpty() || (!waterBorders.isEmpty() && random.nextInt(4) == 0))//todo check if this if is correct
		{
			attackedCountryName = waterBorders.get(random.nextInt(waterBorders.size()));
		}
		else
		{
			attackedCountryName = landBorders.get(random.nextInt(landBorders.size()));
		}

		Player attackedPlayer = findAttackedPlayer(attackedCountryName);
		Country attackedCountry = attackedPlayer.getCountry(attackedCountryName);

		// now we find out who can conquer it
		Country attackingCountry = findAttackingCountry(attackingPlayer.getCountries(), attackedCountry);

		// now to flip a coin
		System.out.println(attackingCountry + "(" + attackingPlayer + ") has attacked " + attackedCountry + "(" + attackedPlayer + ")");

		if (random.nextBoolean())
		{
			System.out.println(attackingCountry + "(" + attackingPlayer + ")" + " has won and conquered " + attackedCountry + " from " + attackedPlayer);
			attackingPlayer.addCountry(attackedCountry);
			attackedPlayer.removeCountry(attackedCountry);
			if (attackedPlayer.getCountries().size() == 0)
			{
				players.remove(attackedPlayer);
				System.out.println(attackedPlayer + " has lost all it's countries and is out of the game");
			}
		}
		else
		{
			System.out.println(attackedCountry + "(" + attackedPlayer + ")" + " has won and conquered " + attackingCountry + " from " + attackingPlayer);

			attackedPlayer.addCountry(attackingCountry);
			attackingPlayer.removeCountry(attackingCountry);
			if (attackingPlayer.getCountries().size() == 0)
			{
				players.remove(attackingPlayer);
				System.out.println(attackingPlayer + " has lost all it's countries and is out of the game");
			}
		}
		//this is the end of a single round
	}

	private static ArrayList<String> removeDuplicates(ArrayList<String> list)
	{
		ArrayList<String> newList = new ArrayList<>();

		for (String element : list)
			if (!newList.contains(element))
				newList.add(element);

		return newList;
	}

	private static Country findAttackingCountry(ArrayList<Country> attackersCountries, Country attackedCountry)
	{
		ArrayList<Country> possibleAttackers = new ArrayList<>();

		for (Country attackerCountry : attackersCountries)
			if (attackerCountry.containsLandBorder(attackedCountry.getName()))
				possibleAttackers.add(attackerCountry);

		for (String waterGroup : attackedCountry.getWaterGroups())
			for (Country attackerCountry : attackersCountries)
				if (attackerCountry.containsWaterGroup(waterGroup))
					possibleAttackers.add(attackerCountry);

		return possibleAttackers.get(random.nextInt(possibleAttackers.size()));
	}

	private static Player findAttackedPlayer(String attackedCountry)
	{
		for (Player player : players)
			if (player.getCountriesNames().contains(attackedCountry))
				return player;

		return null;
	}

	private static void createWaterGroups()
	{
		ArrayList<Country> allCountries = new ArrayList<>();
		for (Player player : players)
			allCountries.addAll(player.getCountries());

		for (Country country : allCountries)
		{
			for (String waterGroupName : country.getWaterGroups())
				addToWaterGroup(country, waterGroupName);
		}
	}

	private static void addToWaterGroup(Country country, String waterGroupName)
	{
		for (WaterGroup waterGroup : waterGroups)
			if (waterGroup.getName().equals(waterGroupName))
			{
				waterGroup.addCountry(country);
				return;
			}

		waterGroups.add(new WaterGroup(waterGroupName, country));
	}

	private static WaterGroup getWaterGroup(String waterGroupName)
	{
		for (WaterGroup waterGroup : waterGroups)
			if (waterGroup.getName().equals(waterGroupName))
				return waterGroup;

		return null;
	}

	private static ArrayList<Player> createTestCountry()
	{
		Gson gson = new Gson();
		ArrayList<Player> players = new ArrayList<>();

		Player israel = new Player("Israel");
		String[] landBorders0 = { "Lebanon", "Syria", "Jordan", "Egypt" };
		String[] waterGroups0 = { "Mediterranean Sea", "Red Sea" };
		israel.addCountry(new Country("Israel", landBorders0, waterGroups0));
		players.add(israel);

		Player lebanon = new Player("Lebanon");
		String[] landBorders1 = { "Syria", "Israel" };
		String[] waterGroups1 = { "Mediterranean Sea" };
		lebanon.addCountry(new Country("Lebanon", landBorders1, waterGroups1));
		players.add(lebanon);

		Player syria = new Player("Syria");
		String[] landBorders2 = { "Lebanon", "Israel", "Jordan" };
		String[] waterGroups2 = { "Mediterranean Sea" };
		syria.addCountry(new Country("Syria", landBorders2, waterGroups2));
		players.add(syria);

		Player jordan = new Player("Jordan");
		String[] landBorders3 = { "Syria", "Israel" };
		String[] waterGroups3 = { "Red Sea" };
		jordan.addCountry(new Country("Jordan", landBorders3, waterGroups3));
		players.add(jordan);

		Player egypt = new Player("Egypt");
		String[] landBorders4 = { "Israel" };
		String[] waterGroups4 = { "Mediterranean Sea", "Red Sea" };
		egypt.addCountry(new Country("Egypt", landBorders4, waterGroups4));
		players.add(egypt);

		System.out.println(gson.toJson(players));

		return players;
	}

}
