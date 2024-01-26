import javax.swing.*;
import java.awt.*;

public class WumpusGameGUI extends JFrame {
    private JPanel gamePanel;
    private JButton moveButton;
    private JButton shootButton;

    public WumpusGameGUI() {
        setTitle("Hunt the Wumpus");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Draw the game state here
                super.paintComponent(g);
            }
        };

        moveButton = new JButton("Move");
        shootButton = new JButton("Shoot");

        moveButton.addActionListener(e -> handleMove());
        shootButton.addActionListener(e -> handleShoot());

        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(moveButton);
        buttonPanel.add(shootButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleMove() {
        // Implement move logic
    }

    private void handleShoot() {
        // Implement shoot logic
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WumpusGameGUI gui = new WumpusGameGUI();
            gui.setVisible(true);
        });
    }
}
