package main.logic;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;

//Class to start a new game and control the game flow
public class GameController {
    Board currentBoard;
    byte currentPlayer;

    public GameController() {
        startNewGame();
    }
    private void startNewGame() {
        currentBoard = new Board();
        currentPlayer = 1;
        }
    public void makeMove(int x, int y) throws invalidMoveException {
        currentBoard.setTile(x, y, currentPlayer);
        if(currentPlayer == 1) {
            currentPlayer = 2;
        }
        else {
            currentPlayer = 1;
        }
    }
}
