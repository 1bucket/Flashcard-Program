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
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Dimension;
import java.awt.Graphics;
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
        frame.setMinimumSize(new Dimension(800, 600));
        editType = FC;
        this.fc = fc;

        // Flashcard text fields
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        JLabel promptLabel = new JLabel("Prompt:");
        masterPanel.add(promptLabel, constrs);
        constrs.gridy = 1;
        Dimension fieldSize = new Dimension(325, 200);
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
        // constrs.gridwidth = 1;
        constrs.gridx = 2;
        constrs.gridy = 0;
        JLabel respLabel = new JLabel("Response:");
        masterPanel.add(respLabel, constrs);
        constrs.gridy = 1;
        resp = new JTextArea(fc.getResponse());
        resp.setPreferredSize(fieldSize);
        masterPanel.add(resp, constrs);

        // Flashcard image fields
        constrs.gridy = 2;
        constrs.gridx = 0;
        masterPanel.add(new JLabel("Prompt Image:"), constrs);
        constrs.gridy = 3;
        JPanel promptImgPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fc.getPromptImg() != null && fc.getPromptImg() != "") {
                    try {
                        g.drawImage(ImageIO.read(new File(fc.getPromptImg())), 0, 0, getWidth(), getHeight(), null);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        System.exit(0);
                    }
                }
            }
        };
        promptImgPanel.setMinimumSize(fieldSize);
        promptImgPanel.setPreferredSize(fieldSize);
        masterPanel.add(promptImgPanel, constrs);
        constrs.gridx = 2;
        constrs.gridy = 2;
        masterPanel.add(new JLabel("Response Image:"), constrs);
        constrs.gridy = 3;
        JPanel respImgPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fc.getRespImg() != null && fc.getRespImg() != "") {
                    try {
                        g.drawImage(ImageIO.read(new File(fc.getRespImg())), 0, 0, getWidth(), getHeight(), null);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        System.exit(0);
                    }
                }
            }
        };
        respImgPanel.setMinimumSize(fieldSize);
        respImgPanel.setPreferredSize(fieldSize);
        masterPanel.add(respImgPanel, constrs);

        // Change image 
        constrs.gridx = 0;
        constrs.gridy = 5;
        JButton changePImg = new JButton("Change prompt image");
        JButton changeRImg = new JButton("Change response image");

        constrs.gridx = 1;
        constrs.gridy = 6;
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        masterPanel.add(confirm, constrs);

        finishSetup();
    }

    public Editor(Sleeve sleeve, Manage root) {
        this(root);
        frame.setMinimumSize(new Dimension(500, 250));
        editType = SLEEVE;
        this.sleeve = sleeve;

        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        JLabel instrs = new JLabel("Sleeve Name:");
        masterPanel.add(instrs, constrs);
        constrs.gridy = 1;
        sleeveName = new JTextField(sleeve.getName());
        sleeveName.setPreferredSize(new Dimension(175, 20));
        masterPanel.add(sleeveName, constrs);
        // constrs.gridy++;
        // JLabel location = new JLabel("Current path of new Sleeve: " + getPath());
        // masterPanel.add(location, constrs);
        constrs.gridy = 2;
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