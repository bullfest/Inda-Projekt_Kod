import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alexander on 2015-05-08.
 */
public abstract class Drawable {
    Point position;
    double angle = 0;
    BufferedImage image;

    public Drawable(Point p, String imagePath) {
        position = p;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Error when reading file: " + imagePath);
        }
    }

    public Point getPos() {
        return position;
    }

    public void setPos(Point p) {
        position = p;
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * Set the angle of the rotation of the sprite relative to the positive x-axis counter-clockwise
     * @param angle The rotation of the sprite in degrees.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}
