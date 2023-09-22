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
    public void makeMove(int x, int y) throws invalidMoveException {
        currentBoard.setTile(x, y, currentPlayer);
         currentPlayer=currentPlayer ==1?2:1;
    }
    private static int[] parseCoordinates(char x, int y){
        int[] coordinates=new int[2];
        coordinates[0]=x-'A';
        coordinates[1]=y-1;
        return coordinates;
    }

    public static void main(String[] args) {
        GameController gameController=new GameController();
        try {
            gameController.makeMove(parseCoordinates('A',1)[0],parseCoordinates('A',1)[1]);
            gameController.makeMove(parseCoordinates('B',3)[0],parseCoordinates('B',3)[1]);
        } catch (invalidMoveException e) {
            e.printStackTrace();
        }
        gameController.currentBoard.printBoard();
    }
}
