import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

@SuppressWarnings("unchecked")
public class Navigator extends JPanel implements ActionListener{ 
    private String prevPath;
    private String currentPath;
    private Stack<String> nextPaths;

    private JLabel pathLabel;

    private JButton prevSleeve;
    private JButton nextSleeve;

    private Manage manageRoot;
    private Mover moverRoot;

    public Navigator(String path, Manage newRoot) {
        this(path);
        manageRoot = newRoot;
    }

    public Navigator(String path, Mover newRoot) {
        this(path);
        moverRoot = newRoot;
    }

    public Navigator(String path) {
        setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();

        prevPath = null;
        currentPath = path;
        nextPaths = new Stack();

        constrs.gridx = 0;
        constrs.gridy = 0;
        constrs.gridwidth = 2;
        pathLabel = new JLabel("Path: " + currentPath);
        add(pathLabel, constrs);
        constrs.gridy = 1;
        constrs.gridwidth = 1;
        prevSleeve = new JButton("<");
        prevSleeve.addActionListener(this);
        add(prevSleeve, constrs);
        constrs.gridx = 1;
        nextSleeve = new JButton(">");
        nextSleeve.addActionListener(this);
        add(nextSleeve, constrs);
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
        pathLabel.setText("Path: " + currentPath);
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
            moverRoot.setPath(currentPath);
        }
        refresh();
    }


}