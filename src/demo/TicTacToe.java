package demo;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {

	public static void displayBoard(Square[][] board) {
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == Square.EMPTY)
					System.out.print("- ");
				else
					System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static Square[][] createBoard() {
		Square[][] board = new Square[3][3];
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = Square.EMPTY;
			}
		}
		return board;
	}

	static Square[][] duplicateBoard(Square[][] original) {
		Square[][] duplicate = new Square[3][3];
		for (int i = 0; i < original[0].length; i++) {
			for (int j = 0; j < original[0].length; j++) {
				duplicate[i][j] = original[i][j];
			}
		}
		return duplicate;
	}

	public static Square[][] doValidMove(Square[][] board, Square type, Vector2D destination) {
		Square[][] result = duplicateBoard(board);
		result[destination.row][destination.col] = type;
		return result;
	}

	public static boolean isBoardFull(Square[][] board) {
		int emptySquares = 0;
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == Square.EMPTY) {
					emptySquares++;
				}
			}
		}
		return emptySquares == 0;
	}

	public static boolean isGameOver(Square[][] board) {
		return (hasWon(board, Square.X) || hasWon(board, Square.O) || isBoardFull(board));
	}

	public static boolean hasWon(Square[][] board, Square type) {
		// check vertical
		if (board[0][0].get() == type.get()
				&& board[1][0].get() == type.get()
				&& board[2][0].get() ==type.get())
			return true;
		if (board[0][1].get() == type.get()
				&& board[1][1].get() == type.get()
				&& board[2][1].get() == type.get())
			return true;
		if (board[0][2].get()  == type.get() 
				&& board[1][2].get() == type.get()
				&& board[2][2].get()  == type.get())
			return true;

		// check horizontal
		if (board[0][0].get()  == type.get()
				&& board[0][1].get()  == type.get()
				&& board[0][2].get() == type.get())
			return true;
		if (board[1][0].get()  == type.get()
				&& board[1][1].get()  == type.get()
				&& board[1][2].get()  == type.get())
			return true;
		if (board[2][0].get()  == type.get()
				&& board[2][1].get()  == type.get()
				&& board[2][2].get()  == type.get())
			return true;

		// check diagonal
		if (board[0][0].get()  == type.get()
				&& board[1][1].get()  == type.get()
				&& board[2][2].get()  == type.get())
			return true;
		if (board[0][2].get()  == type.get()
				&& board[1][1].get()  == type.get()
				&& board[2][0].get()  == type.get())
			return true;
		return false;
	}

