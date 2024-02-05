import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class HuntTheWumpusGUI {

    public static void main(String[] args) {
        Wumpus wumpus0 = new Wumpus();
        Wumpus wumpus1 = new Wumpus();
        Game game = new Game(wumpus0, wumpus1);
        Player player = new Player(game);
        Renderer renderer = Renderer.getInstance();

        DrawMap drawMap = new DrawMap();
        drawMap.drawMap(player.getPlace());

        renderer.setLabel("Write where you want to move", 10, 50, 250, 40);
        JTextField caveNumberField = renderer.setTextField("Cave number", 10, 100, 175, 20);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
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

        renderer.setButton("Move", listener, 40, 150);

    }
}