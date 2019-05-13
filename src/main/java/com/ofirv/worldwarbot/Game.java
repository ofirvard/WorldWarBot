package com.ofirv.worldwarbot;

import java.util.ArrayList;
import java.util.Random;

public class Game
{
    private Random random = new Random();
    private ArrayList<Player> players;
    private ArrayList<WaterGroup> waterGroups;
    private ArrayList<String> movesHistory = new ArrayList<>();
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

        Player attackingPlayer = players.get(random.nextInt(players.size()));

        String attackedCountryName;
        ArrayList<String> landBorders = attackingPlayer.getLandBorders();
        ArrayList<String> waterBorders = attackingPlayer.getWaterBorders(waterGroups);
        if (landBorders.isEmpty() || (!waterBorders.isEmpty() && random.nextInt(4) == 0))
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
        move += "\n" + attackingCountry + "(" + attackingPlayer + ") has attacked " + attackedCountry + "(" + attackedPlayer + ")";

        if (random.nextBoolean())
        {
            move += "\n" + attackingCountry + "(" + attackingPlayer + ")" + " has won and conquered " + attackedCountry + " from " + attackedPlayer;

            attackingPlayer.addCountry(attackedCountry);
            attackedPlayer.removeCountry(attackedCountry);
            if (attackedPlayer.getCountries().size() == 0)
            {
                players.remove(attackedPlayer);
                move += "\n" + attackedPlayer + " has lost all it's countries and is out of the game";
            }
        }
        else
        {
            move += "\n" + attackedCountry + "(" + attackedPlayer + ")" + " has won and conquered " + attackingCountry + " from " + attackingPlayer;

            attackedPlayer.addCountry(attackingCountry);
            attackingPlayer.removeCountry(attackingCountry);
            if (attackingPlayer.getCountries().size() == 0)
            {
                players.remove(attackingPlayer);
                move += "\n" + attackingPlayer + " has lost all it's countries and is out of the game";
            }
        }
        //this is the end of a single round
        if (players.size() == 1)
            move += "\n" + players.get(0) + " has won!";
        System.out.println(move);
        movesHistory.add(move);
    }


    private Country findAttackingCountry(ArrayList<Country> attackersCountries, Country attackedCountry)
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

    private Player findAttackedPlayer(String attackedCountry)
    {
        for (Player player : players)
            if (player.getCountriesNames().contains(attackedCountry))
                return player;

        return null;
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
