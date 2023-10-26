package main.logic.ai;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;
import main.logic.GameController;

import java.util.List;

//Minimax algorithm for Boku
public class MiniMax extends Strategy {

        // Returns optimal value for
// current player (Initially called
// for root and maximizer)
        private int abMiniMax(GameController game, int depth, int player, boolean isMaxPlayer, int alpha, int beta) throws invalidMoveException {

            Board currentBoard = game.getCurrentBoard();
            int evaluation = Heuristic.evaluate(currentBoard.getBoard(), player);
            if (depth == 0 || evaluation == 1000 || evaluation == -1000) {
                return evaluation;
            }
            int bestScore;
            if (isMaxPlayer) {
                bestScore = Integer.MIN_VALUE;
                List<int[]> availableMoves = currentBoard.getAvailableMoves(player);
                for (int[] move : availableMoves) {
                    game.makeMove(move);
                    int score = abMiniMax( game, depth - 1,3-player, false, alpha, beta);
                    bestScore = Math.max(score, bestScore);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) {
//                    System.out.println("Pruned");
                        break;
                    }
                }
            } else {
                bestScore = Integer.MAX_VALUE;
                List<int[]> availableMoves = currentBoard.getAvailableMoves(player);
                for (int[] move : availableMoves) {
                    game.makeMove(move);
                    int score = abMiniMax( game,depth - 1, 3-player, true, alpha, beta);
                    bestScore = Math.min(score, bestScore);
                    beta = Math.min(beta, score);
                    if (beta <= alpha) {
//                    System.out.println("Pruned");
                        break;
                    }
                }
            }
            return bestScore;
        }


    @Override
    public int execute(Object... params) throws invalidMoveException {
        return abMiniMax((GameController) params[0], (int) params[1], (int) params[2], (boolean) params[3], (int) params[4], (int) params[5]);
    }
}
