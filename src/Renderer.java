import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Renderer extends JPanel {

    private static Renderer instance;
    private JFrame frame;
    private int playerLocation = -1;
    // TODO - Make it a bit more flexible. Take length directly from json file.
    private int[] circleX = new int[20];
    private int[] circleY = new int[20];
    private int[] lineIndexes1 = new int[31];
    private int[] lineIndexes2 = new int[31];

    // Public constructor
    private Renderer() {
        frame = new JFrame("Testing");
        configFrame();
    }

    public static Renderer getInstance() {
        if (instance == null) {
            instance = new Renderer();
        }
        return instance;
    }

    public void setPlayerLocation(int playerLocation) {
        this.playerLocation = playerLocation;
    }

    private void configFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Set this instance of Renderer as the content pane

        frame.setContentPane(this);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void drawCircles(Graphics g, int player) {
        int radius = 20;
        for (int i = 0; i < 20; i++) {
            if (player == i) {
                g.setColor(Color.RED);
                g.fillOval(circleX[i], circleY[i], radius, radius);
            } else {
                g.setColor(Color.GREEN);
                g.fillOval(circleX[i], circleY[i], radius, radius);
            }
            // Draw "i" inside the circle
            g.setColor(Color.BLACK);
            Font font = new Font("Arial", Font.PLAIN, 12);
            g.setFont(font);

            int textX = circleX[i] + 3;
            int textY = circleY[i] + 14;
            g.drawString(String.valueOf(i), textX, textY);
        }
    }

    public void drawLines(Graphics g) {
        for (int i = 1; i < lineIndexes1.length; i++) {
            Graphics2D g2 = (Graphics2D) g;
            int startX = circleX[lineIndexes1[i]] + 10; 
            int startY = circleY[lineIndexes1[i]] + 10; 
            int endX = circleX[lineIndexes2[i]] + 10; 
            int endY = circleY[lineIndexes2[i]] + 10; 
    
            Line2D lin = new Line2D.Float(startX, startY, endX, endY);
            g2.draw(lin);
            g2.draw(lin);
        }
    }

    // Override the paintComponent method to perform drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircles(g, playerLocation);
        drawLines(g);
    }

    // Setters for circle coordinates
    public void setCircleCoordinates(int index, int x, int y) {
        if (index >= 0 && index < 20) {
            circleX[index] = x;
            circleY[index] = y;
        }
    }

    public void setLineIndexes(int index, int index1, int index2) {
        if (index1 >= 0 && index1 < 31 && index2 >= 0 && index2 < 31) {
            lineIndexes1[index] = index1;
            lineIndexes2[index] = index2;
        }
    }

    public void setLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, 20);
        frame.add(label);
    }

    public void setLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        frame.add(label);
    }

    public void setButton(String text, ActionListener listener, int x, int y){
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setBounds(x, y, 50, 50);
        frame.add(button);
    }

    public void setTextField(String text, int x, int y){
        JTextField textField = new JTextField(text);
        textField.setBounds(x, y, 100, 30);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(text)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(text);
                }
            }
        });

        frame.add(textField);
        frame.repaint();
    }
}
