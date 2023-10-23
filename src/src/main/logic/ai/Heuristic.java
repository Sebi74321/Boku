package main.logic.ai;
//standard heuristic for Boku
public class Heuristic {

    int score = 0;
    int player;
    int opponent;
    int playerPieces = 0;
    int opponentPieces = 0;
    boolean captureMoveMade = false;
    int possibleOpponentCaptures = 0;

    public Heuristic(int player) {
        this.player = player;
    }

    public int evaluate(int[][] board) {
        opponent = player == 1 ? 2 : 1;

        getCurrentPieces(board);
        getPossibleCaptures(board);



        return score;
    }

    private void getPossibleCaptures(int[][] board) {
        //find pairs of 2 own pieces with 1 opponents piece one either side

        //horizontal, vertical and diagonal
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //horizontal
                if(j<8){
                    if (board[i][j]==player && board[i][j+1]==player && board[i][j+2]==opponent){
                        possibleOpponentCaptures++;
                    }
                    if (board[i][j]==opponent && board[i][j+1]==player && board[i][j+2]==player){
                        possibleOpponentCaptures++;
                    }
                }
                //vertical
                if(i<8){
                    if (board[i][j]==player && board[i+1][j]==player && board[i+2][j]==opponent){
                        possibleOpponentCaptures++;
                    }
                    if (board[i][j]==opponent && board[i+1][j]==player && board[i+2][j]==player){
                        possibleOpponentCaptures++;
                    }
                    //diagonal
                    if(j<8){
                        if (board[i][j]==player && board[i+1][j+1]==player && board[i+2][j+2]==opponent){
                            possibleOpponentCaptures++;
                        }
                        if (board[i][j]==opponent && board[i+1][j+1]==player && board[i+2][j+2]==player){
                            possibleOpponentCaptures++;
                        }
                    }

                }
            }
        }

    }

    private void getCurrentPieces(int[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j]==1){
                    playerPieces++;
                } else if (board[i][j]==2){
                    opponentPieces++;
                }
                else if(board[i][j]==9){
                    captureMoveMade = true;
                }
            }
        }
    }
}
