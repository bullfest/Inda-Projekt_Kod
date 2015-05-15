import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2015-05-08.
 */
public abstract class Entity extends Drawable {

    public Entity(Point p, String filePath) {
        super(p,filePath);
    }

    public List<Point> getCollisionPoints() {
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

    /**
	 * Checks if this entity is colliding with another given entity.
	 * 
	 * @param e The entity with which collision is checked.
	 * @return True if the entities are colliding, false if the
	 * entities aren't.
	 */
    public abstract boolean isColliding(Entity e);
    
    public abstract void collideWith(Entity e);
    
    /**
     * Make changes from the last frame.
     * 
     * @param timeDiff Time since the last frame was made.
     */
    public abstract void update(int timeDiff);
    
    public abstract void kill();
}
