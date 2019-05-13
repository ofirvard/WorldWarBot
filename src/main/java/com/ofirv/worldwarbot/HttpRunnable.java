package com.ofirv.worldwarbot;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpRunnable implements Runnable
{
    static Game game;

    HttpRunnable(Game game)
    {
        this.game = game;
    }

    public void run()
    {
        try
        {
            HttpServer server = HttpServer.create(new InetSocketAddress(8050), 0);
            HttpContext context1 = server.createContext("/lastmove");
            HttpContext context2 = server.createContext("/allmoves");
            context1.setHandler(HttpRunnable::handleRequest1);
            context2.setHandler(HttpRunnable::handleRequest2);
            server.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void handleRequest1(HttpExchange exchange) throws IOException
    {
        String response = game.getLastMove();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequest2(HttpExchange exchange) throws IOException
    {
        String response = game.getAllMoves();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
