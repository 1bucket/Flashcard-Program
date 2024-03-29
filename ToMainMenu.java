import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ToMainMenu implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        new MainMenu();
    }
}