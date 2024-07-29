import java.awt.Color;

public class FCPLabel extends FCPButton{
    public FCPLabel(String textLabel, int size, Color textColor, Color fillColor, Color borderColor) {
        super(textLabel, size, textColor, fillColor, borderColor);
        setEnabled(false);
        setSquishBordered(false);
    }

    public FCPLabel(String textLabel, int size, Color textColor) {
        this(textLabel, size, textColor, null, null);
        setTransparent(true);
    }
}