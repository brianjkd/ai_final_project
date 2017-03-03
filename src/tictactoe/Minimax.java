package tictactoe;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Minimax {
	
	int turn;
	
	public static Vector2D minimax(Square[][] board, boolean minTurn) {
		int bestMove, highScore;
		Square playerValue;
		
		bestMove = -1;
		playerValue= Square.X;
		
		if (minTurn) {
			highScore = Integer.MAX_VALUE;
			playerValue = Square.O;
		}
		else {
			highScore = Integer.MIN_VALUE;
			playerValue = Square.X;
		}
		
		Vector2D result = new Vector2D(highScore, -1);
		
		ArrayList<Integer> actions = TicTacToe.getValidMoves(board);
		
		if (actions.isEmpty() || TicTacToe.isGameOver(board)) {
			highScore = TicTacToe.getBoardSum(board);
			result.row = highScore;
		}
		else {
			if (minTurn) {
				for (int a : actions) {
					board[a/3][a%3] = playerValue;
					Vector2D moveResult = minimax(board, false);
					int moveScore = moveResult.row;
					if (moveScore < highScore) {
						highScore = moveScore;
						bestMove = a;
						result.row = highScore;
						result.col = bestMove;
					}
					board[a/3][a%3] = Square.EMPTY;
				}
			}
			else {
				for (int a : actions) {
					board[a/3][a%3] = playerValue;
					Vector2D moveResult = minimax(board, true);
					int moveScore = moveResult.row;
					if (moveScore > highScore) {
						highScore = moveScore;
						bestMove = a;
						result.row = highScore;
						result.col = bestMove;
					}
					board[a/3][a%3] = Square.EMPTY;
				}
			}
		}

		return result;
	}
	
	/*
	 * This returns the first move that X should do which would be either one of the four corners
	 * or the center square.
	 */
	public static int firstMove(){
		int min = 0;
		int max = 4;
		int initialMove = ThreadLocalRandom.current().nextInt(min, max + 1);
		
		initialMove = initialMove * 2; // don't forget the array goes from [0,8]
				
		return initialMove;
	}
	
	/*
	 * This method returns the move the min player (O in this case) should do
	 * to minimize the results of Max.
	 */
	public static int min(int[][] board) {
		int bestMove = 0;
		
		return bestMove;
	}
	
	/*
	 * This method returns the move the max player (X in this case) should do
	 * to try to win the game.
	 */
	public static int max(int[][] board) {
		int bestMove = 0;
		
		
		return bestMove;
	}


}
