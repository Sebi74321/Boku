package main.logic;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;
import main.logic.ai.Agent;
import main.logic.ai.MiniMax;

import java.util.*;

//Class to start a new game and control the game flow
public final class GameController {

    private Board currentBoard;
    private int currentPlayer;
    private final Stack<Board> boardHistory = new Stack<>();


    public GameController() {
    }


    public GameController(GameController gameController) {
        currentBoard = new Board(gameController.getCurrentBoard());
        currentPlayer = gameController.getCurrentPlayer();
        boardHistory.addAll(gameController.getBoardHistory());
    }

    public Stack<Board> getBoardHistory() {
        return boardHistory;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
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
            return false;
        }
        List<int[]> captureMoves = checkCapture(coordinate, currentBoard, currentPlayer);
        currentBoard.unblockAll();
        if (!captureMoves.isEmpty()) {
            //if a capture move is already given, make that move (relevant for AI)
            if (coordinate.length == 4) {
                makeCaptureMove(captureMoves, Arrays.copyOfRange(coordinate, 2, 4));
                return false;
            }
            return true;

        }
        currentPlayer = currentPlayer == 1 ? 2 : 1;
        return false;
    }

    //makes a capture move
    //returns true if the move was successful
    private boolean makeCaptureMove(List<int[]> captureMoves, int[] move) throws invalidMoveException {

        for (int[] captureMove : captureMoves) {
            if (captureMove[0] == move[0] && captureMove[1] == move[1]) {
                currentBoard.setTile(move[0], move[1], 9);
                boardHistory.push(new Board(currentBoard));
                currentPlayer = currentPlayer == 1 ? 2 : 1;
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
                    if (count >= 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        //vertical
        for (int i = -4; i < 5; i++) {
            if (y + i >= 0 && y + i < 10) {
                if (board[x][y + i] == player) {
                    count++;
                    if (count >= 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        //diagonal 1
        for (int i = -4; i < 5; i++) {
            if (x + i >= 0 && x + i < 10 && y + i >= 0 && y + i < 10) {
                if (board[x + i][y + i] == player) {
                    count++;
                    if (count >= 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
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
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}};

        for (int[] direction : directions) {
            if (coordinate[0] + 3 * direction[0] < 0 || coordinate[0] + 3 * direction[0] > 9 || coordinate[1] + 3 * direction[1] < 0 || coordinate[1] + 3 * direction[1] > 9) {
                continue;
            }
            if (board.getTile(coordinate[0] + direction[0], coordinate[1] + direction[1]) == 3 - currentPlayer) {
                if (board.getTile(coordinate[0] + 2 * direction[0], coordinate[1] + 2 * direction[1]) == 3 - currentPlayer) {
                    if (board.getTile(coordinate[0] + 3 * direction[0], coordinate[1] + 3 * direction[1]) == currentPlayer) {
                        captureMoves.add(new int[]{coordinate[0] + direction[0], coordinate[1] + direction[1]});
                        captureMoves.add(new int[]{coordinate[0] + 2 * direction[0], coordinate[1] + 2 * direction[1]});
                    }
                }
            }
        }
        return captureMoves;
    }


    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.runGame();
    }

    private void runGame() {
        try {
            //setup game
            boolean capture;
            Scanner scanner = new Scanner(System.in);
            String input;
            Agent[] playerAgents = new Agent[2];
            System.out.println("select Player 1: Human or AI");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("AI")) {
                System.out.println("Enter search depth");
                int depth = scanner.nextInt();
                scanner.nextLine();
                Agent agent1 = new Agent(new MiniMax(), 1, depth);
                playerAgents[0] = agent1;
                System.out.println("Player 1 will be played by AI");
            } else {
                System.out.println("Player 1 will be played by Human");
            }
            System.out.println("select Player 2: Human or AI");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("AI")) {
                System.out.println("Enter search depth");
                int depth = scanner.nextInt();
                scanner.nextLine();
                Agent a2 = new Agent(new MiniMax(), 2, depth);
                playerAgents[1] = a2;
                System.out.println("Player 2 will be played by AI");
            } else {
                System.out.println("Player 2 will be played by Human");
            }
            startNewGame();
            label:
            while (true) {
                if (playerAgents[currentPlayer - 1] != null) {
                    int[] move = playerAgents[currentPlayer - 1].findNextMove(this);
                    makeMove(move);
                    if(checkWin(move[0], move[1], currentBoard.getBoard(), currentPlayer)){
                        System.out.println("Player " + (currentPlayer) + " won");
                        break;
                    }
                    System.out.println("Player " + (3-currentPlayer) + " made move " + (char) (move[0] + 'A') + (move[1] + 1));
                    if(move.length == 4){
                        System.out.println("Player " + (3-currentPlayer) + " captured " + (char) (move[2] + 'A') + (move[3] + 1));
                    }
                    currentBoard.printBoard();
                    System.out.println("Player " + currentPlayer + "'s turn");
                    continue;
                }
                input = scanner.nextLine();
                switch (input) {
                    case "exit":
                        System.out.println("Exiting game");
                        break label;
                    case "reset":
                        System.out.println("Resetting game");
                        startNewGame();
                        continue;
                    case "undo":
                        undo();
                        continue;
                }

                if (input.length() != 2) {
                    System.out.println("Invalid input, try again");
                    continue;
                }
                int[] move = parseCoordinates(input.charAt(0), Integer.parseInt(input.substring(1)));
                capture = makeMove(move);
                if(checkWin(move[0], move[1], currentBoard.getBoard(), currentPlayer)){
                    System.out.println("Player " + (currentPlayer) + " won");
                    break;
                }
                currentBoard.printBoard();
                System.out.println("Player " + currentPlayer + "'s turn");
                if (capture) {
                    currentBoard.printBoard();
                    System.out.println("Capture move possible");
                    while (capture) {
                        System.out.println("Enter capture move");
                        String captureInput = scanner.nextLine();

                        switch (captureInput) {
                            case "exit":
                                System.out.println("Exiting game");
                                break label;
                            case "reset":
                                System.out.println("Resetting game");
                                startNewGame();
                                capture = false;
                                continue;
                            case "undo":
                                undo();
                                capture = false;
                                continue;
                        }

                        capture = !makeCaptureMove(checkCapture(parseCoordinates(input.charAt(0), Integer.parseInt(input.substring(1))), currentBoard, currentPlayer), parseCoordinates(captureInput.charAt(0), Integer.parseInt(captureInput.substring(1))));
                        currentBoard.printBoard();
                        System.out.println("Player " + currentPlayer + "'s turn");
                    }

                }
            }
        } catch (invalidMoveException e) {
            e.printStackTrace();
        }
    }

    public void undo() {
        //resets the board to the state before the last move
        //if there are no moves to undo, nothing happens
        if (boardHistory.empty()) {
            System.out.println("No moves to undo");
        } else {
            //check if the last move was a capture move
            //if so, undo the move before that as well
            if (Arrays.stream(boardHistory.peek().getBoard()).anyMatch(a -> Arrays.stream(a).anyMatch(b -> b == 9))) {
                boardHistory.pop();
            }
            currentBoard.setBoard(boardHistory.pop().getBoard());
            currentBoard.printBoard();
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            System.out.println("Player " + currentPlayer + "'s turn");
        }
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }
}
