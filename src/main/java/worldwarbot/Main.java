package worldwarbot;

import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    private static ArrayList<Player> players;
    private static ArrayList<WaterGroup> waterGroups = new ArrayList<>();
    private static Game game;

    public static void main(String[] args) throws IOException
    {
        setupGame();
        doAnotherRound();
//        Thread gameThread = new Thread(new worldwarbot.GameRunnable(game));
//        gameThread.start();
//        System.out.println("game is running");

        Thread serverThread = new Thread(new HttpRunnable(game));
        serverThread.start();
        System.out.println("server is running");
    }

    static void setupGame()
    {
        players = JsonReader.createPlayers();
        createWaterGroups();
        game = new Game(players, waterGroups);
    }

    static void doAnotherRound()
    {
        game.singleRound();
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
}
