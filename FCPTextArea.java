import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class FCPTextArea extends FCPScrollPane implements KeyListener{
    private ArrayList<String> lines;
    private FCPLabel textDisp;

    public FCPTextArea(String label) {
        super();
        add(textDisp = new FCPLabel(label, FCPLabel.SMALL, Color.WHITE));
        toggleLock();
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        textDisp.setButtonWidth((int) preferredSize.getWidth());
        textDisp.resize();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("clack");
        // System.out.println(e.getKeyChar());
        // b += e.getKeyChar();
        // System.out.println(b);
        // System.out.println(e.getKeyCode());
        if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
            textDisp.backspace();
            // System.out.println("back");
        }
        else {
            textDisp.append(e.getKeyChar());
        }
        textDisp.resize();
        // System.out.println(e.getKeyChar());
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        GUI.init();
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(600, 400));
        JPanel master = new JPanel();
        frame.setContentPane(master);
        FCPTextArea box = new FCPTextArea("askdghasg uaj");
        master.add(box);
        box.setPreferredSize(new Dimension(600, 400));


        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}