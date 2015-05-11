/**
 * Created by Alexander on 2015-05-08.
 */
public abstract class Entity extends Drawable {

    public Entity(Point p, String filePath) {
        super(p,filePath);
    }
    
    public abstract boolean isColliding(Entity e);
    
    //collides()?
    
    public abstract void update(double timeDiff);
    
    public abstract void kill();
}
