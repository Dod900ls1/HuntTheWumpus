import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = new Game();
        boolean endGame = true;
        

        // Main loop
        while (endGame) {
            char shotOrWalk = game.getUserInput();
            switch (shotOrWalk) {
                case 'W':
                    game.moveCave(game, game.getPlace());
                    endGame = game.pitTrap(game.getPlace());
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
