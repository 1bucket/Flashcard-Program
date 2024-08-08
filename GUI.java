import java.awt.Color;
import java.awt.Graphics;
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
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {
    // public static final int SMALL_BUTTON = 0;
    // public static final int MEDIUM = 1;

    // current vision: colors ascend in brightness
    // button colors
    private static Color primary;
    private static Color secondary;
    // background/other colors
    private static Color tertiary;
    private static Color quaternary;
    private static Color quinary;
    private static Color senary;
    private static Color septenary;
    private static Color octonary;

    private static Color fcButtonFill;
    private static Color fcButtonBorder;

    private static int smallButtonInset;
    private static int mediumButtonInset;
    private static int maxLineWidthSmall;
    private static int maxLineWidthMed;
    private static Font font;
    private static Color textColor;
    private static int smallFontSize;
    private static int mediumFontSize;

    private static int buttonArcRadius;
    // private static int buttonBorderThickness;
    private static int textBuffer;

    public static void init() {
        primary = new Color(10, 10, 10);
        secondary = offsetBrightness(new Color(99, 36, 156), 0.3);
        tertiary = new Color(240, 240, 240);
        fcButtonFill = GUI.offsetBrightness(GUI.secondary(), -.2);
        fcButtonBorder = GUI.secondary();


        // primary = Color.RED;
        // secondary = Color.BLUE;
        // tertiary = Color.GREEN;
        // quaternary = Color.CYAN;
        // quinary = Color.PINK;
        // senary = Color.ORANGE;
        // septenary = Color.YELLOW;
        // octonary = Color.MAGENTA;

        // first custom theme
        // primary = new Color(218, 179, 255);
        // secondary = new Color(99, 36, 156);
        // tertiary = new Color(166, 121, 208);
        // quaternary = new Color(192, 125, 255);
        // quinary = new Color(178, 123, 228);
        // senary = new Color(194, 152, 232);
        // septenary = new Color(118, 66, 166);

        mediumButtonInset = 8;
        smallButtonInset = 6;
        maxLineWidthSmall = 100;
        maxLineWidthMed = 200;
        // for (Font font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
        //     System.out.println(font);
        // }
        // font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
        font = new Font("Serif", Font.PLAIN, 40);
        textColor = Color.BLACK;
        smallFontSize = 20;
        mediumFontSize = 40;

        buttonArcRadius = 15;
        // buttonBorderThickness = 10;
        textBuffer = 7;
    }

    public static Color primary() {
        return primary;
    }

    public static Color secondary() {
        return secondary;
    }

    public static Color tertiary() {
        return tertiary;
    }

    public static Color quaternary() {
        return quaternary;
    }

    public static Color quinary() {
        return quinary;
    }

    public static Color senary() {
        return senary;
    }

    public static Color septenary() {
        return septenary;
    }

    public static Color octonary() {
        return octonary;
    }

    public static int smallButtonInset() {
        return smallButtonInset;
    }

    public static int mediumButtonInset() {
        return mediumButtonInset;
    }

    public static int maxLineWidthSmall() {
        return maxLineWidthSmall;
    }

    public static int maxLineWidthMed() {
        return maxLineWidthMed;
    }

    public static Font font() {
        return font;
    }

    public static int smallFontSize() {
        return smallFontSize;
    }
    
    public static int mediumFontSize() {
        return mediumFontSize;
    }

    public static Color textColor() {
        return textColor;
    }

    public static int buttonArcRadius() {
        return buttonArcRadius;
    }

    public static Color fcButtonFill() {
        return fcButtonFill;
    }

    public static Color fcButtonBorder() {
        return fcButtonBorder;
    }
    /*
    public static int buttonBorderThickness() {
        return buttonBorderThickness;
    }
    */

    public static Color offsetBrightness(Color color, double doubleOffset) {
        float offset = (float) doubleOffset;
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        if (offset > 0) {
            hsb[2] = (float) Math.min(hsb[2] + doubleOffset, 1);
        }
        else {
            hsb[2] = (float) Math.max(hsb[2] + doubleOffset, 0);
        }
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static int textBuffer() {
        return textBuffer;
    }

    public static void main(String[] args) {
        init();


        JFrame frame = new JFrame("test");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        FCPBackground testPanel = new FCPBackground();
        SpringLayout springLayout = new SpringLayout();
        testPanel.setLayout(springLayout);
        // testPanel.setLayout(null);
        // testPanel.setLayout(new GridBagLayout());
        // GridBagConstraints constrs = new GridBagConstraints();
        // constrs.gridx = 0;
        // constrs.gridy = 0;
        // constrs.fill = GridBagConstraints.HORIZONTAL;
        // constrs.weightx = 1;
        // constrs.weighty = 1;
        // constrs.gr
        // JButton testButton = getButton("yippeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", SMALL_BUTTON);
        FCPButton testButton = new FCPButton("YIPPEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE", FCPButton.SMALL, Color.WHITE, offsetBrightness(secondary, .01), secondary);
        // testButton.toggleCheckbox();
        // testButton = new JButton("yippeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        // testButton.setMinimumSize(new Dimension(100, 75));
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println(testButton.getHeight());
                // System.exit(0);
                System.out.println("activated");
            }
        });
        FCPLabel medButton = new FCPLabel("woohoo", FCPLabel.MEDIUM, Color.WHITE, offsetBrightness(secondary, .01), secondary);
        medButton.setTransparent(true);
        // testButton.setEnabled(false);
        testPanel.add(testButton);
        testPanel.add(medButton);
        FCPScrollPane testScroll = new FCPScrollPane();
        testScroll.setPreferredSize(new Dimension(440 + 2 * textBuffer, 600));
        FCPPanel testFCPPanel = new FCPPanel(Color.BLACK);
        testFCPPanel.add(testScroll);
        testPanel.add(testFCPPanel);
        int middle = (int) dim.getWidth() / 2;
        // System.out.println(testButton.getWidth());
        springLayout.putConstraint(SpringLayout.NORTH, testButton, 15, SpringLayout.NORTH, testPanel);
        springLayout.putConstraint(SpringLayout.WEST, testButton, middle - testButton.getButtonWidth() / 2, SpringLayout.WEST, testPanel);
        springLayout.putConstraint(SpringLayout.NORTH, medButton, 15, SpringLayout.SOUTH, testButton);
        springLayout.putConstraint(SpringLayout.WEST, medButton, middle - medButton.getButtonWidth() / 2, SpringLayout.WEST, testPanel);
        springLayout.putConstraint(SpringLayout.NORTH, testFCPPanel, 15, SpringLayout.SOUTH, medButton);
        springLayout.putConstraint(SpringLayout.WEST, testFCPPanel, 15, SpringLayout.WEST, testPanel);
        // testPanel.add(testButton, constrs);
        // System.out.println("bangah " + testButton.getWidth() + " " + testButton.getHeight());
        frame.setContentPane(testPanel);
        // System.out.println(testPanel.getComponentCount());
        // System.out.println(testButton + ", " + medButton + ", " + testScroll + "|");
        // for (Component comp : testPanel.getComponents()) {
        //     System.out.println(comp);
        // }
        for (int index = 0; index < 5; index += 1) {
            FCPButton button = new FCPButton("woo", FCPButton.SMALL, Color.WHITE, offsetBrightness(secondary, .01), secondary);
            // testScroll.add(button);
            if (index == 3) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // button.setPreferredSize(new Dimension((int) button.getPreferredSize().getWidth() * 2, (int) button.getPreferredSize().getHeight() * 2));
                        testPanel.darken();
                    }
                });
            }
        }
        Flashcard fc = new Flashcard("", "bam", "pow", "images/0000.png", "images/0000.png");
        // System.out.println(fc.equals(new Flashcard("", "bam", "pow", "images/0000.png", "images/0000.png")));
        // ArrayList<Flashcard> a = new ArrayList<Flashcard>();
        // a.add(fc);
        // System.out.println(a.indexOf(new Flashcard("", "bam", "pow", "images/0000.png", "images/0000.png")));
        // System.out.println(fc);
        testScroll.add(new FCButton(fc));
        testScroll.add(new SleeveButton(new Sleeve("kablam", "boom")));
        testScroll.add(new SleeveIcon(offsetBrightness(secondary, -.2)));
        testScroll.add(new FCPCheckbox());
        
        testScroll.adjustChildrenPositions();
        frame.setPreferredSize(dim);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}