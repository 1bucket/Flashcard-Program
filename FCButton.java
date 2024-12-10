import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Dimension;
import java.util.ArrayList;

public class FCButton extends FCPButton implements ActionListener, CheckboxListener{
    private Flashcard fc;
    private boolean open;
    private ArrayList<String> promptLines;
    private ArrayList<String> respLines;
    private int fixedWidth;
    private boolean selected;
    // private JLabel resp;
    // private HolderPane parent;

    // to be removed
    private boolean isStudyCard;
    // private Manage root;
    // -- 
    private BufferedImage promptImg;
    private BufferedImage respImg;

    // might be moved to GUI
    private double promptImgShrinkFactor;
    private double respImgShrinkFactor;

    // new/changed functionalities
    public FCButton(Flashcard fc) {
        super(fc.getPrompt(), FCPButton.SMALL, Color.WHITE, GUI.fcButtonFill(), GUI.fcButtonBorder()); // stub
        this.fc = fc;
        // // System.out.println(this.fc);
        open = false;
        setSquishBordered(false);
        fixedWidth = 400;
        promptLines = getLines();
        selected = false;
        respLines = new ArrayList<String>();
        // FCPButton.splitIntoLines(fc.getResponse(), getButtonWidth() - 2 * (getInset() + GUI.textBuffer()), getFontMetrics(getFont()), respLines);
        if (fc.getPromptImg() != "") {
            try {
                promptImg = ImageIO.read(new File(fc.getPromptImg()));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }
        if (fc.getRespImg() != "") {
            try {
                respImg = ImageIO.read(new File(fc.getRespImg()));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }
        resize();
        addActionListener(this);
    }

    public Flashcard getFC() {
        return fc;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int lineHeight = getFontMetrics(getFont()).getHeight();
        // left alignment
        int xPos = GUI.textBuffer() + getInset();
        if (open) {
            g.setColor(GUI.offsetBrightness(getFill(), 0.1));
            g.fillRoundRect(0, 0, getButtonWidth(), getButtonHeight(), GUI.buttonArcRadius(), GUI.buttonArcRadius());
            int yPos = promptCardHeight() + GUI.textBuffer();
            if (respImg != null) {
                g.drawImage(respImg, xPos, yPos, (int) (respImg.getWidth(null) * respImgShrinkFactor), (int) (respImg.getHeight(null) * respImgShrinkFactor), null);
                yPos += respImg.getHeight(null) * respImgShrinkFactor + GUI.textBuffer();
            }
            yPos += lineHeight;
            g.setColor(getTextColor());
            // // System.out.println(getTextColor());
            for (int index = 0; index < respLines.size(); index += 1) {
                g.drawString(respLines.get(index), xPos, yPos);
                yPos += lineHeight;
            }
        }
        g.setColor(getFill());
        g.fillRoundRect(0, 0, getButtonWidth(), promptCardHeight(), GUI.buttonArcRadius(), GUI.buttonArcRadius());
        int yPos = GUI.textBuffer() + getInset();
        if (promptImg != null) {
            // yPos += GUI.textBuffer();
            g.drawImage(promptImg, xPos, yPos, (int) (promptImg.getWidth(null) * promptImgShrinkFactor), (int) (promptImg.getHeight(null) * promptImgShrinkFactor), null);
            yPos += (int) (promptImg.getHeight(null) * promptImgShrinkFactor) + GUI.textBuffer();
        }
        yPos += lineHeight;
        g.setColor(getTextColor());
        for (int index = 0; index < promptLines.size(); index += 1) {
            g.drawString(promptLines.get(index), xPos, yPos);
            yPos += lineHeight;
        }
        
    }

    private int promptCardHeight() {
        return promptLines.size() * getFontMetrics(getFont()).getHeight() + 
                2 * (GUI.textBuffer() + getInset()) + 
                ((promptImg == null) ? 0 : ((int) (promptImg.getHeight(null) * promptImgShrinkFactor) + GUI.textBuffer()));
    }

    @Override
    public void resize() {
        if (fc == null) return; // resize is called in constructor, this is a bandaid fix
        setButtonWidth(fixedWidth + 2 * (getInset() + GUI.textBuffer()));
        FontMetrics fontDetails = getFontMetrics(getFont());
        int lineHeight = fontDetails.getHeight();
        int height = 2 * (GUI.textBuffer() + getInset());
        FCPButton.splitIntoLines(fc.getPrompt(), fixedWidth, fontDetails, promptLines);
        if (promptImg != null) {
            promptImgShrinkFactor = fixedWidth < promptImg.getWidth(null) ? 
                                        (double) fixedWidth / promptImg.getWidth(null) :
                                        1;
            height += GUI.textBuffer() + (int) (promptImg.getHeight(null) * promptImgShrinkFactor);
        }
        if (open) {
            FCPButton.splitIntoLines(fc.getResponse(), fixedWidth, fontDetails, respLines);
            height += respLines.size() * lineHeight + 2 * GUI.textBuffer();
            if (respImg != null) {
                respImgShrinkFactor = fixedWidth < respImg.getWidth(null) ? 
                                        (double) fixedWidth / respImg.getWidth(null):
                                        1;
                // System.out.println("Scaled height " + (int) (respImgShrinkFactor * respImg.getHeight(null)));
                height += GUI.textBuffer() + (int) (respImg.getHeight(null) * respImgShrinkFactor);
                // System.out.println(height);
            }
        }
        height += promptLines.size() * lineHeight;
        setButtonHeight(height);
        setPreferredSize(new Dimension(getButtonWidth(), height));
    } 

    @Override
    public void toggleReaction(boolean newValue) {
        System.out.println("received: " + newValue);
        selected = newValue;
        // // System.out.println(fc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        open = !open;
        resize();
        // String b = getParent().getClass().;
        // // System.out.println(b.length());
        // // System.out.println(getParent().getClass().equals("class FCPPanel"));
        if (getParent().getClass().toString().equals("class FCPPanel")) {
            // System.out.println("bo");
            FCPPanel pane = (FCPPanel) getParent();
            pane.setPreferredSize(new Dimension(pane.getWidth(), getButtonHeight()));
            // pane.revalidate();
            // pane.repaint();
        }
        revalidate();
        repaint();
    }
}