import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Dimension;

public class SleeveIcon extends JComponent{
    private Color backColor;
    private int arcRad;
    private double scale;
    private int width, height;
    private int scaledWidth, scaledHeight, scaledArcRad;
    private double brightnessToggle;

    public SleeveIcon(Color back) {
        backColor = back;
        width = 200;
        height = 100;
        arcRad = 20;
        brightnessToggle = 1;
        scale(1);
    }

    public int getHeight() {
        return height;
    }

    public void scale(double scale) {
        this.scale = scale;
        scaledWidth = (int) (width * scale);
        scaledHeight = (int) (height * scale);
        scaledArcRad = (int) (arcRad * scale);
        setPreferredSize(new Dimension(scaledWidth, scaledHeight));
    }

    public void flipBrightness() {
        brightnessToggle *= -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // System.out.println("bo");
        g.setColor(backColor);
        g.fillRoundRect(0, 0, scaledWidth, scaledHeight, scaledArcRad, scaledArcRad);
        g.setColor(GUI.offsetBrightness(backColor, .1 * brightnessToggle));
        g.fillRoundRect(0, 0, scaledWidth * 2 / 5, scaledHeight, scaledArcRad, scaledArcRad);
        g.fillRoundRect(0, scaledHeight / 5, scaledWidth, scaledHeight * 4 / 5, scaledArcRad, scaledArcRad);
        g.fillRoundRect(0, scaledHeight / 10, scaledWidth * 4 / 5, scaledHeight / 10, scaledArcRad, scaledArcRad);
        g.setColor(backColor);
        g.fillRoundRect(scaledWidth * 2 / 5, 0, scaledWidth * 3 / 5, scaledHeight / 5, scaledArcRad, scaledArcRad);
    }
}