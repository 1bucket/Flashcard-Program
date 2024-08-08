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

// formerly extended JButton
public class FCButton extends FCPButton implements ActionListener{
    private Flashcard fc;
    private boolean open;
    private ArrayList<String> promptLines;
    private ArrayList<String> respLines;
    private int fixedWidth;
    // private JLabel resp;
    // private HolderPane parent;

    // to be removed
    private boolean isStudyCard;
    private Manage root;
    // -- 
    private BufferedImage promptImg;
    private BufferedImage respImg;

    // might be moved to GUI
    static private int imgShrinkFactor = 1;

    // new/changed functionalities
    public FCButton(Flashcard fc) {
        super(fc.getPrompt(), FCPButton.SMALL, Color.WHITE, GUI.fcButtonFill(), GUI.fcButtonBorder()); // stub
        this.fc = fc;
        // System.out.println(this.fc);
        open = false;
        setSquishBordered(false);
        fixedWidth = 400;
        promptLines = getLines();
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
                g.drawImage(respImg, xPos, yPos, respImg.getWidth(null) / imgShrinkFactor, promptImg.getHeight(null) / imgShrinkFactor, null);
                yPos += respImg.getHeight(null) / imgShrinkFactor + GUI.textBuffer() + lineHeight;
            }
            g.setColor(getTextColor());
            // System.out.println(getTextColor());
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
            g.drawImage(promptImg, xPos, yPos, promptImg.getWidth(null) / imgShrinkFactor, promptImg.getHeight(null) / imgShrinkFactor, null);
            yPos += promptImg.getHeight(null) / imgShrinkFactor + GUI.textBuffer() + lineHeight;
        }
        g.setColor(getTextColor());
        for (int index = 0; index < promptLines.size(); index += 1) {
            g.drawString(promptLines.get(index), xPos, yPos);
            yPos += lineHeight;
        }
        
    }

    private int promptCardHeight() {
        return promptLines.size() * getFontMetrics(getFont()).getHeight() + 
                2 * (GUI.textBuffer() + getInset()) + 
                ((promptImg == null) ? 0 : (promptImg.getHeight(null) / imgShrinkFactor + GUI.textBuffer()));
    }

    @Override
    public void resize() {
        // super.resize();
        if (fc == null) return; // resize is called in constructor, this is a bandaid fix
        FontMetrics fontDetails = getFontMetrics(getFont());
        int lineHeight = fontDetails.getHeight();
        int height = 2 * (GUI.textBuffer() + getInset());

        // int maximumTextWidth = getButtonSize() == SMALL ? GUI.smallFontSize() : GUI.mediumFontSize();
        // int longestPromptLineLength = FCPButton.splitIntoLines(fc.getPrompt(), maximumTextWidth, fontDetails, promptLines);
        // height += promptLines.size() * lineHeight;
        // int longestRespLineLength = -1;
        // int promptImgWidth, respImgWidth;
        FCPButton.splitIntoLines(fc.getPrompt(), fixedWidth, fontDetails, promptLines);
        // promptImgWidth = respImgWidth = -1;
        if (promptImg != null) {
            // promptImgWidth = promptImg.getWidth(null) / imgShrinkFactor;
            height += GUI.textBuffer() + promptImg.getHeight(null) / imgShrinkFactor;
        }
        if (open) {
            // longestRespLineLength = FCPButton.splitIntoLines(fc.getResponse(), maximumTextWidth, fontDetails, respLines);
            FCPButton.splitIntoLines(fc.getResponse(), fixedWidth, fontDetails, respLines);
            // height += respLines.size() * lineHeight + 2 * GUI.textBuffer();
            if (respImg != null) {
                // respImgWidth = respImg.getWidth(null) / imgShrinkFactor;
                height += GUI.textBuffer() + respImg.getHeight(null) / imgShrinkFactor;
            }
        }
        // int longestLineLength = Math.max(longestPromptLineLength, longestRespLineLength);
        // int widestImgWidth = Math.max(respImgWidth, promptImgWidth);
        // maximumTextWidth = Math.max(longestLineLength, widestImgWidth);
        int maximumTextWidth = fixedWidth;
        setButtonWidth(maximumTextWidth + 2 * (getInset() + GUI.textBuffer()));
        FCPButton.splitIntoLines(fc.getPrompt(), maximumTextWidth, fontDetails, promptLines);
        height += promptLines.size() * lineHeight;
        if (open) {
            FCPButton.splitIntoLines(fc.getResponse(), maximumTextWidth, fontDetails, respLines);
            height += respLines.size() * lineHeight + 2 * GUI.textBuffer();
        }
        // System.out.println(height);
        setButtonHeight(height);
        setPreferredSize(new Dimension(getButtonWidth(), height));




        /*
        int widestLineLength = getButtonWidth() - 2 * (getInset() + GUI.textBuffer());
        maximumTextWidth = widestLineLength;
        int height = 2 * (getInset() + GUI.textBuffer());
        if (promptImg != null && promptImg.getWidth(null) / imgShrinkFactor > maximumTextWidth) {
            maximumTextWidth = promptImg.getWidth(null) / imgShrinkFactor;
            height += GUI.textBuffer() + promptImg.getHeight(null);
        }
        if (open && respImg != null && respImg.getWidth(null) / imgShrinkFactor > maximumTextWidth) {
            maximumTextWidth = respImg.getWidth(null) / imgShrinkFactor;
            height += GUI.textBuffer() + respImg.getHeight(null);
        }
        if (maximumTextWidth != widestLineLength) {
            FCPButton.splitIntoLines(getTextLabel(), maximumTextWidth, getFontMetrics(getFont()), getLines());
        }
        height += getLines().size() * getFontMetrics(getFont()).getHeight();
        setButtonHeight(height);
        setButtonWidth(maximumTextWidth + 2 * (GUI.textBuffer() + getInset()));
        setPreferredSize(new Dimension(getButtonWidth(), getButtonHeight()));
        // System.out.println(imgShrinkFactor);
        */
    }
