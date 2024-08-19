import javax.swing.SpringLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.io.File;
import java.util.Scanner;

public class NewManage extends Page implements ActionListener, NavigatorListener, CheckboxListener{
    private SpringLayout masterSpringL;
    private FCPBackground masterPanel;

    private FCPScrollPane genDisplay;
    private FCPScrollPane studyDisplay;
    private FCPPanel actionButtonSlate;
    private Navigator genDisplayNav;
    private String currentPath;

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
    private static ArrayList<Flashcard> masterFCs;
    private ArrayList<Sleeve> masterSleeves;
    private ArrayList<FCButton> generalFCButtons;
    private ArrayList<SleeveButton> generalSleeveButtons;
    private ArrayList<FCButton> studyFCButtons;

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
        masterSleeves = new ArrayList<Sleeve>();
        generalFCButtons = new ArrayList<FCButton>();
        generalSleeveButtons = new ArrayList<SleeveButton>();
        studyFCButtons = new ArrayList<FCButton>();
        genMasterStack();
        genSleeves();
        currentPath = "";
        buttonTitles = new String[]{"Delete", "Move", "Edit", "Add to study stack", "Remove from study stack", "Clear study stack", "New Sleeve", "New Flashcard", "Start studying"};


        // getMasterPanel().setVisible(false);
        masterPanel = new FCPBackground();
        // masterPanel.darken();
        // masterPanel.setVisible(false);
        masterSpringL = new SpringLayout();
        masterPanel.setLayout(masterSpringL);

        genDisplayNav = new Navigator("", this);
        masterPanel.add(genDisplayNav);
        masterSpringL.putConstraint(SpringLayout.NORTH, genDisplayNav, 30, SpringLayout.NORTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, genDisplayNav, 30, SpringLayout.WEST, masterPanel);

        genActionDisplay();
        genGeneralDisplay();
        genStudyDisplay();

        FCPButton home = new FCPButton("Home", FCPButton.SMALL, Color.WHITE, GUI.offsetBrightness(GUI.secondary(), -.9), GUI.offsetBrightness(GUI.secondary(), -.5));
        home.setHoverEffect(FCPButton.BRIGHTEN);
        home.addActionListener(new ToMainMenu());
        masterPanel.add(home);
        masterSpringL.putConstraint(SpringLayout.SOUTH, home, -50, SpringLayout.SOUTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, home, 50, SpringLayout.WEST, masterPanel);


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

