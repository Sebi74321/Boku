package main.logic;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;

//Class to start a new game and control the game flow
public class GameController {
    Board currentBoard;
    int currentPlayer;

    public GameController() {
        startNewGame();
    }
    private void startNewGame() {
        currentBoard = new Board();
        currentPlayer = 1;
        }
    public void makeMove(int[] x) throws invalidMoveException {
        currentBoard.setTile(x[0],x[1], currentPlayer);
         currentPlayer=currentPlayer ==1?2:1;
    }
    private static int[] parseCoordinates(char x, int y){
        int[] coordinates=new int[2];
        x = String.valueOf(x).toUpperCase().charAt(0);
        coordinates[0]=x-'A';
        coordinates[1]=y-1;
        return coordinates;
    }

    private boolean checkWin(int x, int y, int player) {
        int count = 0;
        //check in all 6 directions whether a line of 5 is formed
        //horizontal
        for (int i = -4; i < 5; i++) {
            if (x + i >= 0 && x + i < 10) {
                if (currentBoard.getBoard()[x + i][y] == player) {
                    count++;
                } else {
                    count = 0;
                }
            }
            if (count >= 5) {
                return true;
            }
        }
        //vertical
        for (int i = -4; i < 5; i++) {
            if (y + i >= 0 && y + i < 10) {
                if (currentBoard.getBoard()[x][y + i] == player) {
                    count++;
                } else {
                    count = 0;
                }
            }
            if (count >= 5) {
                return true;
            }
        }
        //diagonal 1
        for (int i = -4; i < 5; i++) {
            if (x + i >= 0 && x + i < 10 && y + i >= 0 && y + i < 10) {
                if (currentBoard.getBoard()[x + i][y + i] == player) {
                    count++;
                } else {
                    count = 0;
                }
            }
            if (count >= 5) {
                return true;
            }
        }
        return false;
    }




    public static void main(String[] args) {
        GameController gameController=new GameController();
        try {
            gameController.makeMove(parseCoordinates('A',1));
            gameController.makeMove(parseCoordinates('B',3));
            gameController.makeMove(parseCoordinates('B',2));
            gameController.makeMove(parseCoordinates('F',5));
            gameController.makeMove(parseCoordinates('C',3));
            gameController.makeMove(parseCoordinates('B',5));
            gameController.makeMove(parseCoordinates('D',4));
            gameController.makeMove(parseCoordinates('J',7));
            gameController.makeMove(parseCoordinates('E',5));
            gameController.makeMove(parseCoordinates('H',5));
            gameController.makeMove(parseCoordinates('F',6));
            System.out.println(gameController.checkWin(parseCoordinates('F',6)[0],parseCoordinates('F',6)[1],1));
        } catch (invalidMoveException e) {
            e.printStackTrace();
        }
        gameController.currentBoard.printBoard();
    }
}
