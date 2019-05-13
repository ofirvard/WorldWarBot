import java.util.ArrayList;

public class Main
{
    private static ArrayList<Player> players;
    private static ArrayList<WaterGroup> waterGroups = new ArrayList<>();

    public static void main(String[] args)
    {
        //todo make RESTful
        players = JsonReader.createPlayers();
        createWaterGroups();
        Game game = new Game(players, waterGroups);
        game.run();
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
