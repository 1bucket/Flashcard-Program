import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToManage implements ActionListener {
    private FCStack studyStack;

    public ToManage() {
        this(new FCStack());
    }

    public ToManage(FCStack curStack) {
        studyStack = curStack;
    }

    public void actionPerformed(ActionEvent e) {
        if (studyStack.isJeopardized()) studyStack.jeopardize();
        new Manage(studyStack);
    }
}