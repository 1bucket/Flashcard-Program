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

public class MainMenu extends Page {
    public MainMenu() {
        Page.setPage(this);
        getMasterPanel().setVisible(false);

        // Master Panel
        // JPanel master = new JPanel();
        FCPBackground master = new FCPBackground();
        
        master.setLayout(new BoxLayout(master, BoxLayout.PAGE_AXIS));
        setMasterPanel(master);

        // Buttons
        // JButton quit = new JButton("Quit");
        FCPButton quit = new FCPButton("Quit", FCPButton.MEDIUM);
        quit.addActionListener(new Quit());

        // JButton manage = new JButton("Manage Flashcards");
        FCPButton manage = new FCPButton("Manage Flashcards", FCPButton.MEDIUM);
        manage.addActionListener(new ToManage());

        // JButton start = new JButton("Start studying");
        FCPButton start = new FCPButton("Start studying", FCPButton.MEDIUM);
        start.addActionListener(new ToStudy());

        // Labels
        // JLabel title = new JLabel("Flashcards");
        FCPLabel title = new FCPLabel("Flashcards", FCPButton.MEDIUM);
        title.setFont(new Font("Comic Sans", Font.PLAIN, 40));
        // JLabel subtitle = new JLabel("A simple Java program!");
        FCPLabel subtitle = new FCPLabel("A simple Java program!", FCPLabel.MEDIUM);
        subtitle.setFont(new Font("Comic Sans", Font.PLAIN, 20));

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
        master.setVisible(true);
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