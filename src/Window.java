import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Window extends JFrame {
    Frame frame = new Frame();

    public Window(int xSize, int ySize,KeyListener listener) {
        add(frame);
        frame.addKeyListener(listener);
        setSize(xSize,ySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void add(Drawable drawable) {
        frame.add(drawable);
    }

    public void remove(Drawable drawable) {
        frame.remove(drawable);
    }

    public void reset() {frame.reset();}

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
                d.draw(g2d);
            }
        }

        void reset() {
            drawables = new HashSet<Drawable>();
        }
    }

}
