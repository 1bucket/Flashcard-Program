import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class FCPPanel extends JPanel {
    private double darkenOffset;
    private Color fill;
    private boolean round;
    private boolean transparent;

    public FCPPanel(Color fill) {
        super();
        transparent = false;
        this.fill = fill;
        darkenOffset = 0;
        round = true;
    }

    public void hide() {
        transparent = true;
    }

    public void reveal() {
        transparent = false;
    }

    public void rounden() {
        round = true;
    }

    public void unrounden() {
        round = false;
    }  

    public void darken() {
        darkenOffset = -.1;
        revalidate();
        repaint();
    }

    public void undarken() {
        darkenOffset = 0;
        revalidate();
        repaint();
    }

    public double getDarkenOffset() {
        return darkenOffset;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (! transparent) {
            g.setColor(fill);
            if (round) {
                g.fillRoundRect(0, 0, getWidth(), getHeight(), GUI.buttonArcRadius(), GUI.buttonArcRadius());
            }
            else {
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
        // g.setColor(Color.RED);
        // g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }
}