import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JTextArea;

public class SleeveCreator extends Creator implements ActionListener {
    // String path;
    // Manage root;
    JTextField input;
    // boolean canClose;
    // JFrame frame;

    public SleeveCreator(String newPath, Manage newRoot) {
        // path = newPath;
        // root = newRoot;
        // canClose = false;

        // frame = new JFrame();
        super(newPath, newRoot);
        JFrame frame = getFrame();
        frame.setTitle("New Sleeve");
        frame.setMinimumSize(new Dimension(300, 150));

        Container masterPanel = frame.getContentPane();
        masterPanel.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        JLabel instrs = new JLabel("Please enter the name for your new Sleeve:");
        masterPanel.add(instrs, constrs);
        constrs.gridy++;
        input = new JTextField();
        input.setPreferredSize(new Dimension(175, 20));
        masterPanel.add(input, constrs);
        constrs.gridy++;
        JLabel location = new JLabel("Current path of new Sleeve: " + getPath());
        masterPanel.add(location, constrs);
        constrs.gridy++;
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        masterPanel.add(confirm, constrs);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            setCloseStatus(true);
            getRoot().createSleeve(getPath(), input.getText());
        }
        catch (Exception ex) {
            new ErrorMsg("Error: Sleeve with the same name already exists in this location");
            setCloseStatus(false);
        }
        attemptDispose();
    }

    

    public static void main(String[] args) {
        // new SleeveCreator("master/Español");
    }
}