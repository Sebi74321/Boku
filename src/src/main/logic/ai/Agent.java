package main.logic.ai;

import main.data.Board;
import main.data.Exceptions.invalidMoveException;
import main.logic.GameController;

import java.util.Arrays;
import java.util.List;

//The Agent Class is used to choose and execute a strategy for the AI to use.
public class Agent {
    Strategy strategy;
    int depth;
    int player;

    public Agent(Strategy strategy, int player, int depth){
        this.strategy = strategy;
        this.player = player;
        this.depth = depth;
    }

    public int[] findNextMove(GameController currentGame) throws invalidMoveException {
        Board board = currentGame.getCurrentBoard();
        List<int[]> nextMoves = board.getAvailableMoves(player);
        int[] moveEval = new int[nextMoves.size()];
        for(int i = 0; i < nextMoves.size(); i++){
            int[] move = nextMoves.get(i);
            GameController simulatedGame = new GameController(currentGame);
            simulatedGame.makeMove(move);
            if(strategy instanceof MiniMax){
                moveEval[i] = strategy.execute(simulatedGame, depth, player, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            else{
                moveEval[i] = strategy.execute(simulatedGame, move);
            }
        }
        int bestMoveIndex = 0;
        int bestMove = Integer.MIN_VALUE;
        for (int i = 0; i < moveEval.length; i++) {
            if (moveEval[i] > bestMove) {
                bestMove = moveEval[i];
                bestMoveIndex = i;
            }
        }
        return nextMoves.get(bestMoveIndex);
    }

    private List<int[][]> findNextMoves(int[][] board, int player) {

        return null;
    }
}
