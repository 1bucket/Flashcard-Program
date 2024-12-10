import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Insets;
import javax.swing.SpringLayout;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

public class FCPTextArea extends FCPScrollPane implements ComponentListener, KeyListener{
    private JTextArea textDisp;
    private String cappedText;
    private int textLimit;
    private boolean capped;

    public FCPTextArea() {
        this("");
    }

    public FCPTextArea(String label) {
        super();
        // add(textDisp = new FCPLabel(label, FCPLabel.SMALL, Color.WHITE));
        add(textDisp = new JTextArea(label) {
            @Override
            public void paintComponent(Graphics g) {
                if (capped) {
                    setText(cappedText);
                }
                super.paintComponent(g);
            }
        });
        adjustChildrenPositions();
        ((SpringLayout) getLayout()).putConstraint(SpringLayout.EAST, textDisp, -getXInset(), SpringLayout.EAST, this);
        textDisp.setCaretPosition(textDisp.getText().length());
        textDisp.setMargin(new Insets(GUI.textBuffer(), GUI.textBuffer(), GUI.textBuffer(), GUI.textBuffer()));
        textDisp.setLineWrap(true);
        textDisp.setWrapStyleWord(true);
        textDisp.setFont(GUI.font().deriveFont((float) GUI.smallFontSize()));
        textDisp.setForeground(Color.WHITE);
        textDisp.setSelectionColor(GUI.offsetBrightness(GUI.secondary(), -.2));
        // textDisp.setSelectionTextColor(GUI.offsetBrightness(Color.WHITE, -.1));
        textDisp.setCaretColor(GUI.offsetBrightness(GUI.secondary(), -.1));
        textDisp.setBackground(Color.BLACK);
        textDisp.addComponentListener(this);
        textDisp.addKeyListener(this);
        toggleLock();
        textLimit = -1;
        cappedText = label;
        capped = false;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        textDisp.setPreferredSize(preferredSize);
    }

    public void setTextLimit(int charLim) {
        textLimit = charLim;
    }

    public void resetTextLimit() {
        textLimit = -1;
    }

    public int getTextLim() {
        return textLimit;
    }

    public String getText() {
        return textDisp.getText();
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("type");
        // System.out.println(textLimit);
        capped = ! (textLimit > -1 && textDisp.getText().length() <= textLimit);
        if (!capped) {
            // System.out.println("hep");
            cappedText = textDisp.getText() + String.valueOf(e.getKeyChar());  
            snapToBottom();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    public static void main(String[] args) {
        GUI.init();
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(600, 400));
        JPanel master = new JPanel();
        frame.setContentPane(master);
        FCPTextArea box = new FCPTextArea("");
        box.setTextLimit(15);
        System.out.println(box.getTextLim());
        master.add(box);
        box.setPreferredSize(new Dimension(200, 100));


        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}