    public static void genMasterStack() {
        try {
            masterFCs.clear();
            File flashcards = new File("flashcards.txt");
            Scanner scanner = new Scanner(flashcards);
            while (scanner.hasNextLine()) {
                String sleevePath = scanner.nextLine();
                String prompt = scanner.nextLine();
                String response = scanner.nextLine();
                Flashcard newFC = new Flashcard(sleevePath, prompt, response);
                masterFCs.add(newFC);

                String promptImgPath = scanner.nextLine();
                if (promptImgPath != "\n") {
                    newFC.setPromptImg(promptImgPath);
                }
                String respImgPath = scanner.nextLine();
                if (respImgPath != "\n") {
                    newFC.setRespImg(respImgPath);
                }
            }
            scanner.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void genSleeves() {
        try {
            masterSleeves.clear();
            Scanner sleeveInfo = new Scanner(new File("sleeves.txt"));
            while (sleeveInfo.hasNextLine()) {
                String sleevePath = sleeveInfo.nextLine();
                String sleeveName = sleeveInfo.nextLine();
                masterSleeves.add(new Sleeve(sleevePath, sleeveName));
                // System.out.println(new Sleeve(sleevePath, sleeveName));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private void genGeneralDisplay() {
        FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.5));
        genDisplay = new FCPScrollPane();
        genDisplay.setFill(GUI.offsetBrightness(GUI.secondary(), -.8));
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        genDisplay.setPreferredSize(new Dimension((int) screenDims.getWidth() / 3, (int) screenDims.getHeight()));
        refreshGenDisplay();
        tray.add(genDisplay);
        masterPanel.add(tray);
        masterSpringL.putConstraint(SpringLayout.NORTH, tray, -5, SpringLayout.NORTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, tray, 380, SpringLayout.WEST, masterPanel);
    }

    public void refreshGenDisplay() {
        genDisplay.removeAll();
        generalFCButtons.clear();
        generalSleeveButtons.clear();
        for (Sleeve sleeve : sleevesWithPath(currentPath)) {
            FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.8));
            // tray = new FCPPanel(Color.RED);
            tray.unrounden();
            SpringLayout trayLayout = new SpringLayout();
            tray.setLayout(trayLayout);
            SleeveButton sButton = new SleeveButton(sleeve);
            sButton.setListener(genDisplayNav);
            generalSleeveButtons.add(sButton);
            FCPCheckbox checkbox = new FCPCheckbox();
            checkbox.addCheckboxListener(sButton);
            checkbox.addCheckboxListener(this);
            tray.add(checkbox);
            trayLayout.putConstraint(SpringLayout.WEST, checkbox, 5, SpringLayout.WEST, tray);
            trayLayout.putConstraint(SpringLayout.NORTH, checkbox, (sButton.getButtonHeight() - checkbox.getButtonHeight()) / 2, SpringLayout.NORTH, sButton);
            tray.add(sButton);
            trayLayout.putConstraint(SpringLayout.WEST, sButton, 5, SpringLayout.EAST, checkbox);
            tray.setPreferredSize(new Dimension(15 + checkbox.getButtonWidth() + sButton.getButtonWidth(), sButton.getButtonHeight()));
            genDisplay.add(tray);
        }
        for (Flashcard fc : fcsWithPath(currentPath)) {
            FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.8));
            // tray = new FCPPanel(Color.RED);
            tray.unrounden();
            SpringLayout trayLayout = new SpringLayout();
            tray.setLayout(trayLayout);
            FCButton fcButton = new FCButton(fc);
            generalFCButtons.add(fcButton);
            FCPCheckbox checkbox = new FCPCheckbox();
            checkbox.addCheckboxListener(this);
            checkbox.addCheckboxListener(fcButton);
            tray.add(checkbox);
            trayLayout.putConstraint(SpringLayout.WEST, checkbox, 5, SpringLayout.WEST, tray);
            trayLayout.putConstraint(SpringLayout.NORTH, checkbox, (fcButton.getButtonHeight() - checkbox.getButtonHeight()) / 2, SpringLayout.NORTH, fcButton);
            tray.add(fcButton);
            trayLayout.putConstraint(SpringLayout.WEST, fcButton, 5, SpringLayout.EAST, checkbox);
            tray.setPreferredSize(new Dimension(15 + checkbox.getButtonWidth() + fcButton.getButtonWidth(), fcButton.getButtonHeight()));
            genDisplay.add(tray);
        }
        genDisplay.revalidate();
        genDisplay.repaint();
    }

    private void genStudyDisplay() {
        FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.5));
        studyDisplay = new FCPScrollPane();
        studyDisplay.setFill(GUI.offsetBrightness(GUI.secondary(), -.8));
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        studyDisplay.setPreferredSize(new Dimension((int) screenDims.getWidth() / 3, (int) screenDims.getHeight()));
        tray.add(studyDisplay);
        masterPanel.add(tray);
        masterSpringL.putConstraint(SpringLayout.NORTH, tray, -5, SpringLayout.NORTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, tray, (int)screenDims.getWidth() / -3 - 10, SpringLayout.EAST, masterPanel);
    }

    public void refreshStudyDisplay() {
        studyDisplay.removeAll();
        studyFCButtons.clear();
        for (Flashcard fc : studyFCs) {
            studyDisplay.add(new FCButton(fc));
        }
        studyDisplay.revalidate();
        studyDisplay.repaint();
    }

    public ArrayList<Flashcard> fcsWithPath(String path) {
        ArrayList<Flashcard> fcs = new ArrayList<Flashcard>();
        for (Flashcard fc : masterFCs) {
            if (fc.getPath().equals(path)) {
                fcs.add(fc);
            }
        }
        return fcs;
    }

    public ArrayList<Sleeve> sleevesWithPath(String path) {
        ArrayList<Sleeve> sleeves = new ArrayList<Sleeve>();
        for (Sleeve sleeve : masterSleeves) {
            if (sleeve.getPath().equals(path)) {
                sleeves.add(sleeve);
            }
        }
        return sleeves;
    }

    private ArrayList<Flashcard> fcsUnderSleeve(Sleeve sleeve) {
        ArrayList<Flashcard> fcs = new ArrayList<Flashcard>();
        String path = sleeve.getPath() + sleeve.getName() + "/";
        for (Flashcard fc : fcsWithPath(path)) {
            if (fcs.indexOf(fc) == -1) {
                fcs.add(fc);
            }
        }
        for (Sleeve innerSleeve : sleevesWithPath(path)) {
            fcs.addAll(fcsUnderSleeve(innerSleeve));
        }
        return fcs;
    }
