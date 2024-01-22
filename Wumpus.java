public class Wumpus {

    private int wumpus;

    /**
     * Returns Array of location of the Wumpus.
     * 
     * @return wumpus The int of wumpus location
     */
    public int getWumpus(){
        return wumpus;
    }

    public void setWumpus(int wumpus){
        this.wumpus = wumpus;
    }

    
    /**
     * Checks if player entered Wumpus cave. Terminate the program if he is.
     * 
     * @param currentPlace
     * @return
     */
    public boolean WumpusTrap(int currentPlace) {
        if (currentPlace == wumpus) {
            System.out.println("You've got to the Wumpus's cave, and he eat you!");
            return false;
        }
        return true;

    }

    /**
     * If player shoot an arrow next to a Wumpus cave, but didn't kill it - Wumpus
     * disclocates to another cave.
     * 
     * @param shot
     * @param currentPlace
     * @return boolean
     */
    public boolean scareWumpus(int shot, int currentPlace, Game game) {
        if (game.generateCaveConnections().get(currentPlace)[0] == wumpus
                || game.generateCaveConnections().get(currentPlace)[1] == wumpus
                || game.generateCaveConnections().get(currentPlace)[2] == wumpus) {
            wumpus = game.generateRandomNumber();
            if (wumpus == currentPlace) {  //Used to be this.place
                System.out.println("You missed. Wumpus heared you. He came to your cave and killed you.");
                return false;
            } else {
                System.out.println("You missed. Wumpus heared you. He dislocated to other cave.");
            }
        }
        return true;
    }
}
