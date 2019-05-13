package worldwarbot;

public class GameRunnable implements Runnable
{
    Game game;

    GameRunnable(Game game)
    {
        this.game = game;
    }

    public void run()
    {
        while (game.getNumberOfPlayers() > 1)
        {
            game.singleRound();
            try
            {
                Thread.sleep(10000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
