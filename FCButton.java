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

public class FCButton extends JButton implements ActionListener{
    private Flashcard fc;
    private JLabel resp;
    // private HolderPane parent;
    private boolean isStudyCard;
    private Manage root;
    private BufferedImage img;

    // public FCButton(String title, Flashcard fc, boolean isStudyCard) {
    //     this(title, fc, null, isStudyCard);
    // }

    public FCButton(Flashcard fc, Manage root, boolean isStudyCard) {
        super(fc.getPrompt());
        this.fc = fc;
        // this.parent = parent;
        this.root = root;
        this.isStudyCard = isStudyCard;
        if (fc.getPromptImgs() != null && fc.getPromptImgs().size() > 0) {
            try {
                // setIcon(new ImageIcon(fc.getPromptImgs().get(0)));
                img = ImageIO.read(new File(fc.getPromptImgs().get(0)));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
            // System.out.println("hello");
        }
        resp = new JLabel(fc.getResponse());
        // setBackground(new Color(196, 255, 197));
        // setOpaque(true);
        // setBorderPainted(false);
        addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, img.getWidth(null) / 100, img.getHeight(null) / 100, null);
    }

    public void actionPerformed(ActionEvent e) {
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