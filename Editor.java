import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import org.apache.commons.lang;
// import java.awt.Container;

public class Editor implements ActionListener {
    private final int FC = 0;
    private final int SLEEVE = 1;

    private Manage root;

    private JFrame frame;
    private JPanel masterPanel;
    private JButton confirm;
    private int editType;

    private Flashcard fc;
    private JTextArea prompt;
    private JTextArea resp;

    private Sleeve sleeve;
    private JTextField sleeveName;

    public Editor(Manage root) {
        this.root = root;
        frame = new JFrame();
        masterPanel = new JPanel();
        masterPanel.setLayout(new GridBagLayout());
        frame.setContentPane(masterPanel);
        confirm = new JButton("Confirm");
    }

    public Editor(Flashcard fc, Manage root) {
        this(root);
        frame.setMinimumSize(new Dimension(575, 300));
        editType = FC;
        this.fc = fc;
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 1;
        JLabel promptLabel = new JLabel("Prompt:");
        masterPanel.add(promptLabel, constrs);
        constrs.gridy = 2;
        Dimension fieldSize = new Dimension(200, 200);
        prompt = new JTextArea(fc.getPrompt());
        prompt.setPreferredSize(fieldSize);
        masterPanel.add(prompt, constrs);
        // constrs.gridx = 0;
        // constrs.gridy = 0;
        // constrs.fill = GridBagConstraints.HORIZONTAL;
        // constrs.gridwidth = 3;
        // JLabel location = new JLabel("Location of new Flashcard: " + getPath());
        // location.setHorizontalAlignment(JLabel.CENTER);
        // masterPanel.add(location, constrs);
        constrs.gridwidth = 1;
        constrs.gridx = 2;
        constrs.gridy = 1;
        JLabel respLabel = new JLabel("Response:");
        masterPanel.add(respLabel, constrs);
        constrs.gridy = 2;
        resp = new JTextArea(fc.getResponse());
        resp.setPreferredSize(fieldSize);
        masterPanel.add(resp, constrs);
        constrs.gridx = 1;
        constrs.gridy = 3;
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        masterPanel.add(confirm, constrs);

        finishSetup();
    }

    public Editor(Sleeve sleeve, Manage root) {
        this(root);
        frame.setMinimumSize(new Dimension(300, 150));
        editType = SLEEVE;
        this.sleeve = sleeve;

        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        JLabel instrs = new JLabel("Sleeve Name:");
        masterPanel.add(instrs, constrs);
        constrs.gridy++;
        sleeveName = new JTextField(sleeve.getName());
        sleeveName.setPreferredSize(new Dimension(175, 20));
        masterPanel.add(sleeveName, constrs);
        // constrs.gridy++;
        // JLabel location = new JLabel("Current path of new Sleeve: " + getPath());
        // masterPanel.add(location, constrs);
        constrs.gridy++;
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        masterPanel.add(confirm, constrs);

        finishSetup();
    }

    private void finishSetup() {
        // frame.revalidate();
        // frame.repaint();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private String filterNewLine(String input) {
        // System.out.println(input.indexOf("\n"));
        // while (input.indexOf("\n") != -1) {
        //     System.out.println(input.indexOf("\n"));
            input = input.replace("\n", " ");
            input = input.trim();
        // }
        return input;
    }

    public void actionPerformed(ActionEvent e) {
        if (editType == FC) {
            String newPrompt = filterNewLine(prompt.getText());
            String newResp = filterNewLine(resp.getText());
            // System.out.println(newResp);
            root.modFC(fc, newPrompt, newResp);
            // root.modFC(fc, prompt.getText().trim(), resp.getText().trim());
        }
        else {
            String newName = filterNewLine(sleeveName.getText());
            root.changeSleeveName(sleeve, newName);
            // root.changeSleeveName(sleeve, sleeveName.getText().trim());
        }
        frame.dispose();
    }
}