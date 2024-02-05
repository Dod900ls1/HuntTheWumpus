import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class HuntTheWumpusGUI {
    Wumpus wumpus0;
    Wumpus wumpus1;
    Game game;
    Player player;
    Renderer renderer;
    DrawMap drawMap;

    public HuntTheWumpusGUI(){
        wumpus0 = new Wumpus();
        wumpus1 = new Wumpus();
        game = new Game(wumpus0, wumpus1);
        player = new Player(game);
        renderer = Renderer.getInstance();
        drawMap = new DrawMap();

        drawMap.drawMap(player.getPlace());
        moveButton();
    }

    private void moveButton(){

        renderer.setLabel("Write where you want to move", 10, 50, 250, 40);
        JTextField caveNumberField = renderer.setTextField("Cave number", 10, 100, 175, 20);

        ActionListener moveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DrawMap drawMap = new DrawMap();
                    int playerMove = Integer.parseInt(caveNumberField.getText());
                    if(player.isRightStep(playerMove)){
                        game.moveCave(player, playerMove);
                        drawMap.resetMap();
                        drawMap.drawMap(playerMove);
                    }else{
                        JOptionPane.showMessageDialog(null, "Invalid input! You can walk only to adjecent caves.");
                    }
                } catch (NumberFormatException ex) {
                    // Handle the case when the input is not an integer
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
                }

            }
        };

        renderer.setButton("Move", moveListener, 40, 150);
    }
    public static void main(String[] args) {
        new HuntTheWumpusGUI();
    }
}