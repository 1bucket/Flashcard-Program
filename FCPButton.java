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
import java.awt.Point;

public class FCPButton extends JButton implements MouseListener{
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;

    private String textLabel;
    private int size;
    private Color fillColor, textColor, borderColor;

    private ArrayList<String> lines;
    private HashMap<String, Integer> linePositions;
    private int lineHeight;

    private int buttonWidth, buttonHeight;
    private int buttonBorderThickness;
    private int textBuffer;
    private boolean squishBordered;
    private int inset;
    private Font font;
    private boolean rigidWidth;
    private boolean rigidHeight;

    private ButtonState state;
    private boolean transparent;

    public FCPButton(String textLabel, int size, Color textColor, Color fillColor, Color borderColor) {
        super();
        this.textLabel = textLabel;
        this.size = size;
        this.textColor = textColor;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        textBuffer = GUI.textBuffer();
        transparent = false;
        lines = new ArrayList<String>();
        linePositions = new HashMap<String, Integer>();
        rigidWidth = rigidHeight = false;
        font = GUI.font().deriveFont((float) (size == SMALL ? GUI.smallFontSize() : GUI.mediumFontSize()));
        inset = size == SMALL ? GUI.smallButtonInset() : GUI.mediumButtonInset();
        squishBordered = true;
        buttonWidth = buttonHeight = -1;
        // System.out.println(font.getSize());
        resize();
        state = ButtonState.NONE;
        if (isEnabled()) {
            addMouseListener(this);
        }
    }

