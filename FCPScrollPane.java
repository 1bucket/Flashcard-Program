import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class FCPScrollPane extends JPanel implements MouseWheelListener{
    private int scrollOffset;

    public FCPScrollPane() {
        super();
        scrollOffset = 0;
        addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        int[] xPositions = new int[]{0, getWidth(), 0};
        int[] yPositions = new int[]{0 + scrollOffset, 0 + scrollOffset, getHeight() + scrollOffset};
        g.fillPolygon(xPositions, yPositions, 3);
        g.setColor(Color.BLUE);
        xPositions = new int[]{getWidth(), getWidth(), 0};
        yPositions = new int[]{0 + scrollOffset, getHeight() + scrollOffset, getHeight() + scrollOffset};
        g.fillPolygon(xPositions, yPositions, 3);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollOffset -= (int) (1.5 * e.getUnitsToScroll());
        scrollOffset = Math.max(scrollOffset, 0);
        // System.out.println(scrollOffset);
        scrollOffset = Math.min(getHeight() - 50, scrollOffset);
        // System.out.println("woo " + scrollOffset);
        revalidate();
        repaint();
    }
}