package tictactoe;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MainForMinimax {

	// field = 0 is Empty, field = 1 is X, field = 2 is O
	public static void main(String[] args) {

		int[][] input = new int[3][3];
		int turn = 1;
		int iterations = 1;
		int numWon = 0;
		int numLost = 0;
		int numTied = 0;

		// generates the board
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				input[i][j] = 0;
			}
		}

		Square board[][] = TicTacToe.createBoard();

		// // First move of X should be one of the corners or center.
		// int firstX = Minimax.firstMove();
		// board.squares[firstX/3][firstX%3] = turn;
		// turn = 2;
		// //------------------------------------------

		for (int i = 0; i < iterations; i++) {
			while (!TicTacToe.isGameOver(board)) {
				if (turn == 1) {

					// Minimax for X's turn
					int move = Minimax.minimax(board, false).col;
					Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(move);
					board = TicTacToe.doValidMove(board, Square.X, destinationCoordinate);

					/*
					 * Random for X's turn ArrayList<Integer> actions =
					 * TicTacToe.getValidMoves(); int move =
					 * actions.get(ThreadLocalRandom.current().nextInt(actions.
					 * size())); board.squares[move / 3][move % 3] = turn;
					 */

					turn = 2;

				} else {

					// int move = Minimax.minimax(board.squares, true).col;
					// board.squares[move / 3][move % 3] = turn;

					ArrayList<Integer> actions = TicTacToe.getValidMoves(board);
					int move = Minimax.minimax(board, true).col;
					Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(move);
					board = TicTacToe.doValidMove(board, Square.O, destinationCoordinate);
					turn = 1;
				}
				// board.displayBoard(board.squares);
				// System.out.println();
			}
			if (TicTacToe.hasWon(board, Square.X)) {
				// System.out.println("X won!");
				numWon++;
			} else if (TicTacToe.hasWon(board, Square.O)) {
				// System.out.println("O won!");
				numLost++;
			} else {
				// System.out.println("It was a tie.");
				numTied++;
			}

		}
		System.out.println("Times Minimax Won: " + numWon);
		System.out.println("Times Minimax Tied: " + numTied);
		System.out.println("Times Minimax Lost: " + numLost);
	}
}
