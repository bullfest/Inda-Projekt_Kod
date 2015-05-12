/**
 * Represents a cannonball entity.
 * 
 */
public class Cannonball extends Entity {
	
	private Point velocity;
	
    public Cannonball(Point p, double angle) {
        super(p, "../resources/pictures/cannonball.png");
        velocity = new Point(0.5, 0); // Length of vector is not yet decided and can change
        setAngle(angle);
    }
    
    /**
     * Sets the angle of travel.
     * 
     * @param angle The angle that the cannonball will have.
     */
    public void setAngle(double angle) {	
		super.setAngle(angle);
		velocity.rotate(angle);
	}

    @Override
    public boolean isColliding(Entity e) {
        //ToDo: Implement
        return false;
    }

    public void update(int timeDiff) {
		getPos().add(velocity, timeDiff);
	}
    
    public void kill(){}
}
