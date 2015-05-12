/**
 * Created by Alexander on 2015-05-08.
 */
public abstract class Entity extends Drawable {

    public Entity(Point p, String filePath) {
        super(p,filePath);
    }
    
    /**
	 * Checks if this entity is colliding with another given entity.
	 * 
	 * @param e The entity with which collision is checked.
	 * @return True if the entities are colliding, false if the
	 * entities aren't.
	 */
    public abstract boolean isColliding(Entity e);
    
    //collidedWith()?
    
    /**
     * Make changes from the last frame.
     * 
     * @param timeDiff Time since the last frame was made.
     */
    public abstract void update(int timeDiff);
    
    public abstract void kill();
}
