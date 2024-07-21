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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.SpringLayout;

public class GUI {
    // public static final int SMALL_BUTTON = 0;
    // public static final int MEDIUM_BUTTON = 1;

    private static Color primary;
    private static Color secondary;
    private static Color tertiary;

    private static Insets smallButtonInsets;
    private static Insets mediumButtonInsets;
    private static int maxLineWidthSmall;
    private static int maxLineWidthMed;
    private static Font font;
    private static Color textColor;
    private static int smallFontSize;
    private static int mediumFontSize;

    private static int buttonArcRadius;
    private static int buttonBorderThickness;

    public static void init() {
        primary = Color.RED;
        secondary = Color.BLUE;
        tertiary = Color.GREEN;

        smallButtonInsets = new Insets(5, 10, 5, 10);
        mediumButtonInsets = new Insets(10, 25, 10, 25);
        maxLineWidthSmall = 100;
        maxLineWidthMed = 150;
        // for (Font font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
        //     System.out.println(font);
        // }
        // font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
        font = new Font("Serif", Font.PLAIN, 40);
        textColor = Color.BLACK;
        smallFontSize = 20;
        mediumFontSize = 40;

        buttonArcRadius = 15;
        buttonBorderThickness = 5;
    }

    public static Color primary() {
        return primary;
    }

    public static Color secondary() {
        return secondary;
    }

    public static Color tertiary() {
        return tertiary;
    }

    public static Insets smallButtonInsets() {
        return smallButtonInsets;
    }

    public static Insets mediumButtonInsets() {
        return mediumButtonInsets;
    }

    public static int maxLineWidthSmall() {
        return maxLineWidthSmall;
    }

    public static int maxLineWidthMed() {
        return maxLineWidthMed;
    }

    public static Font font() {
        return font;
    }

    public static int smallFontSize() {
        return smallFontSize;
    }
    
    public static int mediumFontSize() {
        return mediumFontSize;
    }

    public static Color textColor() {
        return textColor;
    }

    public static int buttonArcRadius() {
        return buttonArcRadius;
    }

