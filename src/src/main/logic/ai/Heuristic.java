package main.logic.ai;
//standard heuristic for Boku
public class Heuristic {

    int score = 0;
    int opponent ;
    int playerPieces = 0;
    int opponentPieces = 0;
    int playerBlocked = 0;
    int opponentBlocked = 0;
    int playerMoves = 0;
    int opponentMoves = 0;
    int playerCaptures = 0;
    int opponentCaptures = 0;
    int playerCaptured = 0;
    int opponentCaptured = 0;
    int playerCapturedBlocked = 0;
    int opponentCapturedBlocked = 0;
    int playerCapturedMoves = 0;
    int opponentCapturedMoves = 0;
    public int evaluate(int[][] board, int player) {
        opponent = player == 1 ? 2 : 1;

        getCurrentPieces(board);

        return score;
    }

    private void getCurrentPieces(int[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j]==1){
                    playerPieces++;
                } else if (board[i][j]==2){
                    opponentPieces++;
                }
            }
        }
    }
}
