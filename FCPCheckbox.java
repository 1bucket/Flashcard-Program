import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Exception;

public class FCPCheckbox extends FCPButton implements ActionListener{
    public static final int NONE = -1;
    public static final int SLEEVE = 0;
    public static final int FC = 1;
    
    private int boxSideLength;
    private boolean selected;
    private Sleeve boundSleeve;
    private Flashcard boundFC;
    private int type;

    public FCPCheckbox(Sleeve sleeve) {
        this();
        boundSleeve = sleeve;
        type = SLEEVE;
    }

    public FCPCheckbox(Flashcard fc) {
        this();
        boundFC = fc;
        type = FC;
    }

    public FCPCheckbox() {
        super("", FCPButton.SMALL, Color.RED, GUI.fcButtonFill(), GUI.fcButtonBorder());
        type = NONE;
        boxSideLength = 15;
        selected = false;
        setSquishBordered(false);
        setButtonWidth(2 * GUI.textBuffer() + boxSideLength);
        setButtonHeight(getButtonWidth());
        setPreferredSize(new Dimension(getButtonWidth(), getButtonHeight()));
        addActionListener(this);
    }

    public boolean isSelected() {
        return selected;
    }

    public void toggleSelect() {
        selected = !selected;
        revalidate();
        repaint();
    }

    public Sleeve getBoundSleeve() throws Exception {
        if (type != SLEEVE) {
            throw new Exception("Error: Checkbox not bound to sleeve; checkbox type is " + type);
        }
        return boundSleeve;
    }

    public Flashcard getBoundFC() throws Exception {
        if (type != FC) {
            throw new Exception("Error: Checkbox not bound to flashcard; checkbox type is " + type);
        }
        return boundFC;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // g.setColor(getBorderColor());
        g.setColor(getFill());
        g.fillRoundRect(0, 0, getButtonWidth(), getButtonHeight(), GUI.buttonArcRadius(), GUI.buttonArcRadius());
        
        // g.fillRoundRect(getInset(), getInset(), 
        //                 getButtonWidth() - 2 * getInset(), getButtonHeight() - 2 * getInset(), 
        //                 GUI.buttonArcRadius(), GUI.buttonArcRadius());
        g.setColor(GUI.offsetBrightness(Color.BLACK, .05));
        g.fillRect(GUI.textBuffer(), GUI.textBuffer(), boxSideLength, boxSideLength);
        if (selected) {
            g.setColor(GUI.offsetBrightness(getFill(), 1));
            g.fillRect(GUI.textBuffer() + 1, GUI.textBuffer() + 1, boxSideLength - 2, boxSideLength - 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        toggleSelect();
    }
}