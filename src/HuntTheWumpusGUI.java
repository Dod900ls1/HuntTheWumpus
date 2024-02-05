import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class HuntTheWumpusGUI {

    public static void main(String[] args) {
        Wumpus wumpus0 = new Wumpus();
        Wumpus wumpus1 = new Wumpus();
        Game game = new Game(wumpus0, wumpus1);
        Player player = new Player(game);

        Renderer renderer = Renderer.getInstance();
        renderer.setLabel("null", 100, 100);
        renderer.setLabel("null", 100, 200);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.out.println("Funny");
            }
        };
        renderer.setButton("M", listener, 200, 50);
        renderer.setLabel("null", 0, 0);
        DrawMap drawMap = new DrawMap();
        drawMap.drawMap(player.getPlace());
        renderer.setTextField("Funny", 100, 0);

        
        
    }
}