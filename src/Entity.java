/**
 * Created by Alexander on 2015-05-08.
 */
public abstract class Entity extends Drawable {

    public Entity(Point p, String filePath) {
        super(p,filePath);
    }

    
    /**
     * Make changes from the last frame.
     * 
     * @param timeDiff Time since the last frame was made.
     */
    public abstract void update(int timeDiff);
    
    public abstract void kill();
}
