/**
 * Represents a cannonball entity.
 * 
 */
public class Cannonball extends Entity {
	
	private Point velocity;
	
    public Cannonball(Point p, double angle) {
        super(p, "resources/pictures/cannonball.png");
        velocity = new Point(4, 0); // Length of vector is not yet decided and can change
        setAngle(angle);
    }
    
    public void setAngle(double angle) {
		
		super.setAngle(angle);
		velocity.rotate(angle);
	}
    
    public void update(double timeDiff) {
		position = position.add(velocity, timeDiff);
	}
    
    public void kill(){}
}
