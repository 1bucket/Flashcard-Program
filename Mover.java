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
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Mover implements ActionListener {
    
    private ArrayList<Sleeve> sleeves;
    private ArrayList<Flashcard> fcs;

    private String curPath;
    private String prevPath;
    private ArrayList<String> nextPaths;

    private Manage root;
    private Navigator nav;

    private JFrame frame;
    private Container masterPanel;
    private JScrollPane disp;

    public Mover(ArrayList<Sleeve> sleevesToMove, ArrayList<Flashcard> fcsToMove, Manage newRoot) {
        sleeves = sleevesToMove;
        fcs = fcsToMove;
        root = newRoot;

        curPath = "";
        prevPath = null;
        nextPaths = null;

        frame = new JFrame("Move Selected");
        frame.setPreferredSize(new Dimension(520, 780));
        masterPanel = frame.getContentPane();
        masterPanel.setLayout(new GridBagLayout());

        // System.out.println("a");
        refresh();
        // System.out.println("b");

        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        // constrs.gridwidth = 2;
        // constrs.fill = GridBagConstraints.BOTH;
        nav = new Navigator("", this);
        masterPanel.add(nav, constrs);
        // constrs.gridwidth = 1;
        constrs.gridy = 2;
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        masterPanel.add(confirm, constrs);
        constrs.gridy = 3;
        JButton newSleeve = new JButton("New Sleeve");
        newSleeve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SleeveCreator(curPath, root);
            }
        });
        masterPanel.add(newSleeve, constrs);

        

        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setPath(String newPath) {
        curPath = newPath;
        refresh();
    }

    private void shiftPath(String newPath) {
        nav.setPath(newPath);
        setPath(newPath);
    }

    private ArrayList<Flashcard> removeFCDupes(ArrayList<Flashcard> mainList, ArrayList<Flashcard> checkList) {
        // System.out.println(mainList.size());
        if (mainList.size() < 1) return mainList;
        Stack<Flashcard> check = new Stack(checkList);
        int failCounter = 0;
        for (int index = 0; mainList.size() > 0 && check.size() > 0; index += 1) {
            index %= mainList.size();
            // System.out.print(mainList.size());
            // System.out.println(", " + check.size());
            if (mainList.get(index).equals(check.peek())) {
                check.pop();
                mainList.remove(index);
                index = 0;
            }
            failCounter += 1;
            if (failCounter == mainList.size()) {
                check.pop();
            }
        }
        return mainList;
    }

    private ArrayList<Sleeve> removeSleeveDupes(ArrayList<Sleeve> mainList, ArrayList<Sleeve> checkList) {
        Stack<Sleeve> check = new Stack(checkList);
        for (int index = 0; mainList.size() > 0 && check.size() > 0; index = (index + 1) % mainList.size()) {
            // Sleeve w = mainList.get(index);
            // System.out.println(w + "[]\n" + check.peek());
            if (mainList.get(index).equals(check.peek())) {
                check.pop();
                mainList.remove(index);
                index = -1;
            }
        }
        return mainList;
    } 

    public void refresh() {
        if (disp != null) {
            disp.setVisible(false);
        }
        JPanel display = new JPanel();
        display.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        ArrayList<Sleeve> currentSleeves = removeSleeveDupes(Manage.findSleevesUnder(curPath), sleeves);
        int numSleeves = 0;
        for (Sleeve sleeve : currentSleeves) {
            constrs.gridy = numSleeves;
            // JButton sleeveButton = new JButton(currentSleeves.get(index).getName());
            JButton sleeveButton = new JButton(sleeve.getName());
            sleeveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    shiftPath(curPath + e.getActionCommand() + "/");
                }
            });
            display.add(sleeveButton, constrs);
            numSleeves++;
        }
        // System.out.println("boop");
        ArrayList<Flashcard> currentFCs = removeFCDupes(Manage.findFCsUnder(curPath), fcs);
        for (int numFCs = 0; numFCs < currentFCs.size(); numFCs++) {
            // System.out.println("bing");
            constrs.gridy = numFCs + numSleeves;
            FCButton fcButton = new FCButton(currentFCs.get(numFCs), null, false);
            fcButton.adaptSize();
            fcButton.setEnabled(false);
            display.add(fcButton, constrs);
        }


        disp = new JScrollPane(display);
        disp.setPreferredSize(new Dimension((int) (masterPanel.getWidth() * 0.8), (int) (masterPanel.getHeight() * 0.8)));
        constrs.gridy = 1;
        masterPanel.add(disp, constrs);
        masterPanel.revalidate();
        masterPanel.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        // System.out.println(root.selectedFCs.size());
        root.move(sleeves, fcs, curPath);
        // root.save();
        root.disposeMover();
        frame.dispose();
    }
}