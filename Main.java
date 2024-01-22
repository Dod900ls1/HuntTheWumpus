public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        boolean runGame = true;
        Player player = new Player();

        // Main loop
        while (runGame) {
            player.getPlace();
            game.nextToWumpus(player.getPlace());
            game.nextToBats(player.getPlace());
            game.nextToPits(player.getPlace());
            char shotOrWalk = game.getUserInput();
            switch (shotOrWalk) {
                case 'W':
                    game.moveCave(game, player.getPlace(), player);
                    if(!game.pitTrap(player.getPlace())){
                        runGame = false; // Checks if we got in a pit
                        break;
                    } else if(!game.WumpusTrap(player.getPlace())){
                        runGame = false; // Checks if we got in a Wumpus cave
                        break;
                    } else if(!game.batTrap(player.getPlace())){
                        runGame = false;
                        break;
                    } 

                    // System.out.printf("Bats are in %d, %d, %d.%nWumpus is in %d.%nPits are in %d, %d.%n",game.getBats()[0],
                    //  game.getBats()[1], game.getBats()[2], game.getWumpus(), game.getPits()[0], game.getPits()[1]);
                    // endGame = game.batTrap(player.getPlace());
                    break;
                case 'S':
                if (!game.shootArrow(game, player.getPlace())) {
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
