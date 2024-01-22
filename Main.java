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
                    game.moveCave(game, player.getPlace(), player);
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

                    // System.out.printf("Bats are in %d, %d, %d.%nWumpus is in %d.%nPits are in %d, %d.%n",game.getBats()[0],
                    //  game.getBats()[1], game.getBats()[2], game.getWumpus(), game.getPits()[0], game.getPits()[1]);
                    // endGame = game.batTrap(player.getPlace());
                    break;
                case 'S':
                if (!player.shootArrow(game, wumpus)) {
                    runGame = false; // Player has run out of arrows
                    
                }
                break;
                default:
                    System.out.println("There's no such option.");
                    break;
            }
        }
    }
}
