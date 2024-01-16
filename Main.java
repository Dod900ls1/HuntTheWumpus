import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = new Game();
        boolean endGame = true;
        int place = (int) (Math.random() * ((20) + 1));

        // Main loop
        while (endGame) {
            char shotOrWalk = game.getUserInput();
            switch (shotOrWalk) {
                case 'W':
                    place = game.moveCave(game, place);
                    System.out.printf("Pits are in: %d, %d%n",game.getPits()[0], game.getPits()[1]);
                    endGame = game.pitTrap(place);
                    break;
                case 'S':
                    // Handle it in future
                    break;
                default:
                    System.out.println("There's no such option.");
                    break;
            }
        }
    }
}
