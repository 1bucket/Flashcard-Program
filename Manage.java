import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.lang.Exception;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Manage extends Page implements ActionListener{
    // Deletion modes
    private final int HARD_DELETE = 0;
    private final int SOFT_DELETE = 1;
    private final int NO_SLEEVE = 2;


    private Sleeve masterSleeve;
    private static ArrayList<Sleeve> allSleeves;
    private FCStack masterStack;
    private FCStack studyStack;
    // ***
    public ArrayList<Flashcard> selectedFCs;
    public ArrayList<Sleeve> selectedSleeves;
    // ***
    private String currentPath;
    // private String prevPath;
    // private Stack<String> nextPaths;
    private ArrayList<Flashcard> openCards;
    private ArrayList<Flashcard> openStudyCards;

    private Mover mover;

    private JPanel masterPanel;
    // private JPanel dispPanel;
    private Navigator navDisp;
    private JScrollPane deckPane;
    private JScrollPane studyPane;
    private ArrayList<HolderPane> studyHolderPanes;
    private ArrayList<HolderPane> deckFCPanes;
    private ArrayList<HolderPane> deckSleevePanes;

    // private JLabel pathLabel;

    // Buttons
    private JButton delete;
    private JButton move;
    private JButton edit;
    private JButton addToStudy;
    private JButton removeFromStudy;
    private JButton clearStudy;
    private JButton prevSleeve;
    private JButton nextSleeve;

    

    private final int STUDY = 0;
    private final int FULLDECK = 1;

    public Manage() {
        this(new FCStack());
    }

    public Manage(FCStack curStack) {
        masterSleeve = null;
        allSleeves = null;
        mover = null;
        selectedFCs = new ArrayList<Flashcard>();
        openCards = new ArrayList<Flashcard>();
        openStudyCards = new ArrayList<Flashcard>();
        selectedSleeves = new ArrayList<Sleeve>();
        // studyStack = curStack;
        genSleeves();
        masterStack = genMasterStack();
        studyStack = new FCStack();
        ArrayList<Flashcard> studyFCs = studyStack.getStack();
        ArrayList<Flashcard> masterDeck = masterStack.getStack();
        ArrayList<Flashcard> newDeck = curStack.getStack();
        while (newDeck.size() > 0) {
            int masterInd = 0;
            Flashcard targetFC = newDeck.remove(0);
            for (int index = masterInd; masterInd < masterDeck.size(); index++) {
                if (targetFC.equals(masterDeck.get(index))) {
                    masterInd = index;
                    break;
                }
            }
            Flashcard twin = masterDeck.get(masterInd);
            studyFCs.add(twin);
        }
        currentPath = "";
        studyHolderPanes = new ArrayList<HolderPane>();
        deckFCPanes = new ArrayList<HolderPane>();
        deckSleevePanes = new ArrayList<HolderPane>();
        // prevPath = null;
        // nextPaths = new Stack<String>();

        getMasterPanel().setVisible(false);

        // GUI

        GridBagConstraints constrs = new GridBagConstraints();
        
        masterPanel = new JPanel();
        masterPanel.setLayout(new GridBagLayout());

        genDeckDisplay();
        genStudyDisplay();


        
        // action buttons
        
        constrs.gridx = 0;
        constrs.gridy = 0;
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridBagLayout());
        delete = new JButton("Delete");
        delete.addActionListener(this);
        actionsPanel.add(delete, constrs);
        constrs.gridy++;
        move = new JButton("Move");
        move.addActionListener(this);
        actionsPanel.add(move, constrs);
        constrs.gridy++;
        edit = new JButton("Edit");
        edit.addActionListener(this);
        actionsPanel.add(edit, constrs);
        constrs.gridy++;
        addToStudy = new JButton("Add to StudyStack");
        addToStudy.addActionListener(this);
        actionsPanel.add(addToStudy, constrs);
        constrs.gridy++;
        removeFromStudy = new JButton("Remove from StudyStack");
        removeFromStudy.addActionListener(this);
        actionsPanel.add(removeFromStudy, constrs);
        constrs.gridy++;
        clearStudy = new JButton("Clear StudyStack");
        clearStudy.addActionListener(this);
        actionsPanel.add(clearStudy, constrs);

        constrs.gridx = 0;
        constrs.gridy = 1;
        masterPanel.add(actionsPanel, constrs);
        refreshActionDisp();

        // selection-independent action buttons

        JPanel creationPane = new JPanel();
        creationPane.setLayout(new GridBagLayout());
        constrs.gridx = 0;
        constrs.gridy = 0;
        JButton createSleeve = new JButton("New Sleeve");
        Manage temp = this;
        createSleeve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SleeveCreator(currentPath, temp);
            }
        });
        creationPane.add(createSleeve, constrs);
        constrs.gridx = 1;
        JButton createFC = new JButton("New Flashcard");
        createFC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FCCreator(currentPath, temp);
            }
        });
        creationPane.add(createFC, constrs);

        constrs.gridx = 2;
        constrs.gridy = 2;
        masterPanel.add(creationPane, constrs);

        constrs.gridx = 0;
        constrs.gridy = 0;
        navDisp = new Navigator("", this);
        masterPanel.add(navDisp, constrs);

        // labels

        // constrs.gridx = 1;
        // constrs.gridy = 2;
        // int pathLen = currentPath.length();
        // JLabel pathLabel = new JLabel("Path: " + (pathLen > 30 ? ".../" + currentPath.substring(pathLen - 10): currentPath));
        // JLabel pathLabel = new JLabel("Path: ");
        // pathLabel.setHorizontalAlignment(JLabel.CENTER);
        // masterPanel.add(pathLabel, constrs);

        // constrs.gridx = 0;
        // constrs.gridy = 0;
        // masterPanel.add(new JPanel(), constrs);
        constrs.gridx = 1;
        JLabel deck = new JLabel("Deck");
        deck.setHorizontalAlignment(JLabel.CENTER);
        // deck.setBackground(Color.red);
        // deck.setOpaque(true);
        masterPanel.add(deck);
        
        constrs.gridx = 2;
        JLabel studyDeck = new JLabel("StudyStack");
        studyDeck.setHorizontalAlignment(JLabel.CENTER);
        // studyDeck.setBackground(Color.red);
        // studyDeck.setOpaque(true);
        masterPanel.add(studyDeck);

        constrs.gridx = 2;
        constrs.gridy = 3;
        JButton study = new JButton("Start Studying");
        // study.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         studyPrompt();
        //     }
        // });
        study.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studyPrompt();
            }
        });
        masterPanel.add(study, constrs);

        constrs.gridx = 0;
        constrs.gridy = 2;
        // JButton returnHome = new JButton("Home");
        // returnHome.addActionListener(new ToMainMenu());
        masterPanel.add(new HomeButton(), constrs);
        


        
        Page.setPage(this);
        setMasterPanel(masterPanel);
    }

    // private void refreshNavDisp() {
    //     prevSleeve.setEnabled(currentPath.indexOf("/") >= 0);
    //     nextSleeve.setEnabled(nextPaths.size() != 0);
    // }

    private void studyPrompt() {
        JFrame promptWindow = new JFrame("How would you like to study?");
        JPanel actionPane = new JPanel();
        actionPane.setLayout(new GridBagLayout());
        JButton jeopardy = new JButton("Jeopardy Mode");
        ActionListener closeFrame = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promptWindow.dispose();
                // System.out.println("close");
            }
        };
        jeopardy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studyStack.jeopardize();
                ToStudy go = new ToStudy(studyStack);
                go.actionPerformed(null);
            }
        });
        jeopardy.addActionListener(closeFrame);
        // jeopardy.addActionListener(new ToStudy(studyStack));
        JButton rand = new JButton("Randomized Order");
        rand.addActionListener(new ToStudy(studyStack));
        rand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studyStack.randomize();
                // System.out.println("random");
            }
        });
        rand.addActionListener(closeFrame);
        JButton alph = new JButton("Alphabetized Order");
        alph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studyStack.alphabetize();
            }
        });
        alph.addActionListener(closeFrame);
        alph.addActionListener(new ToStudy(studyStack));
        
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        actionPane.add(jeopardy, constrs);
        constrs.gridy++;
        actionPane.add(rand, constrs);
        constrs.gridy++;
        actionPane.add(alph, constrs);

        promptWindow.setContentPane(actionPane);
        promptWindow.setMinimumSize(new Dimension(300, 175));
        promptWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        promptWindow.setLocationRelativeTo(null);
        promptWindow.pack();
        promptWindow.setVisible(true);
    }

    private int indexOf(ArrayList<Flashcard> fcs, Flashcard fc) {
        for (int index = 0; index < fcs.size(); index++) {
            Flashcard cand = fcs.get(index);
            if (cand.equals(fc)) {
                return index;
            }
        }
        return -1;
    }

    private void refreshActionDisp() {
        int selFCSize = selectedFCs.size();
        int selSleeveSize = selectedSleeves.size();
        boolean isSelSizeNonzero = selFCSize != 0 || selSleeveSize != 0;
        boolean selHasNonStudyFCs = false;
        boolean isStudySizeNonzero = studyStack.getStack().size() > 0;
        for (Flashcard candFC : selectedFCs) {
            // System.out.println(studyStack.getStack().indexOf(candFC));
            
            if (indexOf(studyStack.getStack(), candFC) == -1) {
                selHasNonStudyFCs = true;
                break;
            }
            
            // if (studyStack.getStack().indexOf(candFC) == -1) {
            //     selHasNonStudyFCs = true;
            // }
        }
        // System.out.println("0: " + isSizeNonzero);
        delete.setEnabled(isSelSizeNonzero);
        move.setEnabled(isSelSizeNonzero);
        addToStudy.setEnabled(isSelSizeNonzero && selHasNonStudyFCs);            
        removeFromStudy.setEnabled(isSelSizeNonzero);
        clearStudy.setEnabled(isStudySizeNonzero);
        // System.out.println(selFCSize + selSleeveSize);
        boolean isOnlyOneSel = selFCSize + selSleeveSize == 1;
        // System.out.println("1: " + isOnlyOneSel);
        edit.setEnabled(isOnlyOneSel);
        masterPanel.revalidate();
        masterPanel.repaint();
    }

    private void genStudyDisplay() {
        studyHolderPanes = new ArrayList<HolderPane>();
        if (studyPane != null) studyPane.setVisible(false);

        JPanel disp = new JPanel();
        disp.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        ArrayList<Flashcard> studyFCs = studyStack.getStack();
        int numOpenFCs = 0;
        for (int numFCs = 0; numFCs < studyFCs.size(); numFCs++) {
            constrs.gridy = numFCs + numOpenFCs;
            Flashcard fc = studyFCs.get(numFCs);
            // disp.add(new FCButton(fc, this, true), constrs);
            HolderPane newHolder = new HolderPane(fc, this, true);
            studyHolderPanes.add(newHolder);
            disp.add(newHolder, constrs);
            for (Flashcard cand : openStudyCards) {
                if (cand.equals(fc)) {
                    constrs.gridy++;
                    numOpenFCs++;
                    // JLabel resp = new JLabel(fc.getResponse());
                    // resp.setHorizontalAlignment(JLabel.CENTER);
                    disp.add(getRespComponent(fc), constrs);
                }
            }
        }

        studyPane = new JScrollPane(disp);
        studyPane.setPreferredSize(new Dimension(350, 400));
        
        constrs.gridx = 2;
        constrs.gridy = 1;
        masterPanel.add(studyPane, constrs);
        masterPanel.revalidate();
        masterPanel.repaint();
    }

    public void genDeckDisplay() {
        GridBagConstraints constrs = new GridBagConstraints();
        if (deckPane != null) deckPane.setVisible(false);

        // dispPanel = new JPanel();
        JPanel disp = new JPanel();
        // dispPanel.setLayout(new GridBagLayout());
        
        int sleeveCount = loadSleevePanes(disp, findSleevesUnder(currentPath));

        // ArrayList<Flashcard> currentFCs = findFCsUnder(currentPath);
        // int openCardCt = 0;
        // for (int fcCount = 0; fcCount < currentFCs.size(); fcCount++) {
        //     constrs.gridy = sleeveCount + fcCount + openCardCt;
        //     Flashcard curFC = currentFCs.get(fcCount);
        //     HolderPane holdPane = new HolderPane(curFC, this);
        //     dispPanel.add(holdPane, constrs);
        //     for (Flashcard openCard : openCards) {
        //         if (curFC.equals(openCard)) {
        //             openCardCt++;
        //             constrs.gridy++;
        //             JLabel resp = new JLabel(curFC.getResponse());
        //             resp.setHorizontalAlignment(JLabel.CENTER);
        //             dispPanel.add(resp, constrs);              
        //         }
        //     }
        // }

        // loadFCPanes(dispPanel, sleeveCount, FULLDECK);
        loadFCPanes(disp, sleeveCount, findFCsUnder(currentPath));

        // deckPane = new JScrollPane(dispPanel);
        deckPane = new JScrollPane(disp);
        deckPane.setPreferredSize(new Dimension(350, 400));
        constrs.gridx = 1;
        constrs.gridy = 1;
        masterPanel.add(deckPane, constrs);
        masterPanel.revalidate();
        masterPanel.repaint();
    }

    private int loadSleevePanes(JPanel disp, ArrayList<Sleeve> currentSleeves) {
        deckSleevePanes = new ArrayList<HolderPane>();
        int sleeveCount = 0;
        disp.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        while (sleeveCount < currentSleeves.size()) {
            constrs.gridy = sleeveCount;
            // dispPanel.add(new HolderPane(currentSleeves.get(sleeveCount), this), constrs);
            HolderPane newHolder = new HolderPane(currentSleeves.get(sleeveCount), this, false);
            deckSleevePanes.add(newHolder);
            disp.add(newHolder, constrs);
            sleeveCount++;
        }
        return sleeveCount;
    }

    private void loadFCPanes(JPanel disp, int sleeveCount, ArrayList<Flashcard> currentFCs) {
        GridBagConstraints constrs = new GridBagConstraints();
        deckFCPanes = new ArrayList<HolderPane>();
        // ArrayList<Flashcard> currentFCs = whichStack == FULLDECK ? findFCsUnder(currentPath) : studyStack.getStack();
        int openCardCt = 0;
        for (int fcCount = 0; fcCount < currentFCs.size(); fcCount++) {
            constrs.gridy = sleeveCount + fcCount + openCardCt;
            Flashcard curFC = currentFCs.get(fcCount);
            HolderPane holdPane = new HolderPane(curFC, this, false);
            deckFCPanes.add(holdPane);
            disp.add(holdPane, constrs);
            for (Flashcard openCard : openCards) {
                if (curFC.equals(openCard)) {
                    openCardCt++;
                    constrs.gridy++;
                    // JLabel resp = new JLabel(curFC.getResponse());
                    // JLabel resp = new JLabel() {
                    //     @Override
                    //     protected void paintComponent(Graphics g) {
                    //         super.paintComponent(g);
                    //     }
                    // }
                    // System.out.println("aef");
                    JLabel resp = getRespComponent(curFC);
                    disp.add(resp, constrs);              
                }
            }
            // System.out.println(openCards.size());
        }
    }

    private JLabel getRespComponent(Flashcard fc) {
        JLabel resp = new JLabel("whoa") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g.setColor(Color.BLACK);
                // System.out.println("r---\n" + fc.getRespImg() + "\n---r");
                // System.out.println(getWidth() + ", " + getHeight());
                if (fc.getRespImg() != "") {
                    try {
                        BufferedImage img = ImageIO.read(new File(fc.getRespImg()));
                        int w = img.getWidth(null) / FCButton.getImgShrinkFactor();
                        int h = img.getHeight(null) / FCButton.getImgShrinkFactor();
                        g.drawImage(img, 10, 10, w, h, null);
                        g.drawString(fc.getResponse(), 10, h + 25);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        System.exit(0);
                    }
                }
                else {
                    // System.out.println("hi");
                    g.drawString(fc.getResponse(), 10, 20);
                }
            }
        };

        resp.setHorizontalAlignment(JLabel.RIGHT);

        if (fc.getRespImg() != "") {
            // System.out.println("here");
            try {
                BufferedImage img = ImageIO.read(new File(fc.getRespImg()));
                int w = img.getWidth(null) / FCButton.getImgShrinkFactor();
                int h = img.getHeight(null) / FCButton.getImgShrinkFactor();
                resp.setPreferredSize(new Dimension(w + 20, h + 40));
                resp.setMinimumSize(new Dimension(w + 20, h + 40));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }
        else {
            // System.out.println("hello");
            resp.setMinimumSize(new Dimension(75, 30));
            resp.setPreferredSize(new Dimension(75, 30));
        }
        return resp;
    }

    private JPanel getFiller(Color color) {
        Dimension fillerSize = new Dimension(100, 10);
        JPanel filler = new JPanel();
        filler.setPreferredSize(fillerSize);
        if (color != null) filler.setBackground(color);
        return filler;
    }

    public ArrayList<Flashcard> getMasterStack() {
        return masterStack.getStack();
    }

    public static FCStack genMasterStack() {
        try {
            FCStack masterStack = new FCStack();
            ArrayList<Flashcard> masterFCs = masterStack.getStack();
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
                    // File promptImgs = new File(promptImgsPath);
                    // if (promptImgs.isDirectory()) {
                    //     for (String path : promptImgs.list()) {
                    //         newFC.addImg(path, Flashcard.PROMPT);
                    //     }
                    // }
                    // else {
                    //     newFC.addImg(promptImgsPath, Flashcard.PROMPT);
                    // }
                    newFC.setPromptImg(promptImgPath);
                }
                // System.out.println("p---\n" + promptImgsPath + "\n---p");
                String respImgPath = scanner.nextLine();
                if (respImgPath != "\n") {
                    // File respImgs = new File(respImgsPath);
                    // if (respImgs.isDirectory()) {
                    //     for (String path : respImgs.list()) {
                    //         newFC.addImg(path, Flashcard.RESP);
                    //     }
                    // }
                    // else {
                    //     newFC.addImg(respImgsPath, Flashcard.RESP);
                    // }
                    newFC.setRespImg(respImgPath);
                }
                
                // System.out.println("r---\n" + respImgsPath + "\n---r");
                }
            scanner.close();
            return masterStack;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            // System.out.println("oops");
            return null;
        }
    }

    public void genSleeves() {
        try {
            allSleeves = new ArrayList<Sleeve>();
            Scanner sleeveInfo = new Scanner(new File("sleeves.txt"));
            while (sleeveInfo.hasNextLine()) {
                String sleevePath = sleeveInfo.nextLine();
                String sleeveName = sleeveInfo.nextLine();
                allSleeves.add(new Sleeve(sleevePath, sleeveName));
                // System.out.println(new Sleeve(sleevePath, sleeveName));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createSleeve(String sleevePath, String sleeveName) throws Exception {
        Sleeve newSleeve = new Sleeve(sleevePath, sleeveName);
        for (Sleeve sleeve : allSleeves) {
            if (newSleeve.equals(sleeve)) {
                throw new Exception("There is already a sleeve with the same name in this directory.");
            }
        }
        allSleeves.add(newSleeve);
        saveSleeves();
        refresh();
    }

    public void move(ArrayList<Sleeve> sleevesToMove, ArrayList<Flashcard> fcsToMove, String newPath) {
        // System.out.println(selectedFCs.size());
        for (Flashcard fc : fcsToMove) {
            moveFC(fc, newPath);
        }
        for (Sleeve sleeve : sleevesToMove) {
            moveSleeve(sleeve, newPath);
        }
        save();
        refresh();
    }

    private void moveFC(Flashcard fc, String newPath) {
        int index = 0;
        ArrayList<Flashcard> deck = masterStack.getStack();
        while (index < deck.size()) {
            if (deck.get(index).equals(fc)) {
                break;
            }
            index++;
        }
        deck.get(index).setPath(newPath);
    }

    private void moveSleeve(Sleeve sleeve, String newPath) {
        String nextPath = sleeve.getPath() + sleeve.getName() + "/";
        for (Flashcard fc : findFCsUnder(nextPath)) {
            moveFC(fc, newPath);
        }
        for (Sleeve underSleeve : findSleevesUnder(nextPath)) {
            moveSleeve(underSleeve, newPath + sleeve.getName() + "/");
        }
        sleeve.setPath(newPath);
    }

    // ** FIX RECURSION FOR THE FLASHCARDS **
    // deletes given Sleeve only, 
    private void softDeleteSleeve(Sleeve sleeve) {
        removeSleeveTrace(sleeve.getPath() + sleeve.getName() + "/", sleeve);
        allSleeves.remove(sleeve);
    }

    private void removeSleeveTrace(String startPt, Sleeve sleeve) {
        String sleeveName = sleeve.getName();
        // int cutStart = startPt.indexOf(sleeveName);
        // int cutEnd = cutStart + sleeveName.length() + 1;
        // String newPath = startPt.substring(0, cutStart) + startPt.substring(cutEnd);
        String sleevePath = sleeve.getPath() + sleeveName + "/";
        String startNewPath = sleevePath.substring(0, sleevePath.length() - sleeveName.length() - 1);
        String endNewPath = startPt.substring(sleevePath.length());
        String newPath = startNewPath + endNewPath;
        for (Sleeve nextSleeve : findSleevesUnder(startPt)) {
            removeSleeveTrace(startPt + nextSleeve.getName() + "/", sleeve);
            nextSleeve.setPath(newPath);
        }
        for (Flashcard fc : findFCsUnder(startPt)) {
            fc.setPath(newPath);
        }
    }

    // deletes given Sleeve and its contents
    private void hardDeleteSleeve(Sleeve sleeve) {
        String sleevePath = sleeve.getPath() + sleeve.getName() + "/";
        ArrayList<Flashcard> fcs = findFCsUnder(sleevePath);
        for (Flashcard fc : fcs) {
            getMasterStack().remove(fc);
            studyStack.getStack().remove(fc);
        }
        ArrayList<Sleeve> innerSleeves = findSleevesUnder(sleevePath);
        for (Sleeve innerSleeve : innerSleeves) {
            allSleeves.remove(innerSleeve);
            hardDeleteSleeve(innerSleeve);
        }
        allSleeves.remove(sleeve);
    }

    public ArrayList<Sleeve> findSleeve(String name) {
        ArrayList<Sleeve> matches = new ArrayList<Sleeve>();
        for (Sleeve cand : allSleeves) {
            if (cand.getName().equals(name)) {
                matches.add(cand);
            }
        }
        return matches;
    }

    public static ArrayList<Sleeve> findSleevesUnder(String path) {
        ArrayList<Sleeve> matches = new ArrayList<Sleeve>();
        for (Sleeve cand : allSleeves) {
            if (path.equals(cand.getPath())) {
                matches.add(cand);
            }
        }
        return matches;
    }

    public static ArrayList<Flashcard> findFCsUnder(String path) {
        ArrayList<Flashcard> matches = new ArrayList<Flashcard>();
        // for (Flashcard cand : getMasterStack()) {
        for (Flashcard cand : genMasterStack().getStack()) {
            if (path.equals(cand.getPath())) {
                matches.add(cand);
            }
        }
        return matches;
    }

    public ArrayList<Flashcard> findFCsUnderUmbrella(String path) {
        ArrayList<Flashcard> matches = new ArrayList<Flashcard>();
        for (Flashcard cand : getMasterStack()) {
            if (cand.getPath().indexOf(path) == 0) {
                matches.add(cand);
            }
        }
        return matches;
    }

    public boolean isOpen(Flashcard fc) {
        for (Flashcard cand : openCards) {
            if (fc.equals(cand)) {
                return true;
            }
        }
        return false;
    }

    public void openCard(Flashcard fc) {
        if (! isOpen(fc)) {
            openCards.add(fc);
        }
        // System.out.println(openCards.size());
        genDeckDisplay();
    }

    public void closeCard(Flashcard fc) {
        for (int index = 0; index < openCards.size(); index++) {
            Flashcard cand = openCards.get(index);
            if (cand.equals(fc)) {
                openCards.remove(cand);
                break;
            }
        }
        genDeckDisplay();
    }

    public void openStudyCard(Flashcard fc) {
        if (studyStack.getStack().indexOf(fc) != -1) {
            openStudyCards.add(fc);
            genStudyDisplay();
        }
    }

    public void closeStudyCard(Flashcard fc) {
        if (studyStack.getStack().indexOf(fc) != -1) {
            openStudyCards.remove(fc);
            genStudyDisplay();
        }
    }

    public void toggleSelect(Flashcard fc) {
        // System.out.println(masterStack.getStack().indexOf(fc));
        // for (Flashcard f : masterStack.getStack()) {
        //     System.out.println(f);
        // }
        if (selectedFCs.indexOf(fc) == -1) {
            selectedFCs.add(fc);
        }
        else {
            selectedFCs.remove(fc);
        }
        refreshActionDisp();
    }

    public void toggleSelect(Sleeve sleeve) {
        ArrayList<Flashcard> allFCsUnder = findFCsUnderUmbrella(sleeve.getPath() + sleeve.getName() + "/");
        if (selectedSleeves.indexOf(sleeve) == -1) {
            selectedSleeves.add(sleeve);
            // for (Flashcard fc : allFCsUnder) {
            //     if (selectedFCs.indexOf(fc) == -1) {
            //         selectedFCs.add(fc);
            //     }
            // }
        }
        else {
            selectedSleeves.remove(sleeve);
            // for (Flashcard fc : allFCsUnder) {
            //     selectedFCs.remove(fc);
            // }
        }
        refreshActionDisp();
    }

    public void createFC(String path, String prompt, String response) throws Exception {
        Flashcard newFC = new Flashcard(path, prompt, response);
        for (Flashcard fc : masterStack.getStack()) {
            if (newFC.equals(fc)) throw new Exception("Flashcard with the same content already exists in this location");
        }
        masterStack.getStack().add(newFC);
        saveFCs();
        genDeckDisplay();
    }

    public void deleteFC(Flashcard fc) {
        ArrayList<Flashcard> deck = getMasterStack();
        int index = 0;
        while (index < deck.size()) {
            if (fc.equals(deck.get(index))) {
                deck.remove(index);
                break;
            }
            index++;
        }
        saveFCs();
        genDeckDisplay();
    }

    private void deleteFCs(ArrayList<Flashcard> fcs) {
        for (Flashcard fc : fcs) {
            deleteFC(fc);
        }
    }

    public void changeSleeveName(Sleeve sleeve, String newName) {
        updatePaths(sleeve.getPath() + sleeve.getName() + "/", sleeve, newName);
        sleeve.setName(newName);
        save();
        refresh();
    }

    private void updatePaths(String startPt, Sleeve sleeve, String newName) {
        String sleeveName = sleeve.getName();
        String sleevePath = sleeve.getPath() + sleeveName + "/";
        String startNewPath = sleevePath.substring(0, sleevePath.length() - sleeveName.length() - 1) + newName + "/";
        String endNewPath = startPt.substring(sleevePath.length());
        String newPath = startNewPath + endNewPath;
        for (Sleeve nextSleeve : findSleevesUnder(startPt)) {
            updatePaths(startPt + nextSleeve.getName() + "/", sleeve, newName);
            nextSleeve.setPath(newPath);
        }
        for (Flashcard fc : findFCsUnder(startPt)) {
            fc.setPath(newPath);
        }
    }

    public void modFC(Flashcard fc, String newPrompt, String newResp) {
        // System.out.println("huh");
        // System.out.println(masterStack.getStack().indexOf(fc));
        // fc.setPrompt(newPrompt);
        // fc.setResponse(newResp);
        Flashcard modFC = findFC(fc.getPath(), fc.getPrompt(), fc.getResponse());
        modFC.setPrompt(newPrompt);
        modFC.setResponse(newResp);
        // System.out.println(masterStack.getStack().indexOf(modFC));
        saveFCs();
        refresh();
    }
    public Sleeve getMasterSleeve() {
        return masterSleeve;
    }

    private void saveSleeves() {
        try {
            String filepath = "sleeves.txt";
            File prev = new File(filepath);
            File saved = new File("temp.txt");

            FileWriter fw = new FileWriter(saved);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            for (Sleeve sleeve : allSleeves) {
                writer.print(sleeve.getPath() + "\n" +
                            sleeve.getName() + "\n");
            }
            writer.flush();
            writer.close();
            prev.delete();
            saved.renameTo(new File(filepath));


        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveFCs() {
        try {
            String filepath = "flashcards.txt";
            File prev = new File(filepath);
            File saved = new File("temp.txt");

            FileWriter fw = new FileWriter(saved);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);

            for (Flashcard fc : masterStack.getStack()) {
                writer.print(fc.getPath() + "\n" +
                             fc.getPrompt() + "\n" + 
                             fc.getResponse() + "\n");
            }
            writer.flush();
            writer.close();
            prev.delete();
            saved.renameTo(new File(filepath));

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        saveSleeves();
        saveFCs();
    }

    public Flashcard findFC(String sleevePath, String prompt, String response) {
        Flashcard temp = new Flashcard(sleevePath, prompt, response);
        for (Flashcard cand : masterStack.getStack()) {
            if (temp.equals(cand)) {
                return cand;
            }
        }
        return null;
    }

    public Sleeve findSleeve(String sleevePath, String name) {
        Sleeve temp = new Sleeve(sleevePath, name);
        for (Sleeve cand : allSleeves) {
            if (temp.equals(cand)) {
                return cand;
            }
        }
        return null;
    }

    public ArrayList<Flashcard> getOpenCards() {
        return openCards;
    }

    public ArrayList<Flashcard> getOpenStudyCards() {
        return openStudyCards;
    }

    public String getPath() {
        return currentPath;
    }

    public void shiftPath(String newPath) {
        openCards = new ArrayList<Flashcard>();
        navDisp.setPath(newPath);
        selectedSleeves = new ArrayList<Sleeve>();
        selectedFCs = new ArrayList<Flashcard>();
        refreshActionDisp();
        setPath(newPath);
    }

    public void setPath(String newPath) {
        // prevPath = currentPath;
        currentPath = newPath;
        // nextPath = null;
        // if (! currentPath.equals(nextPaths.peek())) {
        //     nextPaths = new Stack<String>();
        // }
        genDeckDisplay();
        // refreshNavDisp();
    }

    private void addSleeveToStudy(Sleeve sleeve) {
        String path = sleeve.getPath() + sleeve.getName() + "/";
        ArrayList<Flashcard> study = studyStack.getStack();
        for (Flashcard fc : findFCsUnder(path)) {
            if (study.indexOf(fc) == -1) {
                study.add(fc);
            }
        }
        for (Sleeve innerSleeve : findSleevesUnder(path)) {
            addSleeveToStudy(innerSleeve);
        }
    }

    private String prevPath(String path) {
        int lastTurnInd = path.lastIndexOf("/");
        int newLastTurnInd = path.lastIndexOf("/", lastTurnInd - 1);
        return path.substring(0, newLastTurnInd + 1);
    }

    private void confirmDelete(int deleteMethod) {
        JFrame confirmFrame = new JFrame("Are you sure?");
        confirmFrame.setPreferredSize(new Dimension(200, 100));
        Container confirmPane = confirmFrame.getContentPane();
        confirmPane.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        JLabel warning = new JLabel("This action cannot be undone");
        confirmPane.add(warning, constrs);
        constrs.gridy = 1;
        JButton confirm = new JButton("Do it");
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmFrame.dispose();
                deleteSelected(deleteMethod);
            }
        });
        confirmPane.add(confirm, constrs);

        confirmFrame.pack();
        confirmFrame.setLocationRelativeTo(null);
        confirmFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        confirmFrame.setVisible(true);

    }

    private void deleteSelected(int deleteMethod) {
        if (deleteMethod == HARD_DELETE) {
            for (Sleeve selectedSleeve : selectedSleeves) {
                hardDeleteSleeve(selectedSleeve);
            }
        }
        else if (deleteMethod == SOFT_DELETE) {
            for (Sleeve selectedSleeve : selectedSleeves) {
                softDeleteSleeve(selectedSleeve);
            }
        }
        for (Flashcard selectedFC : selectedFCs) {
            deleteFC(selectedFC);
        }
        selectedFCs = new ArrayList<Flashcard>();
        selectedSleeves = new ArrayList<Sleeve>();
        refresh();
        save();
        resetSelected();

    }

    private void resetSelected() {
        selectedFCs = new ArrayList<Flashcard>();
        selectedSleeves = new ArrayList<Sleeve>();
        // for (HolderPane fcPane : deckFCPanes) {
        //     fcPane.resetCheckBox();
        // }
        // for (HolderPane sleevePane : deckFCPanes) {
        //     fcPane.resetCheckBox();
        // }
    }

    private void refresh() {
        genStudyDisplay();
        genDeckDisplay();
        refreshActionDisp();
        if (mover != null) mover.refresh();
    }

    public void disposeMover() {
        mover = null;
    }

    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Delete": {
                if (selectedSleeves.size() > 0) {
                    JFrame selectionFrame = new JFrame("Choose deletion method");
                    selectionFrame.setPreferredSize(new Dimension(300, 200));
                    Container selectionPane = selectionFrame.getContentPane();
                    selectionPane.setLayout(new GridBagLayout());
                    GridBagConstraints constrs = new GridBagConstraints();
                    constrs.gridx = 0;
                    constrs.gridy = 0;
                    constrs.gridwidth = 2;
                    constrs.gridheight = 2;
                    constrs.fill = GridBagConstraints.BOTH;
                    JTextArea info = new JTextArea("Hard Delete will also erase all contents of the selected Sleeves, " + 
                                                   "while Soft Delete only erases the selected Sleeves and their traces on" + 
                                                   "any other Sleeves/Flashcards.");
                    // info.setMaximumSize(new Dimension(150, 250));
                    info.setEditable(false);
                    info.setLineWrap(true);
                    info.setWrapStyleWord(true);
                    selectionPane.add(info, constrs);
                    constrs.gridx = 0;
                    constrs.gridy = 2;
                    constrs.gridwidth = 1;
                    constrs.gridheight = 1;
                    JButton hardDelete = new JButton("Hard Delete");
                    hardDelete.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            confirmDelete(HARD_DELETE);
                            selectionFrame.dispose();
                        }
                    });
                    selectionPane.add(hardDelete, constrs);
                    constrs.gridx = 1;
                    JButton softDelete = new JButton("Soft Delete");
                    softDelete.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            confirmDelete(SOFT_DELETE);
                            selectionFrame.dispose();
                        }
                    });
                    selectionPane.add(softDelete, constrs);

                    selectionFrame.pack();
                    selectionFrame.setLocationRelativeTo(null);
                    selectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    selectionFrame.setVisible(true);
                    }
                else {
                    confirmDelete(NO_SLEEVE);
                }
                break;
            }
            case "Move": {
                // System.out.println("moving");
                mover = new Mover(selectedSleeves, selectedFCs, this);
                resetSelected();
                break;
            }
            case "Edit": {
                // System.out.println("editing");
                if (selectedFCs.size() == 1) {
                    // System.out.println(masterStack.getStack().indexOf(selectedFCs.get(0)));
                    new Editor(selectedFCs.get(0), this);
                }
                else if (selectedSleeves.size() == 1) {
                    new Editor(selectedSleeves.get(0), this);
                }
                resetSelected();
                break;
            }
            case "Add to StudyStack": {
                ArrayList<Flashcard> study = studyStack.getStack();
                // System.out.println(selectedFCs.size());
                for (Flashcard fc : selectedFCs) {
                    if (indexOf(study, fc) == -1) study.add(fc);
                }
                for (Sleeve sleeve : selectedSleeves) {
                    addSleeveToStudy(sleeve);
                }
                resetSelected();
                // genStudyDisplay();
                break;
            }
            case "Remove from StudyStack": {
                // for (Flashcard fc: selectedFCs) {
                //     // studyStack.getStack().remove(fc);
                //     ArrayList<Flashcard> stack = studyStack.getStack();
                //     for (int index = 0; index < stack.size(); index++) {
                //         if (fc.equals(stack.get(index))) {
                //             stack.remove(index);
                //             index--;
                //         }
                //     }
                // }
                for (Flashcard fc : selectedFCs) {
                    ArrayList<Flashcard> stack = studyStack.getStack();
                    // Flashcard fc = selectedFCs.remove(0);
                    for (int index = 0; index < stack.size(); index++) {
                        if (fc.equals(stack.get(index))) {
                            stack.remove(index);
                            index--;
                        }
                    }
                }
                resetSelected();
                // refresh();
                break;
            }
            case "Clear StudyStack": {
                studyStack = new FCStack();
                resetSelected();
            }
            
            // case "<": {
            //     System.out.println("back");
            //     if (prevPath != null) {
            //         System.out.println("success b");
            //         nextPaths.add(currentPath);
            //         currentPath = prevPath(currentPath);
            //         genDeckDisplay();
            //         refreshNavDisp();
            //     }
            //     System.out.println(currentPath.equals(""));
            //     break;
            // }
            // case ">": {
            //     System.out.println("forw");
            //     if (nextPaths.size() != 0) {
            //         System.out.println("success f");
            //         prevPath = currentPath;
            //         currentPath = nextPaths.pop();
            //         genDeckDisplay();
            //         refreshNavDisp(); 
            //     }
                
            //     break;
            // }
            
        }
        // resetSelected();
        // System.out.println("selectedFC" + selectedFCs.size());
        // System.out.println("selectedSleeves" + selectedSleeves.size());
        refresh();
    }

    public static void main(String[] args) {
        Flashcard a = new Flashcard("s", "t", "u");
        Flashcard b = new Flashcard("s", "t", "u");
        ArrayList<Flashcard> test = new ArrayList<Flashcard>();
        test.add(a);
        test.add(b);
        test.remove(b);
        System.out.println(test.size());
        System.out.println(test.indexOf(b));
        System.out.println(test.indexOf(a));
        // System.out.println(prevPath("master/Español/"));
        // System.out.println(prevPath("master"));
    }
}