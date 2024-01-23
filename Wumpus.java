public class Wumpus {

    private int wumpusLocation;

    /**
     * Returns Array of location of the Wumpus.
     * 
     * @return The int of wumpusLocation location
     */
    public int getWumpusLoc(){
        return wumpusLocation;
    }

    /**
     * Sets the location of the Wumpus
     * 
     * @param wumpusLocation The generated location of the Wumpus
     */
    public void setWumpusLoc(int wumpusLocation){
        this.wumpusLocation = wumpusLocation;
    }

    
    /**
     * Checks if player entered Wumpus cave. Terminate the program if he is.
     * 
     * @param currentPlace The current location of the user
     * @return Boolean of whether the game continues
     */
    public boolean WumpusTrap(int currentPlace) {
        if (currentPlace == wumpusLocation) {
            System.out.println("You've got to the Wumpus's cave, and he eat you!");
            return false;
        }
        return true;

    }

    /**
     * If player shoot an arrow next to a Wumpus cave, but didn't kill it - Wumpus
     * disclocates to another cave.
     * 
     * @param shot The location of where the arrow was shot
     * @param currentPlace The current location of the user
     * @param game The Game object required to access Game class methods
     * @return boolean of whether the game continues
     */
    public boolean scareWumpus(int shot, int currentPlace, Game game) {
        if (game.generateCaveConnections().get(currentPlace)[0] == wumpusLocation
                || game.generateCaveConnections().get(currentPlace)[1] == wumpusLocation
                || game.generateCaveConnections().get(currentPlace)[2] == wumpusLocation) {
            wumpusLocation = game.generateRandomNumber();
            if (wumpusLocation == currentPlace) {  //Used to be this.place
                System.out.println("You missed. Wumpus heared you. He came to your cave and killed you.");
                return false;
            } else {
                System.out.println("You missed. Wumpus heared you. He dislocated to other cave.");
            }
        }
        return true;
    }
}
