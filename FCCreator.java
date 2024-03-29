import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.Color;

public class FCCreator extends Creator implements ActionListener{
    private JTextArea newPrompt;
    private JTextArea newResp;

    public FCCreator(String newPath, Manage root) {
        super(newPath, root);
        JFrame frame = getFrame();
        frame.setTitle("New Flashcard");
        frame.setMinimumSize(new Dimension(575, 300));

        Container masterPanel = frame.getContentPane();
        masterPanel.setLayout(new GridBagLayout());

        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 1;
        JLabel prompt = new JLabel("Prompt:");
        masterPanel.add(prompt, constrs);
        constrs.gridy = 2;
        Dimension fieldSize = new Dimension(200, 200);
        newPrompt = new JTextArea();
        newPrompt.setPreferredSize(fieldSize);
        masterPanel.add(newPrompt, constrs);
        constrs.gridx = 0;
        constrs.gridy = 0;
        constrs.fill = GridBagConstraints.HORIZONTAL;
        constrs.gridwidth = 3;
        JLabel location = new JLabel("Location of new Flashcard: " + getPath());
        location.setHorizontalAlignment(JLabel.CENTER);
        masterPanel.add(location, constrs);
        constrs.gridwidth = 1;
        constrs.gridx = 2;
        constrs.gridy = 1;
        JLabel resp = new JLabel("Response:");
        masterPanel.add(resp, constrs);
        constrs.gridy = 2;
        newResp = new JTextArea();
        newResp.setPreferredSize(fieldSize);
        masterPanel.add(newResp, constrs);
        constrs.gridx = 1;
        constrs.gridy = 3;
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
            getRoot().createFC(getPath(), newPrompt.getText(), newResp.getText());
        }
        catch(Exception ex) {
            new ErrorMsg("Flashcard already exists in this location");
            setCloseStatus(false);
        }
        attemptDispose();
    }

}