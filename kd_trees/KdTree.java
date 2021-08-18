package kd_trees;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final int CANVAS_SIZE = 1;
    private static final double POINT_SIZE = 0.01;

    private Node root;

    private class Node {
        private double key;
        private Point2D value;
        private boolean useX;
        private Node left;
        private Node right;
        private int size;

        public Node(Point2D point, boolean useX, int size) {
            this.key = useX ? point.x() : point.y();
            this.useX = useX;
            this.value = point;
            this.size = size;
        }
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node cur) {
        if (cur == null) return 0;
        return cur.size;
    }

    /**
     * Insert point into the kd-tree,  the x- and y-coordinates of the points as keys in strictly alternating sequence
     *
     * @param point the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        root = insert(root, point, true);
    }

    private Node insert(Node cur, Point2D point, boolean useX) {
        if (cur == null) return new Node(point, useX, 1);
        double key = useX ? point.x() : point.y();
        if (key < cur.key) cur.left = insert(cur.left, point, !useX);
        else if (key > cur.key) cur.right = insert(cur.right, point, !useX);
        else cur.value = point;
        cur.size = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    /**
     * Does the tree contains this point p
     *
     * @param p the point to test
     * @return does the set contain point p
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node cur, Point2D p) {
        if (cur == null) return false;

        if (cur.useX) {
            if (cur.key < p.x()) return contains(cur.right, p);
            if (cur.key > p.x()) return contains(cur.left, p);
            return cur.value.y() == p.y();
        } else {
            if (cur.key < p.y()) return contains(cur.right, p);
            if (cur.key > p.y()) return contains(cur.left, p);
            return cur.value.x() == p.x();
        }
    }

    /**
     * Draw the kd-tree division
     */
    public void draw() {
        draw(root, 0, CANVAS_SIZE, 0, CANVAS_SIZE, 1);
    }

    private void draw(Node cur, double xMin, double xMax, double yMin, double yMax, int level) {
        if (cur == null) return;

        // draw the point
        Point2D point = cur.value;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(POINT_SIZE);
        StdDraw.point(point.x(), point.y());
        // StdDraw.text(point.x(), point.y() - 0.01, point.x() + ", " + point.y());
        // StdDraw.text(point.x(), point.y() + 0.01, level + "");

        // draw the line
        StdDraw.setPenRadius();
        if (cur.useX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(cur.key, yMin, cur.key, yMax);
            // further divide the current rectangular vertically
            draw(cur.left, xMin, cur.key, yMin, yMax, level + 1);
            draw(cur.right, cur.key, xMax, yMin, yMax, level + 1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xMin, cur.key, xMax, cur.key);
            // further divide the current rectangular horizontally
            draw(cur.left, xMin, xMax, yMin, cur.key, level + 1);
            draw(cur.right, xMin, xMax, cur.key, yMax, level + 1);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Queue<Point2D> result = new Queue<>();

        range(rect, root, result);
        return result;
    }

    private void range(RectHV rect, Node cur, Queue<Point2D> result) {
        if (cur == null) return;

        Point2D curPoint = cur.value;

        if (cur.useX) {
            if (cur.key < rect.xmin()) {
                // cur point is to the left of the rect
                range(rect, cur.right, result);
            } else if (cur.key > rect.xmax()) {
                // cur point is to the right of the rect
                range(rect, cur.left, result);
            } else {
                // cur point is in between left and right edges of the rect

                // check and add cur point
                if (rect.contains(curPoint)) result.enqueue(curPoint);

                // check both children
                range(rect, cur.right, result);
                range(rect, cur.left, result);

            }
        } else {
            if (cur.key < rect.ymin()) {
                // cur point is to the bottom of the rect
                range(rect, cur.right, result);
            } else if (cur.key > rect.ymax()) {
                //  cur point is to the top of the rect
                range(rect, cur.left, result);
            } else {
                // cur point is in between top and bottom edges of the rect

                // check and add cur point
                if (rect.contains(curPoint)) result.enqueue(curPoint);

                // check both children
                range(rect, cur.right, result);
                range(rect, cur.left, result);
            }
        }
    }


    public static void main(String[] args) {

        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        kdtree.draw();

        StdDraw.show();

        // process range search queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x0 = x1 = StdDraw.mouseX();
                y0 = y1 = StdDraw.mouseY();
                isDragging = true;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }

            // user stops dragging rectangle
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }

            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();
            kdtree.draw();

            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            // draw the range search results for kd-tree in blue
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLUE);
            for (Point2D p : kdtree.range(rect))
                p.draw();

            StdDraw.show();
            StdDraw.pause(20);
        }
    }


    // public static void main(String[] args) {
    //     // read the n points from a file
    //     In in = new In(args[0]);
    //     int n = in.readInt();
    //     KdTree kdTree = new KdTree();
    //     for (int i = 0; i < n; i++) {
    //         double x = in.readDouble();
    //         double y = in.readDouble();
    //         kdTree.insert(new Point2D(x, y));
    //     }
    //
    //     kdTree.draw();
    //
    //     // does the tree contains these points
    //     StdOut.println(kdTree.contains(new Point2D(0.09, 0.34)));
    //     StdOut.println(kdTree.contains(new Point2D(0.09, 0.32)));
    //
    //     // create a rectangle
    //     RectHV rectangle = new RectHV(0.1, 0.1, 0.3, 0.5);
    //     StdDraw.setPenRadius(POINT_SIZE / 2);
    //     StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
    //     rectangle.draw();
    //
    //     // find range
    //     StdDraw.setPenColor(StdDraw.BOOK_BLUE);
    //     StdDraw.setPenRadius(POINT_SIZE);
    //
    //     Iterable<Point2D> pointsInRange = kdTree.range(rectangle);
    //     for (Point2D pointInRange : pointsInRange) {
    //         StdOut.println(pointInRange.x() + " " + pointInRange.y());
    //         pointInRange.draw();
    //     }
    // }
}
