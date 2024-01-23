import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {

    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    // Initial number of arrows
    private int arrows = 5;

    // Randomly generated starting cave
    private int playerLocation = (int) (Math.random() * ((20) + 1));

    /**
     * Get the current player's location
     * 
     * @return The players Location coordinate
     */
    public int getPlace() {
        return this.playerLocation;
    }

    /**
     * sets the current player's location
     * 
     * @param playerLocation The new location for the player to move
     */
    public void setPlace(int playerLocation){
        this.playerLocation = playerLocation;
    }

    /**
     * Method to display current location and available caves
     * 
     * @param game The Game object to access Game class methods
     */
    public void locationOutput(Game game) {
        System.out.printf("You're in the cave number, %d.%nAvailible caves are: %d, %d, %d.%n", playerLocation,
                game.generateCaveConnections().get(playerLocation)[0], game.generateCaveConnections().get(playerLocation)[1],
                game.generateCaveConnections().get(playerLocation)[2]);
    }

    /**
     * Method to check if the step made by player is valid.
     * 
     * @param input The value chosen by the player to move to
     * @param game The game object needed to access Game class methods
     * @return Whether the move made is valid or not
     */
    public boolean isRightStep(int input, Game game) {
        // Check if the input cave is among the connected caves
        for (int i : game.generateCaveConnections().get(playerLocation)) {
            if (i == input) {
                return true;
            }
        }
        return false;
    }

        /**
     * Methods to inform the player about the proximity of the Wumpus
     * 
     * @param game The Game object needed to access Game class methods
     * @param wumpus The Wumpus object needed to access Wumpus class methods
     */
    public void nextToWumpus(Game game, Wumpus wumpus) {
        if (game.generateCaveConnections().get(playerLocation)[0] == wumpus.getWumpusLoc()
                || game.generateCaveConnections().get(playerLocation)[1] == wumpus.getWumpusLoc()
                || game.generateCaveConnections().get(playerLocation)[2] == wumpus.getWumpusLoc()) {
            System.out.println("You smell the Wumpus in one of neighbour caves!");
        }
        // return;
        // } else {
        //     return;
        // }
    }

        /**
     * Methods to inform the player about the proximity of the bats
     * 
     * @param game The Game object needed to access Game class methods
     */
    public void nextToBats(Game game) {
        for (int i : game.getBatsArr()) {
            if (game.generateCaveConnections().get(playerLocation)[0] == i
                    || game.generateCaveConnections().get(playerLocation)[1] == i
                    || game.generateCaveConnections().get(playerLocation)[2] == i) {
                System.out.println("You can hear Bats near you!");
                return;
            }
        }
    }

    /**
     * Methods to inform the player about the proximity of the pits
     * 
     * @param game The Game object needed to access Game class methods
     */
    public void nextToPits(Game game) {
        for (int i : game.getPitsArr()) {
            if (game.generateCaveConnections().get(playerLocation)[0] == i
                    || game.generateCaveConnections().get(playerLocation)[1] == i
                    || game.generateCaveConnections().get(playerLocation)[2] == i) {
                System.out.println("You can feel the blowing of wind. Pit is near you!");
                return;
            }
        }
    }

    /**
     * Helper method to get an index of an element in array.
     * 
     * @param arr The array used to get the element of
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
     * @param game The Game object needed to access Game class methods
     */
    private void killBat(int shot,Game game) {
        for (int i : game.getBatsArr()) {
            if (shot == i) {
                System.out.println("You killed the bat!");
                game.setBats(indexOf(game.getBatsArr(), shot),-1); // Removes this bat
            }
        }
    }

    /**
     * Checks if player killed the Wumpus. If they did, congratulate the player and
     * terminate the game.
     * 
     * @param shot The location of where the shot was placed
     * @param game The Game object required to access Game class methods
     * @param wumpus The Wumpus object required to access Wumpus class methods
     * @return boolean of whether the game continues
     */
    private boolean killWumpus(int shot,Game game, Wumpus wumpus) {
        if (shot == wumpus.getWumpusLoc()) {
            System.out.println("You've killed the Wumpus, you won!");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Counts how much rows player has left. If they used all of them, terminate
     * program as the player can't kill the Wumpus anymore.
     * 
     * @return boolean of whether the game continues, considering the amount of arrows left
     */
    private boolean arrowCounter() {
        if (arrows > 1) {
            arrows--;
            System.out.printf("You have %d arrows left.%n", arrows);
            return true;
        } else {
            arrows--;
            System.out.println("You've used all of your arrows. Now you're useless. You lost!");
            return false;
        }
    }

    /**
     * This method is responsible for the whole attack system in our game. Calls
     * killWumpus(), arrowCounter(), and scareWumpus() when it is appropriate.
     * 
     * @param game The Game object needed to access Game class methods
     * @param wumpus The Wumpus object needed to access Wumpus class methods
     * @return boolean of whether game continues
     */
    public boolean shootArrow(Game game,Wumpus wumpus) {
        System.out.printf("You can shoot in caves number %d, %d, %d%n",
                game.generateCaveConnections().get(playerLocation)[0], game.generateCaveConnections().get(playerLocation)[1],
                game.generateCaveConnections().get(playerLocation)[2]);

        boolean validInput = false;

        while (!validInput) {
            try {
                int shot = scanner.nextInt();

                if (isRightStep(shot, game)) {
                    validInput = true;

                    killBat(shot,game);
                    if (!killWumpus(shot,game,wumpus))
                        return false;
                    if (!arrowCounter())
                        return false;
                    if (!wumpus.scareWumpus(shot, playerLocation,game))
                        return false;
                } else {
                    System.out.println("You can shot only in adjecent caves.");
                    return true; // Player has arrows left
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
        return true;
    }
}

