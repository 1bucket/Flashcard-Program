import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;

public class Flipper extends JPanel implements ActionListener{
    private FCStack studyStack;
    private Study root;

    // private boolean isJeopardyMode;
    private int studyInd;
    private FCStack correctStack;
    private FCStack missedStack;

    private JPanel cardPanel;
    private JPanel progressPanel;
    private JLabel progressTracker;
    private String prompt;
    private String response;
    private String seePromptAgain;
    private int studySize;
    private Image promptImg;
    private Image respImg;

    public Flipper(FCStack newStack, Study newRoot) {
        super();
        // isJeopardyMode = jeopardyMode;
        studyStack = newStack;
        root = newRoot;
        correctStack = new FCStack();
        missedStack = new FCStack();
        studySize = newStack.getStack().size();
        seePromptAgain = "See prompt again";

        // JPanel
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Subpanels
        progressPanel = new JPanel();
        progressTracker = new JLabel();
        progressTracker.setFont(new Font("Comic Sans", Font.PLAIN, 25));
        progressPanel.add(progressTracker);
        updateProgress();
        add(progressPanel);
        cardPanel = new JPanel();
        updateCard();
        displayPrompt();
    }

    // Display prompt
    public void displayPrompt() {
        cardPanel.setVisible(false);
        cardPanel = new JPanel();
        // JButton promptCard = new JButton(prompt);
        JButton promptCard = new JButton(prompt){
            @Override
            protected void paintComponent(Graphics g) {
                // super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g.setColor(Color.BLACK);
                // System.out.println(getWidth() + ", " + getHeight());
                if (promptImg != null) {
                    int xPos;
                    int imgWidth = promptImg.getWidth(null);
                    int imgHeight = promptImg.getHeight(null);
                    int widthSpace = getWidth() - 20;
                    int heightSpace = getHeight() - 50;
                    if (imgWidth > widthSpace) {
                        promptImg = promptImg.getScaledInstance(widthSpace, imgHeight, BufferedImage.SCALE_SMOOTH);
                        double scale = (double) widthSpace / imgWidth;
                        imgWidth = widthSpace;
                        imgHeight = (int) (imgHeight * scale);
                        promptImg = promptImg.getScaledInstance(widthSpace, imgHeight, BufferedImage.SCALE_SMOOTH);
                    }
                    if (imgHeight > heightSpace) {
                        promptImg = promptImg.getScaledInstance(imgWidth, heightSpace, BufferedImage.SCALE_SMOOTH);
                        double scale = (double) heightSpace / imgHeight;
                        imgHeight = heightSpace;
                        imgWidth = (int) (imgWidth * scale);
                        promptImg = promptImg.getScaledInstance(imgWidth, heightSpace, BufferedImage.SCALE_SMOOTH);
                    }

                    if (imgWidth >= widthSpace) {
                        xPos = 10;
                    }
                    else {
                        xPos = (getWidth() - imgWidth) / 2;
                    }
                    g.drawImage(promptImg, xPos, 10, imgWidth, imgHeight, null);
                    g.drawString(prompt, (getWidth() - prompt.length() * 20)/ 2, heightSpace + 40);
                }
                else {
                    g.drawString(prompt, (getWidth() - prompt.length() * 20) / 2, getHeight() / 2);
                }
                
            }
        };
        promptCard.addActionListener(this);
        promptCard.setFont(new Font("Comic Sans", Font.PLAIN, 40));
        // promptCard.setPreferredSize(new Dimension(600, 400));
        cardPanel.setLayout(new BorderLayout(50, 50));
        cardPanel.add(promptCard, BorderLayout.CENTER);
        // cardPanel.add(Box.createRigidArea(new Dimension(50, 50)), BorderLayout.SOUTH);
        // cardPanel.add(Box.createRigidArea(new Dimension(50, 50)), BorderLayout.EAST);
        // cardPanel.add(Box.createRigidArea(new Dimension(50, 50)), BorderLayout.WEST);
        cardPanel.setPreferredSize(new Dimension(800, 500));
        add(cardPanel);
        setVisible(true);
    }

