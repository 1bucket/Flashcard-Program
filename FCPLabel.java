import java.awt.Color;

public class FCPLabel extends FCPButton{
    public FCPLabel(String textLabel, int size, Color textColor, Color fillColor, Color borderColor) {
        super(textLabel, size, textColor, fillColor, borderColor);
        setEnabled(false);
        setSquishBordered(false);
    }

    public FCPLabel(String textLabel, int size, Color textColor) {
        this(textLabel, size, textColor, Color.RED, Color.RED);
        setTransparent(true);
    }

    public void append(char c) {
        setText(getTextLabel() + c);
        System.out.println(getTextLabel());
        resize();
    }

    public void backspace() {
        setText(getTextLabel().substring(0, getTextLabel().length() - 1));
        resize();
    }
}