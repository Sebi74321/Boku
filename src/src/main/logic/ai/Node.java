package main.logic.ai;
//The Node class is used to create a tree of possible moves for the AI to evaluate.
//Each Node has a Board state and a list of children Nodes.
//It also can be assigned a score, which is used by the AI to determine the best move to make.

import main.data.Board;

import java.util.ArrayList;
import java.util.List;

import static main.logic.GameController.checkCapture;


public class Node {
    int[][] board;
    int score;
    Node parent;
    List<Node> children;
    boolean isMaximizingPlayer;
    int player;
    boolean enablesCapture = false;


    public Node(int[][] board, Node parent,int player, boolean b) {
        this.board = board;
        this.parent = parent;
        this.player = player;
        this.isMaximizingPlayer = b;
        children = new ArrayList<>();
    }

    public void generateChildren() {
        //generate all possible moves for the current board state
        //add them to the children array
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10 ; j++) {
                if(board[i][j] == 0){
                    int[][] newBoard = board.clone();
                    newBoard[i][j] = player;
                    Node child = new Node(newBoard, this, player == 1 ? 2 : 1, !isMaximizingPlayer);
                    int[] coordinate = {i,j};
                    List<int[]> captureCoordinates = checkCapture(coordinate,new Board(newBoard),player);
                    if(!captureCoordinates.isEmpty()){
                        child.enablesCapture = true;

                    }
                    this.children.add(child);
                }
            }
        }
    }

}
