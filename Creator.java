import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JTextArea;

abstract class Creator {
    private String path;
    private Manage root;
    private boolean canClose;
    private JFrame frame;

    public Creator(String newPath, Manage newRoot) {
        path = newPath;
        root = newRoot;
        canClose = false;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    public JFrame getFrame() {
        return frame;
    }

    public String getPath() {
        return path;
    }

    public Manage getRoot() {
        return root;
    }

    public void setCloseStatus(boolean newStatus) {
        canClose = newStatus;
    }

    public boolean canClose() {
        return canClose;
    }

    public void attemptDispose() {
        if (canClose()) getFrame().dispose();
    }
}