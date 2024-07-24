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
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class FCPButton extends JButton implements MouseListener{
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;

    private String textLabel;
    private int size;

    private ArrayList<String> lines;
    private HashMap<String, Integer> linePositions;
    private int lineHeight;

    private int buttonWidth, buttonHeight;
    private int buttonBorderThickness;
    private int inset;
    private Font font;

    private ButtonState state;

    public FCPButton(String textLabel, int size) {
        super();
        this.textLabel = textLabel;
        this.size = size;
        lines = new ArrayList<String>();
        linePositions = new HashMap<String, Integer>();
        font = GUI.font().deriveFont((float) (size == SMALL ? GUI.smallFontSize() : GUI.mediumFontSize()));
        inset = size == SMALL ? GUI.smallButtonInset() : GUI.mediumButtonInset();
        // System.out.println(font.getSize());
        resize();
        state = ButtonState.NONE;
        if (isEnabled()) {
            addMouseListener(this);
        }
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    private void resize() {
        FontMetrics fontDetails = getFontMetrics(font);
        // String text = getText();
        // lines = new ArrayList<String>(); // to maintain correct order for multi-line texts
        // Insets insets = size == SMALL ? GUI.smallButtonInsets() : GUI.mediumButtonInsets();
        int maxTextWidth = size == SMALL ? GUI.maxLineWidthSmall() : GUI.maxLineWidthMed();
        // maxTextWidth -= 2 * GUI.textBuffer() + 4;
        buttonWidth = maxTextWidth + 2 * (inset + GUI.textBuffer());
        buttonBorderThickness = inset / 2;
        // int maxTextWidth = comp.getWidth() - 2 * textBuffer;

        String text = textLabel;
        int textWidth = fontDetails.stringWidth(text);
        // linePositions = new HashMap<String, Integer>();
        while (text.length() > 0) {
            // System.out.println(textWidth);
            // System.out.println(text);
            if (textWidth >= maxTextWidth) {
                String line = "";
                for (int index = 0; index < text.length() + 1; index += 1) {
                    line = text.substring(0, index);
                    // System.out.println(line);
                    int width = fontDetails.stringWidth(line);
                    // System.out.println(width);
                    // System.out.println(maxTextWidth);
                    if (width >= maxTextWidth) {
                        // System.out.println("boop");
                        int xPos = (buttonWidth - width) / 2;
                        linePositions.put(line, xPos);
                        lines.add(line);
                        text = text.substring(index);
                        // System.out.println(text);
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
        lineHeight = fontDetails.getHeight();
        buttonHeight = lineHeight * lines.size() + 2 * inset + GUI.textBuffer();
        // System.out.println(numLines);
        // setMinimumSize(new Dimension(buttonWidth, buttonHeight));
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // System.out.println("painting " + state);
        // int buttonWidth = getWidth();
        // int buttonHeight = getHeight();
        Color primary = GUI.primary();
        Color secondary = GUI.secondary();
        Color buttonPrimary = primary;
        Color buttonSecondary = secondary;
        if (state == ButtonState.ENTERED) {
            float[] hsb = Color.RGBtoHSB(primary.getRed(), primary.getGreen(), primary.getBlue(), null);
            // System.out.println(hsb[2]);
            hsb[2] = Math.max(0, hsb[2] - (float) 0.05);
            // System.out.println(hsb[2]);
            buttonPrimary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            hsb = Color.RGBtoHSB(secondary.getRed(), secondary.getGreen(), secondary.getBlue(), null);
            hsb[2] = Math.max(0, hsb[2] - (float) 0.05);
            // System.out.println(hsb[2]);
            buttonSecondary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            // buttonPrimary = new Color(primary.getRed() + 10, primary.getGreen() + 10, primary.getBlue() + 10);
            // buttonSecondary = new Color(secondary.getRed() + 10, secondary.getGreen() + 10, secondary.getBlue() + 10);
        }
        else if (state == ButtonState.NONE) {
            buttonPrimary = primary;
            buttonSecondary = secondary;
        }
        else if (state == ButtonState.PRESSED) {
            float[] hsb = Color.RGBtoHSB(primary.getRed(), primary.getGreen(), primary.getBlue(), null);
            hsb[2] = Math.max(0, hsb[2] - (float) 0.1);
            buttonPrimary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            hsb = Color.RGBtoHSB(secondary.getRed(), secondary.getGreen(), secondary.getBlue(), null);
            hsb[2] = Math.max(0, hsb[2] - (float) 0.1);
            buttonSecondary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            // buttonPrimary = new Color(primary.getRed() + 20, primary.getGreen() + 20, primary.getBlue() + 20);
            // buttonSecondary = new Color(secondary.getRed() + 20, secondary.getGreen() + 20, secondary.getBlue() + 20);
        }
        else if (state == ButtonState.FOCUSED) {

        }
        // System.out.println(buttonPrimary + ", " + buttonSecondary);
        g.setColor(buttonSecondary);
        if (state != ButtonState.PRESSED) {
            g.fillRoundRect(buttonBorderThickness, buttonBorderThickness, 
                            buttonWidth - 2 * buttonBorderThickness, buttonHeight - 2 * buttonBorderThickness, 
                            GUI.buttonArcRadius(), GUI.buttonArcRadius());
        }
        else {
            g.fillRoundRect(0, 0, buttonWidth, buttonHeight, GUI.buttonArcRadius(), GUI.buttonArcRadius());
        }

        g.setColor(buttonPrimary);
        g.fillRoundRect(2 * buttonBorderThickness, 2 * buttonBorderThickness, 
                        buttonWidth - 2 * inset, buttonHeight - 2 * inset, 
                        GUI.buttonArcRadius(), GUI.buttonArcRadius());

        // comp.setPreferredSize(new Dimension(100, 100));
        g.setFont(font);
        g.setColor(GUI.textColor());
        for (int index = 0; index < lines.size(); index += 1) {
            String line = lines.get(index);
            // System.out.print("Line " + index + ": " + line);
            int xPos = linePositions.get(line);
            int yPos = inset + (index + 1) * lineHeight;
            // System.out.println(" : " + "Pos " + xPos + " " + yPos);
            
            g.drawString(line, xPos, yPos);
            // g.setColor(Color.BLACK);
            // g.drawLine(xPos, yPos - lineHeight, xPos + 15, yPos - lineHeight);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isEnabled()) {
            mousePressed(e);
            mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (isEnabled()) {
            if (state == ButtonState.PRESSED) {
                return;
            }
            else {
                state = ButtonState.ENTERED;
                System.out.println("entered");
                revalidate();
                repaint();
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (isEnabled()) {
            if (state == ButtonState.PRESSED) {
                return;
            }
            else {
                state = ButtonState.NONE;
                System.out.println("exited");
                revalidate();
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isEnabled()) {
            state = ButtonState.PRESSED;
            System.out.println("pressed");
            revalidate();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isEnabled()) {
            state = ButtonState.ENTERED;
            System.out.println("released");
            revalidate();
            repaint();
        }
    }
}