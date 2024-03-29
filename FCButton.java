import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class FCButton extends JButton implements ActionListener{
    private Flashcard fc;
    private JLabel resp;
    // private HolderPane parent;
    private boolean isStudyCard;
    private Manage root;

    // public FCButton(String title, Flashcard fc, boolean isStudyCard) {
    //     this(title, fc, null, isStudyCard);
    // }

    public FCButton(Flashcard fc, Manage root, boolean isStudyCard) {
        super(fc.getPrompt());
        this.fc = fc;
        // this.parent = parent;
        this.root = root;
        this.isStudyCard = isStudyCard;
        resp = new JLabel(fc.getResponse());
        // setBackground(new Color(196, 255, 197));
        // setOpaque(true);
        // setBorderPainted(false);
        addActionListener(this);
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