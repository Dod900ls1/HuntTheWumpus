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

    // // Initial number of arrows
    // private int arrows = 5;

    // Arrays to store pit locations, bat locations, and the Wumpus location
    private int[] pits;
    private int Wumpus;
    private int[] bats;

    // // Randomly generated starting cave
    // private int place = (int) (Math.random() * ((20) + 1));

    private Set<Integer> usedLocations = new HashSet<>();

    /**
     * Constructor to initialize the game state
     */
    public Game() {
        // Randomly generate cave locations for pits, bats, and the Wumpus
        pits = generateUniqueLocations(2);
        Wumpus = generateUniqueLocation();

        int batCount = generateRandomNumber() <= 10 ? 3 : 4;
        bats = generateUniqueLocations(batCount);
    }

    /**
     * Returns Array of location of Pits.
     * 
     * @return pits The array of pit locations
     */
    public int[] getPits(){
        return pits;
    }

    /**
     * Returns Array of location of the Wumpus.
     * 
     * @return wumpus The int of wumpus location
     */
    public int getWumpus(){
        return Wumpus;
    }

    /**
     * Returns Array of location of the bats.
     * 
     * @return bats The array of bat locations
     */
    public int[] getBats(){
        return pits;
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
     * @param count
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
     * @return int
     */
    private int generateRandomNumber() {
        return (int) (Math.random() * 20) + 1; // Generate random number from 1 to 20 inclusive
    }

    /**
     * Method to define cave connections and return the HashMap
     * Caves form a dodecahedron.
     * @return HashMap<Integer, Integer[]>
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
     * @return
     */
    public char getUserInput() {
        System.out.println("Shoot = S, Walk to another cave = W");
        return scanner.next().charAt(0);
    }

    /**
     * Method to move to a new cave based on user input
     * 
     * @param game
     * @param currentPlace
     */
    public void moveCave(Game game, int currentPlace, Player player) {
        boolean validInput = false;

        while (!validInput) {
            try {
                player.locationOutput(currentPlace, game);
                int cave = scanner.nextInt();

                if (player.isRightStep(currentPlace, cave, game)) {
                    validInput = true;
                    player.locationOutput(cave, game);
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
     * @param currentPlace
     * @return
     */
    public boolean pitTrap(int currentPlace) {
        if (currentPlace == pits[0] || currentPlace == pits[1]) {
            System.out.println("HAHAHA! YOU FALL IN A PIT AND DIE!");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if player entered Wumpus cave. Terminate the program if he is.
     * 
     * @param currentPlace
     * @return
     */
    public boolean WumpusTrap(int currentPlace) {
        if (currentPlace == Wumpus) {
            System.out.println("You've got to the Wumpus's cave, and he eat you!");
            return false;
        }
        return true;

    }

    /**
     * Check if the player encounters bats. If bats brought gamer to a pit or to
     * Wumpus, method would terminate the program.
     * 
     * @param currentPlace
     * @return
     */
    public boolean batTrap(int currentPlace) {
        if (currentPlace == bats[0] || currentPlace == bats[1]) {
            this.place = generateRandomNumber();
            if (this.place == this.Wumpus) {
                System.out.println("Bats brought you just in the mouth of Wumpus, loser!");
                return false;
            } else if (this.place == this.pits[0] || this.place == this.pits[1]) {
                System.out.println("Bats threw you in a pit and you died miserably. Great job!");
                Wumpus = generateRandomNumber();
                return false;
            }
            System.out.printf("You've got to the cave with bats. They brought you to the cave number %d%n", this.place);
            locationOutput(this.place);
            return true;
        } else {
            return true;
        }
    }

    /**
     * If player shoot an arrow next to a Wumpus cave, but didn't kill it - Wumpus
     * disclocates to another cave.
     * 
     * @param shot
     * @param currentPlace
     * @return boolean
     */
    public boolean scareWumpus(int shot, int currentPlace) {
        if (generateCaveConnections().get(currentPlace)[0] == Wumpus
                || generateCaveConnections().get(currentPlace)[1] == Wumpus
                || generateCaveConnections().get(currentPlace)[2] == Wumpus) {
            this.Wumpus = generateRandomNumber();
            if (this.Wumpus == this.place) {
                System.out.println("You missed. Wumpus heared you. He came to your cave and killed you.");
                return false;
            } else {
                System.out.println("You missed. Wumpus heared you. He dislocated to other cave.");
            }
        }
        return true;
    }
}
