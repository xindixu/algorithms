package collinear_points;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegments;
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
        Arrays.sort(points);
        if (hasDuplicatedPoints(points)) {
            throw new IllegalArgumentException();
        }

        this.points = points;
        this.lineSegments = new LineSegment[getMaxNumOfLineSegments(points)];
        this.count = 0;
        findSegments();
    }

    private boolean hasDuplicatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    private int getMaxNumOfLineSegments(Point[] points) {
        return points.length;
    }

    private void findSegments() {
        int n = points.length;
        // i = n - 5, otherPoints = [n-4, n-3, n-2, n-1], with length = 4
        for (int i = 0; i < n - 4; i++) {
            Point curPoint = points[i];
            Point[] otherPoints = Arrays.copyOfRange(points, i + 1, n);
            Arrays.sort(otherPoints, curPoint.slopeOrder());

            // generate array of slope between curPoint and otherPoints[j]
            int m = otherPoints.length;
            double[] slopesToCurPoint = new double[m];
            for (int j = 0; j < m; j++) {
                slopesToCurPoint[j] = curPoint.slopeTo(otherPoints[j]);
            }


            int j = 0;
            while (j < otherPoints.length) {
                int k = 1;
                while (j + k < otherPoints.length && slopesToCurPoint[j] == slopesToCurPoint[j + k]) {
                    k++;
                }

                // there are at least 4 points collinear: curPoint, otherPoints[j], otherPoints[j+1], otherPoints[j+2]
                if (k >= 2) {
                    lineSegments[count] = new LineSegment(curPoint, otherPoints[j + k - 1]);
                    count++;
                }
                j = j + k + 1;
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

    private void show(LineSegment[] lineSegments) {
        for (int i = 0; i < count; i++) {
            StdOut.print(lineSegments[i].toString() + " ");
        }
        StdOut.println();
    }

    private void show(double[] nums) {
        for (double num : nums) {
            StdOut.print(num + " ");
        }
        StdOut.println();
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
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            if (segment != null) {
                StdOut.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
}
