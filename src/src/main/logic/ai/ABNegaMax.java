package main.logic.ai;

public class ABNegaMax extends Strategy {

    public int abNegaMax(int[][] state, int depth, int alpha, int beta, int player) {

        int evaluation = Heuristic.evaluate(state, player);
        if (evaluation == 1000 || evaluation == -1000 || depth == 0) {
            return Heuristic.evaluate(state, player);

        }
        return 1;
    }

    @Override
    public int execute(Object... params) {
        return abNegaMax((int[][]) params[0], (int) params[1], (int) params[2], (int) params[3], (int) params[4]);
    }
}
