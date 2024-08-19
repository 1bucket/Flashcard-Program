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
    public static final int BRIGHTEN = 0;
    public static final int DARKEN = 1;

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
    private int hoverEffect;
    // private boolean selected;
    // private boolean checkboxed;

    public FCPButton(String textLabel, int size, Color textColor) {
        this(textLabel, size, textColor, Color.RED, Color.RED);
        setTransparent(true);
    }

    public FCPButton(String textLabel, int size, Color textColor, Color fillColor, Color borderColor) {
        super();
        super.setText(textLabel);
        this.textLabel = textLabel;
        this.size = size;
        this.textColor = textColor;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        hoverEffect = DARKEN;
        transparent = false;
        lines = new ArrayList<String>();
        linePositions = new HashMap<String, Integer>();
        rigidWidth = rigidHeight = false;
        font = GUI.font().deriveFont((float) (size == SMALL ? GUI.smallFontSize() : GUI.mediumFontSize()));
        inset = size == SMALL ? GUI.smallButtonInset() : GUI.mediumButtonInset();
        squishBordered = true;
        buttonWidth = buttonHeight = -1;
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

    @Override
    public void setText(String text) {
        textLabel = text;
        resize();
    }

    public void setButtonWidth(int newWidth) {
        buttonWidth = newWidth;
        rigidWidth = true;
        // resize();
    }

    public void setButtonHeight(int newHeight) {
        buttonHeight = newHeight;
        rigidHeight = true;
        // resize();
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

    public void setHoverEffect(int newEffect) {
        hoverEffect = newEffect;
    }

    public void setTransparent(boolean newValue) {
        transparent = newValue;
        if (!transparent) {
            setSquishBordered(false);
        }
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
        // if (textLabel.equals("<")) System.out.println(widestLineLength);
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
        // if (textLabel.equals("Move")) System.out.println("bWidth: " + buttonWidth);
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }


    public static int splitIntoLines(String text, int maxTextWidth, FontMetrics fontDetails, ArrayList<String> lineList) {
        text = new String(text) + " ";
        int textWidth = fontDetails.stringWidth(text);
        int widestLineLength = -1;
        lineList.clear();
        while (text.trim().length() > 0) {
            int lastSpaceIndex = 0;
            int index = 0;
            while (index < text.length() && lastSpaceIndex < text.length()) {
                index = text.indexOf(" ", lastSpaceIndex + 1);
                if (index < 0) {
                    break;
                }
                String lineCand = text.substring(0, index);
                // System.out.println(text.substring(0, index));
                int lineWidth = fontDetails.stringWidth(lineCand);
                if (index + 1 == text.length()) {
                    String line = text.substring(0, index);
                    text = text.substring(index + 1);
                    lineList.add(line);
                    if (lineWidth > widestLineLength) {
                        widestLineLength = lineWidth;
                    }
                    break;
                }
                else if (lineWidth > maxTextWidth) {
                    // System.out.println("boop");
                    String line = text.substring(0, lastSpaceIndex);
                    text = text.substring(lastSpaceIndex + 1);
                    lineList.add(line);
                    if (lineWidth > widestLineLength) {
                        widestLineLength = lineWidth;
                    }
                    break;
                }
                else {
                    // System.out.println("bo");
                    lastSpaceIndex = index;
                }
                // System.out.println(lineList.size());
                
            }
        }

        /*
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
                widestLineLength = fontDetails.stringWidth(text.trim());
                break;
            }
            textWidth = fontDetails.stringWidth(text);
        }
        */
        // return Math.max(widestLineLength, maxTextWidth);
        return widestLineLength;
    }

    public void textAlignLeft() {
        for (String line : lines) {
            linePositions.put(line, inset + GUI.textBuffer());
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
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        if (!b) {
            state = ButtonState.NONE;
            revalidate();
            repaint();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        Color buttonFill = fillColor;
        Color buttonBorder = borderColor;
        if (state == ButtonState.ENTERED) {
            buttonBorder = GUI.offsetBrightness(buttonBorder, hoverEffect == DARKEN ? -.05 : .15);
            buttonFill = GUI.offsetBrightness(buttonFill, hoverEffect == DARKEN ? -.05 : .15);
        }
        else if (state == ButtonState.PRESSED) {
            buttonFill = GUI.offsetBrightness(buttonFill, hoverEffect == DARKEN ? -0.1 : 0.2);
            buttonBorder = GUI.offsetBrightness(buttonBorder, hoverEffect == DARKEN ? -0.1 : 0.2);
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
            // g.setColor(Color.WHITE);
            // g.fillRect(0, 0, buttonWidth, buttonHeight);
            g.setColor(buttonFill);
            // if (textLabel.equals("Move")) System.out.println("REct widtH:" + (buttonWidth - 2 * inset));
            int pos = squishBordered ? 2 * buttonBorderThickness : 0;
            g.fillRoundRect(pos, pos, 
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
            // System.out.println(line);
            // System.out.print("Line " + index + ": " + line);
            int xPos = linePositions.get(line);
            int yPos = inset + (index + 1) * lineHeight;
            if (!squishBordered) {
                yPos += GUI.textBuffer() - 2;
            }
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
        // if (textLabel.equals("Move")) System.out.println(getWidth());
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