import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean runGame = true;
        Wumpus wumpus0 = new Wumpus();
        Wumpus wumpus1 = new Wumpus();
        Game game = new Game(wumpus0, wumpus1);
        Player player = new Player(game);
        GameStarter gameStarter = new GameStarter();
        Bat bat = new Bat();
        
        
        System.out.println("Do you want to see instructions? (Y/N)");
        String initInstructions = scanner.nextLine();
        gameStarter.starterInstructions(initInstructions);
        
        // Main loop
        while (runGame) {
            player.locationOutput();
            player.onArrow();
            game.checkWarnings(player);
            player.getPlace();
            char shotOrWalk = game.getUserInput();
            switch (shotOrWalk) {
                case 'W':
                    game.moveCave(player);
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
                    runGame = player.shootArrow();
                    break;
                default:
                    System.out.println("There's no such option.");
                    break;
            }

            System.out.println("\n-------------------------------------------------------------------------\n");
        }
    }
}
