package com.ofirv.worldwarbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game
{
	private Random random = new Random();
	private List<Player> players;
	private List<WaterGroup> waterGroups;
	private List<String> movesHistory = new ArrayList<>();
	private int roundNumber = 1;

	Game(ArrayList<Player> players, ArrayList<WaterGroup> waterGroups)
	{
		this.players = players;
		this.waterGroups = waterGroups;
	}

	public void main()
	{
		int round = 1;
		while (players.size() > 1)
		{
			System.out.println("Round " + round++);
			singleRound();
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

	void singleRound()
	{
		String move = "Round " + roundNumber++;

		// which country will attack
		Player attackingPlayer = players.get(random.nextInt(players.size()));

		// who it will attack
		String attackedCountryName = getAttackedCountryName(attackingPlayer.getLandBorders(), attackingPlayer.getWaterBorders(waterGroups));
		Player attackedPlayer = findAttackedPlayer(attackedCountryName);
		Country attackedCountry = attackedPlayer.getCountry(attackedCountryName);

		// now we find out who can conquer it
		Country attackingCountry = findAttackingCountry(attackingPlayer.getCountries(), attackedCountry);

		// now to flip a coin
		move += String.format("\n%s(%s) has attacked %s(%s)", attackingCountry, attackingPlayer, attackedCountry, attackedPlayer);

		if (random.nextBoolean())
			landTransfer(attackingPlayer, attackingCountry, attackedPlayer, attackedCountry, move);
		else
			landTransfer(attackedPlayer, attackedCountry, attackingPlayer, attackingCountry, move);

		//this is the end of a single round
		if (players.size() == 1)
			move += String.format("\n%s has won!", players.get(0));
		System.out.println(move);
		movesHistory.add(move);
	}

	private void landTransfer(Player conqueringPlayer, Country conqueringCountry, Player conqueredPlayer, Country conqueredCountry, String move)
	{
		move += String.format("\n%s(%s) has won and conquered %s from %s", conqueringCountry, conqueringPlayer, conqueredCountry, conqueredPlayer);

		conqueringPlayer.addCountry(conqueredCountry);
		conqueredPlayer.removeCountry(conqueredCountry);
		if (conqueredPlayer.getCountries().size() == 0)
		{
			players.remove(conqueredPlayer);
			move += String.format("\n%s has lost all it's countries and is out of the game", conqueredPlayer);
		}
	}

	private String getAttackedCountryName(ArrayList<String> landBorders, ArrayList<String> waterBorders)
	{
		if (landBorders.isEmpty() || (!waterBorders.isEmpty() && random.nextInt(4) == 0))
			return waterBorders.get(random.nextInt(waterBorders.size()));
		else
			return landBorders.get(random.nextInt(landBorders.size()));
	}

	private Country findAttackingCountry(ArrayList<Country> attackersCountries, Country attackedCountry)
	{
		ArrayList<Country> possibleAttackers = new ArrayList<>();

		attackersCountries.stream().filter(t -> t.containsLandBorder(attackedCountry.getName())).forEach(possibleAttackers::add);

		attackedCountry.getWaterGroups().forEach(waterGroup -> attackersCountries.stream().filter(t -> t.containsWaterGroup(waterGroup)).forEach(possibleAttackers::add));

		return possibleAttackers.get(random.nextInt(possibleAttackers.size()));
	}

	private Player findAttackedPlayer(String attackedCountry)
	{
		return players.stream().filter(t -> t.getCountriesNames().contains(attackedCountry)).findFirst().orElse(null);
	}

	String getLastMove()
	{
		return movesHistory.get(movesHistory.size() - 1);
	}

	String getAllMoves()
	{
		return movesHistory.toString();
	}

	int getNumberOfPlayers()
	{
		return players.size();
	}
}
