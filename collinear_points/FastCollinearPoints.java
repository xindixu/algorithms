package collinear_points;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final Point[] points;
    private final LineSegment[] lineSegments;
    private int count;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        assert points.length == 4;
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);

        if (hasDuplicatedPoints()) {
            throw new IllegalArgumentException();
        }

        this.lineSegments = new LineSegment[getMaxNumOfLineSegments()];
        this.count = 0;
        findSegments();
    }

    private int getMaxNumOfLineSegments() {
        return points.length;
    }

    private boolean hasDuplicatedPoints() {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    private void findSegments() {
        final int n = points.length;

        for (int i = 0; i < n; i++) {
            Point p = points[i];
            Point[] pointsBySlope = points.clone();
            // pointsBySlope is sorted first by y, then by x, then by slope between each point and p
            Arrays.sort(pointsBySlope, p.slopeOrder());

            // the element at index=0 is p
            int j = 1;
            while (j < pointsBySlope.length) {
                LinkedList<Point> candidates = new LinkedList<>();
                // use the slope between p and the first point after p as a reference
                final double SLOPE = p.slopeTo(pointsBySlope[j]);

                // gather all points that has the same slope to p as the reference SLOPE
                do {
                    candidates.add(pointsBySlope[j++]);
                } while (j < pointsBySlope.length && p.slopeTo(pointsBySlope[j]) == SLOPE);

                // only add the line segment iff there are 3+ candidates and the first candidate is to the top/right
                // of p
                if (candidates.size() >= 3 && p.compareTo(candidates.peek()) < 0) {
                    lineSegments[count] = new LineSegment(p, candidates.removeLast());
                    count++;
                }
            }
        }

    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, count);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32000);
        StdDraw.setYscale(0, 32000);
        StdDraw.setPenRadius(0.02);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.setPenRadius();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
