/**
 * Represents a cannonball entity.
 * 
 */
public class Cannonball extends Entity {
	
	private Point velocity;
    private Ship shotBy;
	
    public Cannonball(Point p, double angle, Ship shotBy) {
        super(p, "resources/pictures/cannonball.png");
        velocity = new Point(0.5, 0); // Length of vector is not yet decided and can change
        setAngle(angle);
        this.shotBy = shotBy;
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
        Point center = getCenterPos();
        center.add(velocity, timeDiff);
		setCenter(center);
	}

    @Override
    public void collideWith(Entity e) {
        //TODO
    }

    public void kill(){}
}
