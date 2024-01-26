import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean runGame = true;
        Player player = new Player();
        GameStarter gameStarter = new GameStarter();
        Wumpus wumpus = new Wumpus();
        Bat bat = new Bat();
        Game game = new Game(wumpus);

        boolean isNextToWumpus = false;
        boolean isNextToBat = false;
        boolean isNextToPit = false;
        
        
        System.out.println("Do you want to see instructions? (Y/N)");
        String initInstructions = scanner.nextLine();
        gameStarter.starterInstructions(initInstructions);
        
        // Main loop
        while (runGame) {
            player.locationOutput(game);
            isNextToWumpus = player.nextToWumpus(game,wumpus);
            isNextToBat = player.nextToBats(game);
            isNextToPit = player.nextToPits(game);

            if(isNextToBat|| isNextToWumpus || isNextToPit){
                System.out.println("\n    WARNING:");
                if(isNextToWumpus){
                    System.out.println("\tYou smell the Wumpus in one of neighbour caves!");
                }
                if(isNextToBat){
                    System.out.println("\t You can hear Bats near you!");
                }
                if(isNextToPit){
                    System.out.println("\t You can feel the blowing of wind. Pit is near you!");
                }
                System.out.println();
            }

            player.getPlace();
            char shotOrWalk = game.getUserInput();
            switch (shotOrWalk) {
                case 'W':
                    game.moveCave(game, player);
                    if(!game.pitTrap(player.getPlace())){
                        runGame = false; // Checks if we got in a pit
                        break;
                    } else if(!wumpus.WumpusTrap(player.getPlace())){
                        runGame = false; // Checks if we got in a Wumpus cave
                        break;
                    } else if(!bat.batTrap(player.getPlace(),game,wumpus,player)){
                        runGame = false;
                        break;
                    } 
                    
                    break;
                case 'S':
                    if (!player.shootArrow(game, wumpus)) {
                        runGame = false; // Game has finished (run our of arrows, or wumpus killed)
                    }
                    break;
                default:
                    System.out.println("There's no such option.");
                    break;
            }

            System.out.println("\n-------------------------------------------------------------------------\n");
        }
    }
}
