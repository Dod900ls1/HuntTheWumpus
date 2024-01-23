public class Main {

    public static void main(String[] args) {

        boolean runGame = true;
        Player player = new Player();
        Wumpus wumpus = new Wumpus();
        Bat bat = new Bat();
        Game game = new Game(wumpus);
        player.locationOutput(game);

        // Main loop
        while (runGame) {
            player.getPlace();
            player.nextToWumpus(game,wumpus);
            player.nextToBats(game);
            player.nextToPits(game);
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
        }
    }
}
