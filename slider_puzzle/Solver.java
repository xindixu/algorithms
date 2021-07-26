package slider_puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private static class Move implements Comparable<Move> {
        private final Board board;
        private final Move prevMove;
        private final int moves;
        private final int priority;

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

        public int compareTo(Move that) {
            if (that == null) {
                throw new NullPointerException();
            }
            return priority - that.getPriority();
        }
    }


    private boolean isSolvable;
    private Move moveOriginal;
    private Move moveTwin;

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

        // create initial moves for the original board and the twin board
        moveOriginal = new Move(board, null);
        moveTwin = new Move(board.twin(), null);

        // initialize minPQ for the original board and the twin board
        MinPQ<Move> minPQOriginal = new MinPQ<>();
        MinPQ<Move> minPQTwin = new MinPQ<>();
        minPQOriginal.insert(moveOriginal);
        minPQTwin.insert(moveTwin);

        while (true) {
            moveOriginal = minPQOriginal.delMin();
            moveTwin = minPQTwin.delMin();

            if (moveOriginal.getBoard().isGoal()) {
                isSolvable = true;
                break;
            }

            if (moveTwin.getBoard().isGoal()) {
                isSolvable = false;
                break;
            }

            putNeighborsToPQ(minPQOriginal, moveOriginal);
            putNeighborsToPQ(minPQTwin, moveTwin);
        }
    }


    /**
     * Add neighbors to minPQ, without adding duplicated board
     *
     * @param minPQ the minPQ to update on
     * @param move  current move with info on preMove and the current board
     */
    private void putNeighborsToPQ(MinPQ<Move> minPQ, Move move) {
        Move prevMove = move.getPrevMove();
        for (Board neighborBoard : move.getBoard().neighbors()) {
            if (prevMove != null && neighborBoard.equals(prevMove.getBoard())) {
                continue;
            }
            Move neighborMove = new Move(neighborBoard, move);
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
        // calculate number of moves
        if (!moveOriginal.getBoard().isGoal()) {
            return -1;
        }
        return moveOriginal.getMoves();
    }

    /**
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {

        if (!moveOriginal.getBoard().isGoal()) {
            return null;
        }

        Stack<Board> solution = new Stack<>();
        Move cur = moveOriginal;
        while (cur != null) {
            solution.push(cur.getBoard());
            cur = cur.getPrevMove();
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
