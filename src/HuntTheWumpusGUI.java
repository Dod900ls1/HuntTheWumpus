import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class HuntTheWumpusGUI {
    private Bat bat;
    private Wumpus wumpus0;
    private Wumpus wumpus1;
    private Game game;
    private Player player;
    private Renderer renderer;
    private DrawMap drawMap;
    private JTextField caveNumberField1;
    private JTextField caveNumberField2;
    private JLabel arrowLabel;
    private JLabel warning1;
    private JLabel warning2;
    private JLabel warning3;


    public HuntTheWumpusGUI() {
        SwingUtilities.invokeLater(() -> {
            wumpus0 = new Wumpus();
            wumpus1 = new Wumpus();
            game = new Game(wumpus0, wumpus1);
            player = new Player(game);
            bat = new Bat();
            renderer = Renderer.getInstance();
            drawMap = new DrawMap();
            drawMap.drawMap(player.getPlace());

            warningLabel(player);
            moveButton();
            shotButton();
            arrowCounter(player.getArrows());
        });
    }

    // TODO - Clear text input after we pressed the move button
    private void moveButton() {
        System.out.println(game.getBats(1));
        DrawMap drawMap = new DrawMap(); // Initialize the map
        renderer.setLabel("Write where you want to move", 10, 50, 250, 40);
        caveNumberField1 = renderer.setTextField("Input cave number", 10, 100, 175, 20);

        ActionListener moveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int playerMove = Integer.parseInt(caveNumberField1.getText());
                    if (player.isRightStep(playerMove)) {
                        game.moveCave(player, playerMove);
                        handleGameEvents();
                        drawMap.resetMap();
                        drawMap.drawMap(player.getPlace());
                    } else {
                        showErrorMessage("Invalid input! You can walk only to adjacent caves.");
                    }
                } catch (NumberFormatException ex) {
                    // Handle the case when the input is not an integer
                    showErrorMessage("Invalid input! Please enter a valid integer.");
                }
            }

            private void handleGameEvents() {
                if (!game.pitTrap(player.getPlace())) {
                    showMessageAndClose("You fall into a pit, you're dead!");
                } else if (!wumpus0.WumpusTrap(player.getPlace()) || !wumpus1.WumpusTrap(player.getPlace())) {
                    showMessageAndClose("You walk into a Wumpus cave and it eats you!");
                } else if (!bat.batTrapGUI(player.getPlace(), game, player)) {
                    renderer.windowClose();
                }

                warningLabel(player);
            }

            private void showErrorMessage(String message) {
                JOptionPane.showMessageDialog(null, message);
            }

            private void showMessageAndClose(String message) {
                JOptionPane.showMessageDialog(null, message);
                renderer.windowClose();
            }
        };

        renderer.setButton("Move", moveListener, 40, 150);
    }

    // TODO - Clear text input after we pressed the shot button
    private void shotButton() {
        renderer.setLabel("<html>Write where you want to<br>shoot</html>", 10, 300, 250, 40);
        caveNumberField2 = renderer.setTextField("Input cave number", 10, 350, 175, 20);

        ActionListener shootListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int playerShot = Integer.parseInt(caveNumberField2.getText());

                    if (!player.isRightStep(playerShot)) {
                        JOptionPane.showMessageDialog(null, "Invalid input! You can shoot only to adjacent caves."); 
                        return;
                    }

                    if (!player.shootArrow(playerShot)) {
                        renderer.windowClose();
                        return;
                    }

                    renderer.removeLabel(arrowLabel);
                    arrowCounter(player.getArrows());
                    warningLabel(player);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
                }
            }
        };

        renderer.setButton("Shoot", shootListener, 40, 400);
    }
        /**
     * Check if the player is next to any Dangers. If so, output this:
     * 
     * @param player the Player object required to access Player methods
     * @param game   the Game object required to access Game methods
     */
    private void warningLabel(Player player) {
        boolean isNextToWumpus = player.nextToWumpus();
        boolean isNextToBat = player.nextToBats();
        boolean isNextToPit = player.nextToPits();

        renderer.setLabel("🚨 Warnings:", 650, 150, 170, 30, new Font("Arial", Font.BOLD, 16));

        if (isNextToWumpus) {
            warning1 = renderer.setLabel("<html>👃 You smell the Wumpus in one of<br>the neighboring caves!</html>", 600, 200, 250, 30,
                    new Font("Arial", Font.ITALIC, 16));
        } else {
            if (warning1 != null) {
                renderer.removeLabel(warning1);
                warning1 = null; // Reset the reference
            }
        }

        if (isNextToBat) {
            warning2 = renderer.setLabel("🦇 You can hear Bats near you!", 600, 250, 250, 30,
                    new Font("Arial", Font.ITALIC, 14));
        } else {
            if (warning2 != null) {
                renderer.removeLabel(warning2);
                warning2 = null; // Reset the reference
            }
        }

        if (isNextToPit) {
            warning3 = renderer.setLabel("<html>💨 You can feel a breeze of<br>wind.A pit is near you!</html>", 600, 300, 400, 30,
                    new Font("Arial", Font.ITALIC, 14));
        } else {
            if (warning3 != null) {
                renderer.removeLabel(warning3);
                warning3 = null; // Reset the reference
            }
        }
    }

    private void arrowCounter(int arrowCount) {
        renderer.setLabel("Arrows left", 650, 50, 170, 30);
        Font arrowFont = new Font("Arial", Font.BOLD, 20);
        arrowLabel = renderer.setLabel(String.valueOf(arrowCount), 670, 80, 50, 50, arrowFont);
    }

    public static void main(String[] args) {
        new HuntTheWumpusGUI();
    }
}