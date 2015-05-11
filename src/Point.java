/**
 * Created by Alexander on 2015-05-10.
 */
public class Point extends Vector {
    public Point(int x, int y) {
        super(new double[]{x,y});
    }

    public int getX() {
        return (int)get(0);
    }

    public int getY() {
        return (int)get(1);
    }

    public void add(Point p) {
        setData(add(p, 1).getData());
    }

    /**
     * Rotates the vector counter-clockwise.
     * @param angle The angle for the vector to be rotated in degrees.
     */
    public void rotate(double angle) {
        angle = Math.toRadians(angle);
        Matrix rotate = new Matrix(new double[][] {
                {Math.cos(angle),-Math.sin(angle)},
                {Math.sin(angle),Math.cos(angle)}
        });
        setData(rotate.multiply(this).getData());
    }
    
    public double norm() {
		return Math.sqrt(getX()*getX()+getY()*getY());
	}

    public void normalize() {
        setData(multiply(1/norm()).getData());
    }
}
