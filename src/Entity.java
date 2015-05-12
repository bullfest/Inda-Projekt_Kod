/**
 * Created by Alexander on 2015-05-08.
 */
public abstract class Entity extends Drawable {

    public Entity(Point p, String filePath) {
        super(p,filePath);
    }
    
    public abstract boolean isColliding(Entity e);
    
    //collidedWith()?
    
    public abstract void update(int timeDiff);
    
    public abstract void kill();
}
