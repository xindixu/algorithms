package slider_puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Board {
    private final int[][] tiles;
    private final int n;

    /**
     * Copy the tiles into private tiles
     *
     * @param tiles
     */
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                this.tiles[r][c] = tiles[r][c];
            }
        }
    }

    /**
     * String representation of this board
     * The first line contains the board size n,
     * the remaining n lines contains the n-by-n grid of tiles in row-major order,
     * using 0 to designate the blank square
     *
     * @return the string representation
     */
    public String toString() {
        StringBuilder result = new StringBuilder(String.format("%2d%n", n));

        for (int r = 0; r < n; r++) {
            result.append(" ");
            for (int c = 0; c < n; c++) {
                result.append(String.format("%2d ", tiles[r][c]));
            }
            result.append(String.format("%n"));
        }
        return result.toString();
    }

    public int dimension() {
        return n;
    }


    /**
     * The Hamming distance between a board and the goal board is the number of tiles in the wrong position.
     *
     * @return number of tiles in the wrong position
     */
    public int hamming() {
        int count = 0;
        int correct = 1;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (tiles[r][c] != 0 && tiles[r][c] != correct) {
                    count++;
                }
                correct++;
            }
        }
        return count;
    }

    private int correctRow(int k) {
        return (k - 1) / n;
    }

    private int correctCol(int k) {
        return (k - 1) % n;
    }


    /**
     * Sum of the Manhattan distances (sum of the vertical and horizontal distance) from the tiles to their goal
     * positions.
     *
     * @return sum of distances
     */
    public int manhattan() {
        int count = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int el = tiles[r][c];
                if (el != 0) {
                    int distHori = Math.abs(correctCol(el) - c);
                    int distVert = Math.abs(correctRow(el) - r);
                    count += (distHori + distVert);
                }
            }
        }
        return count;
    }

    public boolean isGoal() {
        int correct = 1;
        int max = n * n;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (tiles[r][c] != correct) {
                    return false;
                }
                correct++;
                if (correct == max) {
                    correct = 0;
                }
            }
        }
        return true;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (n != that.dimension()) {
            return false;
        }

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (tiles[r][c] != that.tiles[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] positionOfEmpty(int[][] array) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (array[r][c] == 0) {
                    return new int[]{r, c};
                }
            }
        }
        throw new NoSuchElementException();
    }

    private void exch(int[][] array, int oriR, int oriC, int destR, int destC) {
        int temp = array[oriR][oriC];
        array[oriR][oriC] = array[destR][destC];
        array[destR][destC] = temp;
    }

    private Board getNeighbor(int row, int col, int newRow, int newCol) {
        int[][] copy = new int[n][];
        for (int i = 0; i < n; i++)
            copy[i] = tiles[i].clone();
        exch(copy, row, col, newRow, newCol);
        return new Board(copy);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<>();
        int[] position = positionOfEmpty(tiles);
        int row = position[0];
        int col = position[1];

        if (row - 1 >= 0) {
            boards.add(getNeighbor(row, col, row - 1, col));
        }
        if (row + 1 < n) {
            boards.add(getNeighbor(row, col, row + 1, col));
        }
        if (col - 1 >= 0) {
            boards.add(getNeighbor(row, col, row, col - 1));
        }
        if (col + 1 < n) {
            boards.add(getNeighbor(row, col, row, col + 1));
        }
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTile = new int[n][];
        for (int i = 0; i < n; i++)
            twinTile[i] = tiles[i].clone();

        int[] position = positionOfEmpty(twinTile);
        int col = position[1] == 0 ? 1 : 0;
        int row = 0;
        int newRow = 1;

        exch(twinTile, row, col, newRow, col);

        return new Board(twinTile);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial.toString());

        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());

        for (Board b : initial.neighbors()) {
            StdOut.println(b.toString());
        }
        StdOut.println(initial.toString());
    }
}