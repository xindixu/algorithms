package slider_puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private final ArrayList<Board> solution;
    private boolean isSolvable;

    private static class Move {
        private final Board board;
        private final Move prevMove;
        private final int moves;
        private final int priority;
        private static final Comparator<Move> BY_PRIORITY_ORDER = new PriorityOrder();

        public Move(Board board, Move prevMove) {
            this.board = board;
            this.prevMove = prevMove;
            this.moves = prevMove == null ? 0 : prevMove.getMoves() + 1;
            this.priority = board.manhattan() + this.moves;
        }

        public int getPriority() {
            return priority;
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return board;
        }

        public Move getPrevMove() {
            return prevMove;
        }

        private static class PriorityOrder implements Comparator<Move> {
            public int compare(Move a, Move b) {
                if (a == null || b == null) {
                    throw new NullPointerException();
                }
                int priorityA = a.getPriority();
                int priorityB = b.getPriority();
                return priorityA - priorityB;
            }
        }

    }

    /**
     * Apply A* algorithm to both the original board and the twin board, in lock-step.
     * Exactly one board will be solvable.
     *
     * @param board the board to be solved
     */
    public Solver(Board board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }

        solution = new ArrayList<>();

        // create initial moves for the original board and the twin board
        Move moveOriginal = new Move(board, null);
        Move moveTwin = new Move(board.twin(), null);

        // initialize minPQ for the original board and the twin board
        MinPQ<Move> minPQOriginal = new MinPQ<>(Move.BY_PRIORITY_ORDER);
        MinPQ<Move> minPQTwin = new MinPQ<>(Move.BY_PRIORITY_ORDER);
        minPQOriginal.insert(moveOriginal);
        minPQTwin.insert(moveTwin);

        while (true) {
            moveOriginal = minPQOriginal.delMin();
            moveTwin = minPQTwin.delMin();
            Board boardOriginal = moveOriginal.getBoard();
            Board boardTwin = moveTwin.getBoard();

            solution.add(boardOriginal);

            if (boardOriginal.isGoal()) {
                isSolvable = true;
                break;
            }

            if (boardTwin.isGoal()) {
                isSolvable = false;
                break;
            }

            putNeighborsToPQ(minPQOriginal, moveOriginal);
            putNeighborsToPQ(minPQTwin, moveTwin);

            if (minPQOriginal.isEmpty()) {
                break;
            }
            if (minPQTwin.isEmpty()) {
                break;
            }
        }
    }


    /**
     * Add neighbors to minPQ, without adding duplicated board
     *
     * @param minPQ   the minPQ to update on
     * @param curMove current move with info on preMove and the current board
     */
    private void putNeighborsToPQ(MinPQ<Move> minPQ, Move curMove) {
        for (Board neighborBoard : curMove.getBoard().neighbors()) {
            if (curMove.getPrevMove() != null && neighborBoard.equals(curMove.getPrevMove().getBoard())) {
                continue;
            }
            Move neighborMove = new Move(neighborBoard, curMove);
            minPQ.insert(neighborMove);
        }
    }

    /**
     * @return is the initial board solvable
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        // calculate number of moves
        return solution.size() - 1;
    }

    /**
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