/*	private static int getBoardSum(Square[][] board, Square type) {
		// 81 is the value of a board where all squares are of the given type.
		// This is just to avoid the sum being negative (if all square of not of
		// the given type). 
		int sum = 81;

		// check vertical
		sum += board[0][0].get() + board[1][0].get() + board[2][0].get();
		sum += board[0][1].get() + board[1][1].get() + board[2][1].get();
		sum += board[0][2].get() + board[1][2].get() + board[2][2].get();

		// check horizontal
		sum += board[0][0].get() + board[0][1].get() + board[0][2].get();
		sum += board[1][0].get() + board[1][1].get() + board[1][2].get();
		sum += board[2][0].get() + board[2][1].get() + board[2][2].get();

		// check diagonal
		sum += board[0][0].get() + board[1][1].get() + board[2][2].get();
		sum += board[0][2].get() + board[1][1].get() + board[0][0].get();

		return sum;
	}*/
	
	
	private static int howGoodWasMove(Square[][] board, Square aspect, Vector2D destination) {
		// If we made a move that did not result in a win,
		// check to see if a move that could result in a win was possible.
		// If true, return a low fitness score
		Square [][] result = doValidMove(board, aspect, destination);
		if (!hasWon(result, aspect)){
			ArrayList<Vector2D> emptySquares = getEmptyCoordinates(board);
			for (Vector2D empty : emptySquares){
				Square [][] alternateBoard = doValidMove(board, aspect, empty);
				if (hasWon(alternateBoard, aspect)){
					return 0; // not a good decision so bad fitness					
				}	
			}
		}
		return 100; // move was fine
	}
	
	
	
	public static ArrayList<Vector2D> getEmptyCoordinates(Square [][] board){
		ArrayList<Vector2D> coords = new ArrayList<>();
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if ( board[i][j] == Square.EMPTY){
					Vector2D c = new Vector2D(i,j);
					coords.add(c);
				}
			}
		}
		return coords;		
	}
	

	public static boolean isMoveValid(Square[][] board, Vector2D destination) {
		return board[destination.row][destination.col] == Square.EMPTY;
	}

	static Vector2D convertIndexToRowCol(int index) {
		// index lies between [0,8] inclusively
		int row = (index) / 3;
		int col = (index) % 3;
		Vector2D c = new Vector2D(row, col);
		return c;
	}


	public static int getBoardFitness(Square[][] board, Square aspect, int destination) {
		Vector2D destinationCoordinate = convertIndexToRowCol(destination);
		// invalid move
		if (!isMoveValid(board, destinationCoordinate)) {
			return 0;
		} 
		else {
			return 100;
			//return howGoodWasMove(board, aspect, destinationCoordinate);
		}
			
		/*
		 * Square [][] resultingBoard = doValidMove(board, aspect,
		 * destinationCoordinate);
		 * 
		 * // did win? if (hasWon(resultingBoard, aspect)) { return winValue; }
		 * 
		 * // TODO need to make sure the board is not already full/ we have
		 * drawn // though, we should handles this else where, like we should
		 * not be // evaluating the neural network when the game is already
		 * complete. // will only confuse the NN
		 * 
		 * // did opponent randomly win in the next turn Square opponentType =
		 * (aspect == Square.O ? Square.X : Square.O); Square [][] futureBoard =
		 * randomMove(resultingBoard, opponentType); if (hasWon(futureBoard,
		 * opponentType)) { return bad; }
		 * 
		 * // int sum = getBoardSum(resultingBoard, aspect); return 100; //
		 * valid move
		 */ }

	public static Square[][] randomMove(Square[][] board, Square aspect) {
		ArrayList<Vector2D> availableMoves = new ArrayList<>();
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == Square.EMPTY) {
					availableMoves.add(new Vector2D(i, j));
				}
			}
		}
		Random random = new Random();
		int randIndex = random.nextInt(availableMoves.size());

		Vector2D moveToDo = availableMoves.get(randIndex);
		Square[][] result = duplicateBoard(board);
		result[moveToDo.row][moveToDo.col] = aspect;
		return result;
	}

	
	public static Square [][] createRandomBoard(){
		Square [][] board;
		do{
			board = createBoard();
			Random random = new Random();
			int numberOfMoves = random.nextInt(9);
			for (int i = 0; i < numberOfMoves; i++){
				Square aspect = whoIsTurn(board); 
				board = randomMove(board, aspect);
			}
		} while(isGameOver(board));
		return board;
	}
	
	public static Square [][] createRandomPlayableBoardXTurn(){
		Square [][] board = createRandomBoard();
		Square aspect = whoIsTurn(board);
		while(aspect !=  Square.X){
			board = createRandomBoard();
			aspect = whoIsTurn(board);
		}
		return board;
	}
	
	public static ArrayList<Square [][]> makeNRandomTrainingBoards(int n){
		ArrayList<Square[][]> trainingBoards = new ArrayList<>();
		for (int i = 0; i <= n; i++){
			Square [][] board = TicTacToe.createRandomPlayableBoardXTurn();
			trainingBoards.add(board);
		}
		return trainingBoards;
	}
	
	static Square whoIsTurn(Square[][] board) {
		int countX = 0;
		int countO = 0;
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == Square.X) {
					countX++;
				} else if (board[i][j] == Square.O) {
					countO++;
				}
			}
		}
		if (countX == countO) {
			return Square.X;
		} else if (countO < countX) {
			return Square.O;
		}
		System.err.println("Should not be null!");
		return null;
	}
	
	public static Square[][] invertBoard(Square[][] board){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(board[i][j] == Square.X){
					board[i][j] = Square.O;
				}
				else if(board[i][j] == Square.O){
					board[i][j] = Square.X;
				}
			}
		}
		return board;
	}
}