    // Flip FC
    public void flip() {
        cardPanel.setVisible(false);
        cardPanel = new JPanel();
        // cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.PAGE_AXIS));
        // cardPanel.setLayout(new GridBagLayout());
        cardPanel.setLayout(new BorderLayout());

        // JLabel responseCard = new JLabel(response);
        JLabel responseCard = new JLabel(response){
            @Override
            protected void paintComponent(Graphics g) {
                // super.paintComponent(g);
                // System.out.println(getWidth() + ", " + getHeight());
                if (respImg != null) {
                    // System.out.println("nuu");
                    int xPos;
                    int imgWidth = respImg.getWidth(null);
                    int imgHeight = respImg.getHeight(null);
                    int widthSpace = getWidth() - 20;
                    int heightSpace = getHeight() - 50;
                    if (imgWidth > widthSpace) {
                        respImg = respImg.getScaledInstance(widthSpace, imgHeight, BufferedImage.SCALE_SMOOTH);
                        double scale = (double) widthSpace / imgWidth;
                        imgWidth = widthSpace;
                        imgHeight = (int) (imgHeight * scale);
                        respImg = respImg.getScaledInstance(widthSpace, imgHeight, BufferedImage.SCALE_SMOOTH);
                    }
                    if (imgHeight > heightSpace) {
                        respImg = respImg.getScaledInstance(imgWidth, heightSpace, BufferedImage.SCALE_SMOOTH);
                        double scale = (double) heightSpace / imgHeight;
                        imgHeight = heightSpace;
                        imgWidth = (int) (imgWidth * scale);
                        respImg = respImg.getScaledInstance(imgWidth, heightSpace, BufferedImage.SCALE_SMOOTH);
                    }

                    if (imgWidth >= widthSpace) {
                        xPos = 10;
                    }
                    else {
                        xPos = (getWidth() - imgWidth) / 2;
                    }
                    g.drawImage(respImg, xPos, 10, imgWidth, imgHeight, null);
                    g.drawString(response, (getWidth() - response.length() * 20)/ 2, heightSpace + 40);
                }
                else {
                    // System.out.println("wee");
                    g.drawString(response, (getWidth() - response.length() * 20) / 2, getHeight() / 2);
                }
            }
        };
        responseCard.setFont(new Font("Comic Sans", Font.PLAIN, 40));
        responseCard.setPreferredSize(new Dimension((int) (0.5 * cardPanel.getWidth()), (int) (0.5 * cardPanel.getHeight())));
        // responseCard.setEnabled(false);

        JButton seePrompt = new JButton(seePromptAgain);
        seePrompt.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        seePrompt.addActionListener(this);

        JLabel question = new JLabel("Got it right?");
        question.setFont(new Font("Comic Sans", Font.PLAIN, 20));

        JButton yes = new JButton("Yes");
        yes.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        yes.addActionListener(this);

        JButton no = new JButton("No");
        no.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        no.addActionListener(this);

        JPanel respPanel = new JPanel();
        respPanel.add(yes);
        respPanel.add(no);
        cardPanel.setPreferredSize(new Dimension(800, 600));
        cardPanel.setMinimumSize(new Dimension(800, 600));

        // BorderLayout code
        cardPanel.add(responseCard, BorderLayout.CENTER);
        JPanel options = new JPanel();
        options.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        constrs.fill = GridBagConstraints.BOTH;
        options.add(seePrompt, constrs);
        seePrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        constrs.gridy = 1;
        options.add(question, constrs);
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        constrs.gridy = 2;
        options.add(respPanel, constrs);
        cardPanel.add(options, BorderLayout.SOUTH);


        // // GridBagLayout code
        // GridBagConstraints constrs = new GridBagConstraints();
        // constrs.gridx = 0;
        // constrs.gridy = 0;
        // constrs.fill = GridBagConstraints.BOTH;
        // cardPanel.add(responseCard, constrs);
        // responseCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        // constrs.gridy = 1;
        // cardPanel.add(seePrompt, constrs);
        // seePrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        // constrs.gridy = 2;
        // cardPanel.add(question, constrs);
        // question.setAlignmentX(Component.CENTER_ALIGNMENT);
        // constrs.gridy = 3;
        // cardPanel.add(respPanel, constrs);

        // // // BoxLayout code
        // // cardPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        // cardPanel.add(responseCard);
        // responseCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        // // cardPanel.add(Box.createRigidArea(new Dimension(0, 120)));
        // cardPanel.add(seePrompt);
        // seePrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        // // cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        // cardPanel.add(question);
        // question.setAlignmentX(Component.CENTER_ALIGNMENT);
        // cardPanel.add(respPanel);

        add(cardPanel);
        // cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setVisible(true);
    }


    // Moves the flipped FC from studyStack to correctStack or missedStack
    public void nextCard(boolean correct) {
        FCStack toAddStack = correct ? correctStack : missedStack;
        toAddStack.getStack().add(studyStack.getStack().get(studyInd));
        studyInd++;
        updateProgress();
        if (studyInd < studyStack.getStack().size()) {
            updateCard();
            displayPrompt();
        }
        else root.finish(missedStack);
    }

    /* Updates the displayed text according to the prompt
       and response of the first FC in studyStack. */
    public void updateCard() {
        Flashcard curFC = studyStack.getStack().get(studyInd);
        // System.out.println(curFC);
        prompt = curFC.getPrompt();
        response = curFC.getResponse();
        try {
            if (curFC.getPromptImg() != "") {
                promptImg = ImageIO.read(new File(curFC.getPromptImg()));
            }
            if (curFC.getRespImg() != "") {
                // System.out.println("respond");
                respImg = ImageIO.read(new File(curFC.getRespImg()));
                // System.out.println(respImg);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        
    }

    // Update progressTracker JLabel
    public void updateProgress() {
        int finishedSize = correctStack.getStack().size() + missedStack.getStack().size();
        progressTracker.setText(finishedSize + "/" + studySize);
    }

    public int getStudySize() {
        return studySize;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals(prompt)) {
            flip();
        }
        else if (cmd.equals(seePromptAgain)) {
            displayPrompt();
        }
        else nextCard(cmd.equals("Yes"));
    }
}