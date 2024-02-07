import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Player {

    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    // Initial number of arrows
    private int arrows = 5;

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    // Randomly generated starting cave
    private int playerLocation = (int) (Math.random() * 20);

    // Create a new unassigned Game object
    Game game;

    /**
     * Constructor to initialize the player class
     * 
     * @param game The Game object needed to access Game methods
     */
    public Player(Game game) {
        this.game = game;
        // Set player's initial location to a unique, unoccupied location
        this.playerLocation = generateUniquePlayerLocation();
    }

    /**
     * Generate a unique player location that is not occupied by pits, wumpuses, or
     * bats.
     * 
     * @return The generated unique player location
     */
    private int generateUniquePlayerLocation() {
        int playerLocation = generateUniqueLocation();
        while (isLocationOccupied(playerLocation)) {
            playerLocation = generateUniqueLocation();
        }
        return playerLocation;
    }

    // Add a helper method to check if a location is occupied by pits, wumpuses, or
    // bats
    private boolean isLocationOccupied(int location) {
        int[] pits = game.getPitsArr();
        int[] bats = game.getBatsArr();
        Wumpus[] wumpuses = game.getWumpusArr();
        int[] wumpusesLoc = new int[2];
        
        for (int i=0; i < wumpuses.length; i++) {
            wumpusesLoc[i] = wumpuses[i].getWumpusLoc();
        }

        for (int wumpus : wumpusesLoc) {
            if (location == wumpus) {
                return true;
            }
        }

        for (int pit : pits) {
            if (location == pit) {
                return true;
            }
        }

        for (int bat : bats) {
            if (location == bat) {
                return true;
            }
        }

        for (Wumpus wumpus : game.getWumpusArr()) {
            if (location == wumpus.getWumpusLoc()) {
                return true;
            }
        }

        return false;
    }

    // Add a helper method to generate a unique location not occupied by pits,
    // wumpuses, or bats
    private int generateUniqueLocation() {
        int location = (int) (Math.random() * 20);
        while (isLocationOccupied(location)) {
            location = (int) (Math.random() * 20);
        }
        return location;
    }

    /**
     * Get the current player's location
     * 
     * @return The players Location coordinate
     */
    public int getPlace() {
        return this.playerLocation;
    }

    public int getArrows() {
        return this.arrows;
    }

    /**
     * sets the current player's location
     * 
     * @param playerLocation The new location for the player to move
     */
    public void setPlace(int playerLocation) {
        this.playerLocation = playerLocation;
    }

    /**
     * Method to display current location and available caves
     */
    public void locationOutput() {
        System.out.printf("You're in the cave number, %d.%nAvailible caves are: %d, %d, %d.%n", playerLocation,
                this.game.generateCaveConnections().get(playerLocation)[0],
                this.game.generateCaveConnections().get(playerLocation)[1],
                this.game.generateCaveConnections().get(playerLocation)[2]);
    }

    /**
     * Method to check if the step made by player is valid.
     * 
     * @param input The value chosen by the player to move to
     * 
     * @return Whether the move made is valid or not
     */
    public boolean isRightStep(int input) {
        // Check if the input cave is among the connected caves
        for (int i : this.game.generateCaveConnections().get(playerLocation)) {
            if (i == input) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methods to inform the player about the proximity of the Wumpus
     * 
     * @return whether user is next to wumpus
     */
    public boolean nextToWumpus() {

        for (Wumpus wumpus : game.getWumpusArr()) {
            // Checks whether the any joined nodes of cave location contains Wumpus
            if ((this.game.generateCaveConnections().get(playerLocation)[0] == wumpus.getWumpusLoc()
                    || this.game.generateCaveConnections().get(playerLocation)[1] == wumpus.getWumpusLoc()
                    || this.game.generateCaveConnections().get(playerLocation)[2] == wumpus.getWumpusLoc())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methods to inform the player about the proximity of the bats
     * 
     * @return whether user is next to bats
     */
    public boolean nextToBats() {
        // Checks whether the any joined nodes of cave location contains Bats
        for (int i : this.game.getBatsArr()) {
            if (this.game.generateCaveConnections().get(playerLocation)[0] == i
                    || this.game.generateCaveConnections().get(playerLocation)[1] == i
                    || this.game.generateCaveConnections().get(playerLocation)[2] == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methods to inform the player about the proximity of the pits
     * 
     * @return whether user is next to pits
     */
    public boolean nextToPits() {
        //// Checks whether the any joined nodes of cave location contains Pits
        for (int i : this.game.getPitsArr()) {
            if (this.game.generateCaveConnections().get(playerLocation)[0] == i
                    || this.game.generateCaveConnections().get(playerLocation)[1] == i
                    || this.game.generateCaveConnections().get(playerLocation)[2] == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get an index of an element in array.
     * 
     * @param arr     The array used to get the element of
     * @param element The element within the array to get the index
     * @return The value of index of the element, or -1 if it doesnt exist
     */
    private static int indexOf(int[] arr, int element) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if player killed a bat.
     * 
     * @param shot The location of where the shot was placed
     */
    private void killBat(int shot) {
        // Checks whether the location of the shot contains a bat
        for (int i : this.game.getBatsArr()) {
            if (shot == i) {
                System.out.println("You killed the bat!");
                this.game.setBats(indexOf(this.game.getBatsArr(), shot), -1); // Removes this bat
            }
        }
    }

    private void killBatGUI(int shot) {
        // Checks whether the location of the shot contains a bat
        for (int i : this.game.getBatsArr()) {
            if (shot == i) {
                System.out.println("You killed the bat!");
                JOptionPane.showMessageDialog(null, "You killed a bat!");
                this.game.setBats(indexOf(this.game.getBatsArr(), shot), -1); // Removes this bat
            }
        }
    }

    /**
     * Checks if player killed the Wumpus. If they did, congratulate the player and
     * terminate the game.
     * 
     * @param shot   The location of where the shot was placed
     * @param wumpus The Wumpus object required to access Wumpus class methods
     * @return boolean of whether the game continues
     */

    private boolean killWumpus(int shot, Wumpus wumpus) {
        // Checks whether the location of the shot contains a wumpus
        if (shot == wumpus.getWumpusLoc()) {
            // Sets that wumpus as dead
            System.out.println("You've killed one of the Wumpus!");
            wumpus.setWumpusLoc(-1);
            wumpus.setWumpusDead();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Counts how much arrows player has left. If all of them used, terminate
     * program as the player can't kill the Wumpus anymore.
     * 
     * @return boolean of whether the game continues, considering the amount of
     *         arrows left
     */
    private boolean arrowCounter() {
        if (arrows > 1) {
            // Decreases the number of arrows available
            arrows--;
            System.out.printf("You have %d arrows left.%n", arrows);
            return true;
        } else {
            arrows--;
            System.out.println("You've used all of your arrows.");

            //If there are still arrows around the map, give the player the chance to find them
            for (int arrowLocs : game.getArrowLocsArr()) {
                if(arrowLocs != -1){
                    System.out.println("However there are still arrows left around the map. Find them!");
                    return true;
                }
            }

            // States no arrows left and ends game
            System.out.println("Now you're useless. You lost!");
            return false;
        }
    }

    /**
     * Checks if the new location of player contains an uncollected arrow
     * This is used to increase the number or arrows available
     * 
     */
    public void onArrow() {
        for (int arrowLocale : game.getArrowLocsArr()) {

            // Checks whether the location of the player contains a player
            if (playerLocation == arrowLocale) {
                arrows++;
                System.out.printf("You picked up an Arrow! You now have %d arrows.\n", arrows);
                this.game.setArrowsLocs(indexOf(this.game.getArrowLocsArr(), playerLocation), -1); // Removes this arrow
            }
        }

    }

    /**
     * This method is responsible for the whole attack system in the game. Calls
     * killWumpus(), arrowCounter(), and scareWumpus() when it is appropriate.
     * 
     * @return boolean of whether game continues
     */
    public boolean shootArrow() {

        //Player cannot shoot if there is no arrows left
        if(arrows == 0){
            System.out.println("You have no arrows left, find more!");
            return true;
        }

        System.out.printf("You can shoot in caves number %d, %d, %d%n",
                this.game.generateCaveConnections().get(playerLocation)[0],
                this.game.generateCaveConnections().get(playerLocation)[1],
                this.game.generateCaveConnections().get(playerLocation)[2]);

        boolean validInput = false;

        while (!validInput) {
            try {
                int shot = scanner.nextInt();

                // Checks whether arrow is permittable
                if (isRightStep(shot)) {
                    validInput = true;

                    // Checks individual components that can affect game in order of importance
                    killBat(shot);
                    for (Wumpus wumpus : this.game.getWumpusArr()) {
                        if (!killWumpus(shot, wumpus)) {
                            return this.game.checkWumpusStatus();
                        }
                        if (!wumpus.scareWumpus(shot, playerLocation, this.game)) {
                            return false;
                        }
                    }
                    if (!arrowCounter())
                        return false;
                } else {
                    System.out.println("You can shot only in adjecent caves.");
                    return true; // Player has arrows left
                }
            } catch (InputMismatchException e) {
                // Used if invalid input is written
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
        return true;
    }

    /**
     * This is a shootArrow method for out GUI version of the game. Does the same
     * thing as previous version
     * except for slight changes in errors handeling.
     * 
     * @param shot
     * @return
     */
    public boolean shootArrow(int shot) {
        try {
            if (!isRightStep(shot)) {
                System.out.println("You can shoot only in adjacent caves.");
                return true; // Player has arrows left
            }

            killBatGUI(shot);

            for (Wumpus wumpus : this.game.getWumpusArr()) {
                if (!killWumpusAndCheckGameStatus(shot, wumpus)) {
                    return this.game.checkWumpusStatus();
                }

                if (!wumpus.scareWumpusGUI(shot, playerLocation, this.game)) {
                    return false;
                }
            }

            return arrowCounter();

        } catch (InputMismatchException e) {
            // Used if invalid input is written
            System.out.println("Invalid input. Please enter an integer.");
            scanner.nextLine();
        }

        return true;
    }

    /**
     * Checks if we killed both Wumpuses or it's the only one.
     * Throws a messageDialog if we killed Wumpus.
     */
    private boolean killWumpusAndCheckGameStatus(int shot, Wumpus wumpus) {
        if (!killWumpus(shot, wumpus)) {
            if (this.game.checkWumpusStatus()) {
                JOptionPane.showMessageDialog(null, "You've killed one Wumpus, good job!");
            } else {
                JOptionPane.showMessageDialog(null, "You've killed both Wumpuses, you won!");
                return false;
            }
        }
        return true;
    }
}
