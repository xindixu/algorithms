package kd_trees;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private static final int CANVAS_SIZE = 1;
    private static final double POINT_SIZE = 0.01;
    private SET<Point2D> set;

    public PointSET() {
        this.set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        set.add(p);
    }

    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Queue<Point2D> result = new Queue<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                result.enqueue(p);
            }
        }
        return result;
    }


    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (set.isEmpty()) return null;

        double minDistance = Double.MAX_VALUE;
        Point2D result = null;
        for (Point2D p : set) {
            double distance = point.distanceTo(p);
            if (distance < minDistance) {
                minDistance = distance;
                result = p;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        PointSET pointSet = new PointSET();
        for (int i = 0; i < n; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            pointSet.insert(new Point2D(x, y));
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, CANVAS_SIZE);
        StdDraw.setYscale(0, CANVAS_SIZE);
        StdDraw.setPenRadius(POINT_SIZE);
        pointSet.draw();

        // create a rectangle
        RectHV rectangle = new RectHV(0.1, 0.1, 0.3, 0.5);
        StdDraw.setPenRadius(POINT_SIZE / 2);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        rectangle.draw();


        // find range
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.setPenRadius(POINT_SIZE);

        Iterable<Point2D> pointsInRange = pointSet.range(rectangle);
        for (Point2D pointInRange : pointsInRange) {
            StdOut.println(pointInRange.x() + " " + pointInRange.y());
            pointInRange.draw();
        }

        // create a point
        Point2D referencePoint = new Point2D(0.89, 0.24);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        referencePoint.draw();

        // find nearest neighbor
        Point2D nearestPoint = pointSet.nearest(referencePoint);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        if (nearestPoint != null) nearestPoint.draw();


        StdDraw.show();

    }

}
