package com.ofirv.worldwarbot;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonReader
{
    static List<Player> createPlayers()
    {
        List<Player> players = new ArrayList<>();
        Gson gson = new Gson();
        try
        {
            String playersString = readFromResource(JsonReader.class, "players.json", Charset.defaultCharset());
            Player[] players1 = gson.fromJson(playersString, Player[].class);
            players.addAll(Arrays.asList(players1));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return players;
    }

    private static String read(String fileName, Charset charset) throws IOException
    {
        StringBuilder returnString = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName), charset); BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                returnString.append("\n");
            }
        }
        return returnString.toString();
    }

    private static String readFromResource(Class classPath, String resource, Charset charset) throws IOException
    {
        InputStream is = classPath.getResourceAsStream("/" + resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line, retrunString = "";
        while ((line = reader.readLine()) != null)
        {
            retrunString += line;
        }

        return retrunString;

//        URL url = classPath.getResource(resource);
//        try
//        {
//            return com.google.common.io.Resources.toString(url, charset);
//        }
//        catch (NullPointerException e)
//        {
//            throw new FileNotFoundException(MessageFormat.format("{0}/{1} (no such resource or directory", classPath.getName(), resource));
//        }
    }
}
