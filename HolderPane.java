import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.ImageIcon;

public class HolderPane extends JPanel implements ItemListener, ActionListener{
    private final int HOLDS_SLEEVE = 0;
    private final int HOLDS_FC = 1;
    private boolean isInStudy;
    private int contentType;
    private Flashcard heldFC;
    private Sleeve heldSleeve;
    private JCheckBox selectButton;
    private JButton sleeveButton;
    private FCButton fcButton;
    private Manage root;
    private boolean selected;

    public HolderPane(Sleeve sleeve, Manage root, boolean isInStudy) {
        super();
        contentType = HOLDS_SLEEVE;
        setup(root, isInStudy);
        
        
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 1;
        constrs.gridy = 0;
        constrs.fill = GridBagConstraints.BOTH;
        // add(new ImageLoader("Sleeve Logo.png"));
        // constrs.gridx = 2;
        sleeveButton = new JButton(sleeve.getName(), new ImageIcon("Sleeve Logo.png"));
        // sleeveButton.setMinimumSize(new Dimension(sleeveButton.getWidth() + 3000, sleeveButton.getHeight()));
        // sleeveButton.setBackground(new Color(196, 255, 255));
        // sleeveButton.setOpaque(true);
        // sleeveButton.setBorderPainted(false);
        sleeveButton.addActionListener(this);
        add(sleeveButton, constrs);
        heldSleeve = sleeve;
        
    }

    public HolderPane(Flashcard fc, Manage root, boolean isInStudy) {
        super();
        contentType = HOLDS_FC;
        setup(root, isInStudy);
        
        
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 1;
        constrs.gridy = 0;
        constrs.fill = GridBagConstraints.BOTH;
        heldFC = fc;
        fcButton = new FCButton(heldFC, root, isInStudy);
        
        add(fcButton, constrs);
        
    }

    private void setup(Manage root, boolean isInStudy) {
        this.root = root;
        this.isInStudy = isInStudy;
        selected = false;
        setLayout(new GridBagLayout());
        selectButton = new JCheckBox();
        selectButton.addItemListener(this);
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        constrs.fill = GridBagConstraints.BOTH;
        add(selectButton, constrs);
    }

    public void resetCheckBox() {
        selectButton.setSelected(false);
    }

    public void itemStateChanged(ItemEvent update) {
        if (contentType == HOLDS_FC) {
            root.toggleSelect(heldFC);
        }
        else {
            root.toggleSelect(heldSleeve);
        }
        selected = ! selected;
        // System.out.println(root.selectedFCs.size());
    }

    public Manage getRoot() {
        return root;
    }

    // clicking on a Sleeve
    public void actionPerformed(ActionEvent e) {
        root.shiftPath(root.getPath() + e.getActionCommand() + "/");
        root.genDeckDisplay();
    }


}