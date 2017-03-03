package tictactoe;

import java.util.ArrayList;

public class FitnessFunctions {
	
	public static final int GOOD = 100;
	public static final int BAD = 0;
	
	
	
	public static int getBoardFitness(Square[][] board, Square aspect, int destination,
		FitnessFunctionType fitnessType) {
			Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
		
			if (!TicTacToe.isMoveValid(board, destinationCoordinate)) {
				// invalid move
				return BAD;
			} 
			else {
				if (fitnessType == FitnessFunctionType.ValidAndWinningMoves){
					return howGoodWasMove(board, aspect, destinationCoordinate);
					
				}
				else if (fitnessType == FitnessFunctionType.OptimalMoves){
					return evaluateByMinimax(board, aspect, destinationCoordinate);
				}
				else return GOOD;
			}
		}
	

	public static int howGoodWasMove(Square[][] board, Square aspect, Vector2D destination) {
		// If we made a move that did not result in a win,
		// check to see if a move that could result in a win was possible.
		// If true, return a low fitness score
		Square [][] result = TicTacToe.doValidMove(board, aspect, destination);
		if (!TicTacToe.hasWon(result, aspect)){
			ArrayList<Vector2D> emptySquares = TicTacToe.getEmptyCoordinates(board);
			for (Vector2D empty : emptySquares){
				Square [][] alternateBoard = TicTacToe.doValidMove(board, aspect, empty);
				if (TicTacToe.hasWon(alternateBoard, aspect)){
					return BAD; // not a good decision so bad fitness					
				}	
			}
		}
		return GOOD; // move was fine
	}
	
	

	public static int evaluateByMinimax(Square[][] board, Square aspect, Vector2D destination){
		boolean minTurn = (aspect == Square.O ? true : false);
		Vector2D move = Minimax.minimax(board, minTurn);
		Vector2D minimaxDestination = TicTacToe.convertIndexToRowCol(move.col);
		
		if (destination.row == minimaxDestination.row
				&& destination.col == minimaxDestination.col){
			return GOOD;
		}
		
		else return BAD;
	}

}
