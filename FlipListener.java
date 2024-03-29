import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FlipListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Page study = Page.getCurPage();
        study.flip();
    }
}