    public void setFontSize(int newSize) {
        font = GUI.font().deriveFont((float) newSize);
        resize();
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public void setButtonWidth(int newWidth) {
        buttonWidth = newWidth;
        rigidWidth = true;
        resize();
    }

    public void setButtonHeight(int newHeight) {
        buttonHeight = newHeight;
        rigidHeight = true;
        resize();
    }

    public void setButtonDims(Dimension dim) {
        buttonWidth = (int) dim.getWidth();
        buttonHeight = (int) dim.getHeight();
        rigidWidth = rigidHeight = true;
        resize();
    }

    public void restoreDefaultWidth() {
        buttonWidth = -1;
        rigidWidth = false;
        resize();
    }

    public void restoreDefaultHeight() {
        buttonHeight = -1;
        rigidHeight = false;
        resize();
    }

    public void restoreDefaultDims() {
        buttonWidth = buttonHeight = -1;
        rigidWidth = rigidHeight = false;
        resize();
    }

    public boolean isSquishBordered() {
        return squishBordered;
    }

    public void setSquishBordered(boolean newValue) {
        squishBordered = newValue;
    }

    public void setTransparent(boolean newValue) {
        transparent = newValue;
    }

    public boolean isTransparent() {
        return transparent;
    }

    private void resize() {
        FontMetrics fontDetails = getFontMetrics(font);
        lines = new ArrayList<String>();
        linePositions = new HashMap<String, Integer>();
        // String text = getText();
        // lines = new ArrayList<String>(); // to maintain correct order for multi-line texts
        // Insets insets = size == SMALL ? GUI.smallButtonInsets() : GUI.mediumButtonInsets();
        int maxTextWidth;
        if (!rigidWidth) {
            maxTextWidth = size == SMALL ? GUI.maxLineWidthSmall() : GUI.maxLineWidthMed();
            buttonWidth = maxTextWidth + 2 * (inset + textBuffer);
        }
        else {
            maxTextWidth = buttonWidth - 2 * (inset + textBuffer);
        }
        // maxTextWidth -= 2 * GUI.textBuffer() + 4;
        buttonBorderThickness = inset / 2;
        // int maxTextWidth = comp.getWidth() - 2 * textBuffer;


        String text = new String(textLabel) + " ";
        int textWidth = fontDetails.stringWidth(text);
        // linePositions = new HashMap<String, Integer>();
        int widestLineLength = maxTextWidth;
        while (text.length() > 0) {
            // System.out.println(textWidth);
            // System.out.println(text);
            // System.out.println(text + ", " + (textWidth >= maxTextWidth));
            if (textWidth >= maxTextWidth) {
                String line = "";
                int index = -1;
                int width;
                while (index < text.length()) {
                    if (text.indexOf(" ", index + 1) == -1) {
                        index += 1;
                        // System.out.println("a");
                    }
                    else {
                        // System.out.println("b");
                        index = text.indexOf(" ", index + 1);
                    }
                    // System.out.println(index);
                    line = text.substring(0, index + 1);
                    width = fontDetails.stringWidth(line);
                    if (width + 2 * textBuffer >= maxTextWidth) {
                        line = line.trim();
                        width = fontDetails.stringWidth(line);
                        if (width > widestLineLength) {
                            widestLineLength = width;
                        }
                        // int xPos = (buttonWidth - width) / 2;
                        // linePositions.put(line, xPos);
                        lines.add(line);
                        text = text.substring(index + 1);
                        break;
                    }
                    // System.exit(0);
                }
                // for (int index = 0; index < text.length() + 1; index += 1) {
                //     line = text.substring(0, index);
                //     int width = fontDetails.stringWidth(line);
                //     if (width + 10 >= maxTextWidth) {
                //         int xPos = (buttonWidth - width) / 2;
                //         linePositions.put(line, xPos);
                //         lines.add(line);
                //         text = text.substring(index);
                //         break;
                //     }
                // }
            }
            else {
                // int xPos = (buttonWidth - textWidth) / 2;
                // linePositions.put(text, xPos);
                lines.add(text.trim());
                break;
            }
            textWidth = fontDetails.stringWidth(text);
        }
        lineHeight = fontDetails.getHeight();
        if (! rigidWidth) {
            buttonWidth = widestLineLength + 2 * (inset + textBuffer);
        }
        if (!rigidHeight) {
            buttonHeight = lineHeight * lines.size() + 2 * inset + textBuffer;
        }
        // default text alignment
        textAlignLeft();

        // for (String line : lines) {
        //     // System.out.println("|" + line + "|");
        //     linePositions.put(line, (buttonWidth - fontDetails.stringWidth(line)) / 2);
        // }
        // System.out.println(numLines);
        // setMinimumSize(new Dimension(buttonWidth, buttonHeight));
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }

    public void textAlignLeft() {
        for (String line : lines) {
            linePositions.put(line, inset + 5);
        }
    }

    public void textAlignCenter() {
        for (String line : lines) {
            linePositions.put(line, (buttonWidth - getFontMetrics(font).stringWidth(line)) / 2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // System.out.println(getLocation().getX() + ", " + getLocation().getY());
        // System.out.println("painting " + state);
        // int buttonWidth = getWidth();
        // int buttonHeight = getHeight();
        // Color primary = GUI.primary();
        // Color secondary = GUI.secondary();
        // Color buttonPrimary = primary;
        // Color buttonBorder = secondary;
        Color buttonFill = fillColor;
        Color buttonBorder = borderColor;
        if (state == ButtonState.ENTERED) {
            buttonBorder = GUI.offsetBrightness(buttonBorder, -.05);
            buttonFill = GUI.offsetBrightness(buttonFill, -.05);

            // float[] hsb = Color.RGBtoHSB(primary.getRed(), primary.getGreen(), primary.getBlue(), null);
            // // System.out.println(hsb[2]);
            // hsb[2] = Math.max(0, hsb[2] - (float) 0.05);
            // // System.out.println(hsb[2]);
            // buttonPrimary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            // hsb = Color.RGBtoHSB(secondary.getRed(), secondary.getGreen(), secondary.getBlue(), null);
            // hsb[2] = Math.max(0, hsb[2] - (float) 0.05);
            // // System.out.println(hsb[2]);
            // buttonSecondary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            // // buttonPrimary = new Color(primary.getRed() + 10, primary.getGreen() + 10, primary.getBlue() + 10);
            // // buttonSecondary = new Color(secondary.getRed() + 10, secondary.getGreen() + 10, secondary.getBlue() + 10);
        }
        else if (state == ButtonState.NONE) {
            // buttonPrimary = primary;
            // buttonSecondary = secondary;
        }
        else if (state == ButtonState.PRESSED) {
            buttonFill = GUI.offsetBrightness(buttonFill, -0.1);
            buttonBorder = GUI.offsetBrightness(buttonBorder, -0.1);

            // float[] hsb = Color.RGBtoHSB(primary.getRed(), primary.getGreen(), primary.getBlue(), null);
            // hsb[2] = Math.max(0, hsb[2] - (float) 0.1);
            // buttonPrimary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            // hsb = Color.RGBtoHSB(secondary.getRed(), secondary.getGreen(), secondary.getBlue(), null);
            // hsb[2] = Math.max(0, hsb[2] - (float) 0.1);
            // buttonSecondary = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            // // buttonPrimary = new Color(primary.getRed() + 20, primary.getGreen() + 20, primary.getBlue() + 20);
            // // buttonSecondary = new Color(secondary.getRed() + 20, secondary.getGreen() + 20, secondary.getBlue() + 20);
        }
        else if (state == ButtonState.FOCUSED) {

        }
        // System.out.println(buttonPrimary + ", " + buttonSecondary);
        if (!transparent) {
            if (squishBordered) {
                g.setColor(buttonBorder);
                if (state != ButtonState.PRESSED) {
                    g.fillRoundRect(buttonBorderThickness, buttonBorderThickness, 
                                    buttonWidth - 2 * buttonBorderThickness, buttonHeight - 2 * buttonBorderThickness, 
                                    GUI.buttonArcRadius(), GUI.buttonArcRadius());
                }
                else {
                    g.fillRoundRect(0, 0, buttonWidth, buttonHeight, GUI.buttonArcRadius(), GUI.buttonArcRadius());
                }
            }

            g.setColor(buttonFill);
            g.fillRoundRect(2 * buttonBorderThickness, 2 * buttonBorderThickness, 
                            buttonWidth - 2 * inset, buttonHeight - 2 * inset, 
                            GUI.buttonArcRadius(), GUI.buttonArcRadius());
        }
        // comp.setPreferredSize(new Dimension(100, 100));
        g.setFont(font);
        g.setColor(textColor);
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
                // System.out.println("entered");
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
                // System.out.println("exited");
                revalidate();
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isEnabled()) {
            state = ButtonState.PRESSED;
            // System.out.println("pressed");
            revalidate();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isEnabled()) {
            state = ButtonState.ENTERED;
            // System.out.println("released");
            revalidate();
            repaint();
        }
    }
}