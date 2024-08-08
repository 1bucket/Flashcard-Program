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
import javax.swing.JPanel;
import javax.swing.JFrame;
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
    // private int textBuffer;
    private boolean squishBordered;
    private int inset;
    private Font font;
    private boolean rigidWidth;
    private boolean rigidHeight;
    // private int checkboxSideLength;

    private ButtonState state;
    private boolean transparent;
    // private boolean selected;
    // private boolean checkboxed;

    public FCPButton(String textLabel, int size, Color textColor, Color fillColor, Color borderColor) {
        super();
        this.textLabel = textLabel;
        this.size = size;
        this.textColor = textColor;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        // textBuffer = GUI.textBuffer();
        // checkboxSideLength = 15;
        transparent = false;
        // checkboxed = false;
        // selected = false;
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

    public int getButtonSize() {
        return size;
    }

    public String getTextLabel() {
        return textLabel;
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public int getInset() {
        return inset;
    }

    public Font getFont() {
        return font;
    }

    public Color getFill() {
        return fillColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setButtonWidth(int newWidth) {
        buttonWidth = newWidth;
        rigidWidth = true;
    }

    public void setButtonHeight(int newHeight) {
        buttonHeight = newHeight;
        rigidHeight = true;
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
        if (squishBordered) {
            inset = size == SMALL ? GUI.smallButtonInset() : GUI.mediumButtonInset();
        }
        else {
            inset = 0;
        }
    }

    public void setTransparent(boolean newValue) {
        transparent = newValue;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void resize() {
        FontMetrics fontDetails = getFontMetrics(font);
        // lines = new ArrayList<String>();
        linePositions = new HashMap<String, Integer>();
        int maxTextWidth;
        if (!rigidWidth) {
            maxTextWidth = size == SMALL ? GUI.maxLineWidthSmall() : GUI.maxLineWidthMed();
            buttonWidth = maxTextWidth + 2 * (inset + GUI.textBuffer());
        }
        else {
            maxTextWidth = buttonWidth - 2 * (inset + GUI.textBuffer());
        }
        buttonBorderThickness = inset / 2;
        int widestLineLength = splitIntoLines(textLabel, maxTextWidth, fontDetails, lines);
        lineHeight = fontDetails.getHeight();
        if (! rigidWidth) {
            buttonWidth = widestLineLength + 2 * (inset + GUI.textBuffer());
        }
        if (!rigidHeight) {
            buttonHeight = lineHeight * lines.size() + 2 * inset + GUI.textBuffer();
        }
        textAlignLeft();
        // if (checkboxed) {
        //     for (int index = 0; index < lines.size(); index += 1) {
        //         linePositions.put(lines.get(index), linePositions.get(lines.get(index)) + checkboxSideLength + 3 * GUI.textBuffer());
        //     }
        //     buttonWidth += checkboxSideLength + 3 * GUI.textBuffer();
        // }
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }


    public static int splitIntoLines(String text, int maxTextWidth, FontMetrics fontDetails, ArrayList<String> lineList) {
        text = new String(text) + " ";
        int textWidth = fontDetails.stringWidth(text);
        int widestLineLength = maxTextWidth;
        lineList.clear();
        while (text.length() > 0) {
            if (textWidth >= maxTextWidth) {
                String line = "";
                int index = -1;
                int width;
                while (index < text.length()) {
                    if (text.indexOf(" ", index + 1) == -1) {
                        index += 1;
                    }
                    else {
                        index = text.indexOf(" ", index + 1);
                    }
                    line = text.substring(0, index + 1);
                    width = fontDetails.stringWidth(line);
                    if (width + 2 * GUI.textBuffer() >= maxTextWidth) {
                        line = line.trim();
                        width = fontDetails.stringWidth(line);
                        if (width > widestLineLength) {
                            widestLineLength = width;
                        }
                        lineList.add(line);
                        text = text.substring(index + 1);
                        break;
                    }
                }
            }
            else {
                lineList.add(text.trim());
                break;
            }
            textWidth = fontDetails.stringWidth(text);
        }
        return widestLineLength;
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
/*
    public void toggleCheckbox() {
        checkboxed = !checkboxed;
        resize();
        revalidate();
        repaint();
    }

    public void toggleSelect() {
        selected = ! selected;
        revalidate();
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }
*/
    @Override
    protected void paintComponent(Graphics g) {
        Color buttonFill = fillColor;
        Color buttonBorder = borderColor;
        if (state == ButtonState.ENTERED) {
            buttonBorder = GUI.offsetBrightness(buttonBorder, -.05);
            buttonFill = GUI.offsetBrightness(buttonFill, -.05);
        }
        else if (state == ButtonState.PRESSED) {
            buttonFill = GUI.offsetBrightness(buttonFill, -0.1);
            buttonBorder = GUI.offsetBrightness(buttonBorder, -0.1);
        }
        else if (state == ButtonState.FOCUSED) {

        }
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
        // if (checkboxed) {
        //     g.setColor(GUI.offsetBrightness(Color.BLACK, .05));
        //     g.drawRect(3 *GUI.textBuffer(), (buttonHeight - checkboxSideLength) / 2, checkboxSideLength, checkboxSideLength);
        // }
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