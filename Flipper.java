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
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Font;

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
    private int studySize;

    public Flipper(FCStack newStack, Study newRoot) {
        super();
        // isJeopardyMode = jeopardyMode;
        studyStack = newStack;
        root = newRoot;
        correctStack = new FCStack();
        missedStack = new FCStack();
        studySize = newStack.getStack().size();

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
        JButton promptCard = new JButton(prompt);
        promptCard.addActionListener(this);
        promptCard.setFont(new Font("Comic Sans", Font.PLAIN, 40));
        cardPanel.setLayout(new BorderLayout(50, 50));
        cardPanel.add(promptCard, BorderLayout.CENTER);
        // cardPanel.add(Box.createRigidArea(new Dimension(50, 50)), BorderLayout.SOUTH);
        // cardPanel.add(Box.createRigidArea(new Dimension(50, 50)), BorderLayout.EAST);
        // cardPanel.add(Box.createRigidArea(new Dimension(50, 50)), BorderLayout.WEST);
        cardPanel.setPreferredSize(new Dimension(800, 400));
        add(cardPanel);
        setVisible(true);
    }

    // Flip FC
    public void flip() {
        cardPanel.setVisible(false);
        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.PAGE_AXIS));

        JLabel responseCard = new JLabel(response);
        responseCard.setFont(new Font("Comic Sans", Font.PLAIN, 40));

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
        cardPanel.setPreferredSize(new Dimension(800, 400));
        cardPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        cardPanel.add(responseCard);
        responseCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        cardPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        cardPanel.add(question);
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(respPanel);
        add(cardPanel);
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        // else if (cmd.equals(("Retry the ones you didn't get"))) {
        //     new Study(missedStack);
        // }
        else nextCard(cmd.equals("Yes"));
    }
}