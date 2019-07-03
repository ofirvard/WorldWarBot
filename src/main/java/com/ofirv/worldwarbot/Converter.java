package com.ofirv.worldwarbot;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Converter
{
    private static List<Player> players;
    private static List<WaterGroup> waterGroups = new ArrayList<>();

    public static void main(String[] args)
    {
        Gson gson = new Gson();
        players = JsonReader.createPlayers();
        createWaterGroups();

        List<SimplePlayer> players2 = new ArrayList<>();

        for (Player player : players)
        {
//            SimplePlayer player1 = new SimplePlayer(player);
            players2.add(new SimplePlayer(player, waterGroups));
        }

        System.out.println(gson.toJson(players2));
    }

    private static void createWaterGroups()
    {
        List<Country> allCountries = new ArrayList<>();
        for (Player player : players)
            allCountries.addAll(player.getCountries());

        for (Country country : allCountries)
            for (String waterGroupName : country.getWaterGroupsNames())
                addToWaterGroup(country, waterGroupName);

//            borders = borders.stream().distinct().collect(Collectors.toList());
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

class SimpleCountry
{
    String name;
    List<String> borders;

    SimpleCountry(Country country, List<WaterGroup> waterGroups)
    {
        this.name = country.getName();
        this.borders = country.getBorders(waterGroups);
    }
}

class SimplePlayer
{
    String name;
    List<SimpleCountry> countries;

    SimplePlayer(Player player, List<WaterGroup> waterGroups)
    {
        this.name = player.getName();
        this.countries = new ArrayList<>();
        for (Country country : player.getCountries())
        {
            countries.add(new SimpleCountry(country, waterGroups));
        }
    }
}