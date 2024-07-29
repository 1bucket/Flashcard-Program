import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SpringLayout;
import java.awt.Point;

public class MainMenu extends Page {
    public MainMenu() {
        Page.setPage(this);
        getMasterPanel().setVisible(false);

        // Master Panel
        // JPanel master = new JPanel();
        FCPBackground master = new FCPBackground();
        
        // master.setLayout(new BoxLayout(master, BoxLayout.PAGE_AXIS));
        SpringLayout springLayout = new SpringLayout();
        master.setLayout(springLayout);

        // Buttons
        // JButton quit = new JButton("Quit");
        Color buttonFill = GUI.offsetBrightness(GUI.secondary(), -.9);
        FCPButton quit = new FCPButton("Quit", FCPButton.SMALL, Color.WHITE, buttonFill, GUI.offsetBrightness(GUI.secondary(), -.2));
        quit.addActionListener(new Quit());

        // JButton manage = new JButton("Manage Flashcards");
        FCPButton manage = new FCPButton("Manage Flashcards", FCPButton.SMALL, Color.WHITE, buttonFill, GUI.offsetBrightness(GUI.secondary(), -.2));
        manage.addActionListener(new ToManage());

        // JButton start = new JButton("Start studying");
        FCPButton start = new FCPButton("Quick Study", FCPButton.SMALL, Color.WHITE, buttonFill, GUI.offsetBrightness(GUI.secondary(), -.2));
        start.addActionListener(new ToStudy());

        // Labels
        // JLabel title = new JLabel("Flashcards");
        FCPLabel title = new FCPLabel("Flashcards", FCPLabel.MEDIUM, GUI.secondary(), GUI.offsetBrightness(GUI.secondary(), -.1), GUI.secondary());
        title.setFont(new Font("Comic Sans", Font.PLAIN, 40));
        title.setTransparent(true);
        // JLabel subtitle = new JLabel("A simple Java program!");
        FCPLabel subtitle = new FCPLabel("A simple Java program!", FCPLabel.SMALL, Color.WHITE);
        subtitle.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        subtitle.setTransparent(true);

        master.add(title);
        master.add(subtitle);
        master.add(start);
        master.add(manage);
        master.add(quit);

        springLayout.putConstraint(SpringLayout.NORTH, title, 150, SpringLayout.NORTH, master);
        springLayout.putConstraint(SpringLayout.WEST, title, 193, SpringLayout.WEST, master);
        springLayout.putConstraint(SpringLayout.NORTH, subtitle, 10, SpringLayout.SOUTH, title);
        springLayout.putConstraint(SpringLayout.WEST, subtitle, 195, SpringLayout.WEST, master);
        springLayout.putConstraint(SpringLayout.NORTH, start, 45, SpringLayout.SOUTH, subtitle);
        springLayout.putConstraint(SpringLayout.WEST, start, 200, SpringLayout.WEST, master);
        springLayout.putConstraint(SpringLayout.NORTH, manage, 10, SpringLayout.SOUTH, start);
        springLayout.putConstraint(SpringLayout.WEST, manage, 200, SpringLayout.WEST, master);
        springLayout.putConstraint(SpringLayout.NORTH, quit, 10, SpringLayout.SOUTH, manage);
        springLayout.putConstraint(SpringLayout.WEST, quit, 200, SpringLayout.WEST, master);

        setMasterPanel(master);

        /*
        BoxLayout 

        // Subpanels
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        // buttonPanel.setBackground(Color.red);

        buttonPanel.add(start);
        centerButton(start);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(manage);
        centerButton(manage);
        manage.setMaximumSize(new Dimension(200, 75));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(quit);
        centerButton(quit);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setMinimumSize(new Dimension(240, 170));
        buttonPanel.setPreferredSize(new Dimension(340, 270));
        buttonPanel.setMaximumSize(new Dimension(440, 370));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));
        // titlePanel.setBackground(Color.cyan);

        titlePanel.add(Box.createRigidArea(new Dimension(0, 40)));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(subtitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 40)));
        

        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // titlePanel.setMinimumSize(new Dimension(240, 170));
        // titlePanel.setPreferredSize(new Dimension(340, 270));
        titlePanel.setMaximumSize(new Dimension(440, 270));

        
        // master.setAlignmentX(Component.CENTER_ALIGNMENT);
        // master.setBorder(BorderFactory.createEmptyBorder(500, 300, 500, 300));

        // Assemble everything

        master.add(titlePanel);
        master.add(buttonPanel);
        // master.setBackground(Color.red);

        */

        master.setVisible(true);
        // System.out.println(title.getLocation().getX());
        // Page.getFrame().add(master, BorderLayout.CENTER);
    }

    private void centerButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        // button.setSize(new Dimension(300, 200));
        button.setMinimumSize(new Dimension(75, 50));
        button.setPreferredSize(new Dimension(100, 75));
        button.setMaximumSize(new Dimension(150, 75));
        button.setFont(new Font("Comic Sans", Font.PLAIN, 19));
    }

    
}