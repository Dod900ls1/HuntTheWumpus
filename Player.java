import java.util.Scanner;

public class Player {

    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    // Initial number of arrows
    private int arrows = 5;

    // Randomly generated starting cave
    private int place = (int) (Math.random() * ((20) + 1));

    /**
     * Get the current player's location
     * 
     * @return int
     */
    public int getPlace() {
        return this.place;
    }

    /**
     * sets the current player's location
     * 
     * @param place
     */
    public void setPlace(int place){
        this.place = place;
    }

    /**
     * Method to display current location and available caves
     * 
     * @param index
     * @param game
     */
    public void locationOutput(int index, Game game) {
        System.out.printf("You're in the cave number, %d.%nAvailible caves are: %d, %d, %d.%n", index,
                game.generateCaveConnections().get(index)[0], game.generateCaveConnections().get(index)[1],
                game.generateCaveConnections().get(index)[2]);
    }

    /**
     * Method to check if the step made by player is valid.
     * 
     * @param index
     * @param input
     * @return boolean
     */
    public boolean isRightStep(int index, int input, Game game) {
        // Check if the input cave is among the connected caves
        for (int i : game.generateCaveConnections().get(index)) {
            if (i == input) {
                return true;
            }
        }
        return false;
    }


}
