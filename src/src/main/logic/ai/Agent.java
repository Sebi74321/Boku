package main.logic.ai;
//The Agent Class is used to choose and execute a strategy for the AI to use.
public class Agent {
    Strategy strategy;
    int player;

    public Agent(Strategy strategy, int player){
        this.strategy = strategy;
    }

    public int[] findNextMove(int[][] board){
       createTree(board);

        return null;
    }

    private Node createTree(int[][] board) {
        Node root = new Node(board, null, player, true);
        root.generateChildren();
        return root;
    }
}
