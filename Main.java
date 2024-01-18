public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        boolean runGame = true;

        // Main loop
        while (runGame) {
            game.getPlace();
            game.nextToWumpus(game.getPlace());
            game.nextToBats(game.getPlace());
            game.nextToPits(game.getPlace());
            char shotOrWalk = game.getUserInput();
            switch (shotOrWalk) {
                case 'W':
                    game.moveCave(game, game.getPlace());
                    if(!game.pitTrap(game.getPlace())){
                        runGame = false; // Checks if we got in a pit
                        break;
                    } else if(!game.WumpusTrap(game.getPlace())){
                        runGame = false; // Checks if we got in a Wumpus cave
                        break;
                    } else if(!game.batTrap(game.getPlace())){
                        runGame = false;
                        break;
                    } 

                    System.out.printf("Bats are in %d, %d, %d.%nWumpus is in %d.%nPits are in %d, %d.%n",game.getBats()[0],
                     game.getBats()[1], game.getBats()[2], game.getWupmus(), game.getPits()[0], game.getPits()[1]);
                    // endGame = game.batTrap(game.getPlace());
                    break;
                case 'S':
                if (!game.shootArrow(game, game.getPlace())) {
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
