import javax.swing.JOptionPane;

public class Wumpus {

    private int wumpusLocation;
    private boolean dead = false;

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
     * Returns whether Wumpus is dead.
     * 
     * @return The boolean of whether wumpus is dead
     */
    public boolean getWumpusDead(){
        return dead;
    }
    
    /**
     * Sets that the wumpus has been killed.
     */
    public void setWumpusDead(){
        dead = true;
    }

    
    /**
     * Checks if player entered Wumpus cave. Terminate the program if true.
     * 
     * @param currentPlace The current location of the user
     * @return Boolean of whether the game continues
     */
    public boolean WumpusTrap(int currentPlace) {
        if (currentPlace == wumpusLocation) {
            System.out.println("You've stumbled upon the wumpus's cave, and it ate you!!");
            return false;
        }
        return true;

    }

    /**
     * If player shoot an arrow next to a Wumpus cave, but didn't kill it - Wumpus
     * reclocates to another cave.
     * 
     * @param shot The location of where the arrow was shot
     * @param currentPlace The current location of the user
     * @param game The Game object required to access Game class methods
     * @return boolean of whether the game continues
     */
    public boolean scareWumpus(int shot, int currentPlace, Game game) {
        //Checks whether Wumpus is in any linked nodes to the player
        if (game.generateCaveConnections().get(currentPlace)[0] == wumpusLocation
                || game.generateCaveConnections().get(currentPlace)[1] == wumpusLocation
                || game.generateCaveConnections().get(currentPlace)[2] == wumpusLocation) {

            int newWumpusLocation;
            do {
                // Generate a new random location until it's different from the wumpus`s location
                newWumpusLocation = game.generateRandomNumber();
            } while (newWumpusLocation == wumpusLocation);
            wumpusLocation = newWumpusLocation;
            if (wumpusLocation == currentPlace) {
                System.out.println("You missed. Wumpus heared you. It came to your cave and killed you.");
                return false;
            } else {
                System.out.println("You missed. Wumpus heared you. It relocated to other cave.");
            }
        }
        return true;
    }

        /**
     * If player shoot an arrow next to a Wumpus cave, but didn't kill it - Wumpus
     * reclocates to another cave. This is a GUI version of this function
     * 
     * @param shot The location of where the arrow was shot
     * @param currentPlace The current location of the user
     * @param game The Game object required to access Game class methods
     * @return boolean of whether the game continues
     */
    public boolean scareWumpusGUI(int shot, int currentPlace, Game game) {
        //Checks whether Wumpus is in any linked nodes to the player
        if (game.generateCaveConnections().get(currentPlace)[0] == wumpusLocation
                || game.generateCaveConnections().get(currentPlace)[1] == wumpusLocation
                || game.generateCaveConnections().get(currentPlace)[2] == wumpusLocation) {

            int newWumpusLocation;
            do {
                // Generate a new random location until it's different from the wumpus`s location
                newWumpusLocation = game.generateRandomNumber();
            } while (newWumpusLocation == wumpusLocation);
            wumpusLocation = newWumpusLocation;
            if (wumpusLocation == currentPlace) {
                JOptionPane.showMessageDialog(null,"You missed. Wumpus heared you. It came to your cave and killed you.");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "You missed. Wumpus heared you. It relocated to other cave.");
            }
        }
        return true;
    }
}
