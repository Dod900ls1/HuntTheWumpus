
public class HuntTheWumpusGUI {

    public static void main(String[] args) {
        Wumpus wumpus0 = new Wumpus();
        Wumpus wumpus1 = new Wumpus();
        Game game = new Game(wumpus0, wumpus1);
        Player player = new Player(game);

        Renderer renderer = Renderer.getInstance();
        renderer.setLabel("null", 0, 0);
        DrawMap drawMap = new DrawMap();

        drawMap.drawMap(player.getPlace());
        
    }
}