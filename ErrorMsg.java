import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JTextArea;

public class ErrorMsg {

    public ErrorMsg(String errorMsg) {
        JFrame errorFrame = new JFrame();
        errorFrame.setTitle("Error");
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setPreferredSize(new Dimension(350, 100));
        Container masterPanel = errorFrame.getContentPane();
        masterPanel.setLayout(new GridBagLayout());
        GridBagConstraints constrs = new GridBagConstraints();
        constrs.gridx = 0;
        constrs.gridy = 0;
        JTextArea msg = new JTextArea(errorMsg);
        msg.setEditable(false);
        msg.setLineWrap(true);
        msg.setPreferredSize(new Dimension(300, 30));
        masterPanel.add(msg, constrs);

        errorFrame.pack();
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setVisible(true);
        
    }
    
}