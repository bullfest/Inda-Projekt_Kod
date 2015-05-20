import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;
import java.util.List;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Island
        extends Drawable
        implements Collideable {

    private static final int NUMBER_OF_NODES = 20;
    private static final int MAX_ISLAND_RADIUS = 100;
    private ArrayList<Point> hull = new ArrayList<Point>();

    public Island() {
        Random random = new Random();
        ArrayList<Point> points = new ArrayList<Point>();

        //Randomize a point at least 100 px from the borders.
        points.add(new Point(random.nextInt(Pirates.WINDOW_WIDTH -200)+100,random.nextInt(Pirates.WINDOW_HEIGHT -200)+100));

        for (int i = 1; i < NUMBER_OF_NODES; i++) {
            points.add(getRandomPoint(points.get(0)));
        }
        findSmallestHull(points);
    }

    /**
     * Gift wrapping algorithm/Jarvis march.
     * An algorithm for finding the smallest convex hull containing all given points.
     * This one is based of the Wikipedia article with the algorithms name.
     * Time complexity h*n (h is number of nodes on hull, n number of points in total)
     * Better algorithms exist but this one was somewhat easy to implement.
     */
    private void findSmallestHull(final ArrayList<Point> points) {
        //Find leftmost point
        Point pointOnHull = points.get(0);
        for (Point point : points) {
            if (point.getX() < pointOnHull.getX())
                pointOnHull = point; //is more left
        }

        do {
            hull.add(pointOnHull);
            Point endPoint = points.get(0);
            for (Point p:points) {
                int turn = findTurn(pointOnHull,endPoint,p);
                if (endPoint.equals(pointOnHull) || turn == -1 ||
                        (turn==0 && dist(pointOnHull,endPoint) < dist(pointOnHull,p))) //line from last pointOnHull to p is "outside" line to endPoint
                    endPoint = p;
            }
            pointOnHull = endPoint;
        } while (!pointOnHull.equals(hull.get(0)));
    }

    private static double dist(final Point p, final Point q)
    {
        final double dx = (q.getX() - p.getX());
        final double dy = (q.getY() - p.getY());
        return dx * dx + dy * dy;
    }

    /**
     * Returns -1, 0, 1 if p,q,r forms a right, straight, or left turn.
     * 1 = left, -1 = right, 0 = none
     *
     * @ref http://www-ma2.upc.es/geoc/mat1q1112/OrientationTests.pdf
     * @param p
     * @param q
     * @param r
     * @return 1 = left, -1 = right, 0 = none
     */
    private static int findTurn(final Point p, final Point q, final Point r)
    {
        final double x1 = (q.getX() - p.getX()) * (r.getY() - p.getY());
        final double x2 = (r.getX() - p.getX()) * (q.getY() - p.getY());
        final int anotherInteger = (int)(x1 - x2);
        return ((Integer)0).compareTo(anotherInteger);
    }

    /**
     * Creates a random Point nearby the Point given in the argument
     * @param nearbyPoint A Point that the randomized should be nearby
     * @return a random Point
     */
    private Point getRandomPoint(Point nearbyPoint) {
        Random random = new Random();

        Point randPoint = new Point(random.nextInt(MAX_ISLAND_RADIUS),0);
        randPoint.rotate(random.nextInt(360));
        randPoint.add(nearbyPoint);
        return randPoint;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(0x458B00));
        int[] xVal = new int[hull.size()], yVal = new int[hull.size()];
        for (int i = 0; i < hull.size(); i++) {
            xVal[i] = (int)hull.get(i).getX();
            yVal[i] = (int)hull.get(i).getY();
        }
        g2d.fillPolygon(xVal, yVal, xVal.length);
    }

    private List<Point> collisionPoints;

    public void computeCollisionPoints() {
        collisionPoints = new ArrayList<Point>();
        Point from = hull.get(hull.size()-1);
        for (Point to : hull) {
            Point edge = new Point(to);
            edge.add(from, -1);
            Point normalizedEdge = new Point(edge);
            normalizedEdge.normalize();
            Point edgeIterator = new Point(normalizedEdge);
            collisionPoints.add(from);
            while (edgeIterator.norm() < edge.norm()) {
                Point edgepoint = new Point(edgeIterator);
                edgepoint.add(from);
                collisionPoints.add(edgepoint);
                edgeIterator.add(normalizedEdge);
            }
            from = to;
        }
    }

    public List<Point> getCollisionPoints() {
        if (collisionPoints == null)
            computeCollisionPoints();
        return collisionPoints;
    }

    @Override
    public Point getCenterPos() {
        double xSum=0,ySum=0;
        for (Point p:hull) {
            xSum+=p.getX();
            ySum+=p.getY();
        }
        return new Point(xSum/hull.size(),ySum/hull.size());
    }

    @Override
    public boolean isColliding(Collideable e) {
        return false;
    }

    long lastShipCollision = 0;

    @Override
    public void collideWith(Collideable e) {
        if (e.getClass().equals(Cannonball.class)) {
            Pirates.remove((Cannonball)e);
        } else if (e.getClass().equals(Ship.class) && System.currentTimeMillis()-lastShipCollision > 40) {
            lastShipCollision = System.currentTimeMillis();
            Ship s = (Ship) e;
            s.takeDamage(50);

            //Bounce away ship
            double minDist = Double.MAX_VALUE;
            int nearestPointIndex = 0; //Will be discarded in first iteration
            for (int i = 0; i < collisionPoints.size(); i++) {
                Point p = collisionPoints.get(i);
                double dx = s.getCenterPos().getX() - p.getX();
                double dy = s.getCenterPos().getY() - p.getY();
                if (dx * dx + dy * dy < minDist) { //No need for sqrt, takes extra time
                    minDist = dx * dx + dy * dy;
                    nearestPointIndex = i;
                }
            }

            //create a vector parallel to the side we collided with (Might not be entirely correct in corners
            //but it'll look somewhat right.
            Point parallelToSide = new Point(collisionPoints.get(nearestPointIndex));
            parallelToSide.add(collisionPoints.get((nearestPointIndex+1)%collisionPoints.size()),-1);
            Point perpToSide = new Point(parallelToSide.getY(),-parallelToSide.getX());

            Point sVelo = s.getVelocity();
            Point velocityNewBasis = sVelo.toBasis(parallelToSide, perpToSide);
            perpToSide.multiply(velocityNewBasis.getY()*-1.3);
            s.addToVelocity(perpToSide);
            //ToDo bounce ship
        }
    }
}