/*
    // to be decommissioned
    public FCButton(Flashcard fc, Manage root, boolean isStudyCard) {
        // super();
        this.fc = fc;
        // this.parent = parent;
        this.root = root;
        this.isStudyCard = isStudyCard;
        if (fc.getPromptImg() != "") {
            try {
                // setIcon(new ImageIcon(fc.getPromptImgs().get(0)));
                img = ImageIO.read(new File(fc.getPromptImg()));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
            // System.out.println("hello");
        }
        // resp = new JLabel(fc.getResponse());
        // imgShrinkFactor = 10;
        // setBackground(new Color(196, 255, 197));
        // setOpaque(true);
        // setBorderPainted(false);
        addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g.setColor(Color.BLACK);
        if (img != null) {
            int w = img.getWidth(null) / imgShrinkFactor;
            int h = img.getHeight(null) / imgShrinkFactor;
            g.drawImage(img, 10, 10, w, h, null);
            g.drawString(fc.getPrompt(), 10, h + 25);
        }
        else {
            g.drawString(fc.getPrompt(), 10, 20);
        }
    }

    public void adaptSize() {
        if (img != null) {
            int w = img.getWidth(null) / imgShrinkFactor;
            int h = img.getHeight(null) / imgShrinkFactor;
            setMinimumSize(new Dimension(w + 20, h + 40));
            setPreferredSize(new Dimension(w + 20, h + 40));
        }
        else {
            setMinimumSize(new Dimension(75, 30));
            setPreferredSize(new Dimension(75, 30));
        }
    }
*/
    public static int getImgShrinkFactor() {
        return imgShrinkFactor;
    }

    public void actionPerformed(ActionEvent e) {
        open = !open;
        resize();
        revalidate();
        repaint();

        // System.out.println("beep");
        // System.out.println(getWidth() + ", " + getHeight());
        // if (isStudyCard) {
        //     if (root.getOpenStudyCards().indexOf(fc) != -1) {
        //         root.closeStudyCard(fc);
        //     }
        //     else {
        //         root.openStudyCard(fc);
        //     }
        // }
        // else {
        //     // System.out.println("decide");
        //     if (root.isOpen(fc)) {
        //         // System.out.println("close");
        //         root.closeCard(fc);
        //     }
        //     else {
        //         // System.out.println("open");
        //         root.openCard(fc);
        //     }
        // }
    }
}