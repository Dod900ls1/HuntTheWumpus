import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        boolean endGame = true;
        System.out.println(game.isRightStep(9, 8));
        int place = (int) (Math.random() * ((20) + 1));
        while (endGame) {
            System.out.println("Shoot = S, Walk to other cave = W");
            char shotOrWalk = scanner.next().charAt(0);
            switch (shotOrWalk) {
                case 'W':
                    boolean validInput = true;
                    while (validInput) {
                        game.locationOutput(place);
                        int cave = scanner.nextInt();

                        if (game.isRightStep(place, cave)) {
                            place = cave;
                            validInput = false;
                            
                        } else {
                            System.out.println("WRONG!");
                        }

                    }
                case 'S':
                    break;
                default:
                    System.out.println("There no such an option.");
                    break;
            }

        }

    }
}
