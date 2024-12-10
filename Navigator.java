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
public class Navigator extends FCPPanel implements ActionListener, SleeveButtonListener{ 
    private String prevPath;
    private String currentPath;
    private Stack<String> nextPaths;

    private FCPLabel pathLabel;

    private FCPButton prevSleeve;
    private FCPButton nextSleeve;

    // private Manage manageRoot;

    private NavigatorListener tracker;
    // private Mover moverRoot;
    /*
    public Navigator(String path, Manage newRoot) {
        this(path);
        manageRoot = newRoot;
    }
    
    public Navigator(String path, Mover newRoot) {
        this(path);
        moverRoot = newRoot;
    }
    */

    public Navigator(String path, NavigatorListener listener) {
        super(Color.BLACK);
        setOpaque(false);
        tracker = listener;
        setPreferredSize(new Dimension(300, 90));
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        prevPath = null;
        currentPath = path;
        nextPaths = new Stack();
        pathLabel = new FCPLabel("Path: " + currentPath, FCPLabel.SMALL, Color.WHITE);
        pathLabel.setButtonWidth(290);
        // pathLabel.setTransparent(false);
        add(pathLabel);
        prevSleeve = new FCPButton("<", FCPButton.SMALL, Color.WHITE, Color.BLACK, Color.RED);
        prevSleeve.setButtonWidth(35);
        prevSleeve.setHoverEffect(FCPButton.BRIGHTEN);
        prevSleeve.setSquishBordered(false);
        // prevSleeve.setTransparent(true);
        // System.out.println(getFontMetrics(prevSleeve.getFont()).stringWidth("<"));
        prevSleeve.addActionListener(this);
        add(prevSleeve);
        nextSleeve = new FCPButton(">", FCPButton.SMALL, Color.WHITE, Color.BLACK, Color.WHITE);
        // nextSleeve.setTransparent(true);
        nextSleeve.setButtonWidth(35);
        nextSleeve.setHoverEffect(FCPButton.BRIGHTEN);
        nextSleeve.setSquishBordered(false);
        nextSleeve.addActionListener(this);
        add(nextSleeve);

        // springLayout.putConstraint(SpringLayout.WEST, pathLabel, ((int)getPreferredSize().getWidth() - pathLabel.getButtonWidth()) / 2, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.WEST, pathLabel, 5, SpringLayout.WEST, this);

        springLayout.putConstraint(SpringLayout.NORTH, prevSleeve, 5, SpringLayout.SOUTH, pathLabel);
        springLayout.putConstraint(SpringLayout.WEST, prevSleeve, (int)getPreferredSize().getWidth() / 2 - prevSleeve.getButtonWidth() - 5, SpringLayout.WEST, this);

        springLayout.putConstraint(SpringLayout.NORTH, nextSleeve, 5, SpringLayout.SOUTH, pathLabel);
        springLayout.putConstraint(SpringLayout.WEST, nextSleeve, (int)getPreferredSize().getWidth() / 2 + 5, SpringLayout.WEST, this);
        refresh();
    }

    //

    public void setPath(String newPath) {
        prevPath = currentPath;
        currentPath = newPath;
        if (! currentPath.equals(nextPaths.peek())) {
            nextPaths = new Stack<String>();
        }
        tracker.pathUpdateReaction(currentPath);
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
        // System.out.println(label);
        revalidate();
        repaint();
    }

    public String getPath() {
        return currentPath;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("<")) {
            if (prevPath != null) {
                // System.out.println("success b");
                nextPaths.add(currentPath);
                currentPath = prevPath(currentPath);
                tracker.pathUpdateReaction(currentPath);
            }
        }
        else {
            if (nextPaths.size() != 0) {
                // System.out.println("success f");
                prevPath = currentPath;
                currentPath = nextPaths.pop();
                tracker.pathUpdateReaction(currentPath);
            }
        }
        /*
        if (manageRoot != null) {
            manageRoot.setPath(currentPath);
        }
        else {
            // moverRoot.setPath(currentPath);
        }
        */
        refresh();
    }

    @Override
    public void pathChange(String newPath) {
        setPath(newPath);
    }

    public static void main(String[] args) {
        GUI.init();
        JFrame frame = new JFrame("boop");
        frame.add(new Navigator("arewi", null));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


}