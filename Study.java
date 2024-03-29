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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.Box;

public class Study extends Page implements ActionListener{

    private FCStack studyStack;
    private FCStack missedStack;
    private Flipper flipper;
    private JPanel masterPanel;
    private int studySize;

    public Study (FCStack newStudyStack) {
        studyStack = newStudyStack;
        // studyInd = 0;
        missedStack = null;
        // studySize = studyStack.getStack().size();
        if (studyStack.getStack().size() == 0) {
            // to add error msgs later
            new MainMenu();
        }
        else {
            setPage(this);
            setup(studyStack);
        }
    }

    private void setup(FCStack stack) {
        getMasterPanel().setVisible(false);
        masterPanel = new JPanel();
        masterPanel.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        flipper = new Flipper(stack, this);
        masterPanel.add(flipper, constrs);
        constrs.gridy = 1;
        masterPanel.add(Box.createRigidArea(new Dimension(50, 20)), constrs);
        constrs.gridy = 2;
        masterPanel.add(new HomeButton(), constrs);
        constrs.gridy = 3;
        masterPanel.add(Box.createRigidArea(new Dimension(50, 20)), constrs);
        setMasterPanel(masterPanel);
    }

    public void finish(FCStack missedStack) {
        studySize = flipper.getStudySize();
        int numCorrect = studySize - missedStack.getStack().size();
        this.missedStack = missedStack;
        displayResults(numCorrect);
    }

    // Display results of study session
    public void displayResults(int correct) {
        JLabel resultsCard = new JLabel();
        String results = "You got " + correct + 
                        " out of " + studySize +
                        " flashcards!";
        resultsCard.setText(results);
        resultsCard.setFont(new Font("Comic Sans", Font.PLAIN, 40));
        Font buttonFont = new Font("Comic Sans", Font.PLAIN, 20);
        // JButton home = new JButton("Home");
        // home.setFont(buttonFont);
        // home.addActionListener(new ToMainMenu());
        JButton retryMissed = new JButton("Retry the ones you didn't get");
        retryMissed.setFont(buttonFont);
        retryMissed.addActionListener(this);
        JButton manage = new JButton("Manage Flashcards");
        manage.setFont(buttonFont);
        // manage.addActionListener(this);
        manage.addActionListener(new ToManage(studyStack));

        JPanel cardPanel = new JPanel();
        // progressPanel.setVisible(false);
        // cardPanel.setVisible(false);
        // cardPanel = new JPanel();
        setMasterPanel(cardPanel);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.PAGE_AXIS));
        cardPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        cardPanel.add(resultsCard);
        resultsCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        HomeButton hmb = new HomeButton();
        hmb.setFont(buttonFont);
        cardPanel.add(hmb);
        hmb.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(retryMissed);
        retryMissed.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(manage);
        manage.setAlignmentX(Component.CENTER_ALIGNMENT);
        // getMasterPanel().add(cardPanel);
        cardPanel.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        // if (cmd.equals(prompt)) {
        //     flip();
        // }
        if (cmd.equals("Retry the ones you didn't get")) {
            if (missedStack.getStack().size() == 0) {
                new MainMenu();
            }
            else {
                // flipper.setVisible(false);
                // flipper = new Flipper(missedStack, this);
                // setMasterPanel(flipper);
                // JFrame frame = Page.getFrame();
                // frame.revalidate();
                // frame.repaint();
                setup(missedStack);
            }
        }
        // else if (cmd.equals("Manage Flashcards")) {
        //     if (studyStack.isJeopardized()) studyStack.jeopardize();
        // }
        // else nextCard(cmd.equals("Yes"));
    }

    

    // // Moves the flipped FC from studyStack to correctStack or missedStack
    // public void nextCard(boolean correct) {
    //     FCStack toAddStack = correct ? correctStack : missedStack;
    //     toAddStack.getStack().add(studyStack.getStack().get(studyInd));
    //     studyInd++;
    //     updateProgress();
    //     if (studyInd < studyStack.getStack().size()) {
    //         updateCard();
    //         displayPrompt();
    //     }
    //     else displayResults();
    // }

    // /* Updates the displayed text according to the prompt
    //    and response of the first FC in studyStack. */
    // public void updateCard() {
    //     Flashcard curFC = studyStack.getStack().get(studyInd);
    //     prompt = curFC.getPrompt();
    //     response = curFC.getResponse();
    // }

    // // Update progressTracker JLabel
    // public void updateProgress() {
    //     int finishedSize = correctStack.getStack().size() + missedStack.getStack().size();
    //     progressTracker.setText(finishedSize + "/" + studySize);
    // }

}