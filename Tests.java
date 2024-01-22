import org.junit.Assert;
import org.junit.Test;


public class Tests {
    Player player = new Player();
    Wumpus wumpus = new Wumpus();
    Game game = new Game(wumpus);
    // @BeforeEach
    // public void initialize() {
    //     game = new Game();
    // }

    @Test
    public void testWalking_ValidUserInput(){
        int caveIndex = 1;
        int userInput = 2;
        
        boolean result = player.isRightStep(caveIndex, userInput, game);
        Assert.assertTrue(result);
    }


    @Test
    public void testWalking_InvalidUserInput(){
        int caveIndex = 1;
        int userInput = 15;
        
        boolean result = player.isRightStep(caveIndex, userInput, game);
        Assert.assertFalse(result);
    }

    @Test
    public void testPitTrap_PlayerFallsInPit() {
        game.setPits(new int[]{1, 2}); 
        boolean result = game.pitTrap(1);
        Assert.assertFalse(result);
    }

    @Test
    public void testPitTrap_PlayerDoesNotFallInPit() {
        game.setPits(new int[]{1, 2});
        boolean result = game.pitTrap(3);
        Assert.assertTrue(result);
    }
 
    
}