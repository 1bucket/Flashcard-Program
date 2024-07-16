import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.FontMetrics;
import java.awt.Font;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GraphicsEnvironment;
import java.awt.Dimension;

public class GUI {
    private static Color primary;
    private static Color secondary;
    private static Color tertiary;

    private static int textBuffer;
    private static Font font;
    private static Color textColor;

    private static int buttonArcRadius;
    private static int buttonBorderThickness;

    public static void init() {
        primary = Color.RED;
        secondary = Color.BLUE;
        tertiary = Color.GREEN;

        textBuffer = 50;
        // for (Font font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
        //     System.out.println(font);
        // }
        // font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
        font = new Font("Serif", Font.PLAIN, 20);
        textColor = Color.BLACK;

        buttonArcRadius = 15;
        buttonBorderThickness = 10;
    }

    public static JButton getButton(String text) {
        return new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                g.setColor(secondary);
                g.fillRoundRect(0, 0, width, height, buttonArcRadius, buttonArcRadius);
                g.setColor(primary);
                g.fillRoundRect(buttonBorderThickness, buttonBorderThickness, 
                                width - 2 * buttonBorderThickness, height - 2 * buttonBorderThickness, 
                                buttonArcRadius, buttonArcRadius);
                drawText(this, getText());
                
            }
        };
    }

    // Assumes centered orientation
    private static void drawText(JComponent comp, String text) {
        FontMetrics fontDetails = comp.getFontMetrics(font);
        ArrayList<String> lines = new ArrayList<String>(); // to maintain correct order for multi-line texts
        int maxTextWidth = comp.getWidth() - 2 * textBuffer;
        int textWidth = fontDetails.stringWidth(text);
        HashMap<String, Integer> linePositions = new HashMap<String, Integer>();
        while (textWidth > 0) {
            if (textWidth > maxTextWidth) {
                String line = "";
                for (int index = 0; index < text.length(); index += 1) {
                    line = text.substring(0, index);
                    int width = fontDetails.stringWidth(line);
                    if (width > maxTextWidth) {
                        int xPos = (comp.getWidth() - width) / 2;
                        linePositions.put(line, xPos);
                        lines.add(line);
                        text = text.substring(index);
                        break;
                    }
                }
            }
            else {
                int xPos = (comp.getWidth() - textWidth) / 2;
                linePositions.put(text, xPos);
                lines.add(text);
                break;
            }
            textWidth = fontDetails.stringWidth(text);
        }
        int lineHeight = comp.getHeight();
        int numLines = lines.size();
        for (int index = 0; index < lines.size(); index += 1) {
            String line = lines.get(index);
            int xPos = linePositions.get(line);
            int yPos = textBuffer + (index + 1) * lineHeight;
            Graphics g = comp.getGraphics();
            g.setFont(font);
            g.setColor(textColor);
            g.drawString(line, xPos, yPos);
        }
    }

    public static Color getPrimary() {
        return primary;
    }
    
    public static Color getSecondary() {
        return secondary;
    }

    public static Color getTertiary() {
        return tertiary;
    }

    public static void main(String[] args) {
        init();

        JFrame frame = new JFrame("test");
        JPanel testPanel = new JPanel();
        testPanel.setLayout(null);
        JButton testButton = getButton("yippeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        // testButton.setMinimumSize(new Dimension(100, 75));
        testPanel.add(testButton);
        frame.setContentPane(testPanel);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}