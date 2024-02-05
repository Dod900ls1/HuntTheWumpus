import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class HuntTheWumpusGUI {
    private Wumpus wumpus0;
    private Wumpus wumpus1;
    private Game game;
    private Player player;
    private Renderer renderer;
    private DrawMap drawMap;
    private JTextField caveNumberField1;
    private JTextField caveNumberField2;
    private JLabel arrowLabel;

    public HuntTheWumpusGUI(){
        wumpus0 = new Wumpus();
        wumpus1 = new Wumpus();
        game = new Game(wumpus0, wumpus1);
        player = new Player(game);
        renderer = Renderer.getInstance();
        drawMap = new DrawMap();
        drawMap.drawMap(player.getPlace());
        
        moveButton();
        shotButton();
        arrowCounter(player.getArrows());
    }

    private void moveButton(){
        
        DrawMap drawMap = new DrawMap(); // Initialize the map
        renderer.setLabel("Write where you want to move", 10, 50, 250, 40);
        caveNumberField1 = renderer.setTextField("Input cave number", 10, 100, 175, 20);

        ActionListener moveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int playerMove = Integer.parseInt(caveNumberField1.getText());
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


    private void shotButton(){
        renderer.setLabel("<html>Write where you want to<br>shoot</html>", 10, 300, 250, 40);
        caveNumberField2 = renderer.setTextField("Input cave number", 10, 350, 175, 20);

        ActionListener shootListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int playerShot = Integer.parseInt(caveNumberField2.getText());
                    
                    if (!player.isRightStep(playerShot)) {
                        JOptionPane.showMessageDialog(null, "Invalid input! You can shoot only to adjacent caves."); // Output error message
                        return;
                    }
        
                    if (!player.shootArrow(playerShot)) {
                        renderer.windowClose();
                        return;
                    }
        
                    renderer.removeLabel(arrowLabel);
                    arrowCounter(player.getArrows());
        
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer."); // Error message
                }
            }
        };
        
        renderer.setButton("Shoot", shootListener, 40, 400);

        renderer.setButton("Shoot", shootListener, 40, 400);
    }

    private void arrowCounter(int arrowCount){
        renderer.setLabel("Arrows left", 700, 50, 170, 30);
        Font arrowFont = new Font("Arial", Font.BOLD, 20);
        arrowLabel = renderer.setLabel(String.valueOf(arrowCount), 740, 100, 50, 50, arrowFont);
    }
    public static void main(String[] args) {
        new HuntTheWumpusGUI();
    }
}