    public static int buttonBorderThickness() {
        return buttonBorderThickness;
    }
    /*
    private static void resize(JButton button, int size) {
        FontMetrics fontDetails = button.getFontMetrics(font);
                String text = button.getText();
                ArrayList<String> lines = new ArrayList<String>(); // to maintain correct order for multi-line texts
                Insets insets = size == SMALL_BUTTON ? smallButtonInsets : mediumButtonInsets;
                int maxTextWidth = size == SMALL_BUTTON ? maxLineWidthSmall : maxLineWidthMed;
                int buttonWidth = maxTextWidth + insets.left + insets.right;
                // int maxTextWidth = comp.getWidth() - 2 * textBuffer;


                int textWidth = fontDetails.stringWidth(text);
                HashMap<String, Integer> linePositions = new HashMap<String, Integer>();
                while (text.length() > 0) {
                    if (textWidth > maxTextWidth) {
                        String line = "";
                        for (int index = 0; index < text.length(); index += 1) {
                            line = text.substring(0, index);
                            int width = fontDetails.stringWidth(line);
                            if (width > maxTextWidth) {
                                int xPos = (buttonWidth - width) / 2;
                                linePositions.put(line, xPos);
                                // System.out.println(xPos);
                                lines.add(line);
                                text = text.substring(index);
                                break;
                            }
                        }
                    }
                    else {
                        int xPos = (buttonWidth - textWidth) / 2;
                        linePositions.put(text, xPos);
                        lines.add(text);
                        break;
                    }
                    textWidth = fontDetails.stringWidth(text);
                }
                int lineHeight = fontDetails.getHeight();
                int numLines = lines.size();
                int buttonHeight = lineHeight * numLines + insets.top + insets.bottom;
                // setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }

    public static JButton getButton(String text, int size) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // int width = getWidth();
                // int height = getHeight();
                // drawButton(this, size);
                FontMetrics fontDetails = getFontMetrics(font);
                String text = getText();
                ArrayList<String> lines = new ArrayList<String>(); // to maintain correct order for multi-line texts
                Insets insets = size == SMALL_BUTTON ? smallButtonInsets : mediumButtonInsets;
                int maxTextWidth = size == SMALL_BUTTON ? maxLineWidthSmall : maxLineWidthMed;
                int buttonWidth = maxTextWidth + insets.left + insets.right;
                // int maxTextWidth = comp.getWidth() - 2 * textBuffer;


                int textWidth = fontDetails.stringWidth(text);
                HashMap<String, Integer> linePositions = new HashMap<String, Integer>();
                while (text.length() > 0) {
                    if (textWidth > maxTextWidth) {
                        String line = "";
                        for (int index = 0; index < text.length(); index += 1) {
                            line = text.substring(0, index);
                            int width = fontDetails.stringWidth(line);
                            if (width > maxTextWidth) {
                                int xPos = (buttonWidth - width) / 2;
                                linePositions.put(line, xPos);
                                // System.out.println(xPos);
                                lines.add(line);
                                text = text.substring(index);
                                break;
                            }
                        }
                    }
                    else {
                        int xPos = (buttonWidth - textWidth) / 2;
                        linePositions.put(text, xPos);
                        lines.add(text);
                        break;
                    }
                    textWidth = fontDetails.stringWidth(text);
                }
                int lineHeight = fontDetails.getHeight();
                int numLines = lines.size();
                int buttonHeight = lineHeight * numLines + insets.top + insets.bottom;
                // setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                setMaximumSize(new Dimension(buttonWidth, buttonHeight));

                System.out.println(buttonWidth + ", " + buttonHeight);
                // Graphics g = getGraphics();
                g.setColor(secondary);
                g.fillRoundRect(0, 0, buttonWidth, buttonHeight, buttonArcRadius, buttonArcRadius);
                g.setColor(primary);
                g.fillRoundRect(buttonBorderThickness, buttonBorderThickness, 
                                buttonWidth - 2 * buttonBorderThickness, buttonHeight - 2 * buttonBorderThickness, 
                                buttonArcRadius, buttonArcRadius);

                // comp.setPreferredSize(new Dimension(100, 100));
                for (int index = 0; index < lines.size(); index += 1) {
                    String line = lines.get(index);
                    System.out.print("Line " + index + ": " + line);
                    int xPos = linePositions.get(line);
                    int yPos = insets.top + (index + 1) * lineHeight;
                    System.out.println(" : " + "Pos " + xPos + " " + yPos);
                    g.setFont(font);
                    g.setColor(textColor);
                    g.drawString(line, xPos, yPos);
                }
                
            }
        };
        resize(button, size);
        return button;
    }

    // Assumes centered orientation
    private static void drawButton(JButton button, int size) {
        FontMetrics fontDetails = button.getFontMetrics(font);
        String text = button.getText();
        ArrayList<String> lines = new ArrayList<String>(); // to maintain correct order for multi-line texts
        Insets insets = size == SMALL_BUTTON ? smallButtonInsets : mediumButtonInsets;
        int maxTextWidth = size == SMALL_BUTTON ? maxLineWidthSmall : maxLineWidthMed;
        int buttonWidth = maxTextWidth + insets.left + insets.right;
        // int maxTextWidth = comp.getWidth() - 2 * textBuffer;


        int textWidth = fontDetails.stringWidth(text);
        HashMap<String, Integer> linePositions = new HashMap<String, Integer>();
        while (text.length() > 0) {
            if (textWidth > maxTextWidth) {
                String line = "";
                for (int index = 0; index < text.length(); index += 1) {
                    line = text.substring(0, index);
                    int width = fontDetails.stringWidth(line);
                    if (width > maxTextWidth) {
                        int xPos = (buttonWidth - width) / 2;
                        linePositions.put(line, xPos);
                        // System.out.println(xPos);
                        lines.add(line);
                        text = text.substring(index);
                        break;
                    }
                }
            }
            else {
                int xPos = (buttonWidth - textWidth) / 2;
                linePositions.put(text, xPos);
                lines.add(text);
                break;
            }
            textWidth = fontDetails.stringWidth(text);
        }
        int lineHeight = fontDetails.getHeight();
        int numLines = lines.size();
        int buttonHeight = lineHeight * numLines + insets.top + insets.bottom;
        button.setMinimumSize(new Dimension(buttonWidth - (insets.left + insets.right) / 2, buttonHeight - (insets.top + insets.bottom) / 2));
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));

        System.out.println(buttonWidth + ", " + buttonHeight);
        Graphics g = button.getGraphics();
        g.setColor(secondary);
        g.fillRoundRect(0, 0, buttonWidth, buttonHeight, buttonArcRadius, buttonArcRadius);
        g.setColor(primary);
        g.fillRoundRect(buttonBorderThickness, buttonBorderThickness, 
                        buttonWidth - 2 * buttonBorderThickness, buttonHeight - 2 * buttonBorderThickness, 
                        buttonArcRadius, buttonArcRadius);

        // comp.setPreferredSize(new Dimension(100, 100));
        for (int index = 0; index < lines.size(); index += 1) {
            String line = lines.get(index);
            System.out.print("Line " + index + ": " + line);
            int xPos = linePositions.get(line);
            int yPos = insets.top + (index + 1) * lineHeight;
            System.out.println(" : " + "Pos " + xPos + " " + yPos);
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
    }*/

    public static void main(String[] args) {
        init();


        JFrame frame = new JFrame("test");
        Dimension dim = new Dimension(800, 600);
        JPanel testPanel = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        testPanel.setLayout(springLayout);
        // testPanel.setLayout(null);
        // testPanel.setLayout(new GridBagLayout());
        // GridBagConstraints constrs = new GridBagConstraints();
        // constrs.gridx = 0;
        // constrs.gridy = 0;
        // constrs.fill = GridBagConstraints.HORIZONTAL;
        // constrs.weightx = 1;
        // constrs.weighty = 1;
        // constrs.gr
        // JButton testButton = getButton("yippeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", SMALL_BUTTON);
        FCPButton testButton = new FCPButton("yippeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", FCPButton.SMALL_BUTTON);
        // testButton = new JButton("yippeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        // testButton.setMinimumSize(new Dimension(100, 75));
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println(testButton.getHeight());
                System.exit(0);
            }
        });
        testPanel.add(testButton);
        int xPos = ((int) dim.getWidth() - testButton.getButtonWidth()) / 2;
        // System.out.println(testButton.getWidth());
        springLayout.putConstraint(SpringLayout.NORTH, testButton, 15, SpringLayout.NORTH, testPanel);
        springLayout.putConstraint(SpringLayout.WEST, testButton, xPos, SpringLayout.WEST, testPanel);
        // testPanel.add(testButton, constrs);
        // System.out.println("bangah " + testButton.getWidth() + " " + testButton.getHeight());
        frame.setContentPane(testPanel);
        frame.setPreferredSize(dim);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}