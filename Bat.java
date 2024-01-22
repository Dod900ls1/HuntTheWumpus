public class Bat {

    /**
     * Check if the player encounters bats. If bats brought gamer to a pit or to
     * Wumpus, method would terminate the program.
     * 
     * @param currentPlace
     * @return
     */
    public boolean batTrap(int currentPlace,Game game,Wumpus wumpus,Player player) {
        if (currentPlace == game.getBats(0) || currentPlace == game.getBats(1)) {
            currentPlace = game.generateRandomNumber();
            if (currentPlace == wumpus.getWumpus()) {
                System.out.println("Bats brought you just in the mouth of Wumpus, loser!");
                return false;
            } else if (currentPlace == game.getPits(0) || currentPlace == game.getPits(1)) {
                System.out.println("Bats threw you in a pit and you died miserably. Great job!");
                // Wumpus = generateRandomNumber();
                return false;
            }
            System.out.printf("You've got to the cave with bats. They brought you to the cave number %d%n", currentPlace);
            player.locationOutput(currentPlace, game);
            return true;
        } else {
            return true;
        }
    }
}
