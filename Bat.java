public class Bat {

    /**
     * Check if the player encounters bats. If bats brought gamer to a pit or to
     * Wumpus, method would terminate the program.
     * 
     * @param currentPlace The current location of the player
     * @param game The Game object required to access Game class methods
     * @param wumpus The Wumpus object required to access Wumpus class methods
     * @param player The Player object required to access Player class methods
     * @return Boolean of whether game continues
     */
    public boolean batTrap(int currentPlace,Game game,Wumpus wumpus,Player player) {
        if (currentPlace == game.getBats(0) || currentPlace == game.getBats(1)) {
            currentPlace = game.generateRandomNumber();
            if (currentPlace == wumpus.getWumpusLoc()) {
                System.out.println("The Bat brought you just in the mouth of Wumpus, loser!");
                return false;
            } else if (currentPlace == game.getPits(0) || currentPlace == game.getPits(1)) {
                System.out.println("The Bat threw you in a pit and you died miserably. Great job!");
                return false;
            }
            System.out.printf("You've got to the cave with bats. They brought you to the cave number %d%n", currentPlace);
            player.locationOutput(game);
            return true;
        } else {
            return true;
        }
    }
}
