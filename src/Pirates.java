import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Pirates {

    private static ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
    private static ArrayList<Cannonball> cannonballs = new ArrayList<Cannonball>();
    private static Window window;
    public static final int WINDOW_HEIGHT = 1000, WINDOW_WIDTH = 1700;

    public static void main(String[] args) throws InterruptedException{
        window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, new KeyListener());
        Integer[] keysShip1 = new Integer[] {87,83,65,68,86};
        Integer[] keysShip2 = new Integer[] {38,40,37,39,96};
        Ship ship1 = new Ship(new Point(50,50),pressedKeys,Arrays.asList(keysShip1));
        Ship ship2 = new Ship(new Point(100,100),pressedKeys,Arrays.asList(keysShip2));

		long timeDiff;
		long lastTime = System.currentTimeMillis(); // System.currentTimeMillis() returns a long

        Random random = new Random();
        ArrayList<Island> islands = new ArrayList<Island>();
        int numberOfIslands = random.nextInt(10);
        for (int i = 0; i < numberOfIslands; i++) {
            islands.add(new Island());
            window.add(islands.get(i));
        }


        window.add(ship1);
        window.add(ship2);

        while (true){
			// First loop iteration might be strange if the initial value of lastTime is wrong
			timeDiff = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
            ship1.update((int)timeDiff);
            ship2.update((int)timeDiff); //each loop shouldn't take more time than an int can hold

            ArrayList<Cannonball> cannonballsClone = (ArrayList<Cannonball>)cannonballs.clone();
            for(Cannonball c:cannonballsClone) {
				c.update((int)timeDiff);
                if (ship1.isColliding(c))
                    c.collideWith(c);
                if (ship2.isColliding(c))
                    c.collideWith(ship2);
                for (Island island:islands) {
                    if (c.isColliding(island))
                        island.collideWith(c);
                }
            }

            for (Island island:islands) {
                if (ship1.isColliding(island))
                    island.collideWith(ship1);
                if (ship2.isColliding(island))
                    island.collideWith(ship2);
            }

            if (ship1.isColliding(ship2)) {
                ship2.collideWith(ship1);
                ship1.collideWith(ship2);
            }


            window.repaint();
            Thread.sleep(10);
        }
    }
    
    public static void addCannonball(Cannonball c) {
		cannonballs.add(c);
		window.add(c);
	}

    public static void remove(Cannonball c) {
        cannonballs.remove(c);
        window.remove(c);
    }

    static class KeyListener implements java.awt.event.KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //Nope
        }

        @Override
        public void keyPressed(KeyEvent e) {
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
