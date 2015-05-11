import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Pirates {

    private static ArrayList<Integer> pressedKeys = new ArrayList<Integer>();

    public static void main(String[] args) throws InterruptedException{
        Window window = new Window(700, 700, new KeyListener());

        Ship ship1 = new Ship(new Point(50,50),pressedKeys);
        Ship ship2 = new Ship(new Point(100,100),pressedKeys);

		double timeDiff;
		double lastTime = System.currentTimeMillis(); // System.currentTimeMillis() returns a long

        window.add(ship1);
        ship1.setAngle(15);
        ship2.setAngle(90);
        window.add(ship2);
        for (int i = 0; i < 360; i++) {
			// First loop iteration might be strange if the initial value of lastTime is wrong
			timeDiff = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis()
            ship1.setAngle(i);
            ship2.setAngle(-i);
            window.repaint();
            Thread.sleep(10);
        }
    }

    static class KeyListener implements java.awt.event.KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //Nope
        }

        @Override
        public void keyPressed(KeyEvent e) {
            pressedKeys.add(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
			while (pressedKeys.contains(e.getKeyCode())) {
				pressedKeys.remove(e.getKeyCode());
			}
        }
    }
}
