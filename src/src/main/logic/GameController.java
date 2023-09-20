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
}
