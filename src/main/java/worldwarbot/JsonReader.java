package worldwarbot;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonReader
{
    static ArrayList<Player> createPlayers()
    {
        ArrayList<Player> players = new ArrayList<Player>();
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
        String line;
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName), charset); BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
        {
            while ((line = bufferedReader.readLine()) != null)
            {
                returnString.append("\n");
            }
        }
        return returnString.toString();
    }

    private static String readFromResource(Class classPath, String resource, Charset charset) throws IOException
    {
        URL url = classPath.getResource(resource);
        try
        {
            return com.google.common.io.Resources.toString(url, charset);
        }
        catch (NullPointerException e)
        {
            throw new FileNotFoundException(MessageFormat.format("{0}/{1} (no such resource or directory", classPath.getName(), resource));
        }
    }
}
