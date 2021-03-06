package com.ofirv.worldwarbot;

import java.util.ArrayList;
import java.util.List;

public class Main
{
	private static List<Player> players;
	private static List<WaterGroup> waterGroups = new ArrayList<>();
	private static Game game;

	public static void main(String[] args)
	{
		setupGame();
		doAnotherRound();
		//        Thread gameThread = new Thread(new GameRunnable(game));
		//        gameThread.start();
		//        System.out.println("game is running");

		Thread serverThread = new Thread(new HttpRunnable(game));
		serverThread.start();
		System.out.println("server is running");
	}

	private static void setupGame()
	{
		players = JsonReader.createPlayers();
		createWaterGroups();
		game = new Game(players, waterGroups);
	}

	private static void doAnotherRound()
	{
		game.singleRound();
	}

	private static void createWaterGroups()
	{
		List<Country> allCountries = new ArrayList<>();
		for (Player player : players)
			allCountries.addAll(player.getCountries());

		for (Country country : allCountries)
		{
			for (String waterGroupName : country.getWaterGroupsNames())
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
}
