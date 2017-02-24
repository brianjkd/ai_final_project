package demo;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {
	
	public static void displayBoard(Square[][] board){
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board[0].length; j++){
				if (board[i][j] == Square.EMPTY)
					System.out.print("  ");
				else 
					System.out.print(board[i][j] + " ");
			}
			System.out.println();	
		}
	}
	
	public static Square [][] createBoard(){
		Square [][] board = new Square [3][3];
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board[0].length; j++){
				board[i][j] = Square.EMPTY;
				}	
			}
		return board;
	}
	
	static Square [][] duplicateBoard(Square [][] original){
		Square [][] duplicate = new Square [3][3];
		for(int i = 0; i < original[0].length; i++){
			for(int j = 0; j < original[0].length; j++){
				duplicate[i][j] = original[i][j];
			}	
		}
		return duplicate;
	}
	
	public static Square [][] doValidMove(Square [][] board, Square type, Vector2D destination){
		Square [][] result = duplicateBoard(board);
		result[destination.row][destination.col] = type;
		return result;
	}
	
	public static boolean hasWon(Square [][] board, Square type){
		// check vertical
		if (board[0][0].get() + board[1][0].get() + board[2][0].get() == 3 * type.get()) return true;
		if (board[0][1].get() + board[1][1].get() + board[2][1].get() == 3 * type.get()) return true;
		if (board[0][2].get() + board[1][2].get() + board[2][2].get() == 3 * type.get()) return true;
		
		// check horizontal
		if (board[0][0].get() + board[0][1].get() + board[0][2].get() == 3 * type.get()) return true;
		if (board[1][0].get() + board[1][1].get() + board[1][2].get() == 3 * type.get()) return true;
		if (board[2][0].get() + board[2][1].get() + board[2][2].get() == 3 * type.get()) return true;
		
		//check diagonal
		if(board[0][0].get() + board[1][1].get() + board[2][2].get() == 3 * type.get()) return true;
		if (board[0][2].get() + board[1][1].get() + board[0][0].get() == 3 * type.get()) return true;
		return false;
	}
	
	private static int getBoardSum(Square [][] board, Square type){
		//81 is the value of a board where all squares are of the given type.
		//This is just to avoid the sum being negative (if all square of not of the given type).
		int sum = 81;
		
		// check vertical
		sum += board[0][0].get() + board[1][0].get() + board[2][0].get();
		sum += board[0][1].get() + board[1][1].get() + board[2][1].get();
		sum += board[0][2].get() + board[1][2].get() + board[2][2].get();
		
		// check horizontal
		sum += board[0][0].get() + board[0][1].get() + board[0][2].get();
		sum += board[1][0].get() + board[1][1].get() + board[1][2].get();
		sum += board[2][0].get() + board[2][1].get() + board[2][2].get();
		
		//check diagonal
		sum += board[0][0].get() + board[1][1].get() + board[2][2].get();
		sum += board[0][2].get() + board[1][1].get() + board[0][0].get();

		return sum;
	}
	
	
	public static boolean isMoveValid(Square [][] board, Vector2D destination){
		return board[destination.row][destination.col] == Square.EMPTY;
	}
	
	static Vector2D convertIndexToRowCol(int index){
		// index lies between [0,8] inclusively
		int row = (index) / 3;
		int col = (index) % 3;
		Vector2D c = new Vector2D(row, col);
		return c;
	}
	
	public static int getBoardFitness(Square [][] board, Square aspect, int destination) {
		int bad = 0;
		int winValue = 100;
		
		Vector2D destinationCoordinate = convertIndexToRowCol(destination);
		// invalid move
		if (!isMoveValid(board, destinationCoordinate )){
			return bad;
		}
		
		Square [] [] resultingBoard = doValidMove(board, aspect, destinationCoordinate);
		
		// did win?
		if (hasWon(resultingBoard, aspect)) {
			return winValue;
		}
		
		// TODO need to make sure the board is not already full/ we have drawn
		// though, we should handles this else where, like we should not be 
		// evaluating the neural network when the game is already complete.
		// will only confuse the NN
		
		// did opponent randomly win in the next turn
		Square opponentType = (aspect == Square.O ? Square.X : Square.O);
		Square [][] futureBoard = randomMove(resultingBoard, opponentType);
		if (hasWon(futureBoard, opponentType)) {
			return bad;
		}
		
		int sum = getBoardSum(resultingBoard, aspect);
		return sum;
	}

	
	public static Square [][] randomMove(Square [][] board, Square aspect){
		ArrayList<Vector2D> availableMoves = new ArrayList<>();
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board[0].length; j++){
				if (board[i][j] == Square.EMPTY){
					availableMoves.add(new Vector2D(i,j));
				}
			}	
		}
		Random random = new Random();
		int randIndex = random.nextInt(availableMoves.size());
		
		Vector2D moveToDo = availableMoves.get(randIndex);
		Square [][] result = duplicateBoard(board);
		result[moveToDo.row][moveToDo.col] = aspect;
		return result;
	}
	
	static Square whoIsTurn(Square [][] board){
		int countX = 0;
		int countO = 0;
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board[0].length; j++){
				if (board[i][j] == Square.X){
					countX++;
				}
				else if (board[i][j] == Square.O){
					countO++;
				}
			}	
		}
		if (countX == countO){
			return Square.X;
		}
		else if (countX == countO + 1){
			return Square.O;
		}
		return null;
	}
		
}
	