import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Board {
    private int[][] board; //stores int values
    private int N; //dimension


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.N = tiles[0].length;
        board = new int[N][N];
        for (int i = 0; i < tiles.length; i++){
            board[i] = tiles[i].clone();
        }
    }

    // string representation of this board
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(this.dimension()).append("\n");
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                str.append(this.board[i][j]).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }


    // board dimension n
    public int dimension(){
        return N;
    }

    // number of tiles out of place
    public int hamming(){
        int count = 0;
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                if (board[i][j] != ((i * dimension()) + j + 1) && board[i][j] != 0) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int sum = 0;

        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){

                int expectedValue = ((i * dimension()) + j + 1);

                if (board[i][j] != expectedValue && board[i][j] != 0){
                    int current = board[i][j];
                    current--;
                    int expectedI = current / dimension();
                    int expectedJ = current % dimension();
                    sum += Math.abs(expectedI - i) + Math.abs(expectedJ - j);
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board otherBoard = (Board) y;
        return Arrays.deepEquals(otherBoard.board, this.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        List<Board> neighbors = new ArrayList<>();
        int zeroI = 0;
        int zeroJ = 0;
        for(int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                if (board[i][j] == 0){
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }

        if (isValid(zeroI, zeroJ - 1)){
            int[][] tempBoard = copy(board);
            exchange(zeroI, zeroJ - 1,zeroI, zeroJ, tempBoard);
            neighbors.add(new Board(tempBoard));
        }
        if (isValid(zeroI, zeroJ + 1)){
            int[][] tempBoard = copy(board);
            exchange(zeroI, zeroJ + 1 ,zeroI, zeroJ, tempBoard);
            neighbors.add(new Board(tempBoard));
        }
        if (isValid(zeroI + 1, zeroJ)){
            int[][] tempBoard = copy(board);
            exchange(zeroI + 1, zeroJ,zeroI, zeroJ, tempBoard);
            neighbors.add(new Board(tempBoard));
        }
        if (isValid(zeroI - 1, zeroJ)){
            int[][] tempBoard = copy(board);
            exchange(zeroI - 1, zeroJ,zeroI, zeroJ, tempBoard);
            neighbors.add(new Board(tempBoard));
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board temp = new Board(board);
        if (temp.board[0][0] != 0 || temp.board[0][1] != 0){
            exchange(0, 0, 0, 1, temp.board);
        }else if (temp.board[1][1] != 0 || temp.board[1][2] != 0){
            exchange(1, 1, 1, 2, temp.board);
        }
        return temp;
    }

    public boolean isValid(int i, int j){
        if (i <= dimension() - 1 && j <= dimension() - 1 && i >= 0 && j >= 0) return true;
        else return false;
    }

    public void exchange(int fi, int fj, int li, int lj, int[][] blocks){
        if (!isValid(fi, fj) || !isValid(li, lj)) return;
        int first = blocks[fi][fj];
        blocks[fi][fj] = blocks[li][lj];
        blocks[li][lj] = first;
    }

    public int[][] copy(int[][] arr){
        int[][] copy = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
	// write your code here
    }
}
