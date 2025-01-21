import javax.swing.SpringLayout;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FCEditor {

    private Flashcard fc;
    SpringLayout springL;
    EditorListener listener;

    public FCEditor(Flashcard targetFC) {
        fc = targetFC;
        
        Dimension editorDim = new Dimension(1000, 750);
        JFrame frame = new JFrame("Edit Flashcard");
        frame.setPreferredSize(editorDim);
        FCPPanel masterPanel = new FCPPanel(Color.BLACK);
        masterPanel.unrounden();
        masterPanel.setPreferredSize(editorDim);
        springL = new SpringLayout();
        masterPanel.setLayout(springL);
        frame.setContentPane(masterPanel);

        int leftMargin = 20, rightMargin = -20, topMargin = 20, bottomMargin = -20;
        int gap = 10;
        Dimension fieldDim = new Dimension(470, 300);
        FCPLabel promptLabel = new FCPLabel("Prompt", FCPLabel.SMALL, Color.WHITE);
        masterPanel.add(promptLabel);
        springL.putConstraint(SpringLayout.WEST, promptLabel, leftMargin, SpringLayout.WEST, masterPanel);
        springL.putConstraint(SpringLayout.NORTH, promptLabel, topMargin, SpringLayout.NORTH, masterPanel);
        FCPPanel promptField = createTextField(fc.getPrompt(), fieldDim);
        masterPanel.add(promptField);
        springL.putConstraint(SpringLayout.WEST, promptField, leftMargin, SpringLayout.WEST, masterPanel);
        springL.putConstraint(SpringLayout.NORTH, promptField, gap, SpringLayout.SOUTH, promptLabel);
        FCPLabel respLabel = new FCPLabel("Response", FCPLabel.SMALL, Color.WHITE);
        masterPanel.add(respLabel);
        springL.putConstraint(SpringLayout.WEST, respLabel, 20, SpringLayout.EAST, promptField);
        springL.putConstraint(SpringLayout.NORTH, respLabel, topMargin, SpringLayout.NORTH, masterPanel);
        FCPPanel respField = createTextField(fc.getResponse(), fieldDim);
        masterPanel.add(respField);
        springL.putConstraint(SpringLayout.EAST, respField, rightMargin, SpringLayout.EAST, masterPanel);
        springL.putConstraint(SpringLayout.NORTH, respField, gap, SpringLayout.SOUTH, respLabel);


        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);        
        frame.setVisible(true);
    }

    private FCPPanel createTextField(String fieldContent, Dimension size) {
        FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.4));
        tray.unrounden();
        tray.setPreferredSize(size);
        SpringLayout trayLayout = new SpringLayout();
        tray.setLayout(trayLayout);
        FCPTextArea content = new FCPTextArea(fieldContent);
        content.unrounden();
        // don't snap to bottom
        size.setSize(size.getWidth() - 2, size.getHeight() - 100);
        content.setPreferredSize(size);
        tray.add(content);
        trayLayout.putConstraint(SpringLayout.WEST, content, 2, SpringLayout.WEST, tray);
        return tray;
    }

    public void setEditorListener(EditorListener newListener) {
        listener = newListener;
    }

    public static void main(String[] args) {
        GUI.init();
        new FCEditor(new Flashcard(null, null, null));
    }
}