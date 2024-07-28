import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.SpringLayout;
import java.awt.Component;

public class FCPScrollPane extends JPanel implements MouseWheelListener{
    private int scrollOffset;
    private int xInset;
    private int yInset;
    private int compGap;
    private SpringLayout springLayout;

    public FCPScrollPane() {
        super();
        setLayout(springLayout = new SpringLayout());
        scrollOffset = 0;
        xInset = 20;
        yInset = 20;
        compGap = 15;
        addMouseWheelListener(this);
    }

    public void adjustChildrenPositions() {
        Component[] comps = getComponents();
        Component comp;
        for (int index = 0; index < comps.length; index += 1) {
            comp = comps[index];
            springLayout.putConstraint(SpringLayout.WEST, comp, xInset, SpringLayout.WEST, this);
            if (index == 0) {
                springLayout.putConstraint(SpringLayout.NORTH, comp, yInset + scrollOffset, SpringLayout.NORTH, this);
            }
            else {
                springLayout.putConstraint(SpringLayout.NORTH, comp, compGap, SpringLayout.SOUTH, comps[index - 1]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // super.paintComponent(g);
        g.setColor(GUI.secondary());
        g.fillRect(0, 0, getWidth(), getHeight());
        paintChildren(g);
        // g.setColor(Color.RED);
        // int[] xPositions = new int[]{0, getWidth(), 0};
        // int[] yPositions = new int[]{0 + scrollOffset, 0 + scrollOffset, getHeight() + scrollOffset};
        // g.fillPolygon(xPositions, yPositions, 3);
        // g.setColor(Color.BLUE);
        // xPositions = new int[]{getWidth(), getWidth(), 0};
        // yPositions = new int[]{0 + scrollOffset, getHeight() + scrollOffset, getHeight() + scrollOffset};
        // g.fillPolygon(xPositions, yPositions, 3);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollOffset -= (int) (1.5 * e.getUnitsToScroll());
        int scrollAllowance = 2 * yInset;
        for (Component comp : getComponents()) {
            scrollAllowance += comp.getPreferredSize().getHeight();
        }
        scrollAllowance += Math.max(0, (getComponentCount() - 1) * compGap);
        scrollAllowance = Math.max(scrollAllowance - getHeight(), 0);
        scrollOffset = Math.max(scrollOffset, -scrollAllowance);
        // System.out.println(scrollAllowance);
        // System.out.println(scrollOffset);
        scrollOffset = Math.min(0, scrollOffset);
        // System.out.println("woo " + scrollOffset);
        adjustChildrenPositions();
        revalidate();
        repaint();
    }
}