import java.util.Collection;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Ship extends Entity {

    private Point velocity = new Point(0,0);
    private final int MAX_SPEED = 5;
    private Collection<Integer> pressedKeys;

    public Ship(Point p, Collection<Integer> pressedKeys,int[] keys){
        super(p, "resources/pictures/ship.png");
        this.pressedKeys = pressedKeys;
    }
    
    public void update(double timeDiff) {
        Point acceleration = new Point(0,-1);
        acceleration.rotate(getAngle());
        velocity.add(acceleration,timeDiff/500);

        if (velocity.norm()>MAX_SPEED) {
            velocity.normalize();
            velocity.setData(velocity.multiply(MAX_SPEED).getData());
        }

        getPos().add(velocity);

	}

    public boolean isColliding(Entity e) {
        //ToDo: Implement
        return false;
    }
    
    public void kill() {
		//TODO
    }
}
