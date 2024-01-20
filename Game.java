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

    // Initial number of arrows
    private int arrows = 5;

    // Arrays to store pit locations, bat locations, and the Wumpus location
    private int[] pits;
    private int Wumpus;
    private int[] bats;

    // Randomly generated starting cave
    private int place = (int) (Math.random() * ((20) + 1));

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
     * Get the current player's location
     * 
     * @return int
     */
    public int getPlace() {
        return this.place;
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
    private HashMap<Integer, Integer[]> generateCaveConnections() {
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
     * Method to display current location and available caves
     * 
     * @param index
     */
    public void locationOutput(int index) {
        System.out.printf("You're in the cave number, %d.%nAvailible caves are: %d, %d, %d.%n", index,
                generateCaveConnections().get(index)[0], generateCaveConnections().get(index)[1],
                generateCaveConnections().get(index)[2]);
    }

    /**
     * Method to check if the step made by player is valid.
     * 
     * @param index
     * @param input
     * @return boolean
     */
    public boolean isRightStep(int index, int input) {
        // Check if the input cave is among the connected caves
        for (int i : generateCaveConnections().get(index)) {
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
    public void nextToWumpus(int currentPlace) {
        if (generateCaveConnections().get(currentPlace)[0] == Wumpus
                || generateCaveConnections().get(currentPlace)[1] == Wumpus
                || generateCaveConnections().get(currentPlace)[2] == Wumpus) {
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
    public void nextToBats(int currentPlace) {
        for (int i : bats) {
            if (generateCaveConnections().get(currentPlace)[0] == i
                    || generateCaveConnections().get(currentPlace)[1] == i
                    || generateCaveConnections().get(currentPlace)[2] == i) {
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
    public void nextToPits(int currentPlace) {
        for (int i : pits) {
            if (generateCaveConnections().get(currentPlace)[0] == i
                    || generateCaveConnections().get(currentPlace)[1] == i
                    || generateCaveConnections().get(currentPlace)[2] == i) {
                System.out.println("You can feel the blowing of wind. Pit is near you!");
                return;
            }
        }
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
    public void moveCave(Game game, int currentPlace) {
        boolean validInput = false;

        while (!validInput) {
            try {
                game.locationOutput(currentPlace);
                int cave = scanner.nextInt();

                if (game.isRightStep(currentPlace, cave)) {
                    validInput = true;
                    game.locationOutput(cave);
                    place = cave;
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
    private void killBat(int shot) {
        for (int i : bats) {
            if (shot == i) {
                System.out.println("You killed the bat!");
                bats[indexOf(bats, shot)] = -1; // Get rid of this bat
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
    private boolean killWumpus(int shot) {
        if (shot == Wumpus) {
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
     * If player shoot an arrow next to a Wumpus cave, but didn't kill it - Wumpus
     * disclocates to another cave.
     * 
     * @param shot
     * @param currentPlace
     * @return boolean
     */
    private boolean scareWumpus(int shot, int currentPlace) {
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

    /**
     * This method is responsible for the whole attack system in our game. Calls
     * killWumpus(), arrowCounter(), and scareWumpus() when it is appropriate.
     * 
     * @param game
     * @param currentPlace
     * @return boolean
     */
    public boolean shootArrow(Game game, int currentPlace) {
        System.out.printf("You can shoot in caves number %d, %d, %d%n",
                generateCaveConnections().get(currentPlace)[0], generateCaveConnections().get(currentPlace)[1],
                generateCaveConnections().get(currentPlace)[2]);

        boolean validInput = false;

        while (!validInput) {
            try {
                int shot = scanner.nextInt();

                if (game.isRightStep(currentPlace, shot)) {
                    validInput = true;

                    killBat(shot);
                    if (!killWumpus(shot))
                        return false;
                    if (!arrowCounter(shot))
                        return false;
                    if (!scareWumpus(shot, currentPlace))
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
