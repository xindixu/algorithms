package collinear_points;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private final LineSegment[] lineSegments;
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
        return Arrays.copyOf(lineSegments, count);
    }

    public static void main(String[] args) {
        Point[] points = {new Point(2, 2), new Point(3, 3), new Point(-2, -1), new Point(4, 4), new Point(-1, -1)};
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

}

