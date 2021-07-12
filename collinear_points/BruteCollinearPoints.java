package collinear_points;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegments;
    private int count;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        assert points.length == 4;
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(points);
        if (hasDuplicatedPoints(points)) {
            throw new IllegalArgumentException();
        }

        this.points = points;
        this.lineSegments = new LineSegment[getMaxNumOfLineSegments(points)];
        this.count = 0;
        findSegments();
    }

    private int getMaxNumOfLineSegments(Point[] points) {
        return points.length;
    }

    private boolean hasDuplicatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    // finds all line segments containing 4 points
    private void findSegments() {
        int n = points.length;
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];

                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            Point[] allPoints = {p, q, r, s};
                            Arrays.sort(allPoints);
                            // p, q, r, s are collinear
                            lineSegments[count] = new LineSegment(allPoints[0], allPoints[3]);
                            count++;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    private void show(Point[] points) {
        for (Point point : points) {
            StdOut.print(point.toString() + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        Point[] points = {new Point(2, 2), new Point(3, 3), new Point(-2, -1), new Point(4, 4), new Point(-1, -1)};
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println(bcp.numberOfSegments());
        LineSegment[] lineSegments = bcp.segments();
        int count = bcp.numberOfSegments();

        for (int i = 0; i < count; i++) {
            StdOut.println(lineSegments[i].toString());
        }
    }

}

