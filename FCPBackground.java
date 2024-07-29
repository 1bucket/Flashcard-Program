import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.FontMetrics;
import java.awt.Font;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GraphicsEnvironment;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.SpringLayout;

public class FCPBackground extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        // Graphics2D g2 = (Graphics2D) g;
        int height = getHeight();
        int width = getWidth();

        //bubbly background
        // g.setClip(-100, -100, width + 200, height + 200);
        // set background color
        g.setColor(GUI.offsetBrightness(GUI.primary(), -.06));
        g.fillRect(0, 0, width, height);

        // action 1
        g.setColor(GUI.offsetBrightness(GUI.primary(), -.03));
        g.fillArc(width / 3, height / 10, width, height, 90, 100);
        
        // action 3
        g.setColor(GUI.offsetBrightness(GUI.primary(), .02));
        g.fillArc(width * 2 / 3, height / - 2, width * 2 / 3, height, 180, 90);
        // action 4
        g.setColor(GUI.offsetBrightness(GUI.primary(), .08));
        g.fillArc(width / 10, height * 3 / 5, width * 4 / 5, height * 4 / 5, 0, 180);
        // action 5
        g.setColor(GUI.offsetBrightness(GUI.primary(), .12));
        g.fillOval(width * 3 / 7, height / 10, width / 2, width / 2);
        // action 6
        g.setColor(GUI.offsetBrightness(GUI.primary(), .081));
        g.fillArc(width * -2 / 5, height * -2 / 3, width * 4 / 5, height * 4 / 3, 270, 90);
        // action 0
        g.setColor(GUI.offsetBrightness(GUI.primary(), .054));
        // g.fillRect(0, 0, width, height);
        g.fillArc(width * 2 / 7, height * -2 / 5, width * 3 / 7, width * 3 / 7, 180, 180);
        // action 7
        g.setColor(GUI.offsetBrightness(GUI.primary(), -.045));
        g.fillArc(width / -3, height * 3 / 7, width * 2 / 3, width * 2 / 3, 270, 180);
        g.setColor(GUI.primary());
        g.fillOval(width / 20, height / 16, width * 3 / 7, width * 3 / 7);


        // action 2
        g.setColor(GUI.primary());
        g.fillOval(width * 5 / 7, height / 3, height * 6 / 7, height * 6 / 7);

    }
}