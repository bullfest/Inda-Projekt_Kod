import java.util.List;

/**
 * Created by Alexander on 2015-05-18.
 */
public interface Collideable {

    /**
     * Checks if this entity is colliding with another given entity.
     *
     * @param e The entity with which collision is checked.
     * @return True if the entities are colliding, false if the
     * entities aren't.
     */
    public abstract boolean isColliding(Collideable e);

    public abstract void collideWith(Collideable e);

    public List<Point> getCollisionPoints();

    public Point getCenterPos();

}
