import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Window extends JFrame {
    Frame frame = new Frame();

    public Window(int xSize, int ySize) {
        add(frame);
        setSize(xSize,ySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void add(Drawable drawable) {
        frame.add(drawable);
    }

    class Frame extends JPanel {
        HashSet<Drawable> drawables = new HashSet<Drawable>();
        Frame() {
            setFocusable(true);
        }

        void add(Drawable d) {
            drawables.add(d);
        }

        void remove(Drawable d) {
            drawables.remove(d);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(new Color(0x006994));
            g.fillRect(0, 0, getWidth(), getHeight());
            Graphics2D g2d = (Graphics2D) g;

            //Make a "snapshot" of the drawables set, to avoid concurrent modification exceptions while drawing.
            Set<Drawable> drawablesSnapshot = (Set<Drawable>)drawables.clone();
            for (Drawable d:drawablesSnapshot) {

                //Based this of something we found on Stackexchange
                    // http://gamedev.stackexchange.com/questions/62196/rotate-image-around-a-specified-origin
                AffineTransform backup = g2d.getTransform();
                AffineTransform trans = new AffineTransform();
                trans.rotate(Math.toRadians(d.getAngle()),
                        d.getPos().getX()+d.getImage().getWidth()/2, //Rotate around center of sprite.
                        d.getPos().getY()+d.getImage().getHeight()/2);

                g2d.transform(trans);
                g2d.drawImage(d.getImage(),null, d.getPos().getX(), d.getPos().getY());
                g2d.setTransform(backup); // restore previous transform
            }
        }
    }

}
