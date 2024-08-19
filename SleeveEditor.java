import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Dimension;

public class SleeveEditor {
    Sleeve sleeve;
    SpringLayout springL;

    public SleeveEditor(Sleeve sleeve) {
        this.sleeve = sleeve;
        JFrame frame = new JFrame("Edit Sleeve");
        JPanel masterPanel = new JPanel();
        springL = new SpringLayout();
        masterPanel.setLayout(springL);
        frame.setContentPane(masterPanel);

        

        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SleeveEditor(new Sleeve());
    }
}