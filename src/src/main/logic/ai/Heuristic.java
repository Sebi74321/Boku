package main.logic.ai;

import static main.logic.GameController.checkWin;

//standard heuristic for Boku
public class Heuristic {

    static int score = 0;
    static int player;
    static int opponent;
    static boolean winningState = false;
    static boolean losingState = false;
    static int playerPieces = 0;
    static int opponentPieces = 0;
    static boolean captureMoveMade = false;
    static int possibleOpponentCaptures = 0;
    static int possibleCaptures = 0;


    public static int evaluate(int[][] board, int player) {
        Heuristic.player = player;
        getCurrentPiecesAndWinningState(board);
        if(winningState){
            return score = 1000;
        }
        else if(losingState){
            return score = -1000;
        }
        getPossibleCaptures(board);
        score = score - possibleOpponentCaptures*10 + possibleCaptures*10;
        return (int) (Math.random()*100);
    }

    private static void getPossibleCaptures(int[][] board) {
        //find possible captures consisting of two same pieces and one opposing piece

        //horizontal, vertical and diagonal
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //horizontal
                if (j < 8) {
                    if ((board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == opponent) || (board[i][j] == opponent && board[i][j + 1] == player && board[i][j + 2] == player)) {
                        possibleOpponentCaptures++;
                    }
                    if ((board[i][j] == opponent && board[i][j + 1] == opponent && board[i][j + 2] == player)||((board[i][j] == player && board[i][j + 1] == opponent && board[i][j + 2] == opponent))) {
                        possibleCaptures++;
                    }
                }
                //vertical
                if (i < 8) {
                    if ((board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == opponent)||(board[i][j] == opponent && board[i + 1][j] == player && board[i + 2][j] == player)) {
                        possibleOpponentCaptures++;
                    }
                    if ((board[i][j] == opponent && board[i + 1][j] == opponent && board[i + 2][j] == player)||(board[i][j] == player && board[i + 1][j] == opponent && board[i + 2][j] == opponent)) {
                        possibleCaptures++;
                    }
                    //diagonal
                    if (j < 8) {
                        if ((board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == opponent)||(board[i][j] == opponent && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player)) {
                            possibleOpponentCaptures++;
                        }
                        if ((board[i][j] == opponent && board[i + 1][j + 1] == opponent && board[i + 2][j + 2] == player)||(board[i][j] == player && board[i + 1][j + 1] == opponent && board[i + 2][j + 2] == opponent)) {
                            possibleCaptures++;
                        }
                    }

                }
            }
        }

    }

    private static void getCurrentPiecesAndWinningState(int[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(checkWin(i,j,board,player)){
                    winningState = true;
                    return;
                }else if(checkWin(i,j,board,opponent)){
                    losingState = true;
                    return;
                }
                if (board[i][j] == 1) {
                    playerPieces++;
                } else if (board[i][j] == 2) {
                    opponentPieces++;
                } else if (board[i][j] == 9) {
                    captureMoveMade = true;
                }
            }
        }
    }
}
