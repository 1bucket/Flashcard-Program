import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Dimension;

public class FCButton extends JButton implements ActionListener{
    private Flashcard fc;
    // private JLabel resp;
    // private HolderPane parent;
    private boolean isStudyCard;
    private Manage root;
    private BufferedImage img;
    static private int imgShrinkFactor = 3;

    // public FCButton(String title, Flashcard fc, boolean isStudyCard) {
    //     this(title, fc, null, isStudyCard);
    // }


    // unfinished
    public FCButton(Flashcard fc, Manage root, boolean isStudyCard) {
        super();
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

    public static int getImgShrinkFactor() {
        return imgShrinkFactor;
    }

    public void actionPerformed(ActionEvent e) {
        // System.out.println("beep");
        // System.out.println(getWidth() + ", " + getHeight());
        if (isStudyCard) {
            if (root.getOpenStudyCards().indexOf(fc) != -1) {
                root.closeStudyCard(fc);
            }
            else {
                root.openStudyCard(fc);
            }
        }
        else {
            // System.out.println("decide");
            if (root.isOpen(fc)) {
                // System.out.println("close");
                root.closeCard(fc);
            }
            else {
                // System.out.println("open");
                root.openCard(fc);
            }
        }
    }
}