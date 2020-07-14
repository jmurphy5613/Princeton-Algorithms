import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private class SearchNode{
        private final int manhattanMoves;
        private int moves;
        private SearchNode prev;
        private Board board;

        public SearchNode(Board first){
            manhattanMoves = first.manhattan();
            prev = null;
            this.board = first;
            moves = 0;
        }
    }
    private SearchNode goalNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null) throw new IllegalArgumentException();
        Order ordering = new Order();
        MinPQ<SearchNode> minPQ = new MinPQ<>(ordering);
        SearchNode Firstnode = new SearchNode(initial);
        minPQ.insert(Firstnode);

        SearchNode min = minPQ.delMin();

        while (!min.board.isGoal()){
            for (Board board : min.board.neighbors()){
                if (min.prev == null || !board.equals(min.prev.board)){
                    SearchNode current = new SearchNode(board);
                    current.moves = min.moves + 1;
                    current.prev = min;
                    minPQ.insert(current);
                }
            }
            min = minPQ.delMin();
        }
        goalNode = min;
        //if the board is not solvable

    }
    //The Manhattan priority function is the Manhattan distance of a board plus the number of moves made so far to get to the search node.
    private class Order implements Comparator<SearchNode>{
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int o1t = o1.manhattanMoves + o1.moves;
            int o2t = o2.manhattanMoves + o1.moves;
            return Integer.compare(o1t, o2t);
        }
    }




    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return goalNode == null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if (isSolvable()) return -1;
        else return goalNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if (isSolvable()) return null;
        List<Board> list = new ArrayList<>();

        for (SearchNode n = goalNode; n != null; n = n.prev){
            list.add(n.board);
        }
        return list;
    }

    // test client (see below)
    public static void main(String[] args){
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
        if (solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
