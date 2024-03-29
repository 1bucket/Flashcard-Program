import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToStudy implements ActionListener {
    private FCStack studyStack;
    private boolean isJeopardized;

    public ToStudy(FCStack newStack) {
        studyStack = newStack;
        // this.isJeopardyMode = isJeopardyMode;
    }

    public ToStudy() {
        this(Manage.genMasterStack().genRandStack(10));
    }
    public void actionPerformed(ActionEvent e) {
        // FCStack demoStack = Manage.genMasterStack();
        // demoStack.getStack().add(new Flashcard("vocab", "biblioteca", "library"));
        // demoStack.getStack().add(new Flashcard("vocab", "árbol", "tree"));
        // new Study(demoStack);
        // for (Flashcard fc : demoStack.getStack()) System.out.println(fc);
        // if (isJeopardized) studyStack.jeopardize();
        // System.out.println("study");
        new Study(studyStack);
    }
}