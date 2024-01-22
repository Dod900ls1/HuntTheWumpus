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
     * @return int
     */
    public int getPlace() {
        return this.playerLocation;
    }

    /**
     * sets the current player's location
     * 
     * @param playerLocation
     */
    public void setPlace(int playerLocation){
        this.playerLocation = playerLocation;
    }

    /**
     * Method to display current location and available caves
     * 
     * @param index
     * @param game
     */
    public void locationOutput(Game game) {
        System.out.printf("You're in the cave number, %d.%nAvailible caves are: %d, %d, %d.%n", playerLocation,
                game.generateCaveConnections().get(playerLocation)[0], game.generateCaveConnections().get(playerLocation)[1],
                game.generateCaveConnections().get(playerLocation)[2]);
    }

    /**
     * Method to check if the step made by player is valid.
     * 
     * @param index
     * @param input
     * @return boolean
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
     * @param currentPlace
     */
    public void nextToWumpus(Game game, Wumpus wumpus) {
        if (game.generateCaveConnections().get(playerLocation)[0] == wumpus.getWumpusLoc()
                || game.generateCaveConnections().get(playerLocation)[1] == wumpus.getWumpusLoc()
                || game.generateCaveConnections().get(playerLocation)[2] == wumpus.getWumpusLoc()) {
            System.out.println("You smell the Wumpus in one of neighbour caves!");
            return;
        } else {
            return;
        }
    }

        /**
     * Methods to inform the player about the proximity of the bats
     * 
     * @param currentPlace
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
     * @param currentPlace
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
     * @param arr
     * @param element
     * @return int
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
     * @param shot
     */
    private void killBat(int shot,Game game) {
        for (int i : game.getBatsArr()) {
            if (shot == i) {
                System.out.println("You killed the bat!");
                //LOOK INTO SHORTENING THIS
                game.setBats(indexOf(game.getBatsArr(), shot),-1); // Get rid of this bat
            }
        }
    }

    /**
     * Checks if player killed the Wumpus. If he did we congratulate the player and
     * terminate the game.
     * 
     * @param shot
     * @return boolean
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
     * Counts how much rows player has left. If he used all of them, we terminate
     * programm as the player can't kill the Wumpus anymore.
     * 
     * @param shot
     * @return boolean
     */
    private boolean arrowCounter(int shot) {
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
     * @param game
     * @param currentPlace
     * @return boolean
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
                    if (!arrowCounter(shot))
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

