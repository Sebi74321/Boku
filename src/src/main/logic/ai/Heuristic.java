package main.logic.ai;

import main.data.Board;

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
    static int playerChains2 = 0;
    static int opponentChains2 = 0;
    static int playerChains3 = 0;
    static int opponentChains3 = 0;
    static int playerChains4 = 0;


    public static int evaluate(int[][] board, int player) {
        resetStats();
        Heuristic.player = player;
        Heuristic.opponent = 3 - player;
        getCurrentPiecesAndWinningState(board);
        checkForChains(board);
        if (winningState) {
            return score = 10000;
        } else if (losingState) {
            return score = -10000;
        }
        getPossibleCaptures(board);
        score = score + playerPieces - opponentPieces;
        score = score + playerChains2 * 2 - opponentChains2 * 2;
        score = score + playerChains3 * 3 - opponentChains3 * 3;
        score = score + playerChains4 * 5;
        if (captureMoveMade) {
            score = score + 5;
        }
        score = score - possibleOpponentCaptures * 3 + (possibleCaptures - 1) * 3;
        return score;
    }

    private static void resetStats() {
        score = 0;
        winningState = false;
        losingState = false;
        playerPieces = 0;
        opponentPieces = 0;
        captureMoveMade = false;
        possibleOpponentCaptures = 0;
        possibleCaptures = 0;
        playerChains2 = 0;
        opponentChains2 = 0;
        playerChains3 = 0;
        opponentChains3 = 0;
        playerChains4 = 0;
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
                    if ((board[i][j] == opponent && board[i][j + 1] == opponent && board[i][j + 2] == player) || ((board[i][j] == player && board[i][j + 1] == opponent && board[i][j + 2] == opponent))) {
                        possibleCaptures++;
                    }
                }
                //vertical
                if (i < 8) {
                    if ((board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == opponent) || (board[i][j] == opponent && board[i + 1][j] == player && board[i + 2][j] == player)) {
                        possibleOpponentCaptures++;
                    }
                    if ((board[i][j] == opponent && board[i + 1][j] == opponent && board[i + 2][j] == player) || (board[i][j] == player && board[i + 1][j] == opponent && board[i + 2][j] == opponent)) {
                        possibleCaptures++;
                    }
                    //diagonal
                    if (j < 8) {
                        if ((board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == opponent) || (board[i][j] == opponent && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player)) {
                            possibleOpponentCaptures++;
                        }
                        if ((board[i][j] == opponent && board[i + 1][j + 1] == opponent && board[i + 2][j + 2] == player) || (board[i][j] == player && board[i + 1][j + 1] == opponent && board[i + 2][j + 2] == opponent)) {
                            possibleCaptures++;
                        }
                    }

                }
            }
        }

    }

    private static void getCurrentPiecesAndWinningState(int[][] board) {
        Board b = new Board(board);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (checkWin(i, j, board, player)) {
                    winningState = true;
                    return;
                } else if (checkWin(i, j, board, opponent)) {
                    losingState = true;
                    return;
                }
                if (board[i][j] == 1) {
                    //weighs tiles with more neighbours higher
                    playerPieces += b.getNeighbours(i, j).size();

                } else if (board[i][j] == 2) {
                    opponentPieces += b.getNeighbours(i, j).size();
                } else if (board[i][j] == 9) {
                    captureMoveMade = true;
                }
            }
        }
    }

    private static void checkForChains(int[][] board) {
        //check for chains of 3 or more pieces
        //horizontal, vertical and diagonal
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //horizontal
                if (j < 8) {
                    //check for chain of 2 with 2 empty spaces on each side
                    if (board[i][j] == player && board[i][j + 1] == player) {
                        if ((j > 0 && board[i][j - 1] == 0) || (j < 7 && board[i][j + 2] == 0)) {
                            playerChains2++;
                        }
                        if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player) {
                            if ((j > 0 && board[i][j - 1] == 0) || (j < 7 && board[i][j + 3] == 0)) {
                                playerChains3++;
                            }
                            if (j < 7 && board[i][j + 3] == player) {
                                if (j < 6 && board[i][j + 4] == 0 || j > 0 && board[i][j - 1] == 0) {
                                    playerChains4++;
                                }
                            }

                        }
                    } else if (board[i][j] == opponent && board[i][j + 1] == opponent) {
                        if ((j > 0 && board[i][j - 1] == 0) || (j < 7 && board[i][j + 2] == 0)) {
                            opponentChains2++;
                        }
                    } else if (board[i][j] == opponent && board[i][j + 1] == opponent && board[i][j + 2] == opponent) {
                        if ((j > 0 && board[i][j - 1] == 0) || (j < 7 && board[i][j + 3] == 0)) {
                            opponentChains3++;
                        }
                        if (j < 7) {
                            if (board[i][j + 3] == 0) {
                                losingState = true;
                            } else if (board[i][j + 3] == opponent) {
                                if (j < 6 && board[i][j + 4] == 0 || j > 0 && board[i][j - 1] == 0) {
                                    losingState = true;
                                }
                            }
                        }
                    }
                }
                //vertical
                if (i < 8) {
                    if (board[i][j] == player && board[i + 1][j] == player) {
                        if ((i > 0 && board[i - 1][j] == 0) || (i < 7 && board[i + 2][j] == 0)) {
                            playerChains2++;
                        }
                    } else if (board[i][j] == opponent && board[i + 1][j] == opponent) {
                        if ((i > 0 && board[i - 1][j] == 0) || (i < 7 && board[i + 2][j] == 0)) {
                            opponentChains2++;
                        }
                    }
                    if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player) {
                        if ((i > 0 && board[i - 1][j] == 0) || (i < 7 && board[i + 3][j] == 0)) {
                            playerChains3++;
                        }
                        if (i < 7) {
                            if (board[i + 3][j] == 0) {
                                playerChains3++;
                            }
                            if (board[i + 3][j] == player) {
                                if (i < 6 && board[i + 4][j] == 0 || i > 0 && board[i - 1][j] == 0) {
                                    playerChains4++;
                                }
                            }
                        }
                    } else if (board[i][j] == opponent && board[i + 1][j] == opponent && board[i + 2][j] == opponent) {
                        if ((i > 0 && board[i - 1][j] == 0) || (i < 7 && board[i + 3][j] == 0)) {
                            opponentChains3++;
                        }
                        if (i < 7) {
                            if (board[i + 3][j] == 0) {
                                losingState = true;
                            } else if (board[i + 3][j] == opponent) {
                                if (i < 6 && board[i + 4][j] == 0 || i > 0 && board[i - 1][j] == 0) {
                                    losingState = true;
                                }
                            }
                        }
                    }
                    //diagonal
                    if (j < 8) {
                        if (board[i][j] == player && board[i + 1][j + 1] == player) {
                            if ((i > 0 && j > 0 && board[i - 1][j - 1] == 0) || (i < 7 && j < 7 && board[i + 2][j + 2] == 0)) {
                                playerChains2++;
                            }
                        } else if (board[i][j] == opponent && board[i + 1][j + 1] == opponent) {
                            if ((i > 0 && j > 0 && board[i - 1][j - 1] == 0) || (i < 7 && j < 7 && board[i + 2][j + 2] == 0)) {
                                opponentChains2++;
                            }
                        }
                            if (board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player) {
                                if ((i > 0 && j > 0 && board[i - 1][j - 1] == 0) || (i < 7 && j < 7 && board[i + 3][j + 3] == 0)) {
                                    playerChains3++;
                                }
                                if (i < 7 && j < 7) {
                                    if (board[i + 3][j + 3] == 0) {
                                        playerChains4++;
                                    }
                                    if (board[i + 3][j + 3] == player) {
                                        if (i < 6 && j < 6 && board[i + 4][j + 4] == 0 || i > 0 && j > 0 && board[i - 1][j - 1] == 0) {
                                            playerChains4++;
                                        }
                                    }
                                }
                            } else if (board[i][j] == opponent && board[i + 1][j + 1] == opponent && board[i + 2][j + 2] == opponent) {
                                if ((i > 0 && j > 0 && board[i - 1][j - 1] == 0) || (i < 7 && j < 7 && board[i + 3][j + 3] == 0)) {
                                    opponentChains3++;
                                }
                                if (i < 7 && j < 7) {
                                    if (board[i + 3][j + 3] == 0) {
                                        losingState = true;
                                    } else if (board[i + 3][j + 3] == opponent) {
                                        if (i < 6 && j < 6 && board[i + 4][j + 4] == 0 || i > 0 && j > 0 && board[i - 1][j - 1] == 0) {
                                            losingState = true;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
