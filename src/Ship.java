import java.util.Collection;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Ship extends Entity {

    private Point velocity = new Point(0,0);

    private Collection<Integer> pressedKeys;

    public Ship(Point p, Collection<Integer> pressedKeys){
        super(p, "resources/pictures/ship.png");
        this.pressedKeys = pressedKeys;
    }
    
    public void update(double timeDiff) {
	}
    
    public void kill() {
		//TODO
    }
}
