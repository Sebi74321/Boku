package main.logic;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;

import java.util.Scanner;
import java.util.Stack;

//Class to start a new game and control the game flow
public class GameController {
    Board currentBoard;
    int currentPlayer;
    Stack<Board> boardHistory = new Stack<>();

    public GameController() {
        startNewGame();
    }
    private void startNewGame() {
        currentBoard = new Board();
        boardHistory.clear();
        currentPlayer = 1;
        System.out.println("New game started");
        currentBoard.printBoard();
        System.out.println("Player 1(white) starts");
        }

    public void makeMove(int[] x) throws invalidMoveException {
        if(!currentBoard.isLegalMove(x[0],x[1])){
            System.out.println("Invalid move, try again");
            return;
        }
        boardHistory.push(new Board(currentBoard));
        currentBoard.setTile(x[0],x[1], currentPlayer);
        if(checkWin(x[0],x[1])){
            System.out.println("Player "+currentPlayer+" wins!");
            startNewGame();
            return;
        }
        if(checkCapture(x[0],x[1])){
            makeCaptureMove();
        }
        currentPlayer=currentPlayer ==1?2:1;
        currentBoard.printBoard();
        System.out.println("Player "+currentPlayer+"'s turn");
    }

    private void makeCaptureMove() {

    }

    private static int[] parseCoordinates(char x, int y){
        int[] coordinates=new int[2];
        x = String.valueOf(x).toUpperCase().charAt(0);
        coordinates[0]=x-'A';
        coordinates[1]=y-1;
        return coordinates;
    }

    private boolean checkWin(int x, int y) {
        int count = 0;
        //check in all 6 directions whether a line of 5 is formed
        //horizontal
        for (int i = -4; i < 5; i++) {
            if (x + i >= 0 && x + i < 10) {
                if (currentBoard.getBoard()[x + i][y] == currentPlayer) {
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
                if (currentBoard.getBoard()[x][y + i] == currentPlayer) {
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
                if (currentBoard.getBoard()[x + i][y + i] == currentPlayer) {
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

private boolean checkCapture(int x, int y){
        //check if a capture move is possible
        //capture moves are only possible if the current move sourronds a pair of enemy stones
        //check in all 6 directions
        //horizontal
    for (int i = -1; i < 2; i++) {
        if (x + i >= 0 && x + i < 10) {
            if (currentBoard.getBoard()[x + i][y] == 3-currentPlayer) {
                if(x+i*2>=0 && x+i*2<10){
                    if(currentBoard.getBoard()[x+i*2][y]==currentPlayer){
                        System.out.println("Capture move possible");
                        return true;
                    }
                }
            }
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
                    System.out.println("Exiting game");
                    break;
                }
                if(input.equals("reset")){
                    System.out.println("Resetting game");
                    gameController.startNewGame();
                    continue;
                }
                if(input.equals("undo")){
                    gameController.undo();
                    continue;
                }
                gameController.makeMove(parseCoordinates(input.charAt(0), Integer.parseInt(input.substring(1))));
            }
        } catch (invalidMoveException e) {
            e.printStackTrace();
        }
    }

    private void undo() {
        //resets the board to the state before the last move
        //if there are no moves to undo, nothing happens
        if (boardHistory.empty()) {
            System.out.println("No moves to undo");
            return;
        }
        else {
            currentBoard.setBoard(boardHistory.pop().getBoard());
            currentBoard.printBoard();
            currentPlayer=currentPlayer ==1?2:1;
            System.out.println("Player "+currentPlayer+"'s turn");
        }
    }
}
