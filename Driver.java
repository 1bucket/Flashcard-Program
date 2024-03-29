import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

public class Driver {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Page.setFrame(frame);
        new MainMenu();
        initFrame(frame);
    }

    private static void initFrame(JFrame frame) {
        frame.setTitle("Bucket's Flashcards");
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}