import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

@SuppressWarnings("unchecked")
public class Navigator extends FCPPanel implements ActionListener{ 
    private String prevPath;
    private String currentPath;
    private Stack<String> nextPaths;

    private FCPLabel pathLabel;

    private FCPButton prevSleeve;
    private FCPButton nextSleeve;

    private Manage manageRoot;
    // private Mover moverRoot;

    public Navigator(String path, Manage newRoot) {
        this(path);
        manageRoot = newRoot;
    }
    /*
    public Navigator(String path, Mover newRoot) {
        this(path);
        moverRoot = newRoot;
    }
    */

    public Navigator(String path) {
        super(Color.BLACK);
        setPreferredSize(new Dimension(250, 200));
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        prevPath = null;
        currentPath = path;
        nextPaths = new Stack();
        pathLabel = new FCPLabel("Path: " + currentPath, FCPLabel.SMALL, Color.WHITE);
        // pathLabel.setTransparent(false);
        add(pathLabel);
        prevSleeve = new FCPButton("<", FCPButton.SMALL, Color.WHITE, Color.BLACK, Color.WHITE);
        prevSleeve.setTransparent(true);
        // System.out.println(getFontMetrics(prevSleeve.getFont()).stringWidth("<"));
        prevSleeve.addActionListener(this);
        add(prevSleeve);
        nextSleeve = new FCPButton(">", FCPButton.SMALL, Color.WHITE, Color.BLACK, Color.WHITE);
        nextSleeve.setTransparent(true);
        nextSleeve.addActionListener(this);
        add(nextSleeve);

        springLayout.putConstraint(SpringLayout.NORTH, pathLabel, 20, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, pathLabel, ((int)getPreferredSize().getWidth() - pathLabel.getButtonWidth()) / 2, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, prevSleeve, 15, SpringLayout.SOUTH, pathLabel);
        // System.out.println((int)getPreferredSize().getWidth() / 2 - prevSleeve.getButtonWidth() - 5);
        // System.out.println(getPreferredSize().getWidth() / 2);
        // System.out.println(prevSleeve.getButtonWidth());

        springLayout.putConstraint(SpringLayout.WEST, prevSleeve, (int)getPreferredSize().getWidth() / 2 - prevSleeve.getButtonWidth() - 5, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, nextSleeve, 15, SpringLayout.SOUTH, pathLabel);
        springLayout.putConstraint(SpringLayout.WEST, nextSleeve, (int)getPreferredSize().getWidth() / 2 + 5, SpringLayout.WEST, this);
        /*
        setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();

        prevPath = null;
        currentPath = path;
        nextPaths = new Stack();

        constrs.gridx = 0;
        constrs.gridy = 0;
        // constrs.gridwidth = 2;
        pathLabel = new JLabel("Path: " + currentPath);
        add(pathLabel, constrs);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        // constrs.gridy = 1;
        // constrs.gridwidth = 1;
        prevSleeve = new JButton("<");
        prevSleeve.addActionListener(this);
        buttonPanel.add(prevSleeve, constrs);
        constrs.gridx = 1;
        nextSleeve = new JButton(">");
        nextSleeve.addActionListener(this);
        buttonPanel.add(nextSleeve, constrs);
        constrs.gridx = 0;
        constrs.gridy = 1;
        add(buttonPanel, constrs);
        */
        refresh();
    }

    public void setPath(String newPath) {
        prevPath = currentPath;
        currentPath = newPath;
        if (! currentPath.equals(nextPaths.peek())) {
            nextPaths = new Stack<String>();
        }
        refresh();
    }

    private String prevPath(String path) {
        int lastTurnInd = path.lastIndexOf("/");
        int newLastTurnInd = path.lastIndexOf("/", lastTurnInd - 1);
        return path.substring(0, newLastTurnInd + 1);
    }

    private void refresh() {
        prevSleeve.setEnabled(currentPath.indexOf("/") >= 0);
        nextSleeve.setEnabled(nextPaths.size() != 0);
        int pathLen = currentPath.length();
        // pathLabel.setText("Path: " + currentPath);
        String label = "Path: ";
        if (pathLen > 20) {
            int lastSleeveIndex = currentPath.lastIndexOf("/");
            int secondLastSleeveIndex = currentPath.substring(0, lastSleeveIndex).lastIndexOf("/");
            String truncated = "..." + currentPath.substring(currentPath.substring(0, secondLastSleeveIndex).lastIndexOf("/"));
            label += truncated;
        }
        else {
            label += currentPath;
        }
        pathLabel.setText(label);
    }

    public String getPath() {
        return currentPath;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("<")) {
            if (prevPath != null) {
                    // System.out.println("success b");
                    nextPaths.add(currentPath);
                    currentPath = prevPath(currentPath);
                }
        }
        else {
            if (nextPaths.size() != 0) {
                    // System.out.println("success f");
                    prevPath = currentPath;
                    currentPath = nextPaths.pop();
                }
        }
        if (manageRoot != null) {
            manageRoot.setPath(currentPath);
        }
        else {
            // moverRoot.setPath(currentPath);
        }
        refresh();
    }

    public static void main(String[] args) {
        GUI.init();
        JFrame frame = new JFrame("boop");
        frame.add(new Navigator("arewi"));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


}