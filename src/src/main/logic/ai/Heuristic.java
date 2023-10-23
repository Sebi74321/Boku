package main.logic.ai;

import static main.logic.GameController.checkWin;

//standard heuristic for Boku
public class Heuristic {

    int score = 0;
    int player;
    int opponent;
    boolean winningState = false;
    boolean losingState = false;
    int playerPieces = 0;
    int opponentPieces = 0;
    boolean captureMoveMade = false;
    int possibleOpponentCaptures = 0;
    int possibleCaptures = 0;


    public Heuristic(int player) {
        this.player = player;
        opponent = player == 1 ? 2 : 1;
    }

    public int evaluate(int[][] board) {
        getCurrentPiecesAndWinningState(board);
        if(winningState){
            return score = 100000;
        }
        else if(losingState){
            return score = -100000;
        }
        getPossibleCaptures(board);


        return score;
    }

    private void getPossibleCaptures(int[][] board) {
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

    private void getCurrentPiecesAndWinningState(int[][] board) {
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
