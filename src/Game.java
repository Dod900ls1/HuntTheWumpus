import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.json.*;


public class Game {


    // HashMap to store cave connections
    private HashMap<Integer, Integer[]> caves = new HashMap<>();

    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    // Arrays to store pit locations, bat locations, Wumpus locations and Arrow
    // locations
    private int[] pits;
    private int[] bats;
    private Wumpus[] arrWumpus = new Wumpus[2];
    private int[] arrowLocs;

    // Set of all the locations already occupied
    private Set<Integer> usedLocations = new HashSet<>();

    /**
     * Constructor to initialize the game state
     * 
     * @param wumpus0 The first object needed to access wumpus methods
     * @param wumpus1 The second object needed to access wumpus methods
     */
    public Game(Wumpus wumpus0, Wumpus wumpus1) {
        // Randomly generate cave locations for pits, bats, and the Wumpuses
        pits = generateUniqueLocations(2);

        wumpus0.setWumpusLoc(generateUniqueLocation());
        wumpus1.setWumpusLoc(generateUniqueLocation());
        arrWumpus[0] = wumpus0;
        arrWumpus[1] = wumpus1;

        int batCount = generateRandomNumber() <= 10 ? 3 : 4;
        bats = generateUniqueLocations(batCount);

        arrowLocs = generateUniqueLocations(batCount - 1);
    }

    /**
     * Returns individual location of pit.
     * 
     * @param index Used to indicate the location to find the exact needed
     *              coordinate
     * 
     * @return The individual pit location
     */
    public int getPits(int index) {
        return pits[index];
    }

    /**
     * Returns Array of location of Pits.
     * 
     * @return The array of pit locations
     */
    public int[] getPitsArr() {
        return pits;
    }

    /**
     * Returns individual location of bat.
     * 
     * @param index Used to indicate the location to find the exact needed
     *              coordinate
     * 
     * @return The individual bat location
     */
    public int getBats(int index) {
        return bats[index];
    }

    /**
     * Returns Array of location of the bats.
     * 
     * @return The array of bat locations
     */
    public int[] getBatsArr() {
        return bats;
    }

    /**
     * Sets the location of an individual bat.
     * 
     * @param index  Used to indicate the location to find the exact needed
     *               coordinate
     * @param newVal Used as the new location value for bats
     */
    public void setBats(int index, int newVal) {
        bats[index] = newVal;
    }

    /**
     * Returns Array of wumpus within the game.
     * 
     * @return The array of wumpus
     */
    public Wumpus[] getWumpusArr() {
        return arrWumpus;
    }

    /**
     * Checks whether both Wumpuses have been killed.
     * 
     * @return boolean of whether the game continues
     */
    public boolean checkWumpusStatus() {
        for (Wumpus wumpus : getWumpusArr()) {
            if (!wumpus.getWumpusDead()) {
                return true; // Set as true if either wumpus is alive
            }
        }
        System.out.println("You've killed both Wumpuses. Very well done!");
        return false;
    }

    /**
     * Returns Array of location of the Arrows.
     * 
     * @return The array of arrows locations
     */
    public int[] getArrowLocsArr() {
        return arrowLocs;
    }

    /**
     * Sets the location of an individual arrows.
     * 
     * @param index  Used to indicate the location to find the exact needed
     *               coordinate
     * @param newVal Used as the new location value for arrows
     */
    public void setArrowsLocs(int index, int newVal) {
        arrowLocs[index] = newVal;
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
     * Method to generate random number between 0 and 19 (inclusive)
     * 
     * @return the randomised number
     */
    public int generateRandomNumber() {
        return (int) (Math.random() * 20);
    }

    /**
     * Method to define cave connections and return the HashMap
     * Caves form a dodecahedron.
     * 
     * @return HashMap<Integer, Integer[]> which is the nodes that can be accessed
     */
    public HashMap<Integer, Integer[]> generateCaveConnections() {
        try (JsonReader reader = Json.createReader(new FileReader("NodeGenerator/dodecahedronCorrected.json"))) {
            JsonObject jsonRoot = reader.readObject();

            // Get the "connections" JsonObject
            JsonObject connectionsObject = jsonRoot.getJsonObject("connections");

            // Iterate over the entries in "connections"
            for (String key : connectionsObject.keySet()) {
                JsonArray connectionArray = connectionsObject.getJsonArray(key);
                caves.put(Integer.parseInt(key), convertJsonInteger(connectionArray));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return caves;
    }

    /**
     * Converts the Json Values to integers
     * 
     * @param jsonArray the Json Array to convert to
     * @return the Array of the converted integers
     */
    private static Integer[] convertJsonInteger(JsonArray jsonArray) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.getInt(i));
        }
        return list.toArray(new Integer[0]);
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
     * @param player the object needed to access Player class methods
     */
    public void moveCave(Player player) {
        boolean validInput = false;

        while (!validInput) {
            try {
                player.locationOutput();
                int cave = scanner.nextInt();

                // Checks whether the move chosen is valid
                if (player.isRightStep(cave)) {
                    // If valid move, updates player's current location
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
     * Overriden method for HuntTheWumpusGUI. Gets move from a JTextField on the
     * screen.
     * 
     * @param player
     * @param move
     */
    public void moveCave(Player player, int move) {

        try {
            player.locationOutput();
            if (player.isRightStep(move)) {
                // If valid move, updates player's current location

                player.setPlace(move);
            } else {
                System.out.println("Invalid input, you can walk only to neighbour caves!");
            }
        } catch (InputMismatchException e) {
            // Ask user for valid input
            System.out.println("Invalid input. Please enter an integer.");
        }

    }

    /**
     * Check if player got to a pit trap. Terminate the program if he did.
     * 
     * @param currentPlace current location of player
     * @return boolean of whether game is still running
     */
    public boolean pitTrap(int currentPlace) {
        // Checks whether the current location is same as those of pits
        if (currentPlace == pits[0] || currentPlace == pits[1]) {
            // If so, output end statement and End Game
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
     * @param game   the Game object required to access Game methods
     */
    public void checkWarnings(Player player) {
        boolean isNextToWumpus = player.nextToWumpus();
        boolean isNextToBat = player.nextToBats();
        boolean isNextToPit = player.nextToPits();

        if (isNextToBat || isNextToWumpus || isNextToPit) {
            System.out.println("\n    WARNING:");
            if (isNextToWumpus) {
                System.out.println("\tYou smell the Wumpus in one of neighbour caves!");
            }
            if (isNextToBat) {
                System.out.println("\t You can hear Bats near you!");
            }
            if (isNextToPit) {
                System.out.println("\t You can feel the blowing of wind. Pit is near you!");
            }
            System.out.println();
        }
    }


}
