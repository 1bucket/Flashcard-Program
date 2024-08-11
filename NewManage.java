import javax.swing.SpringLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Toolkit;

public class NewManage extends Page implements ActionListener{
    private SpringLayout masterSpringL;
    private FCPBackground masterPanel;

    private FCPButton delete;
    private FCPButton move;
    private FCPButton edit;
    private FCPButton addStudy;
    private FCPButton removeStudy;
    private FCPButton clearStudy;
    private FCPButton study;

    private final int DELETE = 0;
    private final int MOVE = 1;
    private final int EDIT = 2;
    private final int ADDSTUDY = 3;
    private final int REMOVESTUDY = 4;
    private final int CLEARSTUDY = 5;
    private final int ADDSLEEVE = 6;
    private final int ADDFC = 7;
    private final int STUDY = 8;
    private String[] buttonTitles;
    
    private ArrayList<Flashcard> studyFCs;
    private ArrayList<Flashcard> masterFCs;
    private ArrayList<Sleeve> allSleeves;

    public NewManage() {
        this(null);
    }

    public NewManage(FCStack stack) {
        if (stack == null) {
            studyFCs = new ArrayList<Flashcard>();
        }
        else {
            studyFCs = stack.getStack();
        }
        masterFCs = new ArrayList<Flashcard>();
        allSleeves = new ArrayList<Sleeve>();
        buttonTitles = new String[]{"Delete", "Move", "Edit", "Add to study stack", "Remove from study stack", "Clear study stack", "New Sleeve", "New Flashcard", "Start studying"};


        // getMasterPanel().setVisible(false);
        masterPanel = new FCPBackground();
        masterPanel.darken();
        // masterPanel.setVisible(false);
        masterSpringL = new SpringLayout();
        masterPanel.setLayout(masterSpringL);

        genActionDisplay();
        genGeneralDisplay();
        genStudyDisplay();


        // getFrame().setTitle("Manage Flashcards");
        // setMasterPanel(masterPanel);
        JFrame testFrame = new JFrame("Manage Flashcards");
        testFrame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        testFrame.setContentPane(masterPanel);
        testFrame.pack();
        testFrame.setLocationRelativeTo(null);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setVisible(true);
        // masterPanel.setVisible(true);
    }

    private void genGeneralDisplay() {
        FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.5));
        FCPScrollPane genDisplay = new FCPScrollPane();
        genDisplay.setFill(GUI.offsetBrightness(GUI.secondary(), -.8));
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        genDisplay.setPreferredSize(new Dimension((int) screenDims.getWidth() / 3, (int) screenDims.getHeight()));
        tray.add(genDisplay);
        masterPanel.add(tray);
        masterSpringL.putConstraint(SpringLayout.NORTH, tray, -5, SpringLayout.NORTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, tray, 380, SpringLayout.WEST, masterPanel);
        
    }

    private void genStudyDisplay() {
        FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.5));
        FCPScrollPane studyDisplay = new FCPScrollPane();
        studyDisplay.setFill(GUI.offsetBrightness(GUI.secondary(), -.8));
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        studyDisplay.setPreferredSize(new Dimension((int) screenDims.getWidth() / 3, (int) screenDims.getHeight()));
        tray.add(studyDisplay);
        masterPanel.add(tray);
        masterSpringL.putConstraint(SpringLayout.NORTH, tray, -5, SpringLayout.NORTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, tray, (int)screenDims.getWidth() / -3 - 10, SpringLayout.EAST, masterPanel);
    }

    private void setupActionButton(FCPButton button) {
        button.addActionListener(this);
        button.setHoverEffect(FCPButton.BRIGHTEN);
        button.setSquishBordered(false);
    }

    private void genActionDisplay() {
        Color fill = GUI.offsetBrightness(GUI.secondary(), -.5);
        FCPPanel back = new FCPPanel(fill);
        fill = GUI.offsetBrightness(GUI.secondary(), -.9);
        FCPPanel buttonSlate = new FCPPanel(fill);
        // FCPPanel buttonSlate = new FCPPanel(GUI.secondary());
        buttonSlate.setPreferredSize(new Dimension(300, 510));
        SpringLayout springL = new SpringLayout();
        buttonSlate.setLayout(springL);

        delete = new FCPButton(buttonTitles[DELETE], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(delete);
        move = new FCPButton(buttonTitles[MOVE], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(move);
        edit = new FCPButton(buttonTitles[EDIT], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(edit);
        addStudy = new FCPButton(buttonTitles[ADDSTUDY], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(addStudy);
        removeStudy = new FCPButton(buttonTitles[REMOVESTUDY], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(removeStudy);
        clearStudy = new FCPButton(buttonTitles[CLEARSTUDY], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(clearStudy);
        FCPButton addSleeve = new FCPButton(buttonTitles[ADDSLEEVE], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(addSleeve);
        FCPButton addFC = new FCPButton(buttonTitles[ADDFC], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(addFC);
        study = new FCPButton(buttonTitles[STUDY], FCPButton.SMALL, Color.WHITE, fill, Color.RED);
        setupActionButton(study);

        int inset = 20;
        int smallInset = inset - 15;
        buttonSlate.add(delete);
        springL.putConstraint(SpringLayout.NORTH, delete, inset, SpringLayout.NORTH, buttonSlate);
        springL.putConstraint(SpringLayout.WEST, delete, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(move);
        springL.putConstraint(SpringLayout.NORTH, move, smallInset, SpringLayout.SOUTH, delete);
        springL.putConstraint(SpringLayout.WEST, move, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(edit);
        springL.putConstraint(SpringLayout.NORTH, edit, smallInset, SpringLayout.SOUTH, move);
        springL.putConstraint(SpringLayout.WEST, edit, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(addStudy);
        // addStudy.setTransparent(false);
        springL.putConstraint(SpringLayout.NORTH, addStudy, smallInset, SpringLayout.SOUTH, edit);
        springL.putConstraint(SpringLayout.WEST, addStudy, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(removeStudy);
        springL.putConstraint(SpringLayout.NORTH, removeStudy, smallInset, SpringLayout.SOUTH, addStudy);
        springL.putConstraint(SpringLayout.WEST, removeStudy, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(clearStudy);
        springL.putConstraint(SpringLayout.NORTH, clearStudy, smallInset, SpringLayout.SOUTH, removeStudy);
        springL.putConstraint(SpringLayout.WEST, clearStudy, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(addSleeve);
        springL.putConstraint(SpringLayout.NORTH, addSleeve, smallInset, SpringLayout.SOUTH, clearStudy);
        springL.putConstraint(SpringLayout.WEST, addSleeve, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(addFC);
        springL.putConstraint(SpringLayout.NORTH, addFC, smallInset, SpringLayout.SOUTH, addSleeve);
        springL.putConstraint(SpringLayout.WEST, addFC, inset + 85, SpringLayout.WEST, buttonSlate);
        buttonSlate.add(study);
        springL.putConstraint(SpringLayout.NORTH, study, smallInset, SpringLayout.SOUTH, addFC);
        springL.putConstraint(SpringLayout.WEST, study, inset + 85, SpringLayout.WEST, buttonSlate);

        back.add(buttonSlate);
        masterPanel.add(back);
        masterSpringL.putConstraint(SpringLayout.NORTH, back, 150, SpringLayout.NORTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, back, -75, SpringLayout.WEST, masterPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    public static void main(String[] args) {
        GUI.init();
        new NewManage();
    }
}