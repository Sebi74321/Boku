package main.logic;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;

import java.util.ArrayList;
import java.util.List;
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

    //returns true if a capture move has to be made
    public boolean makeMove(int[] coordinate) throws invalidMoveException {
        if (!currentBoard.isLegalMove(coordinate[0], coordinate[1])) {
            System.out.println("Invalid move, try again");
            return false;
        }
        boardHistory.push(new Board(currentBoard));
        currentBoard.setTile(coordinate[0], coordinate[1], currentPlayer);
        if (checkWin(coordinate[0], coordinate[1], currentBoard.getBoard(), currentPlayer)) {
            System.out.println("Player " + currentPlayer + " wins!");
            startNewGame();
            return false;
        }
        if (!checkCapture(coordinate, currentBoard, currentPlayer).isEmpty()) {
            currentBoard.printBoard();
            System.out.println("Capture move possible");
            return true;

        }
        currentPlayer = currentPlayer == 1 ? 2 : 1;
        currentBoard.unblockAll();
        currentBoard.printBoard();
        System.out.println("Player " + currentPlayer + "'s turn");
        return false;
    }

    //makes a capture move
    //returns true if the move was successful
    private boolean makeCaptureMove(List<int[]> captureMoves, int[] move) throws invalidMoveException {

        for (int[] captureMove : captureMoves) {
            if (captureMove[0] == move[0] && captureMove[1] == move[1]) {
                currentBoard.setTile(move[0], move[1], 9);
                currentPlayer = currentPlayer == 1 ? 2 : 1;
                currentBoard.printBoard();
                System.out.println("Player " + currentPlayer + "'s turn");
                return true;
            }
        }
        System.out.println("Invalid capture move, try again");
        return false;

    }


    private static int[] parseCoordinates(char x, int y) {
        int[] coordinates = new int[2];
        x = String.valueOf(x).toUpperCase().charAt(0);
        coordinates[0] = x - 'A';
        coordinates[1] = y - 1;
        return coordinates;
    }

    public static boolean checkWin(int x, int y, int[][] board, int player) {
        int count = 0;
        //check in all 6 directions whether a line of 5 is formed
        //horizontal
        for (int i = -4; i < 5; i++) {
            if (x + i >= 0 && x + i < 10) {
                if (board[x + i][y] == player) {
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
                if (board[x][y + i] == player) {
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
                if (board[x + i][y + i] == player) {
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


    public static List<int[]> checkCapture(int[] coordinate, Board board, int currentPlayer) {
        //check if a capture move is possible
        //capture moves are only possible if the current move surrounds a pair of enemy stones
        //check in all 6 directions
        //horizontal
        List<int[]> captureMoves = new ArrayList<>();
        board.getNeighbours(coordinate[0], coordinate[1]);
        for (int[] neighbour : board.getNeighbours(coordinate[0], coordinate[1])) {
            if (board.getBoard(neighbour[0], neighbour[1]) + currentPlayer == 3) {
                for (int[] neighbour2 : board.getNeighbours(neighbour[0], neighbour[1])) {
                    if (board.getBoard(neighbour2[0], neighbour2[1]) + currentPlayer == 3) {
                        for (int[] neighbour3 : board.getNeighbours(neighbour2[0], neighbour2[1])) {
                            if (board.getBoard(neighbour3[0], neighbour3[1]) == currentPlayer) {
                                captureMoves.add(neighbour);
                                captureMoves.add(neighbour2);
                            }
                        }

                    }
                }
            }
        }
        return captureMoves;
    }


    public static void main(String[] args) {
        GameController gameController = new GameController();
        boolean capture;
        try {
            Scanner scanner = new Scanner(System.in);
            String input;
            label:
            while (true) {
                input = scanner.nextLine();
                switch (input) {
                    case "exit":
                        System.out.println("Exiting game");
                        break label;
                    case "reset":
                        System.out.println("Resetting game");
                        gameController.startNewGame();
                        continue;
                    case "undo":
                        gameController.undo();
                        continue;
                }

                if (input.length() != 2) {
                    System.out.println("Invalid input, try again");
                    continue;
                }
                capture = gameController.makeMove(parseCoordinates(input.charAt(0), Integer.parseInt(input.substring(1))));
                if (capture) {
                    while (capture) {
                        System.out.println("Enter capture move");
                        System.out.println(checkCapture(parseCoordinates(input.charAt(0), Integer.parseInt(input.substring(1))), gameController.currentBoard, gameController.currentPlayer));
                        String captureInput = scanner.nextLine();

                        switch (captureInput) {
                            case "exit":
                                System.out.println("Exiting game");
                                break label;
                            case "reset":
                                System.out.println("Resetting game");
                                gameController.startNewGame();
                                capture = false;
                                continue;
                            case "undo":
                                gameController.undo();
                                capture = false;
                                continue;
                        }

                        capture = !gameController.makeCaptureMove(checkCapture(parseCoordinates(input.charAt(0), Integer.parseInt(input.substring(1))), gameController.currentBoard, gameController.currentPlayer), parseCoordinates(captureInput.charAt(0), Integer.parseInt(captureInput.substring(1))));
                    }

                }
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
        } else {
            currentBoard.setBoard(boardHistory.pop().getBoard());
            currentBoard.printBoard();
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            System.out.println("Player " + currentPlayer + "'s turn");
        }
    }
}
