import java.util.ArrayList;
import java.util.List;

/**
 * Represents ships in the game.
 */
public class Ship extends Entity {

    private Point velocity = new Point(0,0);
    private ArrayList<Integer> pressedKeys;
    private long lastShot;
    
    private final double ACCELERATION_PER_SECOND = 2, ROTATION_PER_SECOND = 60,/*Degrees*/
							SHOT_COOLDOWN = 700;

    // {Up,Down,left,right,shoot}
    List<Integer> keys;

    public Ship(Point p, ArrayList<Integer> pressedKeys,List<Integer> keys){
        super(p,  "resources/pictures/ship.png");
        this.pressedKeys = pressedKeys;
        this.keys = keys;
    }
    
    public void update(int timeDiff) {
        velocity.multiply(1-0.75*(timeDiff/1000.0));
        Point acceleration = new Point(0,0);

        for (Integer pressedKey : pressedKeys) {
            int index = keys.indexOf(pressedKey);
            if (index == 0) // Up
                acceleration.add(new Point(ACCELERATION_PER_SECOND, 0));
            if (index == 1) // Down
                acceleration.add(new Point(-ACCELERATION_PER_SECOND, 0));
            if (index == 2) // Left
                setAngle(getAngle() - ROTATION_PER_SECOND * timeDiff / 1000.0);
            if (index == 3) // Right
                setAngle(getAngle() + ROTATION_PER_SECOND * timeDiff / 1000.0);
            if (index == 4) // Shoot
                shoot();
        }
        acceleration.rotate(getAngle());
        velocity.add(acceleration,timeDiff/1000.0);
        
        Point center = getCenterPos();
        center.add(velocity);
        setCenter(center);

	}

    public void collideWith(Entity e){}

    private void shoot() {
        if(System.currentTimeMillis()-lastShot>SHOT_COOLDOWN) {
            lastShot = System.currentTimeMillis();
            Cannonball cannonball = new Cannonball(getCenterPos(),getAngle(),this);
            Pirates.addCannonball(cannonball);
        }
    }

    public boolean isColliding(Entity e) {
        double dx = (getCenterPos().getX()-e.getCenterPos().getX());
        double dy = (getCenterPos().getY()-e.getCenterPos().getY());
        if (dx*dx + dy*dy > 2500) //distance > 50
            return false; //Way too big distance, no risk of collision

        Point forward = new Point(1,0);
        forward.rotate(angle);

        Point perpToForward = new Point(-forward.getY(),forward.getX());

        //Change basis to make the ship "straight"
        Point myTopLeft = getTopLeftPos().toBasis(forward,perpToForward);
        Point myBottomRight = getBottomRightPos().toBasis(forward,perpToForward);

        List<Point> othersPoints = e.getCollisionPoints();

        for (Point p : othersPoints) {
            if ((p.getX()> myTopLeft.getX() && p.getX() < myBottomRight.getX())
                    || (p.getX() < myTopLeft.getX() && p.getX() > myBottomRight.getX()))
                if ((p.getY() > myTopLeft.getY() && p.getY() < myBottomRight.getY())
                        || (p.getY() < myTopLeft.getY() && p.getY() > myBottomRight.getY()))
                    return true;
        }

        return false;
    }
    
    public void kill() {
		//TODO
    }
}
