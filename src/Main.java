import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean runGame = true;
        Player player = new Player();
        GameStarter gameStarter = new GameStarter();
        Wumpus wumpus0 = new Wumpus();
        Wumpus wumpus1 = new Wumpus();
        Bat bat = new Bat();
        Game game = new Game(wumpus0, wumpus1);
        
        
        System.out.println("Do you want to see instructions? (Y/N)");
        String initInstructions = scanner.nextLine();
        gameStarter.starterInstructions(initInstructions);
        
        // Main loop
        while (runGame) {
            player.locationOutput(game);
            game.checkWarnings(player,game);
            player.getPlace();
            char shotOrWalk = game.getUserInput();
            switch (shotOrWalk) {
                case 'W':
                    game.moveCave(game, player);
                    if(!game.pitTrap(player.getPlace())){
                        runGame = false; // Checks if we got in a pit
                        break;
                    } else if(!wumpus0.WumpusTrap(player.getPlace())||!wumpus1.WumpusTrap(player.getPlace())){
                        runGame = false; // Checks if we got in a Wumpus cave
                        break;
                    } else if(!bat.batTrap(player.getPlace(),game,player)){
                        runGame = false;
                    }
                    break;
                case 'S':
                    runGame = player.shootArrow(game);
                    break;
                default:
                    System.out.println("There's no such option.");
                    break;
            }

            System.out.println("\n-------------------------------------------------------------------------\n");
        }
    }
}
