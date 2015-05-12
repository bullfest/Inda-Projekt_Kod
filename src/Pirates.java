import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Pirates {

    private static ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
    private static ArrayList<Cannonball> cannonballs = new ArrayList<Cannonball>();
    private static Window window;

    public static void main(String[] args) throws InterruptedException{
        window = new Window(1700, 1000, new KeyListener());
        Integer[] keysShip1 = new Integer[] {87,83,65,68,86};
        Integer[] keysShip2 = new Integer[] {38,40,37,39,96};
        Ship ship1 = new Ship(new Point(50,50),pressedKeys,Arrays.asList(keysShip1));
        Ship ship2 = new Ship(new Point(100,100),pressedKeys,Arrays.asList(keysShip2));

		long timeDiff;
		long lastTime = System.currentTimeMillis(); // System.currentTimeMillis() returns a long

        window.add(ship1);
        ship1.setAngle(15);
        ship2.setAngle(90);
        window.add(ship2);
        while (true){
			// First loop iteration might be strange if the initial value of lastTime is wrong
			timeDiff = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
            ship1.update((int)timeDiff);
            ship2.update((int)timeDiff); //each loop shouldn't take more time than an int can hold
            
            for(Cannonball c:cannonballs) {
				c.update((int)timeDiff);
			}
            
            window.repaint();
            Thread.sleep(10);
        }
    }
    
    public static void addCannonball(Cannonball c) {
		cannonballs.add(c);
		window.add(c);
	}

    static class KeyListener implements java.awt.event.KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //Nope
        }

        @Override
        public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
            if (!pressedKeys.contains(e.getKeyCode()))
                pressedKeys.add(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
			while (pressedKeys.contains(e.getKeyCode())) {
				pressedKeys.remove((Integer)e.getKeyCode());
			}
        }
    }
}
