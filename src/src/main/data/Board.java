package main.data;

import main.data.Exceptions.invalidMoveException;

public class Board {
//contains a 2D array of bytes representing the board
//0= empty, 1= player 1(white), 2= player 2(black)
    private byte[][] board;

    public Board() {
        board = new byte[10][10];
    }

    public byte[][] getBoard() {
        return board;
    }

    public void setBoard(byte[][] board) {
        this.board = new byte[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, 10);
        }
    }

    public void setTile(int x, int y, byte value) throws invalidMoveException {
        if(Math.abs(x-y)>5){
           throw new invalidMoveException("Invalid coordinates");
        }
        board[x][y] = value;
    }

    public byte getBoard(int x, int y) {
        return board[x][y];
    }

    public boolean isLegalMove(int x, int y, byte value){
        if(Math.abs(x-y)>5){
            return false;
        }
        else return board[x][y] == 0;
    }

}