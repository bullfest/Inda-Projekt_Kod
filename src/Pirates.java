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
    private static ArrayList<Ship> ships = new ArrayList<Ship>();
    private static ArrayList<Island> islands = new ArrayList<Island>();
    private static Window window;

    public static final int WINDOW_HEIGHT = 1000, WINDOW_WIDTH = 1700;

    public static void main(String[] args) throws InterruptedException {

        window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, new KeyListener());

        startGame();
    }

    private static void startGame() throws InterruptedException {
        //Create ships
        Integer[] keysShip1 = new Integer[] {87,83,65,68,86};
        Integer[] keysShip2 = new Integer[] {38,40,37,39,96};
        ships.add(new Ship(new Point(50,50),pressedKeys,Arrays.asList(keysShip1)));
        ships.add(new Ship(new Point(WINDOW_WIDTH-70,WINDOW_HEIGHT-100),pressedKeys,Arrays.asList(keysShip2)));
        ships.get(1).setAngle(180);
        window.add(ships.get(0));
        window.add(ships.get(1));
        HpDisplay hpd = new HpDisplay(ships);
        window.add(hpd);

        //Create Islands
        Random random = new Random();
        int numberOfIslands = random.nextInt(10);
        for (int i = 0; i < numberOfIslands; i++) {
            islands.add(new Island());
            window.add(islands.get(i));
        }

        Point topLeft = new Point(0,0), topRight = new Point(WINDOW_WIDTH,0),
                bottomLeft = new Point(0,WINDOW_HEIGHT), bottomRight = new Point(WINDOW_WIDTH,WINDOW_HEIGHT);
        islands.add(new Island(new Point[] {topLeft,topRight}));
        islands.add(new Island(new Point[] {topLeft, bottomLeft}));
        islands.add(new Island(new Point[] {bottomLeft, bottomRight}));
        islands.add(new Island(new Point[] {topRight, bottomRight}));

        long timeDiff;
        long lastTime = System.currentTimeMillis(); // System.currentTimeMillis() returns a long

        while (ships.size()>1){
            // First loop iteration might be strange if the initial value of lastTime is wrong
            timeDiff = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            //make clones of lists that can be modified at other places
            ArrayList<Cannonball> cannonballsClone = (ArrayList<Cannonball>)cannonballs.clone();
            ArrayList<Ship> shipsClone = (ArrayList<Ship>) ships.clone();
            for (Ship s:shipsClone) {
                s.update((int)timeDiff); //each loop shouldn't take more time than an int can hold
            }


            for(Cannonball c:cannonballsClone) {
                c.update((int)timeDiff);

                //Do cannonball collisions
                for (Ship s:shipsClone)
                    if (s.isColliding(c))
                        c.collideWith(s);
                for (Island island:islands) {
                    if (c.isColliding(island))
                        island.collideWith(c);
                }
            }

            //Do other collisions

            for (Island island:islands) {
                for (Ship s:shipsClone)
                    if (s.isColliding(island))
                        island.collideWith(s);
            }

            for (int i = 0; i < shipsClone.size()-1; i++) {
                for (int j = i+1;j<shipsClone.size();j++) {
                    if (shipsClone.get(i).isColliding(shipsClone.get(j))) {
                        shipsClone.get(i).collideWith(shipsClone.get(j));
                        shipsClone.get(j).collideWith(shipsClone.get(i));
                    }
                }
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

    public static void remove(Ship s) {
        ships.remove(s);
        window.remove(s);
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
