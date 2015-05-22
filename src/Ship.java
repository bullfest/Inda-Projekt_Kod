import java.util.ArrayList;
import java.util.List;

/**
 * Represents ships in the game.
 */
public class Ship
        extends Entity
        implements Collideable {

	private int hitPoints;
    private Point velocity = new Point(0,0);
    private ArrayList<Integer> pressedKeys;
    private long lastShot, lastDamage;
    
    private final double ACCELERATION_PER_SECOND = 5, ROTATION_PER_SECOND = 60,/*Degrees*/
							SHOT_COOLDOWN = 700, DAMAGE_COOLDOWN = 1000;

    // {Up,Down,left,right,shoot}
    List<Integer> keys;

    public Ship(Point p, ArrayList<Integer> pressedKeys,List<Integer> keys){
        super(p,  "resources/pictures/ship.png");
        this.pressedKeys = pressedKeys;
        this.keys = keys;
		hitPoints  = 1000; // Initial value might change
    }

    /**
     *
     * @param timeDiff Time since the last frame was made.
     */
    public void update(int timeDiff) {
        velocity.multiply(1-0.75*(timeDiff/1000.0));
        Point acceleration = new Point(0,0);
		ArrayList<Integer> pressedKeysClone = (ArrayList<Integer>) pressedKeys.clone();
        for (Integer pressedKey : pressedKeysClone) {
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

	public int getHitPoints() {
		return hitPoints;
	}

    public void collideWith(Collideable e){
        if (e.getClass().equals(Ship.class)) {
            Ship s = (Ship) e;
            s.takeDamage(100);

            Point bounce = new Point(s.getCenterPos());
            bounce.add(getCenterPos(), -1);
            bounce.normalize();
            s.addToVelocity(bounce);
        }
    }

    public Point getVelocity() {
        return new Point(velocity); //nobody should be able to edit it directly
    }

    public void addToVelocity(Point p) {
        velocity.add(p);
    }

    private void shoot() {
        if(System.currentTimeMillis()-lastShot > SHOT_COOLDOWN) {
            lastShot = System.currentTimeMillis();
            Cannonball cannonball = new Cannonball(getCenterPos(),getAngle(),this);
            Pirates.addCannonball(cannonball);
        }
    }

	public void takeDamage(int damage) {
		if(System.currentTimeMillis()-lastDamage > DAMAGE_COOLDOWN) {
			lastDamage = System.currentTimeMillis();
			hitPoints = hitPoints - damage;
			if (hitPoints <= 0){
				kill();
				Pirates.remove(this);
			}
		}
    }

    public boolean isColliding(Collideable e) {
        double dx = (getCenterPos().getX()-e.getCenterPos().getX());
        double dy = (getCenterPos().getY()-e.getCenterPos().getY());
        if (dx*dx + dy*dy > 250000) //distance > 500
            return false; //Way too big distance, no risk of collision

        Point forward = new Point(1,0);
        forward.rotate(angle);

        Point perpToForward = new Point(-forward.getY(),forward.getX());

        //Change basis to make the ship "straight"
        Point myTopLeft = getTopLeftPos().toBasis(forward,perpToForward);
        Point myBottomRight = getBottomRightPos().toBasis(forward,perpToForward);

        List<Point> othersPoints = e.getCollisionPoints();

        for (Point p : othersPoints) {
            p = p.toBasis(forward,perpToForward);
            if (((p.getX()> myTopLeft.getX() && p.getX() < myBottomRight.getX())
                    || (p.getX() < myTopLeft.getX() && p.getX() > myBottomRight.getX()))
                    &&
                    ((p.getY() > myTopLeft.getY() && p.getY() < myBottomRight.getY())
                    || (p.getY() < myTopLeft.getY() && p.getY() > myBottomRight.getY())))
                return true;
        }

        return false;
    }
    
    public void kill() {}

    @Override
    public ArrayList<Point> getCollisionPoints() {
        ArrayList<Point> collisionPoints = new ArrayList<Point>();
        Point pos = getPos();
        int distanceBetween = 2;
        for (double i = pos.getX(); i < pos.getX() + image.getWidth()+distanceBetween; i+=distanceBetween) {
            for (double j = pos.getY(); j < pos.getY() + image.getHeight()+distanceBetween; j+=distanceBetween) {
                if (i > pos.getX() + image.getWidth())
                    i = pos.getX() + image.getWidth();
                if (j > pos.getY() + image.getHeight())
                    j = pos.getY() + image.getHeight();
                collisionPoints.add(new Point(i,j));
            }
        }
        return collisionPoints;
    }
}
