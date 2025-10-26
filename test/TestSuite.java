package cardgame.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestCard.class, TestCardDeck.class, TestCardGame.class, 
        TestCounter.class, TestGeneratePack.class, TestPlayer.class})
public class TestSuite{}