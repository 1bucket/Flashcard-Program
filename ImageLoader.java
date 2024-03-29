import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import javax.swing.JComponent;

public class ImageLoader extends JComponent {
    private BufferedImage image;

    public ImageLoader(String imgSourcePath) {
        try {
            image = ImageIO.read(new File(imgSourcePath));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image");
        Component panel = frame.getContentPane();
        frame.add(new ImageLoader("Sleeve Logo.png"));
        // BufferedImage image = null;
        

        frame.setMaximumSize(new Dimension(1000, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}