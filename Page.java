import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.Box;

abstract class Page {
    private static JFrame frame;
    private static JPanel masterPanel;
    private static Page currentPage;

    // getters
    public static JFrame getFrame() {
        return frame;
    }
    public static Container getMasterPanel() {
        return frame.getContentPane();
    }
    public static Page getCurPage() {
        return currentPage;
    }
    // setters
    public static void setFrame(JFrame newFrame) {
        frame = newFrame;
    }
    public static void setMasterPanel(JPanel newPanel) {
        frame.setContentPane(newPanel);
    }
    public static void setPage(Page newPage) {
        currentPage = newPage;
    }
}
