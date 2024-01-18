import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    private HashMap<Integer, Integer[]> caves = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private int arrows = 5;
    private int[] pits;
    private int Wumpus;
    private int[] bats;
    private int place = (int) (Math.random() * ((20) + 1));

    public Game() {
        int first = generateRandomNumber();
        int second = generateRandomNumber();
        int bat1 = generateRandomNumber();
        int bat2 = generateRandomNumber();
        int bat3 = generateRandomNumber();
        int bat4 = generateRandomNumber();
        Wumpus = generateRandomNumber();

        while (second == -1 || first == second || second == place) { // We ensure that pits are different
            second = generateRandomNumber(); // as well as our player doesn't spawn in a pit
        }
        while (Wumpus == place || Wumpus == second || Wumpus == first)
            Wumpus = generateRandomNumber(); // Ensure that our player doesn't spawn on Wumpus, and Wumpus doesn't spawn
                                             // in a pit.
        while (bat1 == place || bat1 == second || bat1 == first || bat1 == Wumpus)
            bat1 = generateRandomNumber();
        while (bat2 == place || bat2 == second || bat2 == first || bat2 == Wumpus || bat2 == bat1)
            bat2 = generateRandomNumber();
        while (bat3 == place || bat3 == second || bat3 == first || bat3 == Wumpus || bat3 == bat1 || bat3 == bat2)
            bat3 = generateRandomNumber();
        while (bat4 == place || bat4 == second || bat4 == first || bat4 == Wumpus || bat4 == bat1 || bat4 == bat3
                || bat4 == bat2)
            bat4 = generateRandomNumber();

        pits = new int[] { first, second };
        if (generateRandomNumber() <= 10) {
            bats = new int[] { bat1, bat2, bat3 };
        } else {
            bats = new int[] { bat1, bat2, bat3, bat4 };
        }
    }

    
    /** 
     * get place does X
     * @return int
     */
    public int getPlace() {
        return this.place;
    }

    public int[] getBats() {
        return this.bats;
    }

    public int[] getPits() {
        return this.pits;
    }

    public int getWupmus() {
        return this.Wumpus;
    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 20) + 1; // Generate random number from 1 to 20 inclusive
    }

    private HashMap<Integer, Integer[]> fullCaves() {
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

    public void locationOutput(int index) {
        System.out.printf("You're in the cave number, %d.%nAvailible caves are: %d, %d, %d.%n", index,
                fullCaves().get(index)[0], fullCaves().get(index)[1], fullCaves().get(index)[2]);
    }

    public boolean isRightStep(int index, int input) {
        for (int i : fullCaves().get(index)) {
            if (i == input) {
                return true;
            }
        }
        return false;
    }

    public void nextToWumpus(int currentPlace) {
        if (fullCaves().get(currentPlace)[0] == Wumpus || fullCaves().get(currentPlace)[1] == Wumpus
                || fullCaves().get(currentPlace)[2] == Wumpus) {
            System.out.println("You smell the Wumpus in one of neighbour caves!");
            return;
        } else {
            return;
        }
    }

    public void nextToBats(int currentPlace) {
        for (int i : bats) {
            if (fullCaves().get(currentPlace)[0] == i || fullCaves().get(currentPlace)[1] == i
                    || fullCaves().get(currentPlace)[2] == i) {
                System.out.println("You can hear Bats near you!");
                return;
            }
        }

    }
    public void nextToPits(int currentPlace){
        for (int i : pits) {
            if (fullCaves().get(currentPlace)[0] == i || fullCaves().get(currentPlace)[1] == i
                    || fullCaves().get(currentPlace)[2] == i) {
                System.out.println("You can feel the blowing of wind. Pit is near you!");
                return;
            }
        }
    }

    public char getUserInput() {
        System.out.println("Shoot = S, Walk to another cave = W");
        return scanner.next().charAt(0);
    }

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
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
    }

    public boolean pitTrap(int currentPlace) {
        if (currentPlace == pits[0] || currentPlace == pits[1]) {
            System.out.println("HAHAHA! YOU FALL IN A PIT AND DIE!");
            return false;
        } else {
            return true;
        }
    }

    public boolean WumpusTrap(int currentPlace) {
        if (currentPlace == Wumpus) {
            System.out.println("You've got to the Wumpus's cave, and he eat you!");
            return false;
        } else {
            return true;
        }
    }

    public boolean batTrap(int currentPlace) {
        if (currentPlace == bats[0] || currentPlace == bats[1]) {
            this.place = generateRandomNumber();
            if (this.place == this.Wumpus) {
                System.out.println("Bats brought you just in the mouth of Wumpus, looser!");
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

    private static int indexOf(int[] arr, int element){
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == element){
                return i;
            }
        }
        return -1;
    }

    private void killBat(int shot){
        for (int i : bats) {
            if(shot == i){
                System.out.println("You killed the bat!");
                bats[indexOf(bats, shot)] = -1; // Get rid of this bat
            }
        }
    }

    private boolean killWumpus(int shot){
        if(shot == Wumpus){
            System.out.println("You've killed the Wumpus, you won!");
            return false;
        }else{
            return true;
        }
    }

    private boolean arrowCounter(int shot){
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

    private boolean scareWumpus(int shot, int currentPlace){
        if (fullCaves().get(currentPlace)[0] == Wumpus || fullCaves().get(currentPlace)[1] == Wumpus
        || fullCaves().get(currentPlace)[2] == Wumpus){
            this.Wumpus = generateRandomNumber();
            if (this.Wumpus == this.place) {
                System.out.println("You missed. Wumpus heared you. He came to your cave and killed you.");
                return false;
            }else{
                System.out.println("You missed. Wumpus heared you. He dislocated to other cave.");
            }
        }
        return true;
    }
    public boolean shootArrow(Game game, int currentPlace) {
        System.out.printf("You can shoot in caves number %d, %d, %d%n",
                fullCaves().get(currentPlace)[0], fullCaves().get(currentPlace)[1], fullCaves().get(currentPlace)[2]);

        boolean validInput = false;
        
        while (!validInput) {
            try {
                int shot = scanner.nextInt();

                if (game.isRightStep(currentPlace, shot)) {
                    validInput = true;
                    
                    killBat(shot);
                    if(!killWumpus(shot))
                        return false;
                    if(!arrowCounter(shot))
                        return false;
                    if(!scareWumpus(shot, currentPlace))
                        return false;
                } else {
                    System.out.println("WRONG!");
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
