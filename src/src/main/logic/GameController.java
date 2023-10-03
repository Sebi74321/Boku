package main.logic;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;

import java.util.Scanner;

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
        System.out.println("New game started");
        System.out.println("Player 1(white) starts");
        }

    public void makeMove(int[] x) throws invalidMoveException {
        currentBoard.setTile(x[0],x[1], currentPlayer);
        if(checkWin(x[0],x[1],currentPlayer)){
            System.out.println("Player "+currentPlayer+" wins!");
            startNewGame();
            return;
        }
        currentPlayer=currentPlayer ==1?2:1;
        currentBoard.printBoard();
        System.out.println("Player "+currentPlayer+"'s turn");
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
            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                input = scanner.nextLine();
                if (input.equals("exit")) {
                    break;
                }
                if(input.equals("reset")){
                    gameController.startNewGame();
                    continue;
                }
                if(input.equals("undo")){
                    undo();
                    continue;
                }
                gameController.makeMove(parseCoordinates(input.charAt(0), Integer.parseInt(input.substring(1))));
            }
        } catch (invalidMoveException e) {
            e.printStackTrace();
        }
        gameController.currentBoard.printBoard();
    }

    private static void undo() {
        //resets the board to the state before the last move

    }
}
