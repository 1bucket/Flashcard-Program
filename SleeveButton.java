import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Dimension;

public class SleeveButton extends FCPButton{
    private Sleeve sleeve;
    private SleeveIcon sleeveIcon;

    public SleeveButton(Sleeve sleeve) {
        super(sleeve.getName(), FCPButton.SMALL, Color.WHITE, GUI.offsetBrightness(GUI.secondary(), -.2), GUI.secondary());
        sleeveIcon = new SleeveIcon(getFill());
        add(sleeveIcon);
        sleeveIcon.setLocation(GUI.textBuffer(), GUI.textBuffer());
        this.sleeve = sleeve;
        resize();
        setSquishBordered(false);
    }

    @Override
    public void resize() {
        super.resize();
        if (sleeveIcon != null) {  // bandaid fix for call when init
            // System.out.println("scale!");
            sleeveIcon.scale((double) (getButtonHeight() - 2 * GUI.textBuffer()) / sleeveIcon.getHeight());
            setButtonWidth(getButtonWidth() + sleeveIcon.getScaledWidth() + GUI.textBuffer());
            setPreferredSize(new Dimension(getButtonWidth(), getButtonHeight()));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getFill());
        g.fillRoundRect(0, 0, getButtonWidth(), getButtonHeight(), GUI.buttonArcRadius(), GUI.buttonArcRadius());
        // new ImageIcon("Sleeve Logo.png").paintIcon(null, g, GUI.textBuffer(), GUI.textBuffer());
        // g.setColor(GUI.offsetBrightness(getFill(), .1));
        int lineHeight = getFontMetrics(getFont()).getHeight();
        paintChildren(g);
        // g.fillRoundRect(GUI.textBuffer(), GUI.textBuffer(), 10, lineHeight, 4, 4);
        // g.fillRoundRect(GUI.textBuffer() + 10, GUI.textBuffer() + lineHeight / 3, 10, lineHeight * 2 / 3, 4, 4);
        // g.fillRoundRect(GUI.textBuffer(), GUI.textBuffer() + lineHeight / 3 + 1, 10, lineHeight * 2 / 3 - 1, 4, 4);
        // g.fillRoundRect(GUI.textBuffer() + 6, GUI.textBuffer() + lineHeight / 3 - 1, 3, 2, 4, 4);
        // g.setColor(getFill());
        // g.fillRoundRect(GUI.textBuffer() + 10, GUI.textBuffer(), 10, lineHeight / 3, 4, 4);
        g.setColor(getTextColor());
        g.drawString(sleeve.getName(), 
                        2 * GUI.textBuffer() + sleeveIcon.getScaledWidth(), 
                        lineHeight + GUI.textBuffer());
    }
}