/*
    private void addSleeveToStudy(Sleeve sleeve) {
        // String path = sleeve.getPath() + sleeve.getName() + "/";
        // for (Flashcard fc : fcsWithPath(path)) {
        //     if (studyFCs.indexOf(fc) == -1) {
        //         studyFCs.add(fc);
        //     }
        // }
        // for (Sleeve innerSleeve : sleevesWithPath(path)) {
        //     addSleeveToStudy(innerSleeve);
        // }
        for (Flashcard fc : fcsUnderSleeve(sleeve)) {
            if (studyFCs.indexOf(fc) == -1) {
                studyFCs.add(fc);
            }
        }
    }
*/

    private void setupActionButton(FCPButton button) {
        button.addActionListener(this);
        button.setHoverEffect(FCPButton.BRIGHTEN);
        button.setSquishBordered(false);
    }

    private void genActionDisplay() {
        Color fill = GUI.offsetBrightness(GUI.secondary(), -.5);
        FCPPanel back = new FCPPanel(fill);
        fill = GUI.offsetBrightness(GUI.secondary(), -.9);
        actionButtonSlate = new FCPPanel(fill);
        actionButtonSlate.setOpaque(false);
        // FCPPanel actionButtonSlate = new FCPPanel(GUI.secondary());
        actionButtonSlate.setPreferredSize(new Dimension(300, 510));
        SpringLayout springL = new SpringLayout();
        actionButtonSlate.setLayout(springL);

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
        actionButtonSlate.add(delete);
        springL.putConstraint(SpringLayout.NORTH, delete, inset, SpringLayout.NORTH, actionButtonSlate);
        springL.putConstraint(SpringLayout.WEST, delete, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(move);
        springL.putConstraint(SpringLayout.NORTH, move, smallInset, SpringLayout.SOUTH, delete);
        springL.putConstraint(SpringLayout.WEST, move, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(edit);
        springL.putConstraint(SpringLayout.NORTH, edit, smallInset, SpringLayout.SOUTH, move);
        springL.putConstraint(SpringLayout.WEST, edit, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(addStudy);
        // addStudy.setTransparent(false);
        springL.putConstraint(SpringLayout.NORTH, addStudy, smallInset, SpringLayout.SOUTH, edit);
        springL.putConstraint(SpringLayout.WEST, addStudy, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(removeStudy);
        springL.putConstraint(SpringLayout.NORTH, removeStudy, smallInset, SpringLayout.SOUTH, addStudy);
        springL.putConstraint(SpringLayout.WEST, removeStudy, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(clearStudy);
        springL.putConstraint(SpringLayout.NORTH, clearStudy, smallInset, SpringLayout.SOUTH, removeStudy);
        springL.putConstraint(SpringLayout.WEST, clearStudy, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(addSleeve);
        springL.putConstraint(SpringLayout.NORTH, addSleeve, smallInset, SpringLayout.SOUTH, clearStudy);
        springL.putConstraint(SpringLayout.WEST, addSleeve, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(addFC);
        springL.putConstraint(SpringLayout.NORTH, addFC, smallInset, SpringLayout.SOUTH, addSleeve);
        springL.putConstraint(SpringLayout.WEST, addFC, inset + 85, SpringLayout.WEST, actionButtonSlate);
        actionButtonSlate.add(study);
        springL.putConstraint(SpringLayout.NORTH, study, smallInset, SpringLayout.SOUTH, addFC);
        springL.putConstraint(SpringLayout.WEST, study, inset + 85, SpringLayout.WEST, actionButtonSlate);

        back.add(actionButtonSlate);
        masterPanel.add(back);
        masterSpringL.putConstraint(SpringLayout.NORTH, back, 150, SpringLayout.NORTH, masterPanel);
        masterSpringL.putConstraint(SpringLayout.WEST, back, -75, SpringLayout.WEST, masterPanel);
        refreshActionDisplay();
    }

    private void refreshActionDisplay() {
        int numSelectedFCs = 0;
        int numSelectedSleeves = 0;
        for (FCButton fcb : generalFCButtons) {
            if (fcb.isSelected()) {
                numSelectedFCs += 1;
            }
        }
        for (SleeveButton sb : generalSleeveButtons) {
            if (sb.isSelected()) {
                numSelectedSleeves += 1;
            }
        }
        boolean isSelSizeNonzero = numSelectedFCs != 0 || numSelectedSleeves != 0;
        boolean selHasNonStudyFCs = false;
        boolean isStudySizeNonzero = studyFCs.size() > 0;
        for (Flashcard candFC : getSelectedFCs()) {
            if (studyFCs.indexOf(candFC) == -1) {
                selHasNonStudyFCs = true;
                break;
            }
        }
        // System.out.println("0: " + isSizeNonzero);
        delete.setEnabled(isSelSizeNonzero);
        move.setEnabled(isSelSizeNonzero);
        addStudy.setEnabled(isSelSizeNonzero && selHasNonStudyFCs);            
        removeStudy.setEnabled(isSelSizeNonzero);
        clearStudy.setEnabled(isStudySizeNonzero);
        // System.out.println(numSelectedFCs + numSelectedSleeves);
        boolean isOnlyOneSel = numSelectedFCs + numSelectedSleeves == 1;
        // System.out.println("1: " + isOnlyOneSel);
        edit.setEnabled(isOnlyOneSel);
        actionButtonSlate.revalidate();
        actionButtonSlate.repaint();
    }

    private ArrayList<Flashcard> getSelectedFCs() {
        ArrayList<Flashcard> selected = new ArrayList<Flashcard>();
        for (FCButton fcb : generalFCButtons) {
            if (fcb.isSelected()) {
                selected.add(fcb.getFC());
            }
        }
        for (Sleeve sleeve : getSelectedSleeves()) {
            selected.addAll(fcsUnderSleeve(sleeve));
        }
        return selected;
    }

    private ArrayList<Sleeve> getSelectedSleeves() {
        ArrayList<Sleeve> selected = new ArrayList<Sleeve>();
        for (SleeveButton sb : generalSleeveButtons) {
            if (sb.isSelected()) {
                selected.add(sb.getSleeve());
            }
        }
        return selected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        System.out.println(cmd);
        if (cmd.equals(buttonTitles[MOVE])) {

        }
        else if (cmd.equals(buttonTitles[EDIT])) {
            
        }
        else if (cmd.equals(buttonTitles[DELETE])) {
            
        }
        else if (cmd.equals(buttonTitles[ADDSTUDY])) {
            for (Flashcard fc : getSelectedFCs()) {
                if (studyFCs.indexOf(fc) == -1) {
                    studyFCs.add(fc);
                }
            }
            // for (Sleeve sleeve : getSelectedSleeves()) {
            //     addSleeveToStudy(sleeve);
            // }
            refreshStudyDisplay();
            refreshActionDisplay();
        }
        else if (cmd.equals(buttonTitles[REMOVESTUDY])) {
            for (Flashcard fc : getSelectedFCs()) {
                studyFCs.remove(fc);
            }
            refreshStudyDisplay();
            refreshActionDisplay();
        }
        else if (cmd.equals(buttonTitles[CLEARSTUDY])) {
            studyFCs.clear();
            refreshStudyDisplay();
            refreshActionDisplay();
        }
        else if (cmd.equals(buttonTitles[ADDFC])) {
            
        }
        else if (cmd.equals(buttonTitles[ADDSLEEVE])) {
            
        }
        else if (cmd.equals(buttonTitles[STUDY])) {
            
        }
        

    }

    @Override
    public void pathUpdateReaction(String newPath) {
        currentPath = newPath;
        refreshGenDisplay();
    }

    @Override
    public void toggleReaction(boolean newValue) {
        System.out.println("toggled");
        refreshActionDisplay();
    }

    public static void main(String[] args) {
        GUI.init();
        new NewManage();
    }
}