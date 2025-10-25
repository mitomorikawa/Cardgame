package cardgame.test;

import cardgame.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPlayer{
    private Player player;
    private Counter counter;
    private int n_of_players = 1;

    @Before
    public void setUp(){
            counter = new Counter();
    }


}