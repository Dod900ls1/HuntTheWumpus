import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Game {

    // HashMap to store cave connections
    private HashMap<Integer, Integer[]> caves = new HashMap<>();

    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);


    // Arrays to store pit locations, bat locations, and the Wumpus location
    private int[] pits;
    private int[] bats;
    private Wumpus[] arrWumpus = new Wumpus[2];

    //Set of all the locations already occupied
    private Set<Integer> usedLocations = new HashSet<>();

    
    /**
     * Constructor to initialize the game state
     * 
     * @param wumpus The object needed to access wumpus methods
     */
    public Game(Wumpus wumpus0, Wumpus wumpus1) {
        // Randomly generate cave locations for pits, bats, and the Wumpus
        pits = generateUniqueLocations(2);
        wumpus0.setWumpusLoc(generateUniqueLocation());
        wumpus1.setWumpusLoc(generateUniqueLocation());
        arrWumpus[0] = wumpus0;
        arrWumpus[1] = wumpus1;

        int batCount = generateRandomNumber() <= 10 ? 3 : 4;
        bats = generateUniqueLocations(batCount);
    }

    /**
     * Returns individual location of pit.
     * 
     * @param index Used to indicate the location to find the exact needed coordinate
     * 
     * @return The individual pit location
     */
    public int getPits(int index){
        return pits[index];
    }

    
    /**
     * Returns Array of location of Pits.
     * 
     * @return The array of pit locations
     */



    public int[] getPitsArr(){
       return pits; 
    }

    /**
     * Returns individual location of bat.
     * 
     * @param index Used to indicate the location to find the exact needed coordinate
     * 
     * @return The individual bat location
     */
    public int getBats(int index){
        return bats[index];
    }

    /**
     * Returns Array of location of the bats.
     * 
     * @return The array of bat locations
     */
    public int[] getBatsArr(){
        return bats;
    }

    public Wumpus[] getWumpusArr(){
        return arrWumpus;
    }

    /**
     * Sets the location of an individual bat.
     * 
     * @param index Used to indicate the location to find the exact needed coordinate
     * @param newVal Used as the new location value for bats
     */
    public void setBats(int index,int newVal){
        bats[index] = newVal;
    }

    public boolean checkWumpusStatus(){
        for (Wumpus wumpus : getWumpusArr()) {
            if(!wumpus.getWumpusDead()){
                return true; //Set as true if either wumpus is alive
            }
        }
        System.out.println("You've killed both Wumpuses. Very well done!");
        return false;
    }

    /**
     * Generates a unique location for game objects.
     * 
     * @return A unique location.
     */
    private int generateUniqueLocation() {
        int location;
        do {
            location = generateRandomNumber();
        } while (!usedLocations.add(location));
        return location;
    }

    /**
     * Generates a set of unique locations that has not been used in the game.
     * 
     * @param count to indicate the number of unique locations
     * @return An array of unique locations
     */
    private int[] generateUniqueLocations(int count) {
        int[] locations = new int[count];
        for (int i = 0; i < count; i++) {
            locations[i] = generateUniqueLocation();
        }
        return locations;
    }

    /**
     * Method to generate random number between 1 and 20 (inclusive)
     * 
     * @return the randomised number
     */
    public int generateRandomNumber() {
        return (int) (Math.random() * 20) + 1; // Generate random number from 1 to 20 inclusive
    }

    /**
     * Method to define cave connections and return the HashMap
     * Caves form a dodecahedron.
     * @return HashMap<Integer, Integer[]> which is the nodes that can be accessed
     */
    public HashMap<Integer, Integer[]> generateCaveConnections() {
        caves.put(1, new Integer[] { 2, 8, 5 });
        caves.put(2, new Integer[] { 1, 3, 10 });
        caves.put(3, new Integer[] { 2, 12, 4 });
        caves.put(4, new Integer[] { 3, 14, 5 });
        caves.put(5, new Integer[] { 4, 6, 1 });
        caves.put(6, new Integer[] { 5, 7, 15 });
        caves.put(7, new Integer[] { 6, 8, 17 });
        caves.put(8, new Integer[] { 1, 7, 9 });
        caves.put(9, new Integer[] { 8, 18, 10 });
        caves.put(10, new Integer[] { 9, 2, 11 });
        caves.put(11, new Integer[] { 10, 19, 12 });
        caves.put(12, new Integer[] { 3, 11, 13 });
        caves.put(13, new Integer[] { 12, 14, 20 });
        caves.put(14, new Integer[] { 4, 13, 15 });
        caves.put(15, new Integer[] { 6, 14, 16 });
        caves.put(16, new Integer[] { 15, 17, 20 });
        caves.put(17, new Integer[] { 7, 18, 16 });
        caves.put(18, new Integer[] { 17, 9, 19 });
        caves.put(19, new Integer[] { 11, 18, 20 });
        caves.put(20, new Integer[] { 13, 16, 19 });

        return caves;
    }


    /**
     * Method to get user input for the next move
     * 
     * @return the user input
     */
    public char getUserInput() {
        System.out.println("Shoot = S, Walk to another cave = W");
        return scanner.next().charAt(0);
    }

    /**
     * Method to move to a new cave based on user input
     * 
     * @param game the object needed to access Game class methods
     * @param player the object needed to access Player class methods
     */
    public void moveCave(Game game, Player player) {
        boolean validInput = false;

        while (!validInput) {
            try {
                player.locationOutput();
                int cave = scanner.nextInt();

                //Checks whether the move chosen is valid
                if (player.isRightStep(cave)) {
                    //If valid move, updates player's current location
                    validInput = true;
                    player.setPlace(cave);
                } else {
                    System.out.println("Invalid input, you can walk only to neighbour caves!");
                }
            } catch (InputMismatchException e) {
                // Ask user for valid input
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Check if player got to a pit trap. Terminate the program if he did.
     * 
     * @param currentPlace current location of player
     * @return boolean of whether game is still running
     */
    public boolean pitTrap(int currentPlace) {
        //Checks whether the current location is same as those of pits
        if (currentPlace == pits[0] || currentPlace == pits[1]) {
            //If so, output end statement and End Game
            System.out.println("HAHAHA! YOU FALL IN A PIT AND DIE!");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the player is next to any Dangers. If so, output this:
     * 
     * @param player the Player object required to access Player methods
     * @param game the Game object required to access Game methods
     */
    public void checkWarnings(Player player){
        boolean isNextToWumpus = player.nextToWumpus();
        boolean isNextToBat = player.nextToBats();
        boolean isNextToPit = player.nextToPits();

        if(isNextToBat|| isNextToWumpus || isNextToPit){
            System.out.println("\n    WARNING:");
            if(isNextToWumpus){
                System.out.println("\tYou smell the Wumpus in one of neighbour caves!");
            }
            if(isNextToBat){
                System.out.println("\t You can hear Bats near you!");
            }
            if(isNextToPit){
                System.out.println("\t You can feel the blowing of wind. Pit is near you!");
            }
            System.out.println();
        }
    }
}
