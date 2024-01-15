public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.locationOutput(20);

        System.out.println(game.isRightStep(2, 20));
        System.out.println(game.isRightStep(2, 1));
    }
}





