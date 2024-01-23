import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {
    private Player player;
    private Game game;
    private Wumpus wumpus;
  

    @Before
    public void setUp() {
        player = new Player();
        wumpus = new Wumpus();
        game = new Game(wumpus);

    }

    @Test
    public void testKillWumpus() 
    throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException{
        
        Field wumpusField = Wumpus.class.getDeclaredField("wumpusLocation");
        wumpusField.setAccessible(true);
        int wumpusPlace = 1;
        wumpusField.set(wumpus, wumpusPlace);

        Method killWumpusMethod = Player.class.getDeclaredMethod("killWumpus", int.class, Game.class, Wumpus.class);
        killWumpusMethod.setAccessible(true);

        boolean result = (boolean) killWumpusMethod.invoke(player, 1, game, wumpus);
        Assert.assertFalse("You've killed the Wumpus, you won!", result);
    }
    
    @Test
    public void testDontKillWumpus() 
    throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException{
        
        Field wumpusField = Wumpus.class.getDeclaredField("wumpusLocation");
        wumpusField.setAccessible(true);
        int wumpusPlace = 1;
        wumpusField.set(wumpus, wumpusPlace);

        Method killWumpusMethod = Player.class.getDeclaredMethod("killWumpus", int.class, Game.class, Wumpus.class);
        killWumpusMethod.setAccessible(true);

        boolean result = (boolean) killWumpusMethod.invoke(player, 2, game, wumpus);
        Assert.assertTrue("", result);
    }

    /**
     * Checks if we killed bat or not
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testKillBat()
            throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {

        Field batField = Game.class.getDeclaredField("bats");
        batField.setAccessible(true);
        int[] batPlace = { 1, 2, 3 };
        batField.set(game, batPlace);

        Method killBatMethod = Player.class.getDeclaredMethod("killBat", int.class, Game.class);
        killBatMethod.setAccessible(true);

        // Call the killBat method
        killBatMethod.invoke(player, 2, game);

        // Check if any bats have the value -1
        int[] batsArr = game.getBatsArr();
        boolean anyBatKilled = Arrays.stream(batsArr).anyMatch(bat -> bat == -1);

        // Assert the condition
        Assert.assertTrue("At least one bat should have been killed", anyBatKilled);
    }
    /**
     * Checks if we loose the game if we finish all of our arrows
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void playerFinishItsArrows()
            throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {

        Field arrowsField = Player.class.getDeclaredField("arrows");
        arrowsField.setAccessible(true);
        arrowsField.set(player, 1);

        Method arrowCounterMethod = Player.class.getDeclaredMethod("arrowCounter");
        arrowCounterMethod.setAccessible(true);
        boolean result = (boolean) arrowCounterMethod.invoke(player);

        Assert.assertFalse("You've used all of your arrows. Now you're useless. You lost!", result);
    }


    /**
     * Checks if wumpus relocates after player make a shot next to it
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testScareWumpus() throws NoSuchFieldException, IllegalAccessException {
        Field wumpusField = Wumpus.class.getDeclaredField("wumpusLocation");
        wumpusField.setAccessible(true);
        int wumpusPlace = 1;
        wumpusField.set(wumpus, wumpusPlace);
        
        boolean result = wumpus.scareWumpus(10, 2, game);
        if (!result) {
            Assert.assertFalse("You missed. Wumpus heared you. He came to your cave and killed you.", result);
        } else {
            // If scareWumpus returns false, check that the player was killed
            Assert.assertNotEquals("You missed. Wumpus heared you. He dislocated to other cave.", wumpusPlace, wumpusField.get(wumpus));
        }
    }


    /**
     * Checks if we actually move through caves
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testWalking_ValidUserInput() throws NoSuchFieldException, IllegalAccessException{
        Field playerLocation = Player.class.getDeclaredField("playerLocation");
        playerLocation.setAccessible(true);
        int location = 1;
        playerLocation.set(player, location);
        int userInput = 2;

        boolean result = player.isRightStep(userInput, game);
        Assert.assertTrue(result);
    }

    /**
     * Checks the case if player try to move to unaccesible cavewumpusLocation
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testWalking_InvalidUserInput() throws NoSuchFieldException, IllegalAccessException{
        Field playerLocation = Player.class.getDeclaredField("playerLocation");
        playerLocation.setAccessible(true);
        int location = 1;
        playerLocation.set(player, location);
        int userInput = 15;

        boolean result = player.isRightStep(userInput, game);
        Assert.assertFalse(result);
    }

    /**
     * Checks if we loose the game if we fall in a pit
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testPitTrap_PlayerFallsInPit() throws NoSuchFieldException, IllegalAccessException {
        Field pitsField = Game.class.getDeclaredField("pits");
        pitsField.setAccessible(true);
        pitsField.set(game, new int[] { 1, 2 });

        boolean result = game.pitTrap(1);
        Assert.assertFalse(result);
    }

    /**
     * Checks if we don't loose the game if we don't fall in a pit. (wtf?)
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testPitTrap_PlayerDoesNotFallInPit() throws NoSuchFieldException, IllegalAccessException {
        Field pitsField = Game.class.getDeclaredField("pits");
        pitsField.setAccessible(true);
        pitsField.set(game, new int[] { 1, 2 });

        boolean result = game.pitTrap(3);
        Assert.assertTrue(result);
    }

    /**
     * Checks if we die when we walk in a Wumpus cave.
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testWumpusTrap_PlayerEntersWumpus() throws NoSuchFieldException, IllegalAccessException {
        Field wumpusField = Wumpus.class.getDeclaredField("wumpusLocation");
        wumpusField.setAccessible(true);
        wumpusField.set(wumpus, 3);

        boolean result = wumpus.WumpusTrap(3);
        Assert.assertFalse(result);
    }

}