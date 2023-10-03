package main.data;

import main.data.Exceptions.invalidMoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    //contains a 2D array of ints representing the board
//0= empty, 1= player 1(white), 2= player 2(black) -1= invalid
    private int[][] board;

    public Board() {
        board = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Math.abs(i - j) > 5) {
                    board[i][j] = -1;
                }
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = new int[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, 10);
        }
    }

    public void setTile(int x, int y, int value) throws invalidMoveException {
        if (x>9||y>9||Math.abs(x - y) > 5) {
            throw new invalidMoveException("Invalid coordinates");
        }
        board[x][y] = value;
    }

    //returns the coordinates of all valid neighbours of the given tile
    public List<int[]> getNeighbours(int x, int y) {
        List<int[]> neighbours = new ArrayList<>();
        int count = 0;
        for (int i = -1; i < 2; i++) {
            if (x + i >= 0 && x + i < 10) {
                for (int j = -1; j < 2; j++) {
                    if (y + j >= 0 && y + j < 10 && i + j != 0) {
                        neighbours.add(new int[]{x + i, y + j});
                        count++;
                    }
                }
            }
        }
        return neighbours;
    }

    public int getBoard(int x, int y) {
        return board[x][y];
    }

    public boolean isLegalMove(int x, int y) {
        if (Math.abs(x - y) > 5) {
            return false;
        } else return board[x][y] == 0;
    }

    public void printBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printNeighbours(int x, int y) {
        List<int[]> neighbours = getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            System.out.println(Arrays.toString(neighbour));
        }
    }
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();
        board.printNeighbours(1,1);
    }
}