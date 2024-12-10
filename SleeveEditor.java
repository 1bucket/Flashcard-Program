import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SleeveEditor {
    Sleeve sleeve;
    SpringLayout springL;
    EditorListener listener;

    public SleeveEditor(Sleeve sleeve) {
        this.sleeve = sleeve;
        JFrame frame = new JFrame("Edit Sleeve");
        FCPPanel masterPanel = new FCPPanel(Color.BLACK);
        masterPanel.unrounden();
        Dimension panelDim = new Dimension(300, 200);
        masterPanel.setPreferredSize(panelDim);
        springL = new SpringLayout();
        masterPanel.setLayout(springL);
        frame.setContentPane(masterPanel);
        FCPLabel prompt = new FCPLabel("Sleeve Name:", FCPLabel.SMALL, Color.WHITE);
        masterPanel.add(prompt);
        FCPPanel tray = new FCPPanel(GUI.offsetBrightness(GUI.secondary(), -.4));
        tray.unrounden();
        SpringLayout trayL = new SpringLayout();
        tray.setLayout(trayL);
        FCPTextArea text = new FCPTextArea(sleeve.getName());
        text.unrounden();
        text.setTextLimit(25);
        tray.add(text);
        trayL.putConstraint(SpringLayout.WEST, text, 2, SpringLayout.WEST, tray);
        text.setPreferredSize(new Dimension((int) panelDim.getWidth() - 40, 50));
        tray.setPreferredSize(new Dimension((int) text.getPreferredSize().getWidth() + 2, (int) text.getPreferredSize().getHeight()));
        masterPanel.add(tray);
        FCPButton confirm = new FCPButton("Confirm", FCPButton.SMALL, Color.WHITE, Color.BLACK, GUI.offsetBrightness(GUI.secondary(), -.2));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sleeve.setName(text.getText());
                listener.editConfirmed();
                frame.dispose();
            }
        });
        masterPanel.add(confirm);

        springL.putConstraint(SpringLayout.NORTH, prompt, 20, SpringLayout.NORTH, masterPanel);
        springL.putConstraint(SpringLayout.WEST, prompt, ((int)panelDim.getWidth() - prompt.getButtonWidth()) / 2, SpringLayout.WEST, masterPanel);
        springL.putConstraint(SpringLayout.NORTH, tray, 3, SpringLayout.SOUTH, prompt);
        springL.putConstraint(SpringLayout.WEST, tray, 20, SpringLayout.WEST, masterPanel);
        springL.putConstraint(SpringLayout.NORTH, confirm, 3, SpringLayout.SOUTH, tray);
        springL.putConstraint(SpringLayout.WEST, confirm, ((int)panelDim.getWidth() - confirm.getButtonWidth()) / 2, SpringLayout.WEST, masterPanel);

        

        frame.setPreferredSize(panelDim);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setEditorListener(EditorListener newListener) {
        listener = newListener;
    }    

    public static void main(String[] args) {
        GUI.init();
        new SleeveEditor(new Sleeve());
    }
}