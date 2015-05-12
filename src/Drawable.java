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

    public Point getCenterPos() {
        Point center = new Point(image.getWidth()/2,image.getHeight()/2);
        center.add(position);
        return center;
    }

    public void setCenter(Point center) {
        center.add(new Point(image.getWidth()/2,image.getHeight()/2),-1); //center-(leftCornerToCenterVector)
        position = center;
    }

    //Methods for getting corners of the current drawn picture

    public Point getTopLeftPos() {
        Point topLeft = new Point(-image.getWidth()/2,-image.getHeight()/2);
        topLeft.rotate(angle);
        topLeft.add(getCenterPos());
        return topLeft;
    }

    public Point getTopRightPos() {
        Point topRight = new Point(image.getWidth()/2,-image.getHeight()/2);
        topRight.rotate(angle);
        topRight.add(getCenterPos());
        return topRight;
    }

    public Point getBottomLeftPos() {
        Point bottomLeft = new Point(-image.getWidth()/2,image.getHeight()/2);
        bottomLeft.rotate(angle);
        bottomLeft.add(getCenterPos());
        return bottomLeft;
    }

    public Point getBottomRightPos() {
        Point bottomRight = new Point(image.getWidth()/2,image.getHeight()/2);
        bottomRight.rotate(angle);
        bottomRight.add(getCenterPos());
        return bottomRight;
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
