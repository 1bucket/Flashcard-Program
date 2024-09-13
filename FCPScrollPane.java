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

public class FCPScrollPane extends FCPPanel implements MouseWheelListener, ComponentListener{
    private int scrollOffset;
    private int xInset;
    private int yInset;
    private int compGap;
    private SpringLayout springLayout;
    private Color fillColor;
    private boolean locked;

    public FCPScrollPane() {
        super(Color.BLACK);
        setLayout(springLayout = new SpringLayout());
        locked = false;
        scrollOffset = 0;
        xInset = 10;
        yInset = 10;
        compGap = 15;
        fillColor = Color.BLACK;
        setOpaque(false);
        addMouseWheelListener(this);
    }

    public void toggleLock() {
        locked = !locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getXInset() {
        return xInset;
    }

    public int getYInset() {
        return yInset;
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
            // springLayout.putConstraint(SpringLayout.EAST, comp, -xInset, SpringLayout.EAST, this);
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

    public int getScrollAllowance() {
        int scrollAllowance = 2 * yInset;
        // System.out.println("_");
        for (Component comp : getComponents()) {
            scrollAllowance += comp.getPreferredSize().getHeight();
            // System.out.println(comp.getPreferredSize().getHeight());
        }
        // System.out.println("_");
        scrollAllowance += Math.max(0, (getComponentCount() - 1) * compGap);
        if (scrollAllowance > getHeight()){ 
            return scrollAllowance - getHeight() + (getComponentCount() > 1 ? 50 : 0);
        }
        else {
            return 0;
        }
        // scrollAllowance = Math.max(scrollAllowance - getHeight(), 0);
        // System.out.println("h" + getHeight());
    }

    public void updateScrollOffset() {
        int scrollAllowance = getScrollAllowance();
        // System.out.println(scrollAllowance);
        scrollOffset = Math.max(scrollOffset, -scrollAllowance);
        scrollOffset = Math.min(0, scrollOffset);
        adjustChildrenPositions();
        revalidate();
        repaint();
    }

    public void snapToBottom() {
        scrollOffset = -getScrollAllowance();
        adjustChildrenPositions();
        revalidate();
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollOffset -= (int) (2 * e.getUnitsToScroll());
        updateScrollOffset();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        updateScrollOffset();
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }
}