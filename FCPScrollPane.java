import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.SpringLayout;
import java.awt.Component;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

public class FCPScrollPane extends JPanel implements MouseWheelListener, ComponentListener{
    private int scrollOffset;
    private int xInset;
    private int yInset;
    private int compGap;
    private SpringLayout springLayout;
    private Color fillColor;
    private boolean locked;

    public FCPScrollPane() {
        super();
        setLayout(springLayout = new SpringLayout());
        locked = false;
        scrollOffset = 0;
        xInset = 10;
        yInset = 20;
        compGap = 15;
        fillColor = Color.BLACK;
        addMouseWheelListener(this);
    }

    public void toggleLock() {
        locked = !locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public Color getFill() {
        return fillColor;
    }

    public void setFill(Color newFill) {
        fillColor = newFill;
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
    public Component add(Component comp) {
        if (locked) {
            System.out.println("Error: Scroll pane is locked");
            System.exit(0);
        }
        comp.addComponentListener(this);
        return super.add(comp);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // super.paintComponent(g);
        g.setColor(fillColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        // paintChildren(g);
        // g.setColor(Color.RED);
        // int[] xPositions = new int[]{0, getWidth(), 0};
        // int[] yPositions = new int[]{0 + scrollOffset, 0 + scrollOffset, getHeight() + scrollOffset};
        // g.fillPolygon(xPositions, yPositions, 3);
        // g.setColor(Color.BLUE);
        // xPositions = new int[]{getWidth(), getWidth(), 0};
        // yPositions = new int[]{0 + scrollOffset, getHeight() + scrollOffset, getHeight() + scrollOffset};
        // g.fillPolygon(xPositions, yPositions, 3);
    }

    public void updateScrollAllowance() {
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollOffset -= (int) (2 * e.getUnitsToScroll());
        updateScrollAllowance();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        updateScrollAllowance();
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }
}