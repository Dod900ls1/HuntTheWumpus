public class Bat {

    /**
     * Check if the player encounters bats. If bats brought gamer to a pit or to
     * Wumpus, method would terminate the program.
     * 
     * @param currentPlace The current location of the player
     * @param game The Game object required to access Game class methods
     * @param player The Player object required to access Player class methods
     * @return Boolean of whether game continues
     */
    public boolean batTrap(int currentPlace,Game game,Player player) {
        //Checks if player has reached same location of bats
        if (checkBatLocation(currentPlace, game)) {
            currentPlace = game.generateRandomNumber();
            for (Wumpus wumpus : game.getWumpusArr()) {
                //Checks if new location is that of Wumpus
                if (currentPlace == wumpus.getWumpusLoc()) {
                    System.out.println("The Bat brought you just in the mouth of Wumpus, loser!");
                    return false;
                } 
            }
            //Checks if new location is that of pits
            if (currentPlace == game.getPits(0) || currentPlace == game.getPits(1)) {
                System.out.println("The Bat threw you in a pit and you died miserably. Great job!");
                return false;
            }
            //If no action, print out new location, and carry on with game
            System.out.printf("You have gone to the cave with bats. They brought you to the cave number %d.%n", currentPlace);
            player.setPlace(currentPlace);
        }
        return true;
    }

    /**
     * Check if the player encounters bats.
     * 
     * @param currentPlace The current location of the player
     * @param game The Game object required to access Game class methods
     * @return Boolean of whether player encounters bat
     */
    private boolean checkBatLocation(int currentPlace, Game game){
        //Loops through array of bat location to check if player encounters bat
        for (int batLocation : game.getBatsArr()) {
            if(batLocation == currentPlace){
                return true;
            }
        }
        return false;
    }
}
