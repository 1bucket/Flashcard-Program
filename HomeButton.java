import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeButton extends JButton {
    public HomeButton() {
        super("Home");
        addActionListener(new ToMainMenu());
    }
}