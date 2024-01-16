import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Random random = new Random();
    private HashMap<Integer, Integer[]> caves = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private int[] pits;

    public Game(){
	    int first = (int) (Math.random() * ((20) + 1));
	    int second = -1;

	    while(second == -1 || first == second){
    		second = (int) (Math.random() * ((20) + 1));;
	    }
        int[] result = {first, second};
        pits = result;	
    }

    // public int[] getPits(){
    //     return this.pits;             I was needed it to check if pits work.
    // }

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



    public char getUserInput() {
        System.out.println("Shoot = S, Walk to another cave = W");
        return scanner.next().charAt(0);
    }

    public int moveCave(Game game, int currentPlace) {
        boolean validInput = true;
        while (validInput) {
            game.locationOutput(currentPlace);
            int cave = scanner.nextInt();

            if (game.isRightStep(currentPlace, cave)) {
                validInput = false;
                game.locationOutput(cave);
                return cave;
            } else {
                System.out.println("WRONG!");
            }
        }
        return currentPlace;
    }

    public boolean pitTrap(int currentPlace){
        if(currentPlace == pits[0] || currentPlace == pits[1]){
            System.out.println("HAHAHA! YOU FALL IN A PIT!");
            return false;
        }else{
            return true;
        }
    }
}
