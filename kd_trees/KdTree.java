package kd_trees;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final int CANVAS_SIZE = 1;
    private static final double POINT_SIZE = 0.01;

    private Node root;

    private class Node {
        private double key;
        private Point2D value;
        private Node left;
        private Node right;
        private int size;

        public Node(Point2D point, boolean useX, int size) {
            this.key = useX ? point.x() : point.y();
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
        StdDraw.text(point.x(), point.y() - 0.01, point.x() + ", " + point.y());
        StdDraw.text(point.x(), point.y() + 0.01, level + "");

        // draw the line
        StdDraw.setPenRadius();
        if (cur.key == point.x()) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(cur.key, yMin, cur.key, yMax);
            draw(cur.left, xMin, cur.key, yMin, yMax, level + 1);
            draw(cur.right, cur.key, xMax, yMin, yMax, level + 1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xMin, cur.key, xMax, cur.key);
            draw(cur.left, xMin, xMax, yMin, cur.key, level + 1);
            draw(cur.right, xMin, xMax, cur.key, yMax, level + 1);
        }
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        KdTree kdTree = new KdTree();
        for (int i = 0; i < n; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            kdTree.insert(new Point2D(x, y));
        }


        kdTree.draw();

    }
}
