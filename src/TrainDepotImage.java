import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

//IMPORTANT: Put the image in the C drive to make the image viewable.
//This class load an Images from an external file

public class TrainDepotImage extends Component {

    BufferedImage img;

    public void paint(Graphics g) {
        g.drawImage(img, 0, -10, null);
    }

    public TrainDepotImage() {
        try {
            img = ImageIO.read(new File("\resources\images\wow-train.png"));
        } catch (IOException e) {
        }

    }

    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(10,10);
        } else {
            return new Dimension(img.getWidth(null), img.getHeight(null));
        }
